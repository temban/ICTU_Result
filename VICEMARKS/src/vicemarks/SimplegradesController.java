/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vicemarks;

import dataaccesslayer.DataAccessLayer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class SimplegradesController implements Initializable {

    @FXML
    private Label notifgood;
    @FXML
    private Label notifbad;
    @FXML
    private ComboBox<String> corn;
    @FXML
    private Button back_home;
    @FXML
    private Button addgrades;
    @FXML
    private AnchorPane simplegradespage;
     @FXML
    private TableColumn<alltableviewwithtextfield, ComboBox> grade; 
    @FXML
    private TableView<alltableviewwithtextfield> mfcoursename;
    @FXML
    private TableColumn<alltableviewwithtextfield, String> sname;
    @FXML
    private TableColumn<alltableviewwithtextfield, String> smatricule;
    Collections collections;
    ObservableList<alltableviewwithtextfield> coursedatalist = FXCollections.observableArrayList();
    ObservableList<String> coursenamelist = FXCollections.observableArrayList();
    ObservableList<String> studentenamelist = FXCollections.observableArrayList();
 
    @FXML
    private TableColumn<alltableviewwithtextfield, Integer> id;
    @FXML
    private TableColumn<alltableviewwithtextfield, String> acgrade;
    @FXML
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[]= dbl.dbreadcourse();
            while(rs[0].next()){
              
                String cname = rs[0].getString("coursename");
                coursenamelist.add(cname);
                
            }
            collections.sort(coursenamelist);
            
            TextFields.bindAutoCompletion(corn.getEditor(), coursenamelist).setOnAutoCompleted(e -> {
                
                try {
                    notifgood.setText(" ");
                    
                    String cn = corn.getEditor().getText();
                    mfcoursename.getItems().clear();
                    
                    ResultSet rss[] = dbl.dbreadcoursedata(cn);
                    while(rss[0].next() && rss[1].next() && rss[2].next() && rss[3].next()){
                        
                        ComboBox grade = new ComboBox();
                        comboboxaction(grade);
                        ObservableList<String> gradelist = FXCollections.observableArrayList();
                        gradelist.add("A");
                        gradelist.add("B+");
                        gradelist.add("B");
                        gradelist.add("C+");
                        gradelist.add("C");
                        gradelist.add("D+");
                        gradelist.add("D");
                        gradelist.add("E");
                        gradelist.add("F");
                        gradelist.add("MF");
                        grade.setItems(gradelist);
                        String cname = rss[0].getString("sname");
                        String smatri = rss[1].getString("smatricule");
                        String acgrade = rss[2].getString("sgrade");
                        Integer idn = rss[3].getInt("id");
                        coursedatalist.add(new alltableviewwithtextfield(cname,smatri,grade,idn,acgrade));
                        
                    }
                    mfcoursename.setItems(coursedatalist);
                    sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
                    smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
                    grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
                    id.setCellValueFactory(new PropertyValueFactory<>("id"));
                    acgrade.setCellValueFactory(new PropertyValueFactory<>("acgrade"));
                } catch (SQLException ex) {
                    Logger.getLogger(GradesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
            });
            
            corn.setItems(coursenamelist);
        } catch (SQLException ex) {
            Logger.getLogger(SimplegradesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btncorn(ActionEvent event) throws SQLException {
        
        notifgood.setText(" ");
        
         String cn = corn.getValue();
        mfcoursename.getItems().clear();
       
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedata(cn);
            while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
               
                ComboBox grade = new ComboBox();
                comboboxaction(grade);
                ObservableList<String> gradelist = FXCollections.observableArrayList();
                gradelist.add("A");
                gradelist.add("B+");
                gradelist.add("B");
                gradelist.add("C+");
                gradelist.add("C");
                gradelist.add("D+");
                gradelist.add("D");
                gradelist.add("E");
                gradelist.add("F");
                gradelist.add("MF");
                grade.setItems(gradelist);
                String cname = rs[0].getString("sname");
                String smatri = rs[1].getString("smatricule");
                String acgrade = rs[2].getString("sgrade");
                Integer idn = rs[3].getInt("id");
                coursedatalist.add(new alltableviewwithtextfield(cname,smatri,grade,idn,acgrade));
                
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            acgrade.setCellValueFactory(new PropertyValueFactory<>("acgrade"));
            
            
            
    }

    @FXML
    private void btnback_home(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WELCOME TO THE GRADE INPUT SYSTEM");
        stage.show(); 
    }

    @FXML
    private void btnaddgrades(ActionEvent event) throws SQLException, IOException {
        
           notifgood.setText(" ");
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        String cn = corn.getValue();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT GRADES EXCEL SHEET");
        File file = fileChooser.showOpenDialog(simplegradespage.getScene().getWindow());
       
        dbaccesslayer.dbaddgradetocourse(file,cn);
        
        
        mfcoursename.getItems().clear();
       
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedata(cn);
            while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
               
                ComboBox grade = new ComboBox();
                comboboxaction(grade);
                ObservableList<String> gradelist = FXCollections.observableArrayList();
                gradelist.add("A");
                gradelist.add("B+");
                gradelist.add("B");
                gradelist.add("C+");
                gradelist.add("C");
                gradelist.add("D+");
                gradelist.add("D");
                gradelist.add("E");
                gradelist.add("F");
                gradelist.add("MF");
                grade.setItems(gradelist);
                String cname = rs[0].getString("sname");
                String smatri = rs[1].getString("smatricule");
                String acgrade = rs[2].getString("sgrade");
                Integer idn = rs[3].getInt("id");
                coursedatalist.add(new alltableviewwithtextfield(cname,smatri,grade,idn,acgrade));
                
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            acgrade.setCellValueFactory(new PropertyValueFactory<>("acgrade"));
            
            
    }
    
    public void comboboxaction(ComboBox combo){
combo.setOnAction(e -> {
    try {
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        
        for(alltableviewwithtextfield table : coursedatalist)
        {
            if(table.grade.getValue()!=null){
              
                    String cn = corn.getValue().toString();
                    String cname = table.getSmatricule();
                    String sgrade = table.grade.getValue().toString();
                    dbaccesslayer.dbstudentpaymentstatus(cn, cname, sgrade);
                    notifgood.setText("SAVED");
               
                
            }
            
        }
        
        String cn = corn.getValue();
        mfcoursename.getItems().clear();
        DataAccessLayer dbl = new DataAccessLayer();
        ResultSet rs[] = dbl.dbreadcoursedata(cn);
        while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
            
            ComboBox grade = new ComboBox();
            comboboxaction(grade);
            ObservableList<String> gradelist = FXCollections.observableArrayList();
            gradelist.add("A");
            gradelist.add("B+");
            gradelist.add("B");
            gradelist.add("C+");
            gradelist.add("C");
            gradelist.add("D+");
            gradelist.add("D");
            gradelist.add("E");
            gradelist.add("F");
            gradelist.add("MF");
            grade.setItems(gradelist);
            String cname = rs[0].getString("sname");
            String smatri = rs[1].getString("smatricule");
            String sge = rs[2].getString("sgrade");
            Integer idn = rs[3].getInt("id");
            coursedatalist.add(new alltableviewwithtextfield(cname,smatri,grade,idn,sge));
            
        }
        mfcoursename.setItems(coursedatalist);
        sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
        smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        acgrade.setCellValueFactory(new PropertyValueFactory<>("acgrade"));
                    } 
     catch (SQLException ex) {
        Logger.getLogger(GradesController.class.getName()).log(Level.SEVERE, null, ex);
    }
              
});
}    
    
}
