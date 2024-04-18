package ServletPerson;

import ServicePerson.PersonServiceDelete;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet
public class ServletDeletePerson extends HttpServlet {





    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {

        try {
            PersonServiceDelete.getInstance().doDelete(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e + "ServletDeletePerson");
        }


    }
}
