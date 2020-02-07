package Models;

import java.sql.Date;
import java.sql.SQLException;

public interface UserTasks {
	public void insert(String cust_name, String moviename,Date dd1,String movietime ,int numberoftickets, int movie_id) throws Exception;
	public String[] getMoviename() throws SQLException;
	public void book(String cust_name, String moviename,Date dd1,String movietime ,int numberoftickets, int movie_id) throws SQLException;
}
