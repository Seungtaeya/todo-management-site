package todo_management.todo_management_site.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todo_management.todo_management_site.Dto.Auth.RegisterDto;
import todo_management.todo_management_site.Dto.Auth.loginDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    void register() {


        RegisterDto dto = new RegisterDto();
        dto.setLoginId("case1");
        dto.setPassword("pw");
        dto.setName("케이스");

        memberService.register(dto);
        Long l = memberService.MemberCount();
        assertThat(l == 1);

        RegisterDto dto2 = new RegisterDto();
        dto.setLoginId("case2");
        dto.setPassword("pw");
        dto.setName("케이스2");
        memberService.register(dto2);


        assertThat(memberService.MemberCount() != 1);
        assertThat(memberService.MemberCount() == 2);
    }

    @Test
    void login() {

        RegisterDto dto = new RegisterDto();
        dto.setLoginId("case1");
        dto.setPassword("pw");
        dto.setName("케이스");

        memberService.register(dto);

        RegisterDto dto2 = new RegisterDto();
        dto.setLoginId("case2");
        dto.setPassword("pw");
        dto.setName("케이스2");
        memberService.register(dto2);

        loginDto loginDto = new loginDto();
        loginDto.setLoginId("case1");
        loginDto.setPassword("pw");

        assertThat(memberService.login(loginDto) != null);
        loginDto.setLoginId("case1");
        loginDto.setPassword("pw1");
        assertThat(memberService.login(loginDto) == null);
    }
}