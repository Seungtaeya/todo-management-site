package todo_management.todo_management_site.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo_management.todo_management_site.Dto.Todo.TodoDto;
import todo_management.todo_management_site.Entity.TodoStatus;
import todo_management.todo_management_site.Service.TodoService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TodoApiController {

    private final TodoService todoService;

    @GetMapping("/api/todos/my")
    public ResponseEntity<List<TodoDto>> APiTodos(HttpSession session) {
        Long loginId = getLoginId(session);

        if(loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TodoDto> todos = todoService.findTodosByLoginId(loginId);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/api/todos/{status}")
    public ResponseEntity<List<TodoDto>> ApiGetTodos(HttpSession session, @PathVariable TodoStatus status) {
        System.out.println("status = " + status);
        Long loginId = getLoginId(session);
        List<TodoDto> todoStatus = todoService.getTodoStatus(loginId, status);
        System.out.println("todoStatus.size() = " + todoStatus.size());

        return ResponseEntity.ok().body(todoStatus);
    }

    @GetMapping("/api/todos/search")
    public ResponseEntity<List<TodoDto>> apiSearch(@RequestParam String keyword,
                                                   @RequestParam String select, HttpSession session) {
        System.out.println("isSelected = " + select);
        System.out.println("keyWord = " + keyword);
        Long id = getLoginId(session);

        List<TodoDto> todos = todoService.todoSearch(id, keyword, select);
        return ResponseEntity.ok(todos);
    }

    @PatchMapping("/api/todos/{todoId}")
    public ResponseEntity<TodoDto> ApiTodoPatch(@PathVariable Long todoId, @RequestBody TodoDto dto, HttpSession session) {
        Long loginMember = getLoginId(session);

        TodoDto updateTodo = todoService.PatchTodo(loginMember, todoId, dto);

        return ResponseEntity.ok(updateTodo);
    }

    @DeleteMapping("/api/todos/{todoId}")
    public ResponseEntity<TodoDto> ApiDeleteTodo(@PathVariable Long todoId, HttpSession session) {
        Long loginMember = getLoginId(session);

        todoService.DeleteTodo(todoId, loginMember);
        return ResponseEntity.ok().build();
    }

    private static Long getLoginId(HttpSession session) {
        return (Long) session.getAttribute("loginMember");
    }
}
