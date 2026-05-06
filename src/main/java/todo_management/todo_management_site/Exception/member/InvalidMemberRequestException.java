package todo_management.todo_management_site.Exception.member;

public class InvalidMemberRequestException extends RuntimeException{
    public InvalidMemberRequestException(String message) {
        super(message);
    }
}
