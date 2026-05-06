package todo_management.todo_management_site.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todo_management.todo_management_site.Dto.Auth.RegisterDto;
import todo_management.todo_management_site.Dto.Auth.loginDto;
import todo_management.todo_management_site.Entity.Member;
import todo_management.todo_management_site.Exception.member.DuplicatedMemberIdException;
import todo_management.todo_management_site.Exception.member.InvalidMemberRequestException;
import todo_management.todo_management_site.Repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long MemberCount() {
        return memberRepository.findAll().stream().count();
    }


    @Transactional
    public void register(RegisterDto dto) {

        if(dto.getLoginId().isBlank() || dto.getPassword().isBlank() || dto.getName().isBlank()) {
           throw new InvalidMemberRequestException("항목을 전부 입력 해주세요.");
        }

        boolean existsByLoginId = memberRepository.existsByLoginId(dto.getLoginId());

        if(existsByLoginId) {
            throw new DuplicatedMemberIdException("이미 존재하는 아이디 입니다.");
        }

        String rawPassword = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member member = new Member(dto.getLoginId(),encodedPassword, dto.getName());


        memberRepository.save(member);
    }

    public loginDto login(loginDto dto) {
        Member member = memberRepository.findByLoginId(dto.getLoginId());
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
        loginDto loginDto = new loginDto();

        if(member == null )
            return null;

        boolean matches = passwordEncoder.matches(dto.getPassword(), member.getPassword());

        if(!matches)
            return null;

        loginDto.setId(member.getId());
        loginDto.setName(member.getName());
        return loginDto;
    }
}
