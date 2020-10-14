/**
 * Sample Skeleton for 'LoginPageView.fxml' Controller Class
 */

package Login;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import connectdb.ConnectionDB;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class LoginPageViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="pb"
    private ProgressBar pb; // Value injected by FXMLLoader

    @FXML // fx:id="doLogin"
    private Button doLogin; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassword"
    private PasswordField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader
    
    Connection con=ConnectionDB.doconnect();
    String MF="";
    
    @FXML
    void doLogin(ActionEvent event) 
    {
    	pb.setProgress(0.2f);
    	MF="Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst=con.prepareStatement("Select password from usertbl where Username=?");
			pst.setString(1,txtUsername.getText());
			ResultSet rs=pst.executeQuery();
			boolean jasus=false;
			pb.setProgress(0.4f);
			while(rs.next())
			{
				jasus=true;
				MF=rs.getString("Password");
				pb.setProgress(0.6f);
			
			 if (txtPassword.getText().equals(MF)) 
			 {
				 //pb.setProgress(1.0f);
	   	    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("MainDashboard/DashboardView.fxml")); 
	   				Scene scene = new Scene(root,1100,800);
	   				Stage stage=new Stage();
	   				stage.setTitle("DASHBOARD");
	   				stage.setScene(scene);
	   				stage.show();
	   				txtUsername.getScene().getWindow().hide();
		     }
			 else
			 {		
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("ERROR");
				 alert.setHeaderText("Incorrect Data");
				 alert.setContentText("Your password is incorrect!");
				 alert.showAndWait();
				 pb.setProgress(0);
		      }
			}
			if(jasus==false)
			{
				Alert alert = new Alert(AlertType.ERROR);
   	    		alert.setTitle("ERROR");
   	    		alert.setHeaderText("Incorrect Data");
   	    		alert.setContentText("Username is not correct...");
   	    		alert.showAndWait();
   	    		pb.setProgress(0);
			}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    void doExit(ActionEvent event) 
    {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setHeaderText("You want to Exit...");
    	alert.setContentText("Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK)
    		System.exit(0);
    }
    
    void doSound(String s)
    {
    	Media sound = new Media(new File(s).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    }
}
