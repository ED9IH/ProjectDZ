package ServicePerson;

import JDBC.DBConnect;
import entity.Person;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Сервис по добавления человека в бд
public class PersonServiceSave {

    private static PersonServiceSave personServiceSave;

    public PersonServiceSave() {
    }

    public static PersonServiceSave getInstance() {
        if (personServiceSave == null) {
            personServiceSave = new PersonServiceSave();
        }
        return personServiceSave;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStream = req.getInputStream();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        String jsonData = builder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        Person person = Person.getInstance();
        person.setName(jsonObject.getString("name"));
        try (Connection connection = new DBConnect().getConnection()) {

            String insertQuery = "INSERT INTO public.\"Person\" (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, person.getName());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resp.getWriter().write("ADD Person");
            }

            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
