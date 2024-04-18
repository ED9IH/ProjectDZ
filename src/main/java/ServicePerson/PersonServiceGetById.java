package ServicePerson;

import JDBC.DBConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Person;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonServiceGetById extends HttpServlet {
    private int id;
    private String json;

    private static PersonServiceGetById personServiceGetById;

    public PersonServiceGetById() {
    }

    public static PersonServiceGetById getInstance(){
        if(personServiceGetById==null){
            personServiceGetById=new PersonServiceGetById();
        }
        return personServiceGetById;
    }

//Сервис по получению Person по id

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        String nameJson = jsonObject.getString("id");
        int id = Integer.parseInt(nameJson);

        try (Connection connection = new DBConnect().getConnection()) {

            String selectQuery = "SELECT * FROM public.\"Person\" WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String json;
            while (resultSet.next()) {
                Person person = Person.getInstance();
                person.setId(Integer.parseInt(resultSet.getString("id")));
                person.setName(resultSet.getString("name"));
                ObjectMapper objectMapper = new ObjectMapper();
                json = objectMapper.writeValueAsString(person);
                resp.getWriter().write(json);
            }


            resultSet.close();
            preparedStatement.close();


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
