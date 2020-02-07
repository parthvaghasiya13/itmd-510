package Models;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class USER_1 implements UserTasks {

		int latest_booking_id = 0;
		Connector connect = new Connector();
		private Connection con=null;
		private Statement statement = null;
		ArrayList rv= new ArrayList();
		String[] moviename= new String[10];
		
		//--------------------insert method to insert the booking details in booking_details table--------//
		
		public void insert(String cust_name, String moviename,Date dd1,String movietime ,int numberoftickets, int movie_id) throws Exception{
			try{
				statement = connect.getConnection().createStatement();
				
				String sql = null;
				//insert query to insert the data
				sql = "INSERT INTO booking_details10 (CUSTOMER_NAME, SEATS_BOOKED, AMOUNT, SHOW_DATE, SHOW_TIME, MOVIE_NAME, MOVIE_ID)" + 
							 "VALUES('"+cust_name+"','"+numberoftickets+"','"+numberoftickets*10+"','"+dd1+"','"+movietime+"','"+moviename+"','"+movie_id+"')";
				statement.executeUpdate(sql);
				//execution of the query
				String SQl1="select * from booking_details10";
				ResultSet rs=statement.executeQuery(SQl1);
				
				//to get latest booking id
				while(rs.next())
				{
					latest_booking_id = rs.getInt("BOOKING_ID");
				}
				
				//displaying user confirmation alert box with booking id
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation Details");
				alert.setHeaderText("Booking ID: " +latest_booking_id + "\n[Please note this booking ID]");
				alert.setContentText("Customer Name: " +cust_name+"\nMovie Name: " +moviename +"\nTotal seats booked: " +numberoftickets+"\nTotal Amount: " +numberoftickets*10+"\nShow Date: " +dd1+"\nShow Timings: " +movietime);
				alert.showAndWait();
	 		}catch(SQLException e){
	 			System.out.println(e.getMessage());
	 		}
			finally{
				statement.close();
			}
		}
		
		//getMoviename method to retrive moviename to display in selectmovie combobox
		public String[] getMoviename() throws SQLException
		{
			try
			{
				statement = connect.getConnection().createStatement();
				String sql="Select Movie_name from moviedetails_09";
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next())
				{
					rv.add(rs.getString(1));
				}
				statement.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			moviename=(String[])rv.toArray(new String[rv.size()]);
			
			return moviename;
		}
		
		//book method which will call insert method to insert the data into table
		public void book(String cust_name, String moviename,Date dd1,String movietime ,int numberoftickets, int movie_id) throws SQLException
		{
			try {
				//calling insert method
				insert(cust_name, moviename,dd1,movietime,numberoftickets,movie_id);
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
