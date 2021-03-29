package skillbox.javapro11.api.request;

import lombok.Data;
import skillbox.javapro11.enums.NotificationTypeCode;

@Data
public class NotificationsRequest {

    private NotificationTypeCode notification_type;
    private Boolean enable;
}
