/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author gadisa
 */
public class SellerModel {
   
    private Connection conn;
   
    public SellerModel(){
       
        
    }
    
   
 public void addItemToDatabase(String name ,String category,String sellername) {
     
      
         String insrt = "insert into sellerItem(name,category,sellername) values (?,?,?) ";
         setUpConnection();
           
       try{
           PreparedStatement insertStatement = conn.prepareStatement(insrt);
              insertStatement.setString(1, name);
              insertStatement.setString(2,category);
              insertStatement.setString(3,sellername);
             
              insertStatement.executeUpdate();
       }catch(Exception e){
           
           System.out.println("Exception line 41");
       }
    
 
 }
 //public void addImageToDatabase(String itemName,int cost , int imgNo,File img)
public void addImageToDatabase(String itemName,int cost , int imgNo,File img){
     String insert =  "insert into itemImage(itemName,cost,imgNo,image) values (?, ?,?,?) ";
    setUpConnection();
     
     try{     
              FileInputStream imagestream = new FileInputStream(img);
              PreparedStatement insertStatement = conn.prepareStatement(insert);
              insertStatement.setString(1, itemName );
              insertStatement.setInt(2,cost);
              insertStatement.setInt(3,imgNo);
              insertStatement.setBinaryStream(4, imagestream );
              insertStatement.executeUpdate();
       }catch(Exception e){System.out.println("Exception line 78");}
 }

  public Boolean authenticateBuyer(String name, String password) throws SQLException{
        Boolean existance = false;
        setUpConnection();
        ResultSet rs = null;
        String retrieve= "select * from sellers where name=? and password=? ";
         
         PreparedStatement stmt = conn.prepareStatement(retrieve);
                stmt.setString(1,name);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                
               if(rs.next()){/*means user exists*/
                    return true;
                }
                else{

                    return false;

                }
               }
 
   public Connection setUpConnection(){
     
       String db_url = "jdbc:mysql://localhost/sweetdeal?characterEncoding=utf8";
         String  db_port = "3306";
          String  db_user = "root";
          String db_password = "";
       try {
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(SellerModel.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           conn = DriverManager.getConnection(db_url, db_user, db_password);
       } catch (SQLException ex) {
           Logger.getLogger(SellerModel.class.getName()).log(Level.SEVERE, null, ex);
       }
       
             return conn; 
       
       }
 
     public DefaultComboBoxModel returnComboModel(){
       DefaultComboBoxModel catMod = new DefaultComboBoxModel();
       catMod.addElement("clothes");
       catMod.addElement("Books");
       catMod.addElement("Shoes");
       catMod.addElement("all");
      
       return catMod;
   } 
 
    
    
}
