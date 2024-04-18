package ServiseBook;

import JDBC.DBConnect;

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
import java.sql.SQLException;

public class ServiceBookSave {

    private static ServiceBookSave serviceBookSave;

    public ServiceBookSave() {
    }


    public static ServiceBookSave getInstance() {
        if (serviceBookSave == null) {
            serviceBookSave = new ServiceBookSave();
        }
        return serviceBookSave;


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
        Book book = Book.getInstance();
        book.setNameBook(jsonObject.getString("name_book"));
        try (Connection connection = new DBConnect().getConnection()) {

            String insertQuery = "INSERT INTO public.\"Book\" (name_book) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, book.getNameBook());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resp.getWriter().write("ADD Book");
            }

            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
