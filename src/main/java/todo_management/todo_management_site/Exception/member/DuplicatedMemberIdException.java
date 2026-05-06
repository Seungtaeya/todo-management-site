package todo_management.todo_management_site.Exception.member;

public class DuplicatedMemberIdException extends RuntimeException{
    public DuplicatedMemberIdException(String message) {
        super(message);
    }
}
