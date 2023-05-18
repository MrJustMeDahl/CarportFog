package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NoMaterialFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUpdateOrder", value = "/adminupdateorder")
public class AdminUpdateOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        int length = Integer.parseInt(request.getParameter("length"));
        int width = Integer.parseInt(request.getParameter("width"));
        int minHeight = Integer.parseInt(request.getParameter("minHeight"));
        HttpSession sessionScope = request.getSession();
        ServletContext applicationScope = getServletContext();
        List<Order> newOrders = (List<Order>) sessionScope.getAttribute("newOrders");
        Order updateOrder = null;
        for(Order o: newOrders){
            if(o.getOrderID() == orderID){
                updateOrder = o;
            }
        }
        boolean hasShed = false;
        for(ItemListMaterial i: updateOrder.getItemList().getMaterials()){
            if(i.getPartFor().equals("shed")){
                hasShed = true;
            }
        }
        try {
            updateOrder.setItemList(new ItemList(length, width, minHeight, hasShed, (List<Post>) applicationScope.getAttribute("allPosts"), (List<Purlin>) applicationScope.getAttribute("allPurlins"), (List<Rafter>) applicationScope.getAttribute("allRafters")));
            updateOrder.getCarport().setMaterials(updateOrder.getItemList().getMaterialsForCarport());
            updateOrder.getCarport().setLength(length);
            updateOrder.getCarport().setWidth(width);
            updateOrder.getCarport().setMinHeight(minHeight);
            updateOrder.setPrice();
            updateOrder.setIndicativePrice();
        } catch (NoMaterialFoundException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        try{
            OrderFacade.updateItemListForOrder(orderID, updateOrder.getItemList(), connectionPool);
            OrderFacade.updateMeasurementsForOrder(orderID, updateOrder.getCarport(), connectionPool);
        } catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("chosenOrderID", orderID);
        request.getRequestDispatcher("WEB-INF/newordersadministration.jsp").forward(request, response);
    }
}
