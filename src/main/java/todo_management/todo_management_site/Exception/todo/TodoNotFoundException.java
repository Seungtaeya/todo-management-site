package todo_management.todo_management_site.Exception.todo;

public class TodoNotFoundException extends RuntimeException{

    public TodoNotFoundException(String message) {
        super(message);
    }
}
