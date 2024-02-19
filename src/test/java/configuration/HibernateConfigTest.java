package configuration;

import lombok.Getter;
import org.hibernate.SessionFactory;

public class HibernateConfigTest {

    @Getter
    private SessionFactory sessionFactory;

    {
        var config = new org.hibernate.cfg.Configuration();
        config.configure();
        sessionFactory = config.buildSessionFactory();
    }
}
