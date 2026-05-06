package todo_management.todo_management_site.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import todo_management.todo_management_site.Exception.member.DuplicatedMemberIdException;
import todo_management.todo_management_site.Exception.member.InvalidMemberRequestException;
import todo_management.todo_management_site.Exception.member.MemberNotFoundException;
import todo_management.todo_management_site.Exception.todo.InvalidTodoRequestException;
import todo_management.todo_management_site.Exception.todo.TodoForbiddenException;
import todo_management.todo_management_site.Exception.todo.TodoNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerTodoForbidden(TodoForbiddenException todoForbiddenException) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "TODO_FORBIDDEN",
                todoForbiddenException.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(InvalidTodoRequestException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidTodoRequest(InvalidTodoRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "INVALID_TODO_REQUEST",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerTodoNotFound(TodoNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "TODO_NOT_FOUND",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFount(MemberNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                "MEMBER_NOT_FOUND",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidMemberRequestException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidMemberRequest(InvalidMemberRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "INVALID_MEMBER_REQUEST",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicatedMemberIdException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicatedMemberId(DuplicatedMemberIdException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "DUPLICATE_MEMBER_ID",
                e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public void handlerException(Exception e) {
        System.out.println("e.getMessage() = " + e.getMessage());
    }
}
