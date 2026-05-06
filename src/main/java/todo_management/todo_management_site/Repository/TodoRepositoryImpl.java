package todo_management.todo_management_site.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todo_management.todo_management_site.Entity.Todo;
import todo_management.todo_management_site.Entity.TodoStatus;

import java.util.List;

import static todo_management.todo_management_site.Entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> searchTodos(Long memberId, String keyword, TodoStatus todoStatus) {
        return jpaQueryFactory.selectFrom(todo)
                .where(
                        memberIdEq(memberId),
                        keywordContains(keyword),
                        todoStatusEq(todoStatus)
                )
                .fetch();
    }

    private BooleanExpression todoStatusEq(TodoStatus todoStatus) {
        return todoStatus != null ? todo.status.eq(todoStatus) : null;
    }

    private BooleanExpression keywordContains(String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }

        return  todo.title.contains(keyword).or(todo.content.contains(keyword));
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? todo.member.id.eq(memberId) : null;
    }
}
