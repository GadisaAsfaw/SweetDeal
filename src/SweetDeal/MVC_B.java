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
public class MVC_B {
    
    
public static void main(String[] args){
    BLoginView blv = new BLoginView();
    Buyer_view  bvpanel = new Buyer_view();
    BuyerModel bm = new BuyerModel();
   
  
    new BuyerController(bm,bvpanel,blv);
    blv.setVisible(true);
  
    
    
}
}

