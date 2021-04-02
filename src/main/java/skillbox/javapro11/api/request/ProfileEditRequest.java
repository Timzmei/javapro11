package skillbox.javapro11.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillbox.javapro11.enums.PermissionMessage;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditRequest {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private String phone;

    @JsonProperty("photo_id")
    private String photo;

    private String about;

    @JsonProperty("town_id")
    private String town;

    @JsonProperty("country_id")
    private String country;

    private PermissionMessage permissionMessage;
}

