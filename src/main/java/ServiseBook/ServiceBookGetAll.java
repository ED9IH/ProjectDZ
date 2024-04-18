package ServiseBook;

import JDBC.DBConnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Book;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceBookGetAll {

    private static ServiceBookGetAll serviceBookGetAll;

    public ServiceBookGetAll() {
    }

    public static ServiceBookGetAll getInstance(){
        if(serviceBookGetAll==null){
            serviceBookGetAll=new ServiceBookGetAll();
        }
        return serviceBookGetAll;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try(Connection connection = new DBConnect().getConnection();) {


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT t.*\n FROM public.\"Book\" t");

            while (resultSet.next()) {
                Book book = Book.getInstance();
                book.setId(Integer.parseInt(resultSet.getString("book_id")));
                book.setNameBook(resultSet.getString("name_book"));
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(book);
                resp.getWriter().write(json);
            }


            resultSet.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
