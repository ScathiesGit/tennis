package scathies.tennis.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class HibernateExecutor {

    private final SessionFactory SESSION_FACTORY;

    public <T> T executeQuery(Function<Session, T> function) {
        T result;
        try (var session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            result = function.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    public void execute(Consumer<Session> consumer) {
        try (var session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            consumer.accept(session);
            session.getTransaction().commit();
        }
    }
}
