
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;



public class Employee {
    JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    /**
     * pour la connection avec la base de donnée
     */
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompant","root","");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

// display table didn't work

    /*

    void table_load() {
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/

// display table
    void table_load() {
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();

            // Convert ResultSet to DefaultTableModel
            DefaultTableModel tableModel = buildTableModel(rs);

            // Set the table model to your JTable
            table1.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Custom method to convert ResultSet to DefaultTableModel
    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Get column names
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Create DefaultTableModel with column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Get data rows
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = rs.getObject(i + 1);
            }
            tableModel.addRow(rowData);
        }

        return tableModel;
    }


    public Employee() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String empname,salary,mobile;
                empname= txtName.getText();
                salary=txtSalary.getText();
                mobile=txtMobile.getText();
                try {
                    pst = con.prepareStatement("insert into employee(empname,salary,mobile)value(?,?,?)");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Added successfully");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Verifier vos données !");

                }






            }

        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                String empid = txtid.getText();
                pst= con.prepareStatement("select empname,salary,mobile from employee where id= ?");
                pst.setString(1,empid);
                ResultSet rs = pst.executeQuery();

                if(rs.next()==true)
                {
                    String empname=rs.getString(1);
                    String emsalary=rs.getString(2);
                    String emmobile=rs.getString(3);

                    txtName.setText(empname);
                    txtSalary.setText(emsalary);
                    txtMobile.setText(emmobile);



                }
                else {
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    JOptionPane.showMessageDialog(null,"Invalid Employee Number");
                }

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();

                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid,empname,salary,mobile;

                empname= txtName.getText();
                salary=txtSalary.getText();
                mobile=txtMobile.getText();
                empid=txtid.getText();



                try{
                    pst =con.prepareStatement("update employee set empname = ?,salary = ?,mobile = ? where id=?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Update!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();

                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;
                empid= txtid.getText();
                try{
                    pst= con.prepareStatement("delete from employee where id = ?");
                    pst.setString(1,empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"has been successfuly deleted");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();


                }
                catch (SQLException e1)
                {
                e1.printStackTrace();
                }

            }


        });
    }
}
