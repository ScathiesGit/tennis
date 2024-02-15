package scathies.tennis.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class HibernateExecutor {

    private static final SessionFactory SESSION_FACTORY;

    static {
        var config = new Configuration();
        config.configure();
        SESSION_FACTORY = config.buildSessionFactory();
    }

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
