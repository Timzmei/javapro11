package skillbox.javapro11.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillbox.javapro11.enums.ReadStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by timurg on 27.04.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Person recipient;

    @Column(name = "message_text", nullable = false)
    private String text;

    @Column(name = "read_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;
}
