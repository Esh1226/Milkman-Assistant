/**
 * Sample Skeleton for 'DeleteRecordsView.fxml' Controller Class
 */

package DeleteRecords;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectdb.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DeleteRecordsViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboid"
    private ComboBox<String> comboid; // Value injected by FXMLLoader

    @FXML // fx:id="txtSD"
    private TextField txtSD; // Value injected by FXMLLoader

    @FXML // fx:id="txtMob"
    private TextField txtMob; // Value injected by FXMLLoader
    Connection con=ConnectionDB.doconnect();
    String MF="";

    @FXML
    void doDelete(ActionEvent event) 
    {
    	MF="Bomb.mp3";
    	doSound(MF);
    	
    	try {
			PreparedStatement pst = con.prepareStatement("Delete from customerentry where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem()); 
			pst.executeUpdate();
			pst = con.prepareStatement("Delete from routine where name=?");			
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			pst.executeUpdate();
			pst = con.prepareStatement("Delete from bills where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	comboid.getEditor().setText("");
    	comboid.getItems().clear();
    	doLoad();
    	txtMob.setText("");
    	txtSD.setText("");
    	
    }
   	
    @FXML
    void doFetchWithCombo(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst = con.prepareStatement("select Mobile,DOS from customerentry where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
	    	ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				txtSD.setText(rs.getDate("DOS")+"");
				txtMob.setText(rs.getString("mobile"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    void doLoad()
    {	
    	try {
			PreparedStatement pst = con.prepareStatement("select * from Customerentry");
        	ResultSet rs= pst.executeQuery();
       		while(rs.next())
       		{
       		String name=rs.getString("name");
       		comboid.getItems().add(name);
       		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
    void initialize() 
    {
    	doLoad();
       
    }
}
