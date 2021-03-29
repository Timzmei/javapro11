package skillbox.javapro11.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import skillbox.javapro11.model.entity.Person;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostConstruct
    //При запуске приложения создает и кодирует секретный код
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(generateSecretKey(64));
    }

    public byte[] generateSecretKey(int length) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(length, random).toString(32).getBytes(StandardCharsets.UTF_8);
    }

    public String createToken(Person person) {

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        //Действие токена равно сутки
        calendar.add(Calendar.DATE, 1);
        Date validity = calendar.getTime();

        return Jwts.builder()
                .claim("email", person.getEmail())
                .claim("firstName", person.getFirstName())
                .claim("lastName", person.getLastName())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUserEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email").toString();
    }

    public String checkFormatToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            //В запросе к токену добавляеться в начале "Bearer " это стандарт, поэтому получить токен с 7-го символа
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean checkTokenIsExist(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}