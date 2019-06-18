/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

/**
 *
 * @author gadisa
 */
public class MVC_S {
    
    
  
  
    
    
    public static void main(String[] args){
     
          SellerView svv = new SellerView();
          SLoginView slv = new SLoginView();
          SellerModel sm = new SellerModel();
        
          
        new SellerController(svv,slv,sm);
        slv.setVisible(true);
    }
    
}
