package todo_management.todo_management_site.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todo_management.todo_management_site.Dto.Auth.RegisterDto;
import todo_management.todo_management_site.Dto.Todo.TodoDto;
import todo_management.todo_management_site.Entity.Member;
import todo_management.todo_management_site.Entity.TodoStatus;
import todo_management.todo_management_site.Repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class TodoServiceTest {

    @Autowired TodoService todoService;
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void create() throws Exception {
        //given
        RegisterDto registerDto = new RegisterDto("test","123","테스트");
        memberService.register(registerDto);
        Member member = memberRepository.findByLoginId("test");
        TodoDto todoDto = new TodoDto();

        todoDto.setTitle("TestTitle");
        todoDto.setContent("Content");
        todoDto.setStatus(TodoStatus.IN_PROGRESS);
        todoDto.setDueDate(LocalDate.now());
        //when
        todoService.CreateTodo(todoDto, member.getId());
        List<TodoDto> todoListByLoginId = todoService.findTodosByLoginId(member.getId());
        //then
        assertThat(todoListByLoginId.size() == 1);
        assertThat(todoListByLoginId.get(0).getTitle().equals("TestTitle"));
    }

    @Test
    public void search() throws Exception {
        //given
        RegisterDto registerDto = new RegisterDto("test","123","테스트");
        memberService.register(registerDto);

        for(int i = 0; i < 20; i++) {
            TodoDto todoDto = new TodoDto();
            todoDto.setTitle("test" + i);
            todoDto.setContent("test" + i);
            todoDto.setStatus(TodoStatus.TODO);
            todoDto.setDueDate(LocalDate.now());
            todoService.CreateTodo(todoDto,1L);
        }
        //when
        List<TodoDto> todos = todoService.todoSearch(1L, "test");

        //then
        assertThat(todos.size()).isEqualTo(20);
    }

//    @Test
//    public void update() throws Exception {
//        RegisterDto registerDto = new RegisterDto("test","123","테스트");
//        memberService.register(registerDto);
//        Member member = memberRepository.findByLoginId("test");
//        TodoDto todoDto = new TodoDto();
//
//        todoDto.setTitle("TestTitle");
//        todoDto.setContent("Content");
//        todoDto.setStatus(TodoStatus.IN_PROGRESS);
//        todoDto.setDueDate(LocalDate.now());
//        todoService.CreateTodo(todoDto, member.getId());
//        TodoDto update = new TodoDto();
//        update.setTitle("updateTodo");
//        update.setContent("updateContent");
//        update.setDueDate(LocalDate.of(2026,5,2));
//        //when
//        TodoDto todoDto1 = todoService.PatchTodo(1L, update);
//        TodoDto todo = todoService.getTodo(1L);
//        //then
//        assertThat(todoDto1.getTitle().equals(todo.getTitle()));
//        assertThat(todoDto1.getContent().equals(todo.getContent()));
//    }
}