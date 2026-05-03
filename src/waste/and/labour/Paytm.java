/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package waste.and.labour;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Paytm class provides a simple interface to display the Paytm website
 * inside the application using a JEditorPane.
 * 
 * It allows users to simulate an online payment by loading the Paytm webpage.
 * A Back button is provided to close the current window.
 * 
 * If the webpage fails to load, a fallback message is displayed.
 * 
 * This class is used as part of the payment module in the system.
 * 
 * @author Prity
 */
public class Paytm extends JFrame{
    
    /**
     * Constructor initializes the Paytm web view UI.
     * It loads the Paytm website and adds a scrollable view.
     */
    Paytm(){
        JEditorPane j = new JEditorPane();
        j.setEditable(false);   

        try {
            j.setPage("https://paytm.com");
        }catch (Exception e) {
            j.setContentType("text/html");
            j.setText("<html>Could not load</html>");
        } 

        JScrollPane scrollPane = new JScrollPane(j);     
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);
        setPreferredSize(new Dimension(800,600));
        
        JButton back=new JButton("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        back.setBounds(610, 20, 80, 40);
        j.add(back);
        
        setSize(800,600);
        setLocation(600,220);
        setVisible(true);
    }
    
    /**
     * Main method to launch the Paytm screen.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args){
        new Paytm().setVisible(true);
    }
}
