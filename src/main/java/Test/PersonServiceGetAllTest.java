package Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import ServicePerson.PersonServiceGetAll;
import org.junit.Test;

public class PersonServiceGetAllTest {

    @Test
    public void testGetAll() throws Exception {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("name")).thenReturn("John Doe");

        PersonServiceGetAll yourClass = new PersonServiceGetAll();
        List<String> jsonList = yourClass.getAll();

        // Проверка, что список не пустой
        assertFalse(jsonList.isEmpty());

    }
}
