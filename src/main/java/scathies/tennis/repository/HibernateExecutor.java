package scathies.tennis.repository;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class HibernateExecutor {

    private final SessionFactory sessionFactory;

    public <T> T executeQuery(Function<Session, T> function) {
        T result;
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = function.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    public void execute(Consumer<Session> consumer) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            consumer.accept(session);
            session.getTransaction().commit();
        }
    }

    @PreDestroy
    public void destroy() {
        sessionFactory.close();
    }
}
