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
//Сервис по обновления человека
public class PersonServiceUpdate {

    private  volatile static PersonServiceUpdate personServiceUpdate;


    public PersonServiceUpdate() {
    }


    public static PersonServiceUpdate getInstance(){
        if(personServiceUpdate==null){
            synchronized (PersonServiceUpdate.class){
                if(personServiceUpdate==null)personServiceUpdate=new PersonServiceUpdate();
            }
        }

        return personServiceUpdate;
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

        int id = jsonObject.getInt("id");
        String jsonName = jsonObject.getString("name");


        try(Connection connection = new DBConnect().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Person\" SET name=? WHERE id=?");
            preparedStatement.setString(1, jsonName);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("UPDATE");

            }
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Error updating record with id " + id, ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
