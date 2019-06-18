/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SweetDeal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author gadisa
 */
public class BLoginView extends JFrame {
    
    private JTextField fn = new JTextField(10);
    private JPasswordField pw = new JPasswordField(10);
    private JButton loginB = new JButton("Login");
    JPanel jp ;
public BLoginView()
   {
        jp = new JPanel();
        jp.add(new JLabel("Name:"));
        jp.add(fn);
        jp.add(new JLabel("password"));
        jp.add(pw);
        jp.add(loginB);
        jp.setLayout(new FlowLayout(FlowLayout.LEFT,250,20));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.add(jp,BorderLayout.CENTER);



  }


public String returnName(){
    return fn.getText().toString();
    
}
public String returnPassword(){
    return pw.getText().toString();
}

public void addActionListener(ActionListener actionForLogin){
       loginB.addActionListener(actionForLogin);
    
}

      
       
      
public void removePanel(){
    jp.setVisible(false);
     this.remove(jp);
    
}
public void addPanel(JPanel p){
    this.add(p);
    p.setVisible(true);
}
public void promptUser(String message){
    JOptionPane.showMessageDialog(rootPane, message);
}

}
