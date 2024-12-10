import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLInjectionExample extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db");
            String user = request.getParameter("username");
            String query = "SELECT * FROM users WHERE username = '?';";
            PreparedStatement stmt = con.prepareStatement(query);

            try {
                stmt.setInt(1, Math.round(Float.parseFloat(request.getParameter("username"))));
            } catch (NumberFormatException e) {
                // MOBB: consider printing this message to logger: mobb-72204bd3d2910aa4632d5a5fedaadbec: Failed to convert input to type integer

                // MOBB: using a default value for the SQL parameter in case the input is not convertible.
                // This is important for preventing users from causing a denial of service to this application by throwing an exception here.
                stmt.setInt(1, 0);
            }
            stmt.executeQuery();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
