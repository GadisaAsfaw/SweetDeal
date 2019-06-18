/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author gadisa
 */
public class SellerController {
    File file;
  
    SellerView svv;
    SLoginView slv;
    SellerModel sm;
    
    
    public SellerController(SellerView svv,SLoginView slv ,SellerModel sm){
        this.svv = svv;
     
        this.slv = slv;
        this.sm = sm;
      
        slv.addActionListener(new SellerLoginAction()  );
        svv.addActionListnerTochooseImage( new sellersChooseAction());
        svv.addActionListnerToAddImage(new SellersAddImageAction());
        svv.addActionListnerToAddItem( new sellersAddItemAction());
        
    }
    class SellerLoginAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             Boolean  exist = null;
            String name = slv.returnName();
            String pw = slv.returnPassword();
          
            try {
                exist = sm.authenticateBuyer(name,pw);
            } catch (SQLException ex) {
                Logger.getLogger(SellerController.class.getName()).log(Level.SEVERE, null, ex);
            }
             if( exist){
           
            slv.removePanel();
            slv.addPanel(svv);
            
              //add category to combobox;
        DefaultComboBoxModel m = sm.returnComboModel();
        svv.addComboBoxModel(m);
             }
             else{
            slv.promptUser("please inter valid information");
        }
            
            
            
        }
    }
    
    
   class sellersChooseAction implements ActionListener{
      

        @Override
        public void actionPerformed(ActionEvent e) {
          file= svv.getSelectFile();
        }
    }
   
   class sellersAddItemAction implements ActionListener{
      

        @Override
        public void actionPerformed(ActionEvent e) {
          String name = svv.getItemName();
          String category = svv.getItemCategory();
         sm.addItemToDatabase(name,category,"n");
           
    }
   }
   class SellersAddImageAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           String itemName = svv.getItemName();
           int cost = (int)svv.getImageCost();
           int imNo = (int)svv.getImageNumber();
           sm.addImageToDatabase(itemName,cost, imNo,file);
        }
   
   }
   
   
   
    
}
