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
import java.sql.SQLException;

/**
 * Servlet implementation class RegSevlet
 */
@WebServlet("/reg")
public class RegSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static String lOAD_DRIVER="com.mysql.cj.jdbc.Driver";
	public static String URL= "jdbc:mysql://localhost:3306/userdb";
	public static String  USERNAME= "root";
	public static String PASSWORD="Paras@12345";
    Connection connection;
    
    public RegSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    public void init(ServletConfig config) throws ServletException {
		 try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName=request.getParameter("firstname");
		String lastName=request.getParameter("lastname");
		String userName=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			PreparedStatement ps = connection.prepareStatement("insert into userinfo values(?,?,?,?)");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, userName);
			ps.setString(4, password);
			
			int num=ps.executeUpdate();
			PrintWriter pw = response.getWriter(); 
			
			if (num > 0) {
				
				pw.println("<html><body><center>");
			    pw.println("Data inserted successfully...!!");
                pw.println("</center></body></html>");
                pw.println("<html><body><center> <a href=login.html>Login</a></center></body></html>");
			} else {
				pw.println("<html><body><center>");
				pw.println("Data insertion failed");               
				pw.println("</center></body></html>");
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
