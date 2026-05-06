package waste.and.labour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * Interface containing reusable UI helper methods for billing screens.
 */
interface BillUI {

    default JButton createBtn(String txt, int y) {
        JButton b = new JButton(txt);
        b.setBounds(50, y, 200, 40);
        b.setBackground(new Color(20, 40, 150));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        return b;
    }

    default void addLabel(JPanel p, String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(x, y, 150, 30);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(l);
    }

    default JLabel buildBackground(String resourcePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
        java.awt.Image img = icon.getImage()
                .getScaledInstance(1400, 800, java.awt.Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(img));
        bg.setBounds(0, 0, 1400, 800);
        return bg;
    }

    default void addTitle(JLabel bg, String subtitle) {
        JLabel t1 = new JLabel("SANTHANA MADHA TRADERS");
        t1.setBounds(300, 30, 700, 40);
        t1.setFont(new Font("Arial", Font.BOLD, 30));
        t1.setForeground(Color.WHITE);
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        bg.add(t1);

        JLabel t2 = new JLabel(subtitle);
        t2.setBounds(300, 70, 700, 30);
        t2.setFont(new Font("Arial", Font.BOLD, 20));
        t2.setForeground(Color.WHITE);
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        bg.add(t2);
    }

    default File getBillsDir() {
        File f = new File("C:\\Bills");
        if (!f.exists()) f.mkdirs();
        return f;
    }
}

/**
 * Utility class for establishing database connection.
 */
class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/steel_management",
                    "root",
                    "Amali@123"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * Main dashboard class for billing system.
 */
public class Bill extends JFrame implements BillUI {

