package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skillbox.javapro11.enums.PermissionMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse extends ResponseData {

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

    private String city;

    private String country;

    @JsonProperty(value = "messages_permission")
    private PermissionMessage messagesPermission;

    @JsonProperty(value = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @JsonProperty(value = "is_blocked")
    private boolean isBlocked;

    private String token;
}
