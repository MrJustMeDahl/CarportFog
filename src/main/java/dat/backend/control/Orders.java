package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "orders", urlPatterns = {"/orders"} )
public class Orders extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**
     * doGet is used for loading in the orders for the user from the DB to be shown on the orders.jsp page.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            List orderlist = OrderFacade.getOrdersByUserID(user.getUserID(), connectionPool);
            request.setAttribute("orderlist", orderlist);


            request.getRequestDispatcher("WEB-INF/orders.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * doPost is used to delete orders from both the shoppingbasket and the orderpage. The boolean "shoppingDeleteCheck" will check if the
     * user deleted the order from the shoppingbasket page, and therefore redirect the user back to the page.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        try{
            boolean shoppingDeleteCheck = Boolean.parseBoolean(request.getParameter("shoppingDelete"));

            OrderFacade.deleteOrder(Integer.parseInt(request.getParameter("currentID")), connectionPool);
            if(shoppingDeleteCheck == true){
                request.getRequestDispatcher("shoppingbasket").forward(request, response);

            }
            doGet(request, response);

        }catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}