    public Bill() {
        setTitle("Dashboard");
        setSize(1400, 800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel bg = buildBackground("/icons/bg.png");
        add(bg);

        addTitle(bg, "BILLING SECTION");

        JPanel side = new JPanel(null);
        side.setBounds(0, 0, 300, 800);
        side.setBackground(new Color(10, 20, 90));
        bg.add(side);

        JButton b1 = createBtn("Buyer Bill", 200);
        JButton b2 = createBtn("Salary Bill", 280);

        side.add(b1);
        side.add(b2);

        b1.addActionListener(e -> new BuyerForm());
        b2.addActionListener(e -> new SalaryForm());

        setVisible(true);
    }

    class BuyerForm extends JFrame implements BillUI {

        JTextField tfName, tfDate, tfBill, tfItem, tfQty, tfRate;
        JLabel     lblTotal;

        // Lists to hold added items
        ArrayList<String>  items = new ArrayList<>();
        ArrayList<Integer> qtys  = new ArrayList<>();
        ArrayList<Integer> rates = new ArrayList<>();

        // Table to preview added items
        DefaultTableModel tableModel;

        BuyerForm() {
            setTitle("Buyer Bill");
            setSize(1400, 800);
            setLayout(null);
            setLocationRelativeTo(null);

            JLabel bg = buildBackground("/icons/bg.png");
            add(bg);
            addTitle(bg, "BUYER BILL");

            
            JPanel left = new JPanel(null);
            left.setBounds(320, 130, 420, 620);
            left.setBackground(new Color(255, 255, 255, 220));
            bg.add(left);

      
            JLabel secInfo = new JLabel("Customer Details");
            secInfo.setBounds(20, 10, 200, 25);
            secInfo.setFont(new Font("Arial", Font.BOLD, 14));
            left.add(secInfo);

            addLabel(left, "Customer Name :", 10, 45);
            addLabel(left, "Date :",          10, 85);
            addLabel(left, "Bill No :",       10, 125);

            tfName = new JTextField(); tfName.setBounds(160, 45, 230, 30); left.add(tfName);
            tfDate = new JTextField(); tfDate.setBounds(160, 85, 230, 30); left.add(tfDate);
            tfBill = new JTextField(); tfBill.setBounds(160, 125, 230, 30); left.add(tfBill);

            // Separator line
            JSeparator sep = new JSeparator();
            sep.setBounds(10, 165, 395, 2);
            left.add(sep);

            JLabel secItem = new JLabel("Add Item");
            secItem.setBounds(20, 175, 200, 25);
            secItem.setFont(new Font("Arial", Font.BOLD, 14));
            left.add(secItem);

            addLabel(left, "Item / Ring Size :", 10, 210);
            addLabel(left, "Quantity :",         10, 250);
            addLabel(left, "Rate (₹) :",         10, 290);

            tfItem = new JTextField(); tfItem.setBounds(160, 210, 230, 30); left.add(tfItem);
            tfQty  = new JTextField(); tfQty .setBounds(160, 250, 230, 30); left.add(tfQty);
            tfRate = new JTextField(); tfRate.setBounds(160, 290, 230, 30); left.add(tfRate);

            JButton btnAdd = new JButton("➕ Add Item");
            btnAdd.setBounds(120, 340, 160, 35);
            btnAdd.setBackground(new Color(0, 128, 0));
            btnAdd.setForeground(Color.WHITE);
            btnAdd.setFont(new Font("Arial", Font.BOLD, 13));
            left.add(btnAdd);

            JButton btnClear = new JButton("🗑 Clear Last");
            btnClear.setBounds(120, 385, 160, 35);
            btnClear.setBackground(new Color(180, 0, 0));
            btnClear.setForeground(Color.WHITE);
            btnClear.setFont(new Font("Arial", Font.BOLD, 13));
            left.add(btnClear);

            lblTotal = new JLabel("TOTAL: ₹ 0");
            lblTotal.setBounds(10, 435, 390, 30);
            lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
            lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
            left.add(lblTotal);

            JButton btnGen = new JButton(" Generate Bill PDF");
            btnGen.setBounds(80, 480, 250, 40);
            btnGen.setBackground(new Color(20, 40, 150));
            btnGen.setForeground(Color.WHITE);
            btnGen.setFont(new Font("Arial", Font.BOLD, 14));
            left.add(btnGen);

           
            JPanel right = new JPanel(null);
            right.setBounds(760, 130, 580, 600);
            right.setBackground(new Color(255, 255, 255, 220));
            bg.add(right);

            JLabel previewTitle = new JLabel("Items Preview");
            previewTitle.setBounds(10, 10, 300, 25);
            previewTitle.setFont(new Font("Arial", Font.BOLD, 14));
            right.add(previewTitle);

            tableModel = new DefaultTableModel(
                    new String[]{"S.No", "Item / Ring", "Qty", "Rate (₹)", "Amount (₹)"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };

            JTable previewTable = new JTable(tableModel);
            previewTable.setRowHeight(28);
            previewTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            previewTable.setFont(new Font("Arial", Font.PLAIN, 13));

            JScrollPane sp = new JScrollPane(previewTable);
            sp.setBounds(10, 45, 550, 520);
            right.add(sp);

    
            btnAdd.addActionListener(e -> {
                String itemTxt = tfItem.getText().trim();
                String qtyTxt  = tfQty .getText().trim();
                String rateTxt = tfRate.getText().trim();

                if (itemTxt.isEmpty() || qtyTxt.isEmpty() || rateTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill Item, Qty and Rate.");
                    return;
                }
                try {
                    int q = Integer.parseInt(qtyTxt);
                    int r = Integer.parseInt(rateTxt);
                    int amt = q * r;

                    items.add(itemTxt);
                    qtys .add(q);
                    rates.add(r);

                    tableModel.addRow(new Object[]{
                            items.size(), itemTxt, q, r, amt
                    });

                    int total = 0;
                    for (int i = 0; i < items.size(); i++) total += qtys.get(i) * rates.get(i);
                    lblTotal.setText("TOTAL: ₹ " + total);

                    tfItem.setText(""); tfQty.setText(""); tfRate.setText("");
                    tfItem.requestFocus();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Qty and Rate must be numbers.");
                }
            });

            btnClear.addActionListener(e -> {
                if (items.isEmpty()) return;
                int last = items.size() - 1;
                items.remove(last);
                qtys .remove(last);
                rates.remove(last);
                tableModel.removeRow(last);

                int total = 0;
                for (int i = 0; i < items.size(); i++) total += qtys.get(i) * rates.get(i);
                lblTotal.setText("TOTAL: ₹ " + total);
            });

            btnGen.addActionListener(e -> generateBuyerPDF());

            setVisible(true);
        }

        /** Generates a PDF buyer bill with all added items. */
        void generateBuyerPDF() {
            if (tfName.getText().trim().isEmpty() || tfBill.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill Customer Name and Bill No.");
                return;
            }
            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add at least one item.");
                return;
            }

            try {
                int totalAll = 0;

                Document doc  = new Document();
                String   path = getBillsDir() + "\\Buyer_" + tfBill.getText().trim()
                                + "_" + System.currentTimeMillis() + ".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(path));
                doc.open();

                com.itextpdf.text.Font boldLarge =
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                com.itextpdf.text.Font bold12    =
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                com.itextpdf.text.Font normal12  =
                        FontFactory.getFont(FontFactory.HELVETICA, 12);

              
                Paragraph header = new Paragraph("SANTHANA MADHA TRADERS", boldLarge);
                header.setAlignment(Element.ALIGN_CENTER);
                doc.add(header);

                Paragraph sub = new Paragraph("BUYER BILL", bold12);
                sub.setAlignment(Element.ALIGN_CENTER);
                doc.add(sub);

                doc.add(new Paragraph("Sivakasi | Phone: 8248184142", normal12));
                doc.add(new Paragraph(" "));

         
                doc.add(new Paragraph("Customer : " + tfName.getText().trim(), normal12));
                doc.add(new Paragraph("Date     : " + tfDate.getText().trim(), normal12));
                doc.add(new Paragraph("Bill No  : " + tfBill.getText().trim(), normal12));
                doc.add(new Paragraph(" "));

       
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1f, 3f, 2f, 2f, 2f});

         
                com.itextpdf.text.BaseColor headerBg =
                        new com.itextpdf.text.BaseColor(20, 40, 150);

                for (String h : new String[]{"S.No", "Item / Ring Size", "Quantity", "Rate (₹)", "Amount (₹)"}) {
                    PdfPCell cell = new PdfPCell(new Phrase(h, bold12));
                    cell.setBackgroundColor(headerBg);
           
                    com.itextpdf.text.Font whiteFont =
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12,
                                    com.itextpdf.text.Font.BOLD,
                                    com.itextpdf.text.BaseColor.WHITE);
                    cell.setPhrase(new Phrase(h, whiteFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(6);
                    table.addCell(cell);
                }

                for (int i = 0; i < items.size(); i++) {
                    int amt = qtys.get(i) * rates.get(i);
                    totalAll += amt;

              
                    com.itextpdf.text.BaseColor rowBg = (i % 2 == 0)
                            ? com.itextpdf.text.BaseColor.WHITE
                            : new com.itextpdf.text.BaseColor(235, 240, 255);

                    for (String val : new String[]{
                            String.valueOf(i + 1),
                            items.get(i),
                            String.valueOf(qtys.get(i)),
                            String.valueOf(rates.get(i)),
                            String.valueOf(amt)
                    }) {
                        PdfPCell cell = new PdfPCell(new Phrase(val, normal12));
                        cell.setBackgroundColor(rowBg);
                        cell.setPadding(5);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }

                doc.add(table);
                doc.add(new Paragraph(" "));

            
                Paragraph totalPara = new Paragraph("TOTAL: ₹ " + totalAll, bold12);
                totalPara.setAlignment(Element.ALIGN_RIGHT);
                doc.add(totalPara);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

       
                Paragraph sign1 = new Paragraph("For SANTHANA MADHA TRADERS", normal12);
                sign1.setAlignment(Element.ALIGN_RIGHT);
                doc.add(sign1);

                Paragraph sign2 = new Paragraph("Authorized Signature", normal12);
                sign2.setAlignment(Element.ALIGN_RIGHT);
                doc.add(sign2);

                doc.close();
                JOptionPane.showMessageDialog(this,
                        "Bill Generated!\nSaved at: " + path);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }


    class SalaryForm extends JFrame implements BillUI {

        JTextField tfName, tfDays, tfSalary, tfBillNo, tfDate, tfRefBill;
        JLabel     lblTotal;

        SalaryForm() {
            setTitle("Salary Bill");
            setSize(1400, 800);
            setLayout(null);
            setLocationRelativeTo(null);

            JLabel bg = buildBackground("/icons/bg.png");
            add(bg);
            addTitle(bg, "SALARY BILL");

            JPanel p = new JPanel(null);
            p.setBounds(450, 120, 480, 500);
            p.setBackground(new Color(255, 255, 255, 220));
            bg.add(p);

            JLabel sec = new JLabel("Worker Salary Details");
            sec.setBounds(20, 10, 300, 28);
            sec.setFont(new Font("Arial", Font.BOLD, 15));
            p.add(sec);

            addLabel(p, "Worker Name :",     10, 50);
            addLabel(p, "Bill No :",         10, 95);
            addLabel(p, "Ref. Bill No :",    10, 140);   
            addLabel(p, "Date :",            10, 185);
            addLabel(p, "Days Worked :",     10, 230);
            addLabel(p, "Daily Wage (₹) :",  10, 275);

            tfName   = new JTextField(); tfName  .setBounds(170, 50,  270, 32); p.add(tfName);
            tfBillNo = new JTextField(); tfBillNo.setBounds(170, 95,  270, 32); p.add(tfBillNo);
            tfRefBill= new JTextField(); tfRefBill.setBounds(170, 140, 270, 32); p.add(tfRefBill);
            tfDate   = new JTextField(); tfDate  .setBounds(170, 185, 270, 32); p.add(tfDate);
            tfDays   = new JTextField(); tfDays  .setBounds(170, 230, 270, 32); p.add(tfDays);
            tfSalary = new JTextField(); tfSalary.setBounds(170, 275, 270, 32); p.add(tfSalary);

            lblTotal = new JLabel("Total Salary: ₹ --");
            lblTotal.setBounds(20, 325, 420, 30);
            lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
            lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
            p.add(lblTotal);

           
            javax.swing.event.DocumentListener dl = new javax.swing.event.DocumentListener() {
                public void insertUpdate (javax.swing.event.DocumentEvent e) { updateTotal(); }
                public void removeUpdate (javax.swing.event.DocumentEvent e) { updateTotal(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTotal(); }
            };
            tfDays  .getDocument().addDocumentListener(dl);
            tfSalary.getDocument().addDocumentListener(dl);

            JButton btnSave = new JButton("💾 Save & Generate PDF");
            btnSave.setBounds(110, 375, 240, 45);
            btnSave.setBackground(new Color(20, 40, 150));
            btnSave.setForeground(Color.WHITE);
            btnSave.setFont(new Font("Arial", Font.BOLD, 14));
            p.add(btnSave);

            btnSave.addActionListener(e -> saveAndGenerate());

            setVisible(true);
        }

        /** Updates the live total label while the user types. */
        void updateTotal() {
            try {
                int d = Integer.parseInt(tfDays  .getText().trim());
                int s = Integer.parseInt(tfSalary.getText().trim());
                lblTotal.setText("Total Salary: ₹ " + (d * s));
            } catch (NumberFormatException ex) {
                lblTotal.setText("Total Salary: ₹ --");
            }
        }

        /** Saves to DB and generates PDF salary bill. */
        void saveAndGenerate() {
            String name    = tfName   .getText().trim();
            String billNo  = tfBillNo .getText().trim();
            String refBill = tfRefBill.getText().trim();   
            String date    = tfDate   .getText().trim();

            if (name.isEmpty() || billNo.isEmpty() || date.isEmpty()
                    || tfDays.getText().trim().isEmpty()
                    || tfSalary.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try {
                int d     = Integer.parseInt(tfDays  .getText().trim());
                int s     = Integer.parseInt(tfSalary.getText().trim());
                int total = d * s;

            
                Connection con = DBConnection.getConnection();
                if (con != null) {
                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO salary_bill(name, days, salary, total, bill_no, ref_bill, date) "
                          + "VALUES (?,?,?,?,?,?,?)");
                    ps.setString(1, name);
                    ps.setInt   (2, d);
                    ps.setInt   (3, s);
                    ps.setInt   (4, total);
                    ps.setString(5, billNo);
                    ps.setString(6, refBill);
                    ps.setString(7, date);
                    ps.executeUpdate();
                    con.close();
                }

                Document doc  = new Document();
                String   path = getBillsDir() + "\\Salary_" + billNo
                                + "_" + System.currentTimeMillis() + ".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(path));
                doc.open();

                com.itextpdf.text.Font boldLarge =
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                com.itextpdf.text.Font bold12    =
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
                com.itextpdf.text.Font normal12  =
                        FontFactory.getFont(FontFactory.HELVETICA, 12);

                Paragraph header = new Paragraph("SANTHANA MADHA TRADERS", boldLarge);
                header.setAlignment(Element.ALIGN_CENTER);
                doc.add(header);

                Paragraph sub = new Paragraph("SALARY BILL", bold12);
                sub.setAlignment(Element.ALIGN_CENTER);
                doc.add(sub);

                doc.add(new Paragraph("Sivakasi | Phone: 8248184142", normal12));
                doc.add(new Paragraph(" "));

                doc.add(new Paragraph("Worker Name : " + name,    normal12));
                doc.add(new Paragraph("Bill No     : " + billNo,  normal12));

            
                if (!refBill.isEmpty()) {
                    doc.add(new Paragraph("Ref Bill No : " + refBill, normal12));
                }

                doc.add(new Paragraph("Date        : " + date, normal12));
                doc.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1f, 3f, 2f, 2f, 2f});

                com.itextpdf.text.BaseColor headerBg =
                        new com.itextpdf.text.BaseColor(20, 40, 150);

                for (String h : new String[]{"S.No", "Particulars", "Days", "Rate (₹)", "Amount (₹)"}) {
                    com.itextpdf.text.Font whiteFont =
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12,
                                    com.itextpdf.text.Font.BOLD,
                                    com.itextpdf.text.BaseColor.WHITE);
                    PdfPCell cell = new PdfPCell(new Phrase(h, whiteFont));
                    cell.setBackgroundColor(headerBg);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(6);
                    table.addCell(cell);
                }

            
                com.itextpdf.text.BaseColor rowBg =
                        new com.itextpdf.text.BaseColor(235, 240, 255);
                for (String val : new String[]{
                        "1", "Salary", String.valueOf(d), String.valueOf(s), String.valueOf(total)
                }) {
                    PdfPCell cell = new PdfPCell(new Phrase(val, normal12));
                    cell.setBackgroundColor(rowBg);
                    cell.setPadding(5);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                doc.add(table);
                doc.add(new Paragraph(" "));

                Paragraph tot = new Paragraph("TOTAL: ₹ " + total, bold12);
                tot.setAlignment(Element.ALIGN_RIGHT);
                doc.add(tot);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                Paragraph sign1 = new Paragraph("For SANTHANA MADHA TRADERS", normal12);
                sign1.setAlignment(Element.ALIGN_RIGHT);
                doc.add(sign1);

                Paragraph sign2 = new Paragraph("Authorized Signature", normal12);
                sign2.setAlignment(Element.ALIGN_RIGHT);
                doc.add(sign2);

                doc.close();
                JOptionPane.showMessageDialog(this,
                        "Saved & PDF Generated!\nSaved at: " + path);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Bill();
    }
}
