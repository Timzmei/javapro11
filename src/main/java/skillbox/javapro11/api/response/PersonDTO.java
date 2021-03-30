package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import skillbox.javapro11.enums.PermissionMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class PersonDTO implements ResponseData {

    private long id;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "reg_date")
    private LocalDateTime registrationDate;

    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;

    private String email;

    private String phone;

    private String photo;

    private String about;

    private LocationOrLanguageDTO city;

    private LocationOrLanguageDTO country;

    @JsonProperty(value = "messages_permission")
    private PermissionMessage messagesPermission;

    @JsonProperty(value = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @JsonProperty(value = "is_blocked")
    private boolean isBlocked;

    private String token;
}
