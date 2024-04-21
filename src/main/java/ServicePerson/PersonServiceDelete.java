
package ServicePerson;

import JDBC.DBConnect;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Сервис по удалению книги
public class PersonServiceDelete {

    private static PersonServiceDelete personServiceDelete;


    public static PersonServiceDelete getInstance() {
        if (personServiceDelete == null) {
            synchronized (PersonServiceDelete.class) {
                if (personServiceDelete == null)
                    personServiceDelete = new PersonServiceDelete();
            }


        }
        return personServiceDelete;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InputStream inputStream = req.getInputStream(); // а чего его не закрываем?
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        String jsonData = builder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        int id = Integer.parseInt(jsonObject.getString("id"));

        try(Connection connection = new DBConnect().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Person\" WHERE id=?");
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Record with id " + id + " deleted successfully");
            } else if (rowsAffected == 0) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Requested  " + id + " one does not exist, or has already been deleted");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Error deleting record with id " + id, ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
