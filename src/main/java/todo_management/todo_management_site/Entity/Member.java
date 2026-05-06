package todo_management.todo_management_site.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime created;

    public Member(String loginID, String password, String name) {
        this.loginId = loginID;
        this.password = password;
        this.name = name;
    }

    @PrePersist
    private void created() {
        this.created = LocalDateTime.now();
    }
}
