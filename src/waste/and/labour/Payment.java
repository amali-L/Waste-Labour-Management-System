
package waste.and.labour;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Payment class provides a simple UI for initiating payment using Paytm.
 * It displays a payment screen with a Paytm image and buttons to proceed
 * with payment or go back to the previous screen.
 * 
 * When the user clicks "Pay", a new Paytm window is opened.
 * When the user clicks "Back", the current window is closed.
 * 
 * This class is part of the waste and labour management system.
 * 
 * @author Prity
 */
public class Payment extends JFrame{
    
    public Payment(){
         /**
     * Constructor initializes the Payment UI components.
     * It sets up labels, images, and buttons for user interaction.
     */
        setLayout(null);
        setBounds(600, 220, 800, 600);
        
        JLabel label=new JLabel("Pay using Paytm");
        label.setFont(new Font("Raleway", Font.BOLD, 40));
        label.setBounds(50, 20, 350, 45);
        add(label);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/paytm.jpeg"));
        Image i8 = i7.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l4 = new JLabel(i9);
        l4.setBounds(0, 150, 800, 600);
        add(l4);
        
        JButton pay = new JButton("Pay");
        pay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Paytm().setVisible(true);
            }
        });
        pay.setBounds(420, 20, 80, 40);
        add(pay);
    
        JButton back=new JButton("Back");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        back.setBounds(510, 20, 80, 40);
        add(back);
        
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);

    }
        /**
     * Main method to launch the Payment screen.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args){
        new Payment().setVisible(true);
    }
    
}