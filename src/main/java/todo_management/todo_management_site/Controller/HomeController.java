package todo_management.todo_management_site.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todo_management.todo_management_site.Dto.Auth.RegisterDto;
import todo_management.todo_management_site.Dto.Auth.loginDto;
import todo_management.todo_management_site.Dto.Todo.TodoDto;
import todo_management.todo_management_site.Exception.member.DuplicatedMemberIdException;
import todo_management.todo_management_site.Exception.member.InvalidMemberRequestException;
import todo_management.todo_management_site.Exception.todo.InvalidTodoRequestException;
import todo_management.todo_management_site.Exception.todo.TodoForbiddenException;
import todo_management.todo_management_site.Exception.todo.TodoNotFoundException;
import todo_management.todo_management_site.Service.MemberService;
import todo_management.todo_management_site.Service.TodoService;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final TodoService todoService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        model.addAttribute("loginMemberId", session.getAttribute("loginMember"));
        return "home";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "members/registerForm";
    }

    @PostMapping("/register")
    public String register(RegisterDto dto, RedirectAttributes redirectAttributes) {
        System.out.println("dto.toString() = " + dto.toString());
        try {
            memberService.register(dto);
        } catch (DuplicatedMemberIdException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/register";
        } catch (InvalidMemberRequestException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(loginDto dto, RedirectAttributes model, HttpServletRequest request) {
        loginDto login = memberService.login(dto);

        if(login == null) {
            String msg = "아이디 또는 비밀번호가 일치하지 않습니다.";
            model.addFlashAttribute("msg", msg);
            return "redirect:/login";
        }

//        request.changeSessionId();

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", login.getId());
        session.setAttribute("loginMemberName", login.getName());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/Todo/new")
    public String TodoCreateForm(Model model, HttpSession session) {

        if(session.getAttribute("loginMember") == null) {
            return "members/loginForm";
        }
        model.addAttribute("today", LocalDate.now());
        return "Todo/createTodoForm";
    }

    @PostMapping("/Todo/new")
    public String TodoCreate(TodoDto dto,HttpSession session, RedirectAttributes redirectAttributes) {
        Long loginMember = (Long) session.getAttribute("loginMember");
        try {
            todoService.CreateTodo(dto, loginMember);
        } catch (InvalidTodoRequestException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/Todo/new";
        }
        return "redirect:/";
    }

    @GetMapping("/todo/detail/{todoId}")
    public String TodoDetail(@PathVariable Long todoId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Long loginMember = (Long) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/login";
        }

        try {
            TodoDto todo = todoService.getTodo(todoId, loginMember);
            model.addAttribute("todo", todo);
            return "Todo/TodoDetail";
        } catch (TodoForbiddenException e ) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        } catch (TodoNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }
}
