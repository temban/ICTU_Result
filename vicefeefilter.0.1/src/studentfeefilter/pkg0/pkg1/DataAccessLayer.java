/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfeefilter.pkg0.pkg1;


import com.gembox.spreadsheet.SpreadsheetInfo;
import com.sun.rowset.internal.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import org.shaded.apache.poi.hssf.usermodel.HSSFCell;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.shaded.apache.poi.ss.usermodel.DataFormatter;

/**
 *
 * @author KAMDEM VADECE
 */
public class DataAccessLayer {
     Connection con;
     DBconnect dbcon = new DBconnect();
     static int size;
      
   public DataAccessLayer(){
     
    }
  
  
   public void dbaddcourse(String course) throws SQLException{
   
       con = dbcon.getConnection(); 
       ResultSet coursename = con.createStatement().executeQuery("select id from course where coursename=\""+course+"\"");
       
    if(!coursename.next()){
       String sql = "insert into course (coursename) values (?)";
       PreparedStatement addcourse = con.prepareStatement(sql);
       addcourse.setString(1,course);
       addcourse.execute();
    }
   }
   
   public void dbdeletecourse(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "delete from course where coursename=?";
       PreparedStatement deletecourse = con.prepareStatement(sql);
       deletecourse.setString(1,course);
       deletecourse.execute();
   }
   
   public ResultSet[] dbreadcourse() throws SQLException{
       con = dbcon.getConnection();
       ResultSet coursename = con.createStatement().executeQuery("select coursename from course");
       
       ResultSet lecturer = con.createStatement().executeQuery("select lecturer from course");
      
        return new ResultSet[]{coursename,lecturer};
   
   }
    public ResultSet dbreadstudent(String course) throws SQLException{
       con = dbcon.getConnection();
       ResultSet coursename = con.createStatement().executeQuery("select sname from "+course+"");
      
        return coursename;
   
   }
   
    public void dbaddcoursetable(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS "+course+"(id int AUTO_INCREMENT primary KEY, sname varchar(100), smatricule varchar(100), se_mail varchar(100), sphonenumber varchar(100), sfeestatus varchar(20))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
   
    public void dbdeletecoursetable(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "DROP table if exists "+course+"";
       PreparedStatement deletecoursetable = con.prepareStatement(sql);
       deletecoursetable.execute();
   }
    
    public void dbaddexcelsheet (String course, File file) throws SQLException, FileNotFoundException, IOException{
     con = dbcon.getConnection(); 
       String sql = "insert into "+course+" (sname,smatricule,se_mail,sphonenumber,sfeestatus) values (?,?,?,?,?)";
       PreparedStatement addexcelsheet = con.prepareStatement(sql);
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow firstRow;
       
       String defaultfeestatus = "MF";
       Iterator rowIter = hssfSheet.rowIterator();
       String data;
       HSSFRow r = (HSSFRow)rowIter.next(); 
       short lastCellNum = r.getLastCellNum();
        int[] dataCount = new int[lastCellNum];
        int col = 0;
        
        while(rowIter.hasNext()) {
            Iterator cellIter = ((HSSFRow)rowIter.next()).cellIterator();
            while(cellIter.hasNext()) {
                HSSFCell cell = (HSSFCell)cellIter.next();
                col = cell.getColumnIndex();
                dataCount[col] += 1;
                DataFormatter df = new DataFormatter();
                data = df.formatCellValue(cell);
                System.out.println("Data: " + data);
            }
        }
        f.close();
  
        for(int x = 0; x < dataCount.length; x++) {
           size = dataCount[x];
        }
       for(int i=0; i<=size; i++){
        firstRow = hssfSheet.getRow(i);
       
        addexcelsheet.setString(1,firstRow.getCell(0).getStringCellValue());
        addexcelsheet.setString(2,firstRow.getCell(1).getStringCellValue());
        addexcelsheet.setString(3,firstRow.getCell(2).getStringCellValue());
        addexcelsheet.setString(4,firstRow.getCell(3).getStringCellValue());
        addexcelsheet.setString(5,defaultfeestatus);
      
       addexcelsheet.execute();
       }
   
    }
     public ResultSet[] dbreadcoursedata(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sqln = "SELECT sname FROM "+coursen;
       PreparedStatement readcoursedatan = con.prepareStatement(sqln);
      
       
       String sqlm = "SELECT smatricule FROM "+coursen;
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sfeestatus FROM "+coursen;
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen;
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       ResultSet coursename = readcoursedatan.executeQuery();
       ResultSet coursematricule = readcoursedatam.executeQuery();
       ResultSet feestatus = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{coursename,coursematricule,feestatus,id};
   
   }
   
      public ResultSet[] dbreadcoursedataparticularstudent(String coursen,String sname) throws SQLException{
       con = dbcon.getConnection(); 
      
       String sqlm = "SELECT smatricule FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sfeestatus FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       
       ResultSet smatricule = readcoursedatam.executeQuery();
       ResultSet feestatus = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{smatricule,feestatus,id};
   
   }
   
     public void dbstudentpaymentstatus(String coursen ,String matricule,String feestatus) throws SQLException{
       con = dbcon.getConnection();
       String sql = "UPDATE "+coursen+" SET sfeestatus=\""+feestatus+"\" WHERE smatricule=\""+matricule+"\"";
       PreparedStatement studentpaymentstatus = con.prepareStatement(sql);
     
       studentpaymentstatus.execute();
   }
     
   
      public ResultSet dbmfchecker(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT sfeestatus FROM "+coursen;
       PreparedStatement mfchecker = con.prepareStatement(sql);
      
       ResultSet coursename = mfchecker.executeQuery();

      
        return coursename;
   
   }
      
      public ResultSet dbexportcourse(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT * FROM "+coursen;
       PreparedStatement courselist = con.prepareStatement(sql);
      
       ResultSet course = courselist.executeQuery();

      
        return course;
     
}

    public void dbaddlecturertocourse(String lecturer, String coursen) throws SQLException {
       con = dbcon.getConnection();
       String sql = "UPDATE course SET lecturer=\""+lecturer+"\" WHERE coursename=\""+coursen+"\"";
       PreparedStatement studentpaymentstatus = con.prepareStatement(sql);
     
       studentpaymentstatus.execute();  
    }
    
    public String dbreadlecturer(String coursen) throws SQLException{
    con = dbcon.getConnection(); 
       String sql = "SELECT lecturer FROM course WHERE coursename =\""+coursen+"\"" ;
       PreparedStatement le = con.prepareStatement(sql);
      
       ResultSet lectu = le.executeQuery();
       lectu.next();
       String lecturername = lectu.getString("lecturer");
    return lecturername;
    }
}
