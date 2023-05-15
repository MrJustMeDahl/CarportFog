package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Carport;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "createaccount", urlPatterns = {"/signup"} )
public class CreateAccount extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**

     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("register.jsp");

    }


    /**

     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("user",null);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");


        User user = new User(0, null, null, null, 0, null, "user");


        try{



            boolean emailPassed = user.checkEmail(email);
            boolean phoneNumberPassed = user.checkPhoneNumber(phoneNumber);
            boolean namePassed = user.checkName(fullName);


            if(emailPassed && phoneNumberPassed && namePassed) {

                user = UserFacade.createUser(email, password, Integer.parseInt(phoneNumber), address, fullName, "user", connectionPool);
            }else {
                user = null;
            }




        }catch (NumberFormatException e){
            request.setAttribute("errorMessage", "telefon nr ikke indtastet korrekt. prøv igen.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }catch (Exception e) {
            // Handle other exceptions such as database errors
            request.setAttribute("errorMessage", "Hov, der skete en fejl ved registreringen, prøv igen..");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        session=request.getSession();
        session.setAttribute("user", user);
        request.getRequestDispatcher("login.jsp").forward(request, response);

    }

}