/**
 * Sample Skeleton for 'DatabaseView.fxml' Controller Class
 */

package DatabaseDashboard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import connectdb.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class DatabaseViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="lbl"
    private Label lbl; // Value injected by FXMLLoader
    
    //Connection con=ConnectionDB.doconnect();
    String MF="Sound.mp3";;
    
    @FXML
    void doAboutUs(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("AboutUs/AboutUsView.fxml")); 
			Scene scene = new Scene(root,776,693);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void doBack(ActionEvent event) 
    {
    	doSound(MF);
    	lbl.getScene().getWindow().hide();
    	
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainDashboard/DashboardView.fxml"));
			Scene scene = new Scene(root,1100,800);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void doBillPayment(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillPayment/BillPaymentView.fxml")); 
			Scene scene = new Scene(root,600,600);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doBillingConsole(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillingConsole/BillTableView.fxml")); 
			Scene scene = new Scene(root,1000,800);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doCustomerHistory(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerHistory/CustomerTableView.fxml")); 
			Scene scene = new Scene(root,950,723);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doCustomerReg(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerRegistration/CustmerEntryView.fxml")); 
			Scene scene = new Scene(root,600,567);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doDeleteRecords(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("DeleteRecords/DeleteRecordsView.fxml")); 
			Scene scene = new Scene(root,549,377);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doGenerateBill(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Bill/BillView.fxml")); 
			Scene scene = new Scene(root,676,790);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doLogout(ActionEvent event) 
    { 
    	doSound(MF);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setHeaderText("You want to Log Out...");
    	alert.setContentText("Are you sure?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK)
    	{
    		lbl.getScene().getWindow().hide();
    		try {
    			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Login/LoginPageView.fxml")); 
    			Scene scene = new Scene(root,531,373);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
    			
    			} 
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    }

    @FXML
    void doVariationLog(ActionEvent event) 
    {
    	doSound(MF);
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VariationLog/RETableView.fxml")); 
			Scene scene = new Scene(root,907,749);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doVaritionEntry(ActionEvent event) 
    {
    	doSound(MF);
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VariationEntry/RegularEntryView.fxml")); 
			Scene scene = new Scene(root,600,600);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();	
		} 
    	catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    void doSound(String s)
    {
    	Media sound = new Media(new File(s).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
