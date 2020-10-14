/**
 * Sample Skeleton for 'BillTableView.fxml' Controller Class
 */

package BillingConsole;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class BillTableViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tbl"
    private TableView<BillBean> tbl; // Value injected by FXMLLoader

    @FXML // fx:id="rb1"
    private RadioButton rb1; // Value injected by FXMLLoader

    @FXML // fx:id="BILL"
    private ToggleGroup BILL; // Value injected by FXMLLoader

    @FXML // fx:id="rb2"
    private RadioButton rb2; // Value injected by FXMLLoader

    @FXML // fx:id="comboid"
    private ComboBox<String> comboid; // Value injected by FXMLLoader
   
    @FXML // fx:id="lblAmnt"
    private Label lblAmnt; // Value injected by FXMLLoader

    Connection con=ConnectionDB.doconnect();
    ObservableList<BillBean> list;
    String MF="";

    @FXML
    void doExport(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Bills");

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

        FileOutputStream fileOut;
		try { 
			FileChooser fileChooser = new FileChooser();
	    	File selectedFile = fileChooser.showSaveDialog(null);
	    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel", "*.xls", "*.xlsx","*.csv"));
	    	if (selectedFile != null) 
	    	{
			fileOut = new FileOutputStream(selectedFile.getName());
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
    void doFetchWithName(ActionEvent event) 
    {

		rb1.setSelected(false);
    	rb2.setSelected(false);
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst = con.prepareStatement("select * from bills where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			doLoad(pst);
	    	doFillColumn();
	    	pst=con.prepareStatement("select sum(amnt) as TA from bills where name=?");
	    	pst.setString(1,comboid.getSelectionModel().getSelectedItem());
	    	ResultSet rs=pst.executeQuery();
			while(rs.next()){
			lblAmnt.setText("Total Amount:"+rs.getFloat("TA"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @FXML
    void doFetchPaid(ActionEvent event) 
    {
    	comboid.getEditor().setText("");
    	MF="Sound.mp3";
    	doSound(MF);
		try {
			PreparedStatement pst = con.prepareStatement("select * from bills where status=1");
			doLoad(pst);
	    	doFillColumn();
	    	pst=con.prepareStatement("select sum(amnt) as TA from bills where status=1");
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
			lblAmnt.setText("Total Amount:"+rs.getFloat("TA"));
			}
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doFetchPending(ActionEvent event) 
    {
    	comboid.getEditor().setText("");
    	MF="Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst = con.prepareStatement("select * from bills where status=0");
			doLoad(pst);
	    	doFillColumn();
	    	pst=con.prepareStatement("select sum(amnt) as TA from bills where status=0");
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
			lblAmnt.setText("Total Amount:"+rs.getFloat("TA"));
			}
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doFetchAll(ActionEvent event) 
    {
    	comboid.getEditor().setText("");
		rb1.setSelected(false);
    	rb2.setSelected(false);
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst = con.prepareStatement("select * from bills");
			doLoad(pst);
			doFillColumn();
			pst=con.prepareStatement("select sum(amnt) as TA from bills");
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
			lblAmnt.setText("Total Amount:"+rs.getFloat("TA"));
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	rb1.setSelected(false);
    	rb2.setSelected(false);
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
       			BillBean bb=new BillBean();
       			bb.setName(rs.getString("name"));
       			bb.setSd(rs.getString("Start date"));
       			bb.setEd(rs.getString("End date"));
       			bb.setCq(rs.getFloat("cqty"));
	       		bb.setBq(rs.getFloat("bqty"));
	       		bb.setAmnt(rs.getFloat("amnt"));
	       		bb.setS(rs.getInt("status"));
	       		//System.out.println(rs.getString("name")+rs.getString("mobile")+rs.getString("address")+rs.getString("dos")+rs.getFloat("cqty")+rs.getFloat("bqty")+rs.getFloat("cprice")+rs.getFloat("bprice")+rs.getInt("status"));
	       		list.add(bb);
	       	}
   		if(jasus==false)
   		{
   			Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("ERROR");
	    		alert.setHeaderText("Incorrect Data");
	    		alert.setContentText("Entered value is not valid...");
	    		alert.showAndWait();
   		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

    }
    
    void doFillColumn()
    {
    	TableColumn<BillBean,String> n=new TableColumn<>("Name");
    	n.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	TableColumn<BillBean,String> sd=new TableColumn<>("Start Date");
    	sd.setCellValueFactory(new PropertyValueFactory<>("sd"));
    	
    	TableColumn<BillBean,String> ed=new TableColumn<>("End Date");
    	ed.setCellValueFactory(new PropertyValueFactory<>("ed"));
    	
    	TableColumn<BillBean,String> cq=new TableColumn<>("CQty");
    	cq.setCellValueFactory(new PropertyValueFactory<>("cq"));
    	
    	TableColumn<BillBean,String> bq=new TableColumn<>("BQty");
    	bq.setCellValueFactory(new PropertyValueFactory<>("bq"));
    	
    	TableColumn<BillBean,Float> a=new TableColumn<>("Amount");
    	a.setCellValueFactory(new PropertyValueFactory<>("amnt"));

    	TableColumn<BillBean,Integer> s=new TableColumn<>("Status");
    	s.setCellValueFactory(new PropertyValueFactory<>("s"));
    	
    	tbl.getColumns().clear();
    	tbl.getColumns().addAll(n,sd,ed,cq,bq,a,s);
    	tbl.setItems(list);
    	
    }
    
    void doFetchCombo()
    {
    	comboid.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() 
        {
       
			public void handle(javafx.scene.input.KeyEvent event) {
				MF="Typing.mp3";
				doSound(MF);		
				
			}
        });
		try 
		{
			PreparedStatement pst = con.prepareStatement("select distinct name from bills");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
				{
				comboid.getItems().add(rs.getString(1));
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
    void initialize() {
    	doFetchCombo();

    }
}
