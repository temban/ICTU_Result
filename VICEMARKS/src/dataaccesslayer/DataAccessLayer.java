/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccesslayer;


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
     static int sizes;
     static String cnames ="";
     static String lectname="";
      
   public DataAccessLayer(){
     
    }
  
   public void dbaddcourse() throws SQLException{
   
       con = dbcon.getConnection(); 
       ResultSet coursename = con.createStatement().executeQuery("select id from course where coursename=\""+cnames+"\"");
       
    if(!coursename.next()){
       String sql1 = "insert into course (coursename,lecturer) values (?,?)";
       PreparedStatement addcourse = con.prepareStatement(sql1);
       addcourse.setString(1,cnames);
       addcourse.setString(2,lectname);
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
      ResultSet lecturer  = con.createStatement().executeQuery("select lecturer from course");
        return new ResultSet[]{coursename,lecturer};
   
   }
    public ResultSet dbreadstudent(String course) throws SQLException{
       con = dbcon.getConnection();
       ResultSet coursename = con.createStatement().executeQuery("select sname from "+course+"");
      
        return coursename;
   
   }
   
    public void dbaddcoursetable() throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "CREATE table if not EXISTS "+cnames+"(id int AUTO_INCREMENT primary KEY, sname varchar(100), smatricule varchar(100), se_mail varchar(100), sphonenumber varchar(100), sgrade varchar(20))";
       PreparedStatement addcoursetable = con.prepareStatement(sql);
       addcoursetable.execute();
   }
   
    public void dbdeletecoursetable(String course) throws SQLException{
       con = dbcon.getConnection(); 
       
       String sql = "DROP table if exists "+course+"";
       PreparedStatement deletecoursetable = con.prepareStatement(sql);
       deletecoursetable.execute();
   }
    
    public void dbaddexcelsheet (File file) throws SQLException, FileNotFoundException, IOException{
     con = dbcon.getConnection(); 
       String sql = "insert into "+cnames+" (sname,smatricule,se_mail,sphonenumber,sgrade) values (?,?,?,?,?)";
       PreparedStatement addexcelsheet = con.prepareStatement(sql);
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow firstRow;
       
       //String defaultsgrade = "PENDING";
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
       for(int i=3; i<=size; i++){
        firstRow = hssfSheet.getRow(i);
       
        addexcelsheet.setString(1,firstRow.getCell(1).getStringCellValue());
        addexcelsheet.setString(2,firstRow.getCell(2).getStringCellValue());
        addexcelsheet.setString(3,firstRow.getCell(3).getStringCellValue());
        addexcelsheet.setString(4,firstRow.getCell(4).getStringCellValue());
        addexcelsheet.setString(5,firstRow.getCell(5).getStringCellValue());
        //addexcelsheet.setString(5,defaultsgrade);
      
       addexcelsheet.execute();
       }
   
    }
     public ResultSet[] dbreadcoursedata(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sqln = "SELECT sname FROM "+coursen;
       PreparedStatement readcoursedatan = con.prepareStatement(sqln);
      
       
       String sqlm = "SELECT smatricule FROM "+coursen;
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+coursen;
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen;
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       ResultSet coursename = readcoursedatan.executeQuery();
       ResultSet coursematricule = readcoursedatam.executeQuery();
       ResultSet grade = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{coursename,coursematricule,grade,id};
   
   }
   
      public ResultSet[] dbreadcoursedataparticularstudent(String coursen,String sname) throws SQLException{
       con = dbcon.getConnection(); 
      
       String sqlm = "SELECT smatricule FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readfeestatus = con.prepareStatement(sqls);
       
       String sqli = "SELECT id FROM "+coursen+" where sname=\""+sname+"\"";
       PreparedStatement readid = con.prepareStatement(sqli);
     
         
       
       ResultSet smatricule = readcoursedatam.executeQuery();
       ResultSet feestatus = readfeestatus.executeQuery();
       ResultSet id = readid.executeQuery();
      
        return new ResultSet[]{smatricule,feestatus,id};
   
   }
   
     public void dbstudentpaymentstatus(String coursen ,String matricule,String grade) throws SQLException{
       con = dbcon.getConnection();
       String sql = "UPDATE "+coursen+" SET sgrade=\""+grade+"\" WHERE smatricule=\""+matricule+"\"";
       PreparedStatement studentpaymentstatus = con.prepareStatement(sql);
     
       studentpaymentstatus.execute();
   }
     
   
      public ResultSet dbmfchecker(String coursen) throws SQLException{
       con = dbcon.getConnection(); 
       String sql = "SELECT sgrade FROM "+coursen;
       PreparedStatement mfchecker = con.prepareStatement(sql);
      
       ResultSet coursename = mfchecker.executeQuery();

      
        return coursename;
   
   }
     
       public void dbgetcoursenameandlecturerfromexcelsheet(File file) throws SQLException, IOException{
       con = dbcon.getConnection();
       
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow Row0;
        HSSFRow Row1;

        Row0 = hssfSheet.getRow(0);
        Row1 = hssfSheet.getRow(1);
       
       cnames= Row0.getCell(1).getStringCellValue();
       lectname= Row1.getCell(1).getStringCellValue();
       
        }
       public ResultSet[] dbgetmatriculeemailgrade(String cn) throws SQLException{
       con = dbcon.getConnection(); 
       String sqlm = "SELECT smatricule FROM "+cn;
       PreparedStatement readcoursedatam = con.prepareStatement(sqlm);
       
       String sqls = "SELECT sgrade FROM "+cn;
       PreparedStatement readfsgrade = con.prepareStatement(sqls);
       
       String sqli = "SELECT se_mail FROM "+cn;
       PreparedStatement reademail = con.prepareStatement(sqli);
     
         
       
       ResultSet smatricule = readcoursedatam.executeQuery();
       ResultSet sgrade = readfsgrade.executeQuery();
       ResultSet email = reademail.executeQuery();
      
        return new ResultSet[]{smatricule,sgrade,email};
   
       
       }
      
       
       public void dbaddgradetocourse(File file, String cn) throws SQLException, FileNotFoundException, IOException{
          con = dbcon.getConnection(); 
       
       String sql1 = "CREATE table if not EXISTS "+cn+"grade(id int AUTO_INCREMENT primary KEY, sname varchar(100), smatricule varchar(100),sgrade varchar(20))";
       PreparedStatement addgradetable = con.prepareStatement(sql1);
       addgradetable.execute();
       
       
       
       
      
       String sql2 = "insert into "+cn+"grade (sname,smatricule,sgrade) values (?,?,?)";
       PreparedStatement addexcelsheet = con.prepareStatement(sql2);
       FileInputStream f = new FileInputStream(new File(file.getAbsolutePath()));
       HSSFWorkbook hssfWorkbook=new HSSFWorkbook(f);
        HSSFSheet hssfSheet=  hssfWorkbook.getSheetAt(0);
        HSSFRow firstRow;
  
       Iterator rowIter = hssfSheet.rowIterator();
       String data;
       HSSFRow r = (HSSFRow)rowIter.next(); 
       short lastCellNum = r.getLastCellNum();
        int[] dataCount = new int[lastCellNum];
        int col = 0;
        sizes = 0;
        while(rowIter.hasNext()) {
            sizes +=1; 
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
  
      
       for(int i=3; i<=sizes; i++){
        firstRow = hssfSheet.getRow(i);
       
        addexcelsheet.setString(1,firstRow.getCell(0).getStringCellValue());
        addexcelsheet.setString(2,firstRow.getCell(1).getStringCellValue());
        addexcelsheet.setString(3,firstRow.getCell(2).getStringCellValue());
      
      
       addexcelsheet.execute();
       }
 
       
       
       String sql3 = "SELECT smatricule FROM "+cn;
       PreparedStatement readematricule = con.prepareStatement(sql3); 
       ResultSet smatricule = readematricule.executeQuery(); 
       while(smatricule.next()){
       String sm = smatricule.getString("smatricule");
       String sql4 = "SELECT sgrade FROM "+cn+"grade WHERE smatricule=\""+sm+"\"";
       PreparedStatement readematriculegrade = con.prepareStatement(sql4); 
       ResultSet idgrade = readematriculegrade.executeQuery();
       if(idgrade.next()){
       String sgrade = idgrade.getString("sgrade");
       String sql5 = "SELECT sgrade FROM "+cn+" WHERE smatricule=\""+sm+"\"";
       PreparedStatement readfeestatus = con.prepareStatement(sql5); 
       ResultSet feestatus = readfeestatus.executeQuery();
       feestatus.next();
       String fs = feestatus.getString("sgrade");
       if(fs.equals("PAYED")){
       String sql6 = "UPDATE "+cn+" SET sgrade=\""+sgrade+"\" WHERE smatricule=\""+sm+"\"";
       PreparedStatement studentgrade = con.prepareStatement(sql6);
     
       studentgrade.execute();
       }
       }
       }
       
       
       String sql6 = "DROP table if exists "+cn+"grade";
       PreparedStatement deletegradetable = con.prepareStatement(sql6);
       deletegradetable.execute();
       
       
       
       }
}
