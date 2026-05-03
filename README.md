# Waste-Labour-Management-System
Santhana Madha Traders — Steel Ring Management System A Java Swing desktop application for managing the complete operations of a steel ring manufacturing business — from raw material procurement and labour management to production, sales, and waste tracking.  Built with core Java OOP principles, a MySQL backend, and a clean GUI using Java Swing.


ModuleDescription
Login & SignupSecure user authentication with security question and forget-password flow
Raw MaterialAdd, update, and view raw material stock with date-range reports via stored procedures
Ring InventoryTrack ring stock — add, update, check by ID, generate ring-type and stock reports LabourManage labour records — name, skill type, and daily wage rate
WageCalculate and store total salary (days worked × wage per day)
WasteRecord waste per material — auto-calculates waste from total vs used
SalesRecord ring sales, calculate total revenue, generate sales reports by date range
BillGenerate buyer bills and salary bills with print support💳 PaymentPayment tracking via Paytm / manual entry

 OOP Concepts Applied
ConceptWhere UsedAbstract ClassBaseForm — blueprint for all form windowsInheritanceAll forms extend BaseForm → inherit window setupMethod Overriding@Override getFormTitle(), initUI(), clearFields() in each formEncapsulationLabourRecord, WageRecord, WasteRecord model classes with private fields + getters/settersInterfaceAll forms implement ActionListener; BillUI interface with default methodsPolymorphismactionPerformed() handles multiple buttons differently in each classConstructor Chainingsuper() in every form → BaseForm() → setupWindow()Static MembersDBConnection.getConnection() — shared DB utility, no object neededAbstractionValidator utility class hides all validation logic behind simple method names

Database Tables
TablePurposesignupUser accounts with security Q&AlabourLabour name, skill, wage ratewageSalary records — days worked, rate, totalwasteWaste amount per material per dateraw_materialRaw material stock and datesring_inventoryRing types, quantities, datessalesRing sales — quantity, price, dateproductionRing type vs raw material used

Stored procedures used for date-range reports:
total_raw_material_period, total_rings_period, total_stock_period, total_sales_amount_period






Tech Stack

Language: Java 
GUI: Java Swing (JFrame, JPanel, JTable, JScrollPane)
Database: MySQL 8.x
JDBC Driver: mysql-connector-java
IDE: NetBeans / IntelliJ IDEA / Eclipse


📁 Project Structure
src/
└── waste/and/labour/
    ├── BaseForm.java          # Abstract parent class for all forms
    ├── Validator.java         # Data validation utility (static methods)
    ├── Conn.java              # Database connection
    ├── DBConnection.java      # Static DB connection utility
    ├── Login.java             # Entry point — login screen
    ├── Signup.java            # New user registration
    ├── ForgetPassword.java    # Password recovery
    ├── home.java              # Main dashboard / navigation
    ├── Labour.java            # Labour management
    ├── Wage.java              # Wage calculation
    ├── Waste.java             # Waste tracking
    ├── Raw.java               # Raw material management
    ├── Ringi.java             # Ring inventory management
    ├── Sales.java             # Sales & reporting
    ├── Bill.java              # Billing (BillUI interface)
    ├── Payment.java           # Payment module
    ├── Paytm.java             # Paytm payment entry
    ├── Pro.java               # Production module
    ├── LabourRecord.java      # Encapsulation model — labour
    ├── WageRecord.java        # Encapsulation model — wage
    └── WasteRecord.java       # Encapsulation model — waste
icons/                         # UI background images

👥 Authors
Developed by Amali, Aarthi, and Samreen as part of a Java desktop application project.

⭐ If you found this useful, give it a star on GitHub!Content1777728220758_SMT.docxdocx1777802330536_package waste.docxdocx
