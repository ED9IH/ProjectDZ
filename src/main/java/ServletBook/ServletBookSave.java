package ServletBook;

import ServiseBook.ServiceBookSave;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletBookSave extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            ServiceBookSave.getInstance().doPost(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
