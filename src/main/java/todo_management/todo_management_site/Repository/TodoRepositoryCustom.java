package todo_management.todo_management_site.Repository;

import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;

import java.util.List;

public interface TodoRepositoryCustom {

    List<Todo> searchTodos(Long memberId, String keyword, TodoStatus todoStatus);
}
