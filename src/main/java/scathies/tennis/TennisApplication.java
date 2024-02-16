package scathies.tennis;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
public class TennisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisApplication.class, args);
    }
}
