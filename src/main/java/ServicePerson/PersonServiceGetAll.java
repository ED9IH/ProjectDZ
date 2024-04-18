package ServicePerson;

import DTO.PersonDTO;
import JDBC.DBConnect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Сервис по получениею всех книг
public class PersonServiceGetAll {

    private static PersonServiceGetAll personServiceGetAll;

    public PersonServiceGetAll() {
    }


    public static PersonServiceGetAll getInstance() {
        if (personServiceGetAll == null) {
            synchronized (PersonServiceGetAll.class) {
                if (personServiceGetAll == null)
                    personServiceGetAll = new PersonServiceGetAll();
            }
        }
        return personServiceGetAll;


    }

    public static List<String> getAll() {
        PersonDTO personDTO = new PersonDTO();
        List<String> json = new ArrayList<>();

        try (Connection connection = new DBConnect().getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT t.*FROM public.\"Person\" t");

            while (resultSet.next()) {
                Person person = Person.getInstance();
                person.setId(Integer.parseInt(resultSet.getString("id")));
                person.setName(resultSet.getString("name"));
                ObjectMapper mapper = new ObjectMapper();
                json.add(mapper.writeValueAsString(personDTO.convertPersonDTO(person)));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String result = getAll().stream().map(Object::toString).collect(Collectors.joining());

        resp.getWriter().write(result);


    }

}
