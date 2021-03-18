package skillbox.javapro11.api.request;

import lombok.Data;

@Data
public class NotificationsRequest {

    private NotificationTypeCode notificationTypeCode;
    private Boolean enable;
}
