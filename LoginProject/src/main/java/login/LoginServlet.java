package login;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public static String lOAD_DRIVER="com.mysql.cj.jdbc.Driver";
	public static String URL= "jdbc:mysql://localhost:3306/userdb";
	public static String  USERNAME= "root";
	public static String PASSWORD="Paras@12345";
    Connection connection;
	
    public LoginServlet() {
        
    	super();

    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uname = request.getParameter("username");
	    String pword = request.getParameter("password");

	    response.setContentType("text/html");
	    PrintWriter pw = response.getWriter();

	    try {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM userinfo WHERE username=?");
	        ps.setString(1, uname);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String storedPassword = rs.getString("password"); // assuming password field column is "password"
	            if (storedPassword.equals(pword)) {
	                pw.println("<html><body><center>");
	                pw.println("Welcome: " + uname);
	                pw.println("</center></body></html>");
	            } else {
	                pw.println("<html><body><center>");
	                pw.println("Incorrect password");
	                pw.println("</center></body></html>");
	            }
	        } else {
	            pw.println("<html><body bgcolor='red'><center>");
	            pw.println("User not found");
	            pw.println("</center></body></html>");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        pw.println("<html><body bgcolor='red'><center>");
	        pw.println("Database error");
	        pw.println("</center></body></html>");
	    } finally {
	        pw.close();
	    }
	}


}
