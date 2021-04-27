package skillbox.javapro11.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillbox.javapro11.enums.ReadStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by timurg on 28.04.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dialog {
    // Нужно разобраться, в сваггере и в структуре БД (та, что в картинке) сущность Dialog различные
    // Пока прописал все возможные варианты.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(name = "unread_count", nullable = false)
    private int unreadCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message", nullable = false)
    private Message message;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "invite_code", nullable = false)
    private String inviteCode;
}
