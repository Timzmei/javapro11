package skillbox.javapro11.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse extends ResponseData {
    private long id;
    private String tag;
}
