package skillbox.javapro11.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Created by Sizenko Egor on 02.04.2021.
 */

@Getter
public class PostRequest {
	private String title;
	@JsonProperty("post_text")
	private String text;
}
