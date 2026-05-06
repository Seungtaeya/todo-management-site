package todo_management.todo_management_site.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByMemberId(Long loginId);

    List<Todo> findByMemberIdAndStatus(Long loginId, TodoStatus status);


    List<Todo> findByMemberIdAndTitleContaining(Long memberId, String title);
}
