/**
 * Sample Skeleton for '222CustomerTableView.fxml' Controller Class
 */

package CustomerHistory;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import connectdb.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CustomerTableViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tbl"
    private TableView<CustomerBean> tbl; // Value injected by FXMLLoader

    Connection con=ConnectionDB.doconnect();
    ObservableList<CustomerBean> list;
    String MF ="";
    
    @FXML // fx:id="comboid"
    private ComboBox<String> comboid; // Value injected by FXMLLoader
    

    @FXML // fx:id="dateSD"
    private DatePicker dateSD; // Value injected by FXMLLoader
    
    @FXML
    void doFindDate(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	if(dateSD.getEditor().getText().isEmpty()==false)
       	{
    		LocalDate local=dateSD.getValue();
        	java.sql.Date dos= Date.valueOf(local);
    	try {
			PreparedStatement pst=con.prepareStatement("Select * from Customerentry where DOS=?");
			pst.setDate(1,dos);
			doLoad(pst);
			doFillClmn();
    		} 
    	catch (SQLException e) 
    		{
			e.printStackTrace();
    		}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("ERROR");
	   		alert.setHeaderText("Empty Box");
	   		alert.setContentText("Please Enter Starting Date...");
	   		alert.showAndWait();
    	}
    }

    @FXML
    void dofindWithname(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst=con.prepareStatement("Select * from Customerentry where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			doLoad(pst);
			doFillClmn();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    }
    
    /*@FXML
    void doFetchWithCombo(ActionEvent event) 
    {     
       	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst=con.prepareStatement("Select * from customerentry where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			doLoad(pst);
			doFillClmn();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
*/    
    @FXML
    void doExport(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Customer Table");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tbl.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tbl.getColumns().get(j).getText());
        }

        for (int i = 0; i < tbl.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tbl.getColumns().size(); j++) {
                if(tbl.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(tbl.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }
		try { 
			FileChooser file=new FileChooser();
			file.setTitle("Save File");
			File f=file.showSaveDialog(null);
			file.getExtensionFilters().addAll(new ExtensionFilter("Excel", "*.xls", "*.xlsx"));
	    	if (f != null) 
	    	{
			FileOutputStream fileOut = new FileOutputStream(f.getName());
			workbook.write(fileOut);
			fileOut.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("File Exported..");
			alert.setContentText("Your file is exported to Excel.");
			alert.showAndWait();
	    	}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
    
    @FXML
    void dofetchall(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	comboid.getEditor().setText("");
    	dateSD.getEditor().setText("");
    	try {
			PreparedStatement pst = con.prepareStatement("select * from Customerentry");
			doLoad(pst);
			doFillClmn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	}
    
    void doFillClmn()
    {
    	TableColumn<CustomerBean,String> name= new TableColumn<>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	TableColumn<CustomerBean,String> mobno= new TableColumn<>("Mobile Number");
    	mobno.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	
    	TableColumn<CustomerBean,String> adrs= new TableColumn<>("Address");
    	adrs.setCellValueFactory(new PropertyValueFactory<>("address"));
    	
    	TableColumn<CustomerBean,String> dos= new TableColumn<>("Date of Start");
    	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));
    	
    	TableColumn<CustomerBean,Float> cqty= new TableColumn<>("Cow Milk Qty");
    	cqty.setCellValueFactory(new PropertyValueFactory<>("cqty"));
    	
    	TableColumn<CustomerBean,Float> bqty= new TableColumn<>("Buffalo Milk Qty");
    	bqty.setCellValueFactory(new PropertyValueFactory<>("bqty"));
    	
    	TableColumn<CustomerBean,Float> cprice= new TableColumn<>("Cow Milk Price");
    	cprice.setCellValueFactory(new PropertyValueFactory<>("cprice"));
    	
    	TableColumn<CustomerBean,Float> bprice= new TableColumn<>("Buff. Milk Qty");
    	bprice.setCellValueFactory(new PropertyValueFactory<>("bqty"));
    	
    	TableColumn<CustomerBean,Integer> s= new TableColumn<>("Status");
    	s.setCellValueFactory(new PropertyValueFactory<>("status"));
    	
    	tbl.getColumns().clear();
    	tbl.getColumns().addAll(name,mobno,adrs,dos,cqty,bqty,cprice,bprice,s);
    	tbl.setItems(list);
    
    }
    
    void doLoad(PreparedStatement pst)
    {	
    
    	list=FXCollections.observableArrayList();
    	try {	
        	ResultSet rs= pst.executeQuery();
        	boolean jasus=false;
       		while(rs.next())
       		{
       			jasus=true;
       			CustomerBean cb=new CustomerBean();
       			cb.setName(rs.getString("name"));
       			cb.setMobile(rs.getString("mobile"));
       			cb.setAddress(rs.getString("address"));
       			cb.setDos(rs.getString("dos"));
       			cb.setCqty(rs.getFloat("cqty"));
	       		cb.setBqty(rs.getFloat("bqty"));
	       		cb.setCprice(rs.getFloat("cprice"));
	       		cb.setBprice(rs.getFloat("bprice"));
	       		cb.setStatus(rs.getInt("status"));
	       		
	       		//System.out.println(rs.getString("name")+rs.getString("mobile")+rs.getString("address")+rs.getString("dos")+rs.getFloat("cqty")+rs.getFloat("bqty")+rs.getFloat("cprice")+rs.getFloat("bprice")+rs.getInt("status"));
	       		list.add(cb);
	       	}
       		if(jasus==false)
       		{
       			Alert alert = new Alert(AlertType.ERROR);
   	    		alert.setTitle("ERROR");
   	    		alert.setHeaderText("Empty Box");
   	    		alert.setContentText("Please Enter value...");
   	    		alert.showAndWait();
       		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    void doSound(String s)
    {
    	Media sound = new Media(new File(s).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    }
    
    void doLoadCombo()
    {
    	comboid.setOnKeyReleased(new EventHandler<KeyEvent>() 
        {
            public void handle(final KeyEvent keyEvent) 
            {
                handleEvent(keyEvent);
            }

			private void handleEvent(KeyEvent keyEvent) {
				MF="Typing.mp3";
				doSound(MF);
				
			}
        });

    	try {
			PreparedStatement pst=con.prepareStatement("Select distinct name from customerentry");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				comboid.getItems().add(rs.getString("name"));	
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	    doLoadCombo();
    	    
    }
}
