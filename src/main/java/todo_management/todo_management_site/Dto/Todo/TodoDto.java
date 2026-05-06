package todo_management.todo_management_site.Dto.Todo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo_management.todo_management_site.Entity.Member;
import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TodoDto {

    Long id;
    String title;
    String content;
    private TodoStatus status;
    private LocalDate dueDate;
    LocalDate createdAt;
    LocalDate updatedAt;

    public TodoDto(Long id, String title, String content, TodoStatus status, LocalDate dueDate, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TodoDto from(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getStatus(),
                todo.getDueDate(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }

    public Todo toEntity(Member member) {
        return new Todo(member,title,content,status,dueDate);
    }

    @Override
    public String toString() {
        return "TodoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
