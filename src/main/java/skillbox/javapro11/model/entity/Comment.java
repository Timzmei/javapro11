package skillbox.javapro11.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(name = "parent_id", columnDefinition = "int")
    private long parentId;

    @Column(name = "comment_text", columnDefinition = "text")
    @NotNull
    private String commentText;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotNull
    private long time;

    @Column(name = "author_id")
    @NotNull
    private long authorId;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;
}
