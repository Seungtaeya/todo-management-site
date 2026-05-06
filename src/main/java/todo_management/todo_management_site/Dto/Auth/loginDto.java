package todo_management.todo_management_site.Dto.Auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class loginDto {

    Long id;
    String name;
    String loginId;
    String password;
}
