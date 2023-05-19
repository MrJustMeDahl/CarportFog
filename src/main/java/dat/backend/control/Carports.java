package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NoMaterialFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "carport", urlPatterns = {"/carport"} )
public class Carports extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    /**
     * doGet is used to redirect to the carport.jsp, where the user is able to customize the dimensions of a carport.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        //response.sendRedirect("WEB-INF/carport.jsp");
        int skur = Integer.parseInt(request.getParameter("shed"));
        if (skur == 1){
            request.setAttribute("shed", 1);
        }
        else if(skur == 0){
            request.setAttribute("shed", 0);

        }

        request.getRequestDispatcher("WEB-INF/carport.jsp").forward(request, response);

    }


    /**
     * doPost is designed to take the users input and make use of the createOrder() method. The itemlist will also be
     * generated for both the carport and the shed (if applicable). Both the order and itemlist are then saved to the DB.
     * @param request Used for loading in the data on the request scope.
     * @param response Is used to set the contentType.
     * @throws IOException Is cast if the input/output is invalid.
     * @throws ServletException is cast when theres an error using Servlets in general.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        ServletContext applicationScope = getServletContext();
        User user = (User) session.getAttribute("user");

        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));
        int  height = Integer.parseInt(request.getParameter("height"));


        Boolean check = Boolean.valueOf(request.getParameter("shedCheck"));
        int shedLength = 0;
        int shedWidth = 0;


        if(check == true){

            shedLength = Integer.parseInt(request.getParameter("shedLength"));
            shedWidth = Integer.parseInt(request.getParameter("shedWidth"));

        }

        ItemList itemList = null;
        try {
            itemList = new ItemList(length, width, height, check, shedLength, shedWidth, (List<Post>) applicationScope.getAttribute("allPosts"), (List<Purlin>) applicationScope.getAttribute("allPurlins"), (List<Rafter>) applicationScope.getAttribute("allRafters"), (List<Roof>) applicationScope.getAttribute("allRoofs"), (List<Sheathing>) applicationScope.getAttribute("allSheathings"));
        } catch (NoMaterialFoundException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
            Carport carport = new Carport(itemList.getMaterialsForCarport(), width, length, height, new Shed(itemList.getMaterialsForShed(), shedWidth, shedLength, height));

        if (check == true){
            carport.setCheckShed(true);
            request.setAttribute("shed", 1);
        }
        else {
            request.setAttribute("shed", 0);
        }

        try{
            Order order = OrderFacade.createOrder(carport, user.getUserID(), carport.getPrice()+carport.getShed().getPrice(), carport.getIndicativePrice()+carport.getShed().getIndicativePrice(), itemList, connectionPool);
            OrderFacade.addItemlistToDB(order.getItemList(), order.getOrderID(), connectionPool);
            request.getRequestDispatcher("shoppingbasket").forward(request, response);
        }
        catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

}