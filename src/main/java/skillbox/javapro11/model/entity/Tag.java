package skillbox.javapro11.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Artem on 21.04.2021.
 */

@Entity
@Data
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    private String tag;
}
