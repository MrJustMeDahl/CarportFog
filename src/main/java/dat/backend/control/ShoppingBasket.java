package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
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

@WebServlet(name = "shoppingbasket", urlPatterns = {"/shoppingbasket"} )
public class ShoppingBasket extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**
     * doGet will load a pending order into the shoppingbasket on requestscope, which will act as the shoppingbasket
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<Order> orderList = OrderMapper.getOrdersByUserID(user.getUserID() , connectionPool);
            request.setAttribute("orderlist", orderList);

            for (Order o: orderList) {
                if(Objects.equals(o.getOrderStatus(), "pending")){

                    request.setAttribute("order", o);
                    session.setAttribute("order",o);


                }
            }
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("shoppingbasket.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doGet(request, response);
    }

}