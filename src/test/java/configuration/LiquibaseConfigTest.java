package configuration;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LiquibaseConfigTest {

    private static String urlDb;
    private static String username;
    private static String password;
    private static String changeLogFile;

    static {
        var prop = new Properties();
        try {
            prop.load(
                    LiquibaseConfigTest.class.getResourceAsStream("/liquibase_test.properties")
            );
            urlDb = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            changeLogFile = prop.getProperty("changeLogFile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static void executeSqlScripts() {
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));
            liquibase.update("");
        }
    }
}
