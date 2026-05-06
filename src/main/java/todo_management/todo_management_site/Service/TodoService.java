package todo_management.todo_management_site.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo_management.todo_management_site.Dto.Todo.TodoDto;
import todo_management.todo_management_site.Entity.Member;
import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;
import todo_management.todo_management_site.Exception.member.MemberNotFoundException;
import todo_management.todo_management_site.Exception.todo.InvalidTodoRequestException;
import todo_management.todo_management_site.Exception.todo.TodoForbiddenException;
import todo_management.todo_management_site.Exception.todo.TodoNotFoundException;
import todo_management.todo_management_site.Repository.MemberRepository;
import todo_management.todo_management_site.Repository.TodoRepository;
import todo_management.todo_management_site.Repository.TodoRepositoryCustom;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;

    private final MemberRepository memberRepository;
    private final TodoRepositoryCustom todoRepositoryCustom;

    public List<TodoDto> findTodosByLoginId(Long loginId) {

        List<Todo> todos = todoRepository.findByMemberId(loginId);
        return todos.stream().map(TodoDto::from).toList();
    }

    public TodoDto getTodo(Long todoId, Long loginMember) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("찾으시는 게시글이 없습니다."));

        ForbiddenCheck(todo, loginMember, "본인의 할 일만 조회 가능합니다.");

        return TodoDto.from(todo);
    }

    public List<TodoDto> getTodoStatus(Long loginId, TodoStatus status) {
        if(status == TodoStatus.all) {
            return findTodosByLoginId(loginId);
        }
        List<Todo> todos = todoRepository.findByMemberIdAndStatus(loginId, status);

        return todos.stream().map(TodoDto::from).toList();
    }

    public List<TodoDto> todoSearch(Long id, String keyWord, String select) {
        if(keyWord.isBlank() || keyWord == null) {
            List<Todo> todos = todoRepository.findByMemberId(id);
            return todos.stream().map(TodoDto::from).toList();
        }

        if(select.equals("title")) {
            List<Todo> todos = todoRepository.findByMemberIdAndTitleContaining(id, keyWord);
            return todos.stream().map(TodoDto::from).toList();
        }

        List<Todo> todos = todoRepositoryCustom.searchTodos(id, keyWord, null);
        return todos.stream().map(TodoDto::from).toList();

    }

    @Transactional
    public void CreateTodo(TodoDto dto, Long loginId) {
        Member member = memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if(dto.getTitle().isEmpty() || dto.getContent().isEmpty() || dto.getStatus() == null) {
            throw new InvalidTodoRequestException("제목 내용 상태를 전부 작성 해주셔야 합니다.");
        }
        System.out.println("dto.getDueDate() = " + dto.getDueDate());
        Todo todo = dto.toEntity(member);

        System.out.println("todo.getDueDate() = " + todo.getDueDate());
        todoRepository.save(todo);

    }

    @Transactional
    public TodoDto PatchTodo(Long loginMember, Long todoId, TodoDto dto) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("찾으시는 게시글이 없습니다."));

        ForbiddenCheck(todo, loginMember, "해당 할 일에 대한 수정 권한이 없습니다.");

        todo.update(dto.getTitle(), dto.getContent(), dto.getStatus(), dto.getDueDate());
        return TodoDto.from(todo);
    }

    @Transactional
    public void DeleteTodo(Long todoId, Long loginMember) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("찾으시는 게시글이 없습니다."));

        ForbiddenCheck(todo, loginMember, "본인의 할 일만 삭제 가능합니다.");

        todoRepository.delete(todo);
    }

    private static void ForbiddenCheck(Todo todo, Long loginMember, String message) {
        if (!todo.getMember().getId().equals(loginMember)) {
            throw new TodoForbiddenException(message);
        }
    }
}
