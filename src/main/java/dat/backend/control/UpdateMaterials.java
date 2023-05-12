package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateMaterials", value = "/updatematerials")
public class UpdateMaterials extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        try {
            User user = (User) session.getAttribute("user");

            List<Post>postList = (List<Post>) request.getServletContext().getAttribute("allPosts");
            List<Rafter>rafterList = (List<Rafter>) request.getServletContext().getAttribute("allRafters");
            List<Purlin>purlinList = (List<Purlin>) request.getServletContext().getAttribute("allPurlins");
            request.setAttribute("post", postList);
            request.setAttribute("rafter",rafterList);
            request.setAttribute("purlin",purlinList);

        } catch (Exception e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }

        request.getRequestDispatcher("WEB-INF/updatematerials.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // You shouldn't end up here anyway, therefore you get sent back to frontpage
        response.sendRedirect("index.jsp");

    }
}
