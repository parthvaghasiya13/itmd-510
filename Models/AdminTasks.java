package Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.TextField;

public interface AdminTasks {
	public void createTable() throws Exception;
	public void addMovieDetail(int mvnum1, String movie_name,String audi_name,String username_) throws SQLException;
	public void updateMovieDetail(int movie_id, String movie_name2) throws SQLException;
	public void deleteMovieDetail(String movie_name1) throws SQLException;
	public ResultSet getResultSet() throws Exception;
	public void getMovieDetails() throws Exception;
	public ResultSet getResultSet_2() throws Exception;
	public void getBookingDetails() throws Exception;
}
