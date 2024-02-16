package scathies.tennis.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        var config = new org.hibernate.cfg.Configuration();
        config.configure();
        return config.buildSessionFactory();
    }
}
