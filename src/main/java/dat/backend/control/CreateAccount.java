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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.sendRedirect("register.jsp");

    }


    /**
     *Using the dopost method we get some parameters from the register.jsp that are input from user and  we check the email phonenumber and
     * name to see if its input correctly, if all conditions are met, then we create a user. and write it down in database
     *
     * @param request   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *
     * @param response  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client
     *
     * @throws IOException
     * @throws ServletException
     * @author Danyal
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

        boolean emailPassed = user.checkEmail(email);
        boolean phoneNumberPassed = user.checkPhoneNumber(phoneNumber);
        boolean namePassed = user.checkName(fullName);

        try{
            if(emailPassed && phoneNumberPassed && namePassed) {

                user = UserFacade.createUser(email, password, Integer.parseInt(phoneNumber), address, fullName, "user", connectionPool);
            }else {
                user = null;
            }
        }catch (DatabaseException e) {
            // Handle other exceptions such as database errors
            request.setAttribute("errormessage", "Hov, der skete en fejl ved registreringen, prøv igen..");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        if(user != null) {
            request.setAttribute("registermessage", "Din konto er blevet oprettet, du kan nu logge ind.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (!emailPassed){
            request.setAttribute("registermessage", "Hov, din email er ikke korrekt - prøv igen.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!phoneNumberPassed){
            request.setAttribute("registermessage", "Hov, dit telefonnummer er ikke korrekt - prøv igen.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!namePassed){
            request.setAttribute("registermessage", "Hov, dit navn må ikke indeholde tal - prøv igen.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

}