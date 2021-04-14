package skillbox.javapro11.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person person;

    @Column(columnDefinition = "varchar(255)")
    @NotNull
    private String title;

    @Column(name = "post_text", columnDefinition = "text")
    @NotNull
    private String text;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy="post")
    private List<PostLike> postLikeList;

    @OneToMany(mappedBy="post")
    private List<Comment> comments;


}
