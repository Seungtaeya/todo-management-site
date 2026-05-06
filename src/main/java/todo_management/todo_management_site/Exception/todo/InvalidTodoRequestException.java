package todo_management.todo_management_site.Exception.todo;

public class InvalidTodoRequestException extends RuntimeException{

    public InvalidTodoRequestException(String message) {
        super(message);
    }
}
