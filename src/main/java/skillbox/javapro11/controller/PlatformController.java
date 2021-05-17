package skillbox.javapro11.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skillbox.javapro11.api.response.CommonListResponse;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/platform")
public class PlatformController {
    @GetMapping("/languages")
    public CommonListResponse getLanguages() {
        return new CommonListResponse("", LocalDateTime.now(), null);
    }
}
