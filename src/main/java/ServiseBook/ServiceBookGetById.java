package ServiseBook;

import JDBC.DBConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Book;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceBookGetById {


    private static ServiceBookGetById serviceBookGetById;


    public ServiceBookGetById() {
    }

    public static ServiceBookGetById getInstance() {
        if (serviceBookGetById == null) {
            serviceBookGetById = new ServiceBookGetById();
        }
        return serviceBookGetById;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStream = req.getInputStream();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        // В результате получаем строку JSON
        String jsonData = builder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        String nameJson = jsonObject.getString("book_id");
        int id = Integer.parseInt(nameJson);

        try (Connection connection = new DBConnect().getConnection()) {
            String selectQuery = "SELECT * FROM public.\"Book\" WHERE book_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String json;
            while (resultSet.next()) {
                Book book = Book.getInstance();
                book.setId(Integer.parseInt(resultSet.getString("book_id")));
                book.setNameBook(resultSet.getString("name_book"));
                ObjectMapper objectMapper = new ObjectMapper();
                json = objectMapper.writeValueAsString(book);
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
