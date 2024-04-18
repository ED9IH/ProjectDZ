package ServicePerson;

import JDBC.DBConnect;
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

//Сервис по снятию брони книги с человека
public class PersonServiceRemovingArmorBooks {

    private static PersonServiceRemovingArmorBooks personServiceRemovingArmorBooks;

    public PersonServiceRemovingArmorBooks() {
    }

    public static PersonServiceRemovingArmorBooks getInstance() {
        if (personServiceRemovingArmorBooks == null) {
            personServiceRemovingArmorBooks = new PersonServiceRemovingArmorBooks();
        }
        return personServiceRemovingArmorBooks;
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStream = req.getInputStream();
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                builder.append(line);
            }
        }
        String jsonData = builder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        int bookId = jsonObject.getInt("book_id");


        try (Connection connection = new DBConnect().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Book\" SET person_id=null WHERE book_id=?");
            preparedStatement.setInt(1, bookId);
            System.out.println(preparedStatement);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected);
            if (rowsAffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Book released");
            }

            preparedStatement.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Error record with id " + bookId, ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
