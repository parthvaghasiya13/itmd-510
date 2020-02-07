package application;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.Statement;

import Models.Admin;
import Models.Connector;
import Models.USER_1;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class Main extends Application {
	Stage window1;
	Stage adminpage;
	Stage adminmainpage;
	Stage addmovie;
	Stage deleteMovie1;
	Stage updateMovie1;
	Stage mainmenu_;
	
	String[] moviename= new String[10];
	USER_1 d1 = new USER_1();
	Admin object_admin = new Admin();
	Map<String,String> hash1 = new HashMap<String,String>();
	private Statement statement1 = null;
	private Statement statement10 = null;
	private java.sql.Connection con = null;
	private java.sql.Connection con1 = null;
	ResultSet rs=null;
	ResultSet rs22=null;
	@SuppressWarnings("unchecked")
	@Override
	
	
	public void start(Stage primaryStage) {
		
		window1 = primaryStage;
		adminmainpage = primaryStage;
		addmovie=primaryStage;
		deleteMovie1=primaryStage;
		updateMovie1=primaryStage;
		mainmenu_=primaryStage;
		
		//-------------------------<creating movie booking page>--------------------------//
		//Gridpane for user booking page
		GridPane Mainpage = new GridPane();
		//datepicker for choosing desired show date
		DatePicker mvdate= new DatePicker();
		
		//textfield to let the user enter his/her desired number of tickets
		TextField entks = new TextField();
		TextField entname = new TextField();
		
		//labels to give the user instructions
		Label entername=new Label("ENTER YOUR NAME:");
		Label movie=new Label("SELECT MOVIE:");
		Label select_date=new Label("SELECT SHOW DATE:");
		Label select_time=new Label("SELECT SHOW TIMINGS:");
		Label enternumber = new Label("ENTER NUMBER OF TICKETS($10/ticket)");
		Label wc = new Label("WELCOME TO PV  MOVIE BOOKING SYSTEM ");
		
		//Buttons
		Button adminlogin = new Button("LOGIN FOR ADMIN");
		Button book = new Button("BOOK");
		Button back2mainmenu = new Button("Back to Main Menu"); 
		
		//combobox for displaying movie name and movie timings
		ComboBox mvtime = new ComboBox();
		ComboBox mvname = new ComboBox();
		
		//getting movie name from database
		try {
			moviename=d1.getMoviename();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		for(int i=0;i<moviename.length;i++ )
		{
			String abc = moviename[i];
			mvname.getItems().add(abc);
			mvname.setValue(moviename[i]);
		}
		mvname.setValue(null);
		
		mvtime.getItems().addAll("12pm","3pm","6pm","9pm");
			
		mvname.setOnAction(e->{
			
			ReadOnlyObjectProperty s1 = mvname.getSelectionModel().selectedItemProperty();
			String m1 = (String) s1.getValue();
			
			//datepicker
			mvdate.setOnAction(e1->{

				LocalDate ldnow= LocalDate.now();
				LocalDate ldpicker=mvdate.getValue();
				LocalDate ld7=LocalDate.now().plusDays(7);
				
				//if user selects  previous date 
				if(ldpicker.compareTo(ldnow) < 0 )
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Please make a valid selection");
					alert.setHeaderText(null);
					alert.setContentText("INVALID DATE");
					alert.showAndWait();
					
				}
				//if user try to select movie date for more than 7 days
				else if(ldpicker.compareTo(ld7)>=1)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Please change the date");
					alert.setHeaderText(null);
					alert.setContentText("You can make reservation for next 7 days only");
					alert.showAndWait();
					//System.out.println(ldpicker);
				}
				//if user selects valid date
				else
				{
					//DateTimeFormatter ddt =  DateTimeFormatter.ofPattern("yyyy/mm/dd");
					Date  dd1 =  java.sql.Date.valueOf(ldpicker);
					//System.out.println(ldpicker);
		
				mvtime.setOnAction(o->{
					ReadOnlyObjectProperty s23 = mvtime.getSelectionModel().selectedItemProperty();
					String m2 = (String) s23.getValue();
					//System.out.println(m2);
					
					//by clicking book button
					book.setOnAction(t->{
						String notickets=entks.getText();
						int nt = Integer.parseInt(notickets);
						String cust_name=entname.getText();
						
						Connector connect1 = new Connector();
						con1=connect1.getConnection();
						try {
							statement10 = con1.createStatement();
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						
						//local variable to perform special task
						int abc = 0;
						
						//to see how many tikets are already booked for the particular show
						String sql10 = "SELECT SUM(SEATS_BOOKED) FROM BOOKING_DETAILS10 WHERE SHOW_DATE='"+dd1+"'AND MOVIE_NAME='"+m1+"' AND SHOW_TIME='"+m2+"';";
						try {
							ResultSet rs1 = statement10.executeQuery(sql10);
							while(rs1.next()){
								abc = rs1.getInt(1);}
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						
						int movie_id = 0;
						//to get movie_id
						String sql11 = "SELECT movieid FROM MOVIEDETAILS_09 WHERE MOVIE_NAME='"+m1+"';";
						try {
							ResultSet rs22 = statement10.executeQuery(sql11);
							while(rs22.next()){
								movie_id = rs22.getInt(1);}
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						
						//to define auditorium capacity
						final int fix_num = 40;
						
						//if user selects more than auditorium seats capacity
						if(fix_num<abc){
							Alert alert2 = new Alert(AlertType.INFORMATION);
							alert2.setTitle("Seats are not available");
							alert2.setHeaderText(null);
							alert2.setContentText("Entered Seats are not available for this particular show");
							alert2.showAndWait();
						}
						//if user's desired seats are not available for the particular show
						else if(fix_num<nt){
							Alert alert3 = new Alert(AlertType.INFORMATION);
							alert3.setTitle("Seats are not available");
							alert3.setHeaderText(null);
							alert3.setContentText("You cannot book more than 40 seats ");
							alert3.showAndWait();
						}
						//booking accepted
						else{
							try {
								d1.book(cust_name, m1, dd1, m2, nt,movie_id);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
					}); 

				});
	
				}
			});	
		});
		
		//adding all lables, buttons to gridpane
		wc.setFont(new Font("Arial", 15));
		Mainpage.add(wc,0,0);
		Mainpage.setHgap(10);
		Mainpage.setVgap(1);
		Mainpage.add(movie, 0, 40);
		Mainpage.add(mvname, 1, 40);
		Mainpage.add(mvdate, 1, 50);
		Mainpage.add(mvtime, 1, 60);
		Mainpage.add(entername, 0, 70);
		Mainpage.add(select_date, 0, 50);
		Mainpage.add(select_time, 0, 60);
		Mainpage.add(back2mainmenu, 0, 100);
		Mainpage.setPadding(new Insets(10));
		Mainpage.add(book, 1, 100);
		Mainpage.add(adminlogin, 0, 130);
		Mainpage.add(entks,1 , 80);
		Mainpage.add(enternumber, 0, 80);
		Mainpage.add(entname, 1, 70);
		
		//creating scene
		Scene scene = new Scene(Mainpage, 600, 600);
		
		//for background image
		BackgroundImage myBI= new BackgroundImage(new Image("file:/C:/Users/parth/workspace/MovieTicketBooking/src/Images/image3.png",600,600,false,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		
		//attaching background image to mainpage gridpane
		Mainpage.setBackground(new Background(myBI));
		adminpage = primaryStage;
		
		//creating gridpane for admin login page
		GridPane adminpg = new GridPane();
		//attaching background image to admin login page
		adminpg.setBackground(new Background(myBI));
		
		//creating scene
		Scene adminscene = new Scene(adminpg, 600, 600);
		
		//assigning task on the event of click of adminlogin button
		adminlogin.setOnAction(e -> {
			adminpage.setScene(adminscene);
			adminpage.show();
		});
		
		
		//--------------------------<Main Menu><First Page>------------------------------//
		
		//creating gridpane for first page of application
		GridPane mainmenu_d = new GridPane();
		
		//creating scene for first page
		Scene mainmenu_scene = new Scene(mainmenu_d, 600,600);
		
		//label for user greeting
		Label info = new Label("Welcome to PV Movie Booking System");
		info.setFont(new Font("Arial", 25));
		
		//buttons on main page
		Button admin_login = new Button("Login as admin/manager");
		Button Guest_booking = new Button("Continue as Guest");
		
		//assigning background image on main page
		mainmenu_d.setBackground(new Background(myBI));
		mainmenu_d.setVgap(3);
		mainmenu_d.setHgap(100);
		mainmenu_d.add(info,1,20); 
		admin_login.setPadding(new Insets(10));
		Guest_booking.setPadding(new Insets(10));
		
		//adding buttons on the main page
		mainmenu_d.add(admin_login,1,40); 
		mainmenu_d.add(Guest_booking,1,60); 
		
		//displaying main screen
		mainmenu_.setScene(mainmenu_scene);
		mainmenu_.show();
		
		//assigning task for admin_login button
		admin_login.setOnAction(e->{
			adminpage.setScene(adminscene);
			adminpage.show();
		});
		
		//assigning task for guest_booking button
		Guest_booking.setOnAction(e->{
			window1.setScene(scene);
			window1.show();
		});
		
		//assigning task for back2mainmenu button
		back2mainmenu.setOnAction(e -> {
			mainmenu_.setScene(mainmenu_scene);
			mainmenu_.show();
		});


		// ----------------------------------<ADMIN LOGIN PAGE>-----------------------------------------//
		
		//creation of button
		Button admlogin1 = new Button("LOGIN");
		Button back1 = new Button("BACK to Main Menu");
		Button back2book = new Button(" BACK to Booking Menu");
		
		//creation of label
		Label username = new Label("USERNAME");
		Label password1 = new Label("PASSWORD");
		
		//creation of textfield
		TextField adusername = new TextField();
		
		//creation of passwordfield
		PasswordField password = new PasswordField();
		
		//assigning labels, button to admin login page
		adminpg.setHgap(10);
		adminpg.setVgap(1);
		adminpg.add(admlogin1, 0,30 );
		adminpg.add(adusername, 1, 10);
		adminpg.add(password, 1, 20);
		adminpg.add(username, 0, 10);
		adminpg.add(password1, 0, 20);
		adminpg.add(back1, 0, 40);
		adminpg.add(back2book, 1, 40);
		
		//assigning task to back1 button
		back1.setOnAction(e -> {			
			mainmenu_.setScene(mainmenu_scene);
			mainmenu_.show();
		});

		//assigning task to back2book button which will redirect user to booking page
		back2book.setOnAction(e -> {			
			window1.setScene(scene);
			window1.show();
		});
		
		
		
		//----------<Admin task panel>---------//

		//creating gridpane for admin task panel
		GridPane adminpage1 = new GridPane();
		
		//for background of admin task panel
		BackgroundImage myBI2= new BackgroundImage(new Image("file:/C:/Users/parth/workspace/MovieTicketBooking/src/Images/background_image1.jpg",600,600,false,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		adminpage1.setBackground(new Background(myBI2));
		
		//creating scene for admin task panel
		Scene activity = new Scene(adminpage1, 600, 600);
		
		//creating buttons for the use in admin task panel
		Button addmovie2 = new Button("ADD MOVIE");
		addmovie2.setPadding(new Insets(20));
		Button deletemovie = new Button("DELETE MOVIE");
		deletemovie.setPadding(new Insets(20));
		Button updatemovie = new Button("UPDATE MOVIE");
		updatemovie.setPadding(new Insets(20));
		Button viewmovie = new Button("VIEW MOVIE DETAILS TABLE");
		viewmovie.setPadding(new Insets(20));
		Button bookmovie_v = new Button("VIEW BOOKING DETAILS TABLE");
		bookmovie_v.setPadding(new Insets(20));
		Button back2 = new Button("LOG OUT");
		
		//assigning buttons to admin task panel
		adminpage1.setHgap(10);
		adminpage1.setVgap(1);
		adminpage1.add(addmovie2, 10, 70);
		adminpage1.setPadding(new Insets(10));
		adminpage1.add(deletemovie, 10, 90);
		adminpage1.add(updatemovie, 10, 110);
		adminpage1.add(viewmovie, 10, 130);
		adminpage1.add(bookmovie_v, 10, 150);
		adminpage1.add(back2, 10, 170);
		
		//assigning task to viewmovie button which will display movie details page
		viewmovie.setOnAction(e->{
			try {
				object_admin.getMovieDetails();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//assigning task to bookmovie button which will display booking details page
		bookmovie_v.setOnAction(e->{
			try {
				object_admin.getBookingDetails();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//assigning task to admlogin1 to check login credentials
		admlogin1.setOnAction(e->{
			
			try{
				Connector connect = new Connector();
				
				con=connect.getConnection();
				statement1 = con.createStatement();
				String sql = "SELECT * FROM ADMIN_LOGIN";
				rs = statement1.executeQuery(sql);
				while(rs.next())
				{
					hash1.put(rs.getString(1), rs.getString(2));
				}
				String password2 = password.getText();
				if(password2.equals(hash1.get(adusername.getText())))
				{
					adminmainpage.setScene(activity);
					adminmainpage.show();
				}
				else
				{
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Wrong Credentials");
					alert1.setHeaderText(null);
					alert1.setContentText("PLEASE CHECK YOUR USERNAME AND PASSWORD");
					alert1.showAndWait();
				}
				statement1.close();
			}catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			finally{
				try{
					con.close();
				} catch (SQLException e1){
					e1.printStackTrace();
				}
			}
		});
		
		//assigning task to back button which will logout the user
		back2.setOnAction(e->{
			mainmenu_.setScene(mainmenu_scene);
			mainmenu_.show();		
		});	
		
		//-----------------------<ADD MOVIE CODE>------------------------------//
		
			
			
			//creating gridpane for add movie 
			GridPane addMovie = new GridPane();
			
			//attaching backgrouond
			addMovie.setBackground(new Background(myBI));
			
			//creating scene
			Scene addmovie1=new Scene(addMovie,600,600);
			
			//creating labels, textfield and adding it to gridpane
			addMovie.setHgap(10);
			addMovie.setVgap(1);
			Label entermvn = new Label("ENTER MOVIE ID");
			addMovie.add(entermvn, 0, 10);
			Label entermvname= new Label("ENTER MOVIE NAME");
			addMovie.add(entermvname, 0, 20);
			Label enteraudi= new Label("ENTER AUDI NUMBER");
			addMovie.add(enteraudi, 0, 30);
			
			TextField mvnum = new TextField();
			addMovie.add(mvnum, 1, 10);
			TextField mvname1 = new TextField();
			addMovie.add(mvname1, 1, 20);
			TextField audinum= new TextField();
			addMovie.add(audinum, 1, 30);
			
			//buttons and adding it to gridpane		
			Button add = new Button("ADD");
			addMovie.add(add, 0,40 );
			Button b1 = new Button("BACK");
			addMovie.add(b1, 0,50 );
			
			
			//assigning task to addmovie2 button which will redirect user to add movie page
			addmovie2.setOnAction(e->{
				addmovie.setScene(addmovie1);
				addmovie.show();			
			});
			
			//assigning task to add button which will add new movie into database table
			add.setOnAction(e->{
				final String username_ = adusername.getText();
				String mov_num=mvnum.getText();
				int mvnum1 = Integer.parseInt(mov_num);
				String movie_name=mvname1.getText();
				String audi_name=audinum.getText();
				try {
					//using addmoviedetail method of admin class
					object_admin.addMovieDetail(mvnum1, movie_name, audi_name, username_ );
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			});
			
			//assigning task to b1 button which will redirect user to admin task panel 
			b1.setOnAction(e->{
				adminmainpage.setScene(activity);
				adminmainpage.show();
			});
			
			
			
	//---------------------------<DELETE MOVIE CODE>-------------------------------//
			
			//creating gridpane for delete movie page
			GridPane deleteMovie2 = new GridPane();
			
			//attaching background image to delete movie page
			deleteMovie2.setBackground(new Background(myBI));
			
			//creating scene for delete movie page
			Scene delete=new Scene(deleteMovie2,600,600);
			
			//labels, buttons and textfield creation and attching it to deletemovie2 gridpane
			deleteMovie2.setHgap(10);
			deleteMovie2.setVgap(1);
			Label entermvnfordelete = new Label("ENTER MOVIE NAME");
			deleteMovie2.add(entermvnfordelete, 0, 10);
			TextField movie_name_e = new TextField();
			deleteMovie2.add(movie_name_e, 1, 10);
			Button delete1 = new Button("DELETE");
			deleteMovie2.add(delete1, 0, 20);
			Button b2 = new Button("BACK");
			deleteMovie2.add(b2, 0, 30);
			
			//assigning task to deletemovie button which will redirect user to delete movie page
			deletemovie.setOnAction(e->{
				deleteMovie1.setScene(delete);
				deleteMovie1.show();
				
			});
			
			//assigning task to delete1 button which will delete movie from moviedetails table
			delete1.setOnAction(e->{
				String movie_name1=movie_name_e.getText();
				try {
					object_admin.deleteMovieDetail(movie_name1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			});
			
			//assigning task to b2 button which will redirect user to admin task panel
			b2.setOnAction(e->{
				adminmainpage.setScene(activity);
				adminmainpage.show();
				
			});
			
			//----------------------<UPDATE MOVIE CODE>------------------------------//
			
					//creation of gridpane for update movie task
					GridPane updateMovie = new GridPane();
					
					//attaching background to update movie gridpane
					updateMovie.setBackground(new Background(myBI));
					
					//creating scene for update movie gridpane
					Scene update=new Scene(updateMovie,600,600);
					
					//creation of buttons, labels, textfields and attaching it to update movie gridpane
					updateMovie.setHgap(10);
					updateMovie.setVgap(1);
					Label entermvnforupdate = new Label("ENTER MOVIE ID");
					updateMovie.add(entermvnforupdate, 0, 10);
					TextField mvnumber_update = new TextField();
					updateMovie.add(mvnumber_update, 1, 10);
					Label entermvnameforupdate = new Label("UPDATE MOVIE NAME");
					updateMovie.add(entermvnameforupdate, 0, 20);
					TextField mvname_update = new TextField();
					updateMovie.add(mvname_update, 1, 20);
					Button update_b = new Button("UPDATE");
					updateMovie.add(update_b, 0, 30);
					Button b3 = new Button("BACK");
					updateMovie.add(b3, 0, 40);
					
					//assigning task to updatemovie button which will redirect user to updatemovie panel
					updatemovie.setOnAction(e->{
						updateMovie1.setScene(update);
						updateMovie1.show();
					});
					
					//assigning task to update_b button which will update movie name with the use of movie id
					update_b.setOnAction(e->{
						String mov_num_u=mvnumber_update.getText();
						int movie_id = Integer.parseInt(mov_num_u);
						String movie_name2=mvname_update.getText();
						
						try {
							//calling updateMovieDetail mathod of admin class
							object_admin.updateMovieDetail(movie_id,movie_name2);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					});
					
					//assigning task to b3 button which will redirect user to admin task pane
					b3.setOnAction(e->{
						adminmainpage.setScene(activity);
						adminmainpage.show();
				});		

	}

	public static void main(String[] args) {
		launch(args);
	}
}
