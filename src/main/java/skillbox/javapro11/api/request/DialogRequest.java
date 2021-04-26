package skillbox.javapro11.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by timur_guliev on 27.04.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogRequest {

    @JsonProperty("user_ids")
    private int[] userIds;
    private String link;

    @JsonProperty("message_text")
    private String messageText;
    private long ts;
    private int pts;

    @JsonProperty("preview_length")
    private int previewLength;
    private int onlines;

    @JsonProperty("events_limit")
    private int eventsLimit;

    @JsonProperty("msgs_limit")
    private int msgsLimit;

    @JsonProperty("max_msg_id")
    private int maxMsgId;

}
