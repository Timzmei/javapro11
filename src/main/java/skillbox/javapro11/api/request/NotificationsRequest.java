package skillbox.javapro11.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import skillbox.javapro11.enums.NotificationTypeCode;

@Data
@AllArgsConstructor
public class NotificationsRequest {

    @JsonProperty("notification_type")
    private NotificationTypeCode notificationTypeCode;
    private Boolean enable;
}
