package skillbox.javapro11.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import skillbox.javapro11.security.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LogoutSuccessHandlerImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {

        String token = jwtTokenProvider.checkFormatToken(request);
        if (token != null) {
            jwtTokenProvider.deleteToken(token);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
}