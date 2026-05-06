package todo_management.todo_management_site.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo_management.todo_management_site.Entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {

//    Optional<Member> findByLoginId(String loginId);
    Member findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
