/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author gadisa
 * 
 */
 
public class BuyerController {
    
    BLoginView blv;    
    BuyerModel BM ;
    Buyer_view BVpanel;
   

public BuyerController( BuyerModel BM , Buyer_view BVpanel,BLoginView blv){
    this.BM = BM;
    this.BVpanel = BVpanel;
    this.blv =blv;
    
    
    
       blv.addActionListener(new BuyerLoginAction());
       BVpanel.addActionToBackButton(new BackButtonAction());
       BVpanel.addActionToNextButton(new NextButtonAction());
       BVpanel.addActionToListView(new ListSelectionAction());
       BVpanel.addActionToComboBox(new ComboboxAction());
       BVpanel.addActionToBuyButton(new BuyButtonAction());
    
}

class ComboboxAction implements   ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            DefaultListModel dlm =null;
            String selectedCat = BVpanel.returnSelectedCategory();
           
             if(selectedCat.equals("all")){
                dlm =(DefaultListModel) BM.retriveItemNameFromDatabase();
                try{
                BVpanel.addListModelToListView(dlm);
                }catch(Exception ex){}
                //System.out.println(dlm);
            }
            else{
            dlm =(DefaultListModel) BM.retriveItemNameFromDatabase(selectedCat);
            try{
            BVpanel.addListModelToListView(dlm);
            }catch(Exception ex){System.out.println();}
                //System.out.println(dlm);
            }
           
        
           //blv.promptUser("item changed"+selectedCat);
        }
    }
      




class BackButtonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          String selectedItem = BVpanel.returnSelectedItem();
          imagesOfSI = BM.retriveImagesForSelectedItem(selectedItem);
          costOfSI = BM.retriveCostforeachImage(selectedItem);
          IMnumberSI = BM.retriveImageNoforeachImage(selectedItem);
           System.out.println(imagesOfSI.size());
              if(index <=0){
                  index =0;
              }
              BVpanel.addImageToLabel(imagesOfSI.get(index));
              BVpanel.setCost( costOfSI.get(index).toString() );
              BVpanel.setImageNumber( IMnumberSI.get(index).toString());
              
             
              if(index >0){
               index -=1;
              }
          
        
           
        }
    }
class NextButtonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          String selectedItem = BVpanel.returnSelectedItem();
          imagesOfSI = BM.retriveImagesForSelectedItem(selectedItem);
          costOfSI = BM.retriveCostforeachImage(selectedItem);
          IMnumberSI = BM.retriveImageNoforeachImage(selectedItem);
              if(index >=imagesOfSI.size() -1){
                  index =imagesOfSI.size()-1;
              }
              BVpanel.addImageToLabel(imagesOfSI.get(index));
              BVpanel.setCost( costOfSI.get(index).toString() );
              BVpanel.setImageNumber( IMnumberSI.get(index).toString());
              if(index < imagesOfSI.size() -1){
              index +=1;
              }
         
       //System.out.println(selectedItem);
           
        }
    }
class ListSelectionAction implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            String selectedItem = BVpanel.returnSelectedItem();
            imagesOfSI = BM.retriveImagesForSelectedItem(selectedItem);
             BVpanel.addImageToLabel(imagesOfSI.get(0));
         
        }
    }
 class BuyerLoginAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Boolean  exist = null;
            String name = blv.returnName();
            String pw = blv.returnPassword();
         
           
            try {
               exist = BM.authenticateBuyer(name,pw);
            } catch (SQLException ex) {
                Logger.getLogger(BuyerController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        if( exist){
           
            
        //add new panel
        blv.removePanel();
        blv.addPanel(BVpanel);
        //add items to JList
        DefaultListModel dlm =(DefaultListModel) BM.retriveItemNameFromDatabase();
        BVpanel.addListModelToListView(dlm);
        
        //add category to combobox;
        DefaultComboBoxModel m = BM.returnComboModel();
        BVpanel.addComboBoxModel(m);
        
        //FIRST IMAGE
        ImageIcon ic = BM.retriveFrstimageForFrstItem();
        BVpanel.addTheFirsImageToLabel(ic);
        BVpanel.setCost("100");
        BVpanel.setImageNumber("1" );
        BVpanel.SetfirsItemSelectd(0);
        
    
        
        
        }
        else{
            blv.promptUser("please inter valid information");
        }
        
        }
     
 }
class BuyButtonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          String itmName = BVpanel.returnSelectedItem();
          String byrName = BVpanel.getBuyerName();
          String byrAdrs = BVpanel.getBuyerAddress();
          int imgNo = BVpanel.getImageNo();
          
          BM.addItemtoDB(itmName, byrName, byrAdrs);
          BM.addItemImageToDB(itmName, imgNo);
         BVpanel.promptUser("sccessfully orderd");
        }
    }
 
 
     int index =4;
     ArrayList<ImageIcon> imagesOfSI ;
     ArrayList<Integer> costOfSI ;
     ArrayList<Integer> IMnumberSI ;  
}
