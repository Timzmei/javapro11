package skillbox.javapro11.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.LogoutResponse;
import skillbox.javapro11.service.ConvertTimeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {

        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setTimestamp(ConvertTimeService.convertLocalDateTimeToLong(LocalDateTime.now()));
        responseData.setData(new LogoutResponse("ok"));

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writeValueAsString(responseData));
        response.getWriter().flush();

    }
}