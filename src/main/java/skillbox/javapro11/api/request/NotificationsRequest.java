package skillbox.javapro11.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationsRequest {

    @JsonProperty("notification_type")
    private NotificationTypeCode notificationTypeCode;
    private Boolean enable;
}
