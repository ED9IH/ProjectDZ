package ServicePerson;

import JDBC.DBConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Book;
import entity.Person;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;

public class PersonServiceGetPersonBookBy {

    public static PersonServiceGetPersonBookBy personServiceGetPersonBookBy;

    public PersonServiceGetPersonBookBy() {
    }


    public static PersonServiceGetPersonBookBy getInstance() {
        if (personServiceGetPersonBookBy == null) {
            personServiceGetPersonBookBy = new PersonServiceGetPersonBookBy();
        }
        return personServiceGetPersonBookBy;

    }

    //Сервис по получению человека, за которым закреплены книги

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        String idJson = jsonObject.getString("id");
        int id = Integer.parseInt(idJson);

        try(Connection connection = new DBConnect().getConnection()) {

            String selectQuery = "select public.\"Person\".id,name, b.Book_id, name_book from public.\"Person\" join public.\"Book\" B on public.\"Person\".id = B.person_id WHERE person_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Object, Object> result = new HashMap<>();
            while (resultSet.next()) {
                Person person = Person.getInstance();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                Book book = Book.getInstance();
                book.setId(resultSet.getInt("book_id"));
                book.setNameBook(resultSet.getString("name_book"));
                result.put(person, book);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);
                resp.getWriter().write(json);


            }
            resultSet.close();
            preparedStatement.close();
            connection.close();


        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}
