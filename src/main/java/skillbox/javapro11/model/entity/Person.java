package skillbox.javapro11.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.enums.PermissionMessage;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "birth_date")
    private LocalDate birthday;

    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    private String photo;

    @Column(length = 2048)
    private String about;

    private String town;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Column(name = "messages_permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionMessage permissionMessage;

    @Column(name = "last_online_time", nullable = false)
    private LocalTime lastTimeOnline;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    public Person(RegisterRequest registerRequest) {
        this.email = registerRequest.getEmail();
        this.password = registerRequest.getEmail();
        this.firstName = registerRequest.getFirstName();
        this.lastName = registerRequest.getLastName();
        this.registrationDate = LocalDateTime.now();
    }
}
