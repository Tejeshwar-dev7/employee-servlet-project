import java.io.*;
import java.sql.*;
import javax.servlet.*;

public class DisplayServlet implements Servlet
{
    public void service(ServletRequest req,
                        ServletResponse res)
                        throws ServletException, IOException
    {
        PrintWriter pw = res.getWriter();

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sample",
                    "root",
                    "teja@27092006"
                );

            Statement st = con.createStatement();

            ResultSet rs =
                st.executeQuery("select * from employee");

            pw.println("<html><body>");

            pw.println("<table border='1'>");

            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>NAME</th>");
            pw.println("<th>SALARY</th>");
            pw.println("</tr>");

            while(rs.next())
            {
                pw.println("<tr>");

                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getFloat(3) + "</td>");

                pw.println("</tr>");
            }

            pw.println("</table>");

           
            pw.println("<script>");
            pw.println("setInterval(() => {");
            pw.println("fetch('CheckChangeServlet')");
            pw.println(".then(res => res.text())");
            pw.println(".then(data => {");
            pw.println("if (data === 'changed') { location.reload(); }");
            pw.println("});");
            pw.println("}, 3000);");
            pw.println("</script>");

            pw.println("</body></html>");

            con.close();
        }
        catch(Exception e)
        {
            pw.println(e);
        }
    }

    public void init(ServletConfig c){}
    public void destroy(){}
    public ServletConfig getServletConfig(){ return null; }
    public String getServletInfo(){ return null; }
}