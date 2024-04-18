package ServiseBook;

import JDBC.DBConnect;
import ServletPerson.ServletDeletePerson;
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

public class ServiceBookDelete {

    private static ServiceBookDelete serviceBookDelete;


    public ServiceBookDelete() {
    }

    public static synchronized ServiceBookDelete getInstance(){
        if(serviceBookDelete==null){
            serviceBookDelete=new ServiceBookDelete();
        }
        return serviceBookDelete;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        String jsonId = jsonObject.getString("id");
        int id = Integer.parseInt(jsonId);

        try (Connection connection = new DBConnect().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Book\"WHERE book_id = ?;");
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Record with id " + id + " deleted successfully");
            } else if (rowsAffected == 0) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Requested  " + id + " one does not exist, or has already been deleted");
            }

            preparedStatement.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Error deleting record with id " + id, ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
