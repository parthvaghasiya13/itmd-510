package Models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class Admin extends MovieDetails implements AdminTasks{
	Connector connect = new Connector();
	private Statement statement = null;
	public int adminId;
	public String name;
	public String username;
	public String password;
	
	//----------------------------<code for creating a table in database>-----------------------//

			public void createTable() throws Exception{
				try{			
					//create table query to create the table
					String sql = "CREATE TABLE MOVIEDETAILS_09" + 
								 "(MOVIEID INTEGER not NULL AUTO_INCREMENT," + 
								 "MOVIE_NAME VARCHAR(7)," + 
								 "AUDI_NUMBER INTEGER," + 
								 "FROM_MOVIE_DATE DATE," +
								 "PRIMARY KEY (MOVIEID))";

					//execution of the query
					statement.executeUpdate(sql);

					System.out.println("Table MOVIEDETAILS created into the database!!");
					statement.close();
		 		}catch(SQLException e){
		 			System.out.println(e.getMessage());
		 		}
			}
	
	//-------------------------<getResultSet>--------------------------//
	//to view moviedetails table
			
	public ResultSet getResultSet() throws Exception{
		ResultSet rs = null;
		try{
			statement = connect.getConnection().createStatement();
			//select query to display the result
			String sql = "SELECT * from moviedetails_09;";
			
			//execution of the query
			rs = statement.executeQuery(sql);
 		}catch(SQLException e){
 			System.out.println(e.getMessage());
 		}
		return rs;
	}
	
	
	public void getMovieDetails() throws Exception{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> column = new Vector<String>();
		try{
			//create ResultSet object to fetch the data from the database
			ResultSet rs = getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();
			
			int columns = metaData.getColumnCount();
			
			String columns1 ="";
			
			//create a loop to generate number of columns and rows according to the columns
			
			for(int i=1;i<=columns;i++){
				columns1 = metaData.getColumnName(i);
				column.add(columns1);
			}
			//get row data from the table
			while(rs.next()){
				Vector<Object> row = new Vector<Object>(columns);
				
				for(int i=1;i<=columns;i++){
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
			rs.close();
			
			//create an object dtModel that will hold the data and column
			DefaultTableModel Model = new DefaultTableModel(data,column);
			
			JTable table= new JTable(Model);
			
			//create JFrame to display JTable in a table format
			JFrame frame = new JFrame("Movie Details");
			frame.setSize(700,200);
			frame.add(new JScrollPane(table));
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//to view booking_details table
	public ResultSet getResultSet_2() throws Exception{
		ResultSet rs = null;
		try{
			statement = connect.getConnection().createStatement();
			//select query to display the result
			String sql = "SELECT * from booking_details10;";
			
			//execution of the query
			rs = statement.executeQuery(sql);
 		}catch(SQLException e){
 			System.out.println(e.getMessage());
 		}
		return rs;
	}
	public void getBookingDetails() throws Exception{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> column = new Vector<String>();
		try{
			//create ResultSet object to fetch the data from the database
			ResultSet rs = getResultSet_2();
			ResultSetMetaData metaData = rs.getMetaData();
			
			int columns = metaData.getColumnCount();
			
			String columns1 ="";
			
			//create a loop to generate number of columns and rows according to the columns
			
			for(int i=1;i<=columns;i++){
				columns1 = metaData.getColumnName(i);
				column.add(columns1);
			}
			//get row data from the table
			while(rs.next()){
				Vector<Object> row = new Vector<Object>(columns);
				
				for(int i=1;i<=columns;i++){
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
			rs.close();
			
			//create an object dtModel that will hold the data and column
			DefaultTableModel Model = new DefaultTableModel(data,column);
			
			JTable table= new JTable(Model);
			
			//create JFrame to display JTable in a table format
			JFrame frame = new JFrame("Movie Details");
			frame.setSize(700,200);
			frame.add(new JScrollPane(table));
			//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//addMoviveDetail method to add the movie
	@Override
	public void addMovieDetail(int mvnum1, String movie_name,String audi_name, String username_) throws SQLException {
		// TODO Auto-generated method stub
		try{
			statement = connect.getConnection().createStatement();
			String sql = null;
			
			//insert query to insert the data
			sql = "INSERT INTO moviedetails_09 (MOVIEID, MOVIE_NAME, AUDI_NUMBER, USER_ID)" + 
						 "VALUES('"+mvnum1+"','"+movie_name+"','"+audi_name+"','"+username_+"')";
			
			//execution of the query
			statement.executeUpdate(sql);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("MOVIE ADDED");
			alert.setHeaderText(null);
			alert.setContentText("YOU HAVE SUCCESSFULLY ADDED A MOVIE");
			alert.showAndWait();
			//System.out.println("Records inserted into the database!!");
			}catch(SQLException e){
				//if any  problem occurs
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("INVALID");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage() + "\nPlease change audi number (It's aready reserved)");
				alert.showAndWait();
 		}
		finally{
			statement.close();
		}
	}
	
	//updateMovieDetail method to update movie name with the use of movie ID
	@Override
	public void updateMovieDetail(int movie_id, String movie_name2) throws SQLException {
		// TODO Auto-generated method stub
		try{
			statement = connect.getConnection().createStatement();
			String sql = null;
			
			//update query to update the data
			sql = "UPDATE MOVIEDETAILS_09 SET MOVIE_NAME = '"+movie_name2+"' WHERE MOVIEID ='"+movie_id+"' ";
			
			//execution of the query
			statement.executeUpdate(sql);
			
			//Giving user a confirmation message
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("MOVIE UPDATED");
			alert.setHeaderText(null);
			alert.setContentText("YOU HAVE SUCCESSFULLY UPDATED A MOVIE");

			alert.showAndWait();
			
			}
		catch(SQLException e){
			//if any problem occurs
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INVALID");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
 		}
		finally{
			statement.close();
		}
	}
	
	//deleteMovieDetail method to delete the movie with the use of Movie's name
	//@Override
	public void deleteMovieDetail(String movie_name1) throws SQLException {
		// TODO Auto-generated method stub
		try{
			statement = connect.getConnection().createStatement();
			String sql = null;
			
			//delete query to delete the data
			sql = "DELETE FROM MOVIEDETAILS_09 WHERE MOVIE_NAME = '"+movie_name1+"'";
			
			//execution of the query
			statement.executeUpdate(sql);
			
			//giving user a confirmation message
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("MOVIE REMOVED");
			alert.setHeaderText(null);
			alert.setContentText("YOU HAVE SUCCESSFULLY REMOVED A MOVIE");

			alert.showAndWait();
			
 		}catch(SQLException e){
 			//if any problem occurs
 			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INVALID");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
 		}
		finally{
			statement.close();
		}		
	}
	
}
