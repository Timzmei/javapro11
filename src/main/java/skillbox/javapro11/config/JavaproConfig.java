package skillbox.javapro11.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.model.entity.Comment;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class JavaproConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true)
        .setSkipNullEnabled(true)
        .setFieldAccessLevel(PRIVATE);
    mapper.createTypeMap(Comment.class, CommentResponse.class).addMappings(m -> m.map(com -> com.getPost().getId(), CommentResponse::setPostId));
    return mapper;
  }
}
