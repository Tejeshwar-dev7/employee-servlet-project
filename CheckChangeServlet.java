import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CheckChangeServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        PrintWriter out = res.getWriter();

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sample",
                "root",
                "teja@27092006"
            );

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM employee");
            rs.next();

            int newCount = rs.getInt(1);

            HttpSession session = req.getSession();
            Integer oldCount = (Integer) session.getAttribute("count");

            if (oldCount == null) {
                session.setAttribute("count", newCount);
                out.print("same");
            }
            else if (oldCount != newCount) {
                session.setAttribute("count", newCount);
                out.print("changed");
            }
            else {
                out.print("same");
            }

            con.close();
        }
        catch(Exception e) {
            out.print("error");
        }
    }
}