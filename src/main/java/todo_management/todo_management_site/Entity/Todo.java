package todo_management.todo_management_site.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    @Column(name = "due_Date", nullable = false)
    private LocalDate dueDate;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public Todo(Member member, String title, String content, TodoStatus status, LocalDate dueDate) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = status;
        this.dueDate = dueDate;
    }

    public void update(String title, String content, TodoStatus status, LocalDate dueDate) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.dueDate = dueDate;
        this.updatedAt = LocalDate.now();
    }

    @PrePersist
    private void created(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
