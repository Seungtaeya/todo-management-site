package todo_management.todo_management_site.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todo_management.todo_management_site.Dto.Auth.RegisterDto;
import todo_management.todo_management_site.Dto.Todo.TodoDto;
import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;
import todo_management.todo_management_site.Service.MemberService;
import todo_management.todo_management_site.Service.TodoService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryImplTest {

    @Autowired TodoRepositoryCustom todoRepositoryCustom;
    @Autowired MemberService memberService;
    @Autowired
    TodoService todoService;
    @Test
    public void search() throws Exception {
        //given
        RegisterDto registerDto = new RegisterDto("test","123","테스트");
        memberService.register(registerDto);

        for(int i = 0; i < 20; i++) {
            TodoDto todoDto = new TodoDto();
            todoDto.setTitle("test" + i);
            todoDto.setContent("test" + i+1);
            todoDto.setStatus(TodoStatus.TODO);
            todoDto.setDueDate(LocalDate.now());
            todoService.CreateTodo(todoDto,1L);
        }

        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("test99");
        todoDto.setContent("test");
        todoDto.setStatus(TodoStatus.DONE);
        todoDto.setDueDate(LocalDate.now());
        todoService.CreateTodo(todoDto,1L);
        //when

        List<Todo> searchTitleTodos = todoRepositoryCustom.searchTodos(1L, "test", null);
        List<Todo> searchStatusTodos = todoRepositoryCustom.searchTodos(1L, null, TodoStatus.DONE);

        //then
        assertThat(searchTitleTodos.size()).isEqualTo(21);
        assertThat(searchStatusTodos.size()).isEqualTo(1);

    }
}