/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListModel;

/**
 *
 * @author gadisa
 */
public class BuyerModel {
       private Connection conn;
      
     
       
    public BuyerModel(){
   
       
      
        
    }
    
    
    public void addItemtoDB(String itemName,String byrName,String byrAddress){
            String insrt = "insert into ordereditem(itemName,buyerName,buyerAdress) values (?,?,?) ";
         setUpConnection();
           
       try{
           PreparedStatement insertStatement = conn.prepareStatement(insrt);
              insertStatement.setString(1, itemName);
              insertStatement.setString(2,byrName);
              insertStatement.setString(3,byrAddress);
              insertStatement.executeUpdate();
       }catch(Exception e){System.out.println("Exception line 54");}
    }
    
    public void addItemImageToDB(String itemName,int imgNumber){
        
         String insrt = "insert into orderdimage(itemName,imgNumber) values (?,?) ";
         setUpConnection();
           
       try{
           PreparedStatement insertStatement = conn.prepareStatement(insrt);
              insertStatement.setString(1, itemName);
              insertStatement.setInt(2,imgNumber);
              insertStatement.executeUpdate();
       }catch(Exception e){System.out.println("Exception line 54");}
        
    }
    

   public ListModel retriveItemNameFromDatabase(){
       ResultSet rs = null;
       DefaultListModel listMod = new   DefaultListModel();
       String retrieve = "select name from sellerItem ";
       setUpConnection();
        try{
           Statement st=conn.createStatement(); 
           rs = st.executeQuery(retrieve);
       
            while(rs.next()){
               String name = rs.getString("name");
               listMod.addElement(name);
               }
        
          }catch(Exception e){System.out.println("error while retriving item name");}
       
        return listMod;
    }
   
    public ListModel retriveItemNameFromDatabase(String selectedCategory){
       ResultSet rs = null;
       DefaultListModel listMod = new   DefaultListModel();
       String retrieve = "select name from sellerItem where category = ?";
     
       setUpConnection();
        try{
          PreparedStatement stmt = conn.prepareStatement(retrieve);
          stmt.setString(1,selectedCategory);
          rs = stmt.executeQuery();
              
        while(rs.next()){
            String name = rs.getString("name");
            listMod.addElement(name);
            
        }
        
         }catch(Exception e){System.out.println("error while retriving item name");}
       
        return listMod;
    }
    
    
    
   public ImageIcon retriveFrstimageForFrstItem(){
       
          ResultSet rs =null;
          ImageIcon  icon = null;
          byte[] imagedata = null ;
          Image img;
          String frstItemName = retriveItemNameFromDatabase().getElementAt(0).toString();
          String retrieve = "select image from itemImage ";
          
       try{
         Statement st=conn.createStatement(); 
         rs = st.executeQuery(retrieve);
         while(rs.next()){
           imagedata = rs.getBytes("image") ;
           }
         img = Toolkit.getDefaultToolkit().createImage(imagedata);
         icon =new ImageIcon(img);
      }catch(Exception e){}
       
       return icon;
   }
   
   public    ArrayList<ImageIcon> retriveImagesForSelectedItem(String itemName){
       setUpConnection();
        ResultSet rs = null;
        ArrayList<ImageIcon> imgList = new ArrayList<>();
        ImageIcon  icon = null;
        byte[] imagedata = null ;
        Image img = null;
        try{
          String retrieve = "select image from itemImage where itemName=? ";
          PreparedStatement stmt = conn.prepareStatement(retrieve);
          stmt.setString(1,itemName );
          rs = stmt.executeQuery();
              
        while(rs.next()){
             imagedata = rs.getBytes("image") ;
             img = Toolkit.getDefaultToolkit().createImage(imagedata);
             icon =new ImageIcon(img);
             imgList.add(icon);
            
        }
        }catch(Exception e){System.out.println("FAILS TO RETRIVE IMAGES OF SELECTED ITEM");}
       
       return  imgList;
   }
   public  ArrayList<Integer> retriveCostforeachImage(String itemName){
        setUpConnection();
         ResultSet rs = null;
         ArrayList<Integer> costList = new ArrayList<>();
         int cost;
          try{ 
                String retrieve = "select cost from itemImage where itemName=? ";
                PreparedStatement stmt = conn.prepareStatement(retrieve);
                stmt.setString(1,itemName );
                rs = stmt.executeQuery();
                while(rs.next()){
                        cost = rs.getInt("cost");
                        costList.add(cost);
                }
       
           }catch(Exception e){System.out.println("FAILS TO RETRIVE COST OF SELECTED ITEM");}  
    return costList;
  }
   
     public  ArrayList<Integer> retriveImageNoforeachImage(String itemName){
         setUpConnection();
         ResultSet rs = null;
         ArrayList<Integer> NumberList = new ArrayList<>();
         int cost;
          try{ 
                String retrieve = "select imgNo from itemImage where itemName=? ";
                PreparedStatement stmt = conn.prepareStatement(retrieve);
                stmt.setString(1,itemName );
                rs = stmt.executeQuery();
                while(rs.next()){
                        cost = rs.getInt("imgNo");
                        NumberList.add(cost);
                }
       
           }catch(Exception e){System.out.println("FAILS TO RETRIVE Number OF SELECTED ITEM");}  
    return NumberList;
                
   }
   
   public Boolean authenticateBuyer(String name, String password) throws SQLException{
         Boolean existance = false;
         setUpConnection();
         ResultSet rs = null;
         String retrieve= "select * from buyers where name=? and password=? ";
         
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
     
    
   public DefaultComboBoxModel returnComboModel(){
       DefaultComboBoxModel catMod = new DefaultComboBoxModel();
      
       catMod.addElement("all");
       catMod.addElement("clothes");
       catMod.addElement("Books");
       catMod.addElement("Shoes");
    
      
      
       return catMod;
   } 
   
    
    
       public Connection setUpConnection(){
     
         String db_url = "jdbc:mysql://localhost/sweetdeal?characterEncoding=utf8";
         String  db_port = "3306";
         String  db_user = "root";
         String db_password = "";
       try {
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException ex) {
           System.out.println("Error will loding class");
       }
       try {
           conn = DriverManager.getConnection(db_url, db_user, db_password);
       } catch (SQLException ex) {
         System.out.println("Error on driver manager");
       }
       
             return conn; 
       
       }  
    
    
}
