package waste.and.labour;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    About() {
        setTitle("About System");
        setSize(500, 300);
        setLocation(400, 200);
        setLayout(null);

        JLabel l1 = new JLabel("<html><center>SANTHANA MADHA TRADERS<br><br>"
                + "Developed by: "
                 +   " AARTHI T S "
                + "AMALI ROOBA FELIX L"
                + "KURSHID SAMREEN A H"
                + "Project for Mini Project<br>"
                + "2026</center></html>");

        l1.setBounds(50, 50, 400, 150);
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(l1);

        setVisible(true);
    }
}
