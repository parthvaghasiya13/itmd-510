/*
Programmer: Parth Kishorbhai Vaghasiya, File Name: Connector.java, FINAL PROJECT, Date: 11/27/2017
*/
package Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	Connection connect = null;
	 public Connection getConnection() {
		 try {
			 		//This will load the MySQL driver, each DB has its own driver
			 	Class.forName("com.mysql.jdbc.Driver");
			 	
			 		// Setup the connection with the DB
			 	connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net:3306/fp510?user=fpuser&password=510");
			 }
		 catch(SQLException e)
		 	{
			 System.out.println(e.getMessage());
		 }
		 catch(ClassNotFoundException e)
		 {
		 e.printStackTrace();
		 }
		 return connect;
	 }
}
