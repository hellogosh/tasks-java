package training.tracker.db;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskDatabaseTest {

    @Test
    @Tag("db")
    void shouldReadOpenTaskCountFromDatabase() throws SQLException {
        try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:tracker");
            Statement statement = connection.createStatement()) {
                statement.execute("create table tasks (task_key varchar(20), status varchar(20))");
                statement.execute("insert into tasks values ('QA-1', 'OPEN')");
            statement.execute("insert into tasks values ('QA-2', 'DONE')");
            statement.execute("insert into tasks values ('QA-3', 'OPEN')" );
            statement.execute("insert into tasks values ('QA-4', 'IN_PROGRESS')" );

            ResultSet resultSet = statement.executeQuery(
                    "select count(*) as total from tasks where status = 'OPEN' ");
            resultSet.next();

            assertThat(resultSet.getInt("total")).isEqualTo(2);

            ResultSet status = statement.executeQuery("select status from tasks where task_key = 'QA-1'");

            assertThat(status.next()).isEqualTo(true);
            assertThat(status.getString("status")).isEqualTo("OPEN");

            ResultSet existing = statement.executeQuery("select task_key from tasks where task_key = 'QA-2'");

            assertThat(existing.next()).isEqualTo(true);



        }

    }
}
