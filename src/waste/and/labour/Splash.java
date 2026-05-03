
package waste.and.labour;
import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public   class Splash  extends JFrame implements Runnable {
    Thread thread;
    
    Splash(){
        setSize(1200,600);
        setLocation(300,200);
      
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/molten_steel2.png"));
        Image i2= i1.getImage().getScaledInstance(1200,600,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel image = new JLabel(i3);
      
        add(image);
        setVisible(true);
        thread =new Thread(this);
        thread.start();
       
        
    }
    
    
    public void run(){
        try{
            Thread.sleep(7000);
            
            setVisible(false);
        }catch( Exception e){
            
        }
    }
     public static void main(String[] args){
      Splash frame = new Splash();
      int x=1;
      for(int i=0;i<=500;x+=7,i+=6){
          frame.setLocation(750 - (x+i)/2 ,400 - (i/2));
          frame.setSize(x+i , i );
          try {
              Thread.sleep(10);
          } catch(Exception e ) {
              
          }
      }
     }
        
}

