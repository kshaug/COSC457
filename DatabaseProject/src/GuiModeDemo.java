
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * This is an example of several GUI and Database access techniques in the same
 * class. Though there is a lot of code here, much of it was auto-generated with
 * the Netbeans GUI editor tool. To fully understand this example you should
 * read the comments carefully and examine the GUI editor view in detail...
 * including the event handlers added to the various components.
 *
 * Though there is a lot more that can be done (like sorted tables, etc.) I
 * decided leave out a lot of more "advanced" techniques as not to clutter this
 * example any more than absolutely necessary. Though, it should be noted that
 * GUI's generally involve a lot of code...
 *
 * @author Adam J. Conover, D.Sc. <aconover@towson.edu>
 */
public class GuiModeDemo extends javax.swing.JFrame {
    //The Database access object
    private DbAccess dba;

    /**
     * Creates new form GuiModeDemo
     */
    public GuiModeDemo() {
        // The is just the call to the Netbeans generated method which construct the user interface.
        initComponents();
    }

    private void guiSetup() {
        // Do any "data dependent" interface initializations
        populateDepartmentComboBox();

        // Since certain interface elements depend on a database connection,
        // Only enable them once the connection is made.
        boolean dbState = this.dba != null;
        this.jButton_ConnectDB.setEnabled(!dbState);
        this.jTextField_DbUrl.setEnabled(!dbState);
        this.jButton_Demo1.setEnabled(dbState);
        this.jButton_Demo2.setEnabled(dbState);
        this.jRadioButton_Men.setEnabled(dbState);
        this.jRadioButton_Women.setEnabled(dbState);
        this.jComboBox_DepartmentNumber.setEnabled(dbState);
    }

    /**
     * Demo statement1 as in the text based demo.
     */
    void statementDemo1() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        try {
            ResultSet results = dba.getResultExample2();

            // Since this query produces an aggregate result, it produces one (and only
            // one) result row.
            results.next();

            float avgSalary = results.getFloat("avg_salary");

            this.jTextField_Salary.setText(nf.format(avgSalary));

            results.close();
        } catch (SQLException ex) {
            ex.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }
    }

    /**
     * Demo statement2 as in the text based demo.
     */
    void statementDemo2() {
        // All modifications to a tables data are done through the table's "model"
        DefaultTableModel model = (DefaultTableModel) this.jTable_EmployeeData.getModel();

        // clear existing rows
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        // Get the results from demo 2 and use this data to populate a GUI table.
        try {
            // Get the database results
            ResultSet results = dba.getResultExample1();

            // While there are more results to process
            while (results.next()) {
                // Build a Vector of Strings for the table row
                List<String> data = new LinkedList<String>();
                data.add(results.getString("FNAME"));
                data.add(results.getString("MINT"));
                data.add(results.getString("LNAME"));

                // Get the date as a intermediate variable, just to simplify the formatting.
                Date bDate = results.getDate("BDATE");

                // Format a date string based on a locale-specific date format.
                String fmtDate = DateFormat.getDateInstance(DateFormat.LONG).format(bDate);
                data.add(fmtDate);

                // Add the data row to the table.
                model.addRow(data.toArray());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }
    }

    /**
     * Demo3 as in the text based demo.
     */
    void PreparedStatementDemo1(String gender, String dept) {
        char genderCode = gender.charAt(0);
        int deptCode = Integer.parseInt(dept);

        try {
            ResultSet results = dba.getResultExample3(deptCode, genderCode);
            while (results.next()) {
                float hours = results.getFloat("total_hours");
                String name = results.getString("fname");
                String out = String.format("%s worked %.2f hours.\n", name, hours);

                this.jTextArea_HoursOutput.append(out);
            }
            results.close();
        } catch (SQLException ex) {
            ex.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The contents of this method are always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_Gender = new javax.swing.ButtonGroup();
        jLabel_DbUrl = new javax.swing.JLabel();
        jTextField_DbUrl = new javax.swing.JTextField();
        jButton_ConnectDB = new javax.swing.JButton();
        jPanel_Demo1 = new javax.swing.JPanel();
        jLabel_Salary = new javax.swing.JLabel();
        jTextField_Salary = new javax.swing.JTextField();
        jButton_Demo1 = new javax.swing.JButton();
        jSplitPane = new javax.swing.JSplitPane();
        jPanel_Demo2 = new javax.swing.JPanel();
        jScrollPane_TableContainer = new javax.swing.JScrollPane();
        jTable_EmployeeData = new javax.swing.JTable();
        jButton_Demo2 = new javax.swing.JButton();
        jPanel_Demo3 = new javax.swing.JPanel();
        jRadioButton_Men = new javax.swing.JRadioButton();
        jRadioButton_Women = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_HoursOutput = new javax.swing.JTextArea();
        jComboBox_DepartmentNumber = new javax.swing.JComboBox();
        jLabel_Department = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COSC457 Database Connectivity Example");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                applicationClosing(evt);
            }
        });

        jLabel_DbUrl.setText("Database URL:");

        jTextField_DbUrl.setText("jdbc:derby:./COSC457-Chapter3-DB");

        jButton_ConnectDB.setText("Connect to Database");
        jButton_ConnectDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConnectDBActionPerformed(evt);
            }
        });

        jPanel_Demo1.setBorder(javax.swing.BorderFactory.createTitledBorder("Demo 1"));

        jLabel_Salary.setText("Average Salary:");

        jTextField_Salary.setEditable(false);

        jButton_Demo1.setText("Get Average Salary");
        jButton_Demo1.setEnabled(false);
        jButton_Demo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Demo1(evt);
            }
        });

        javax.swing.GroupLayout jPanel_Demo1Layout = new javax.swing.GroupLayout(jPanel_Demo1);
        jPanel_Demo1.setLayout(jPanel_Demo1Layout);
        jPanel_Demo1Layout.setHorizontalGroup(
            jPanel_Demo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Salary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Salary, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jButton_Demo1)
                .addContainerGap())
        );
        jPanel_Demo1Layout.setVerticalGroup(
            jPanel_Demo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel_Salary)
                .addComponent(jTextField_Salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton_Demo1))
        );

        jSplitPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder()));
        jSplitPane.setDividerSize(10);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setResizeWeight(0.75);
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setOneTouchExpandable(true);

        jPanel_Demo2.setBorder(javax.swing.BorderFactory.createTitledBorder("Demo 2"));

        jTable_EmployeeData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "MI", "Last Name", "Birth Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane_TableContainer.setViewportView(jTable_EmployeeData);
        jTable_EmployeeData.getColumnModel().getColumn(1).setMinWidth(10);
        jTable_EmployeeData.getColumnModel().getColumn(1).setPreferredWidth(10);

        jButton_Demo2.setText("Display Employee Table");
        jButton_Demo2.setEnabled(false);
        jButton_Demo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Demo2(evt);
            }
        });

        javax.swing.GroupLayout jPanel_Demo2Layout = new javax.swing.GroupLayout(jPanel_Demo2);
        jPanel_Demo2.setLayout(jPanel_Demo2Layout);
        jPanel_Demo2Layout.setHorizontalGroup(
            jPanel_Demo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Demo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_TableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addComponent(jButton_Demo2))
                .addContainerGap())
        );
        jPanel_Demo2Layout.setVerticalGroup(
            jPanel_Demo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo2Layout.createSequentialGroup()
                .addComponent(jButton_Demo2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_TableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
        );

        jSplitPane.setTopComponent(jPanel_Demo2);

        jPanel_Demo3.setBorder(javax.swing.BorderFactory.createTitledBorder("Demo 3"));

        buttonGroup_Gender.add(jRadioButton_Men);
        jRadioButton_Men.setText("Men");
        jRadioButton_Men.setActionCommand("M");
        jRadioButton_Men.setEnabled(false);
        jRadioButton_Men.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComponents_Demo3(evt);
            }
        });

        buttonGroup_Gender.add(jRadioButton_Women);
        jRadioButton_Women.setText("Women");
        jRadioButton_Women.setActionCommand("F");
        jRadioButton_Women.setEnabled(false);
        jRadioButton_Women.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComponents_Demo3(evt);
            }
        });

        jTextArea_HoursOutput.setColumns(20);
        jTextArea_HoursOutput.setRows(3);
        jScrollPane1.setViewportView(jTextArea_HoursOutput);

        jComboBox_DepartmentNumber.setEnabled(false);
        jComboBox_DepartmentNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComponents_Demo3(evt);
            }
        });

        jLabel_Department.setText("Department:");

        javax.swing.GroupLayout jPanel_Demo3Layout = new javax.swing.GroupLayout(jPanel_Demo3);
        jPanel_Demo3.setLayout(jPanel_Demo3Layout);
        jPanel_Demo3Layout.setHorizontalGroup(
            jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_Men)
                    .addComponent(jRadioButton_Women))
                .addGap(18, 18, 18)
                .addGroup(jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Department)
                    .addGroup(jPanel_Demo3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jComboBox_DepartmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_Demo3Layout.setVerticalGroup(
            jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Demo3Layout.createSequentialGroup()
                .addGroup(jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_Demo3Layout.createSequentialGroup()
                        .addGroup(jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton_Men)
                            .addComponent(jLabel_Department))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_Demo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton_Women)
                            .addComponent(jComboBox_DepartmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSplitPane.setBottomComponent(jPanel_Demo3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel_DbUrl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_DbUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_ConnectDB))
                    .addComponent(jPanel_Demo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_DbUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_DbUrl)
                    .addComponent(jButton_ConnectDB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Demo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ////////////////////////////////////////////////////////////////////////////////////
    ///// The "Event Handler" methods below had their *signatures* auto-generated  /////
    ///// by Netbeans, but the programmer must fill in the details.  It is good    /////
    ///// programming practice to make the event handlers as small as possible and /////
    ///// only do "GUI Related" things withing them.  Call another method for the  /////
    ///// core program logic!                                                      /////
    ////////////////////////////////////////////////////////////////////////////////////
    private void jButton_Demo1(ActionEvent evt) {//GEN-FIRST:event_jButton_Demo1
        statementDemo1();
    }//GEN-LAST:event_jButton_Demo1

    private void jButton_Demo2(ActionEvent evt) {//GEN-FIRST:event_jButton_Demo2
        statementDemo2();
    }//GEN-LAST:event_jButton_Demo2

    private void jComponents_Demo3(ActionEvent evt) {//GEN-FIRST:event_jComponents_Demo3
        ButtonModel genderSelectionModel = this.buttonGroup_Gender.getSelection();
        Object selectedDepartment = this.jComboBox_DepartmentNumber.getSelectedItem();

        // Since this method is triggerd by events from multiple components
        // -- and depends on the state of multiple components -- it's entirely
        // possible that another component might not be "ready" when an event occurs.
        //
        // Though explicitly checking for "null" is *usually* a bad practice, this is
        // actually the easiest way to handle this particular situation. Otherwize,
        // a lot more code would be needed to make sure everything happens in order.
        if (genderSelectionModel != null && selectedDepartment != null) {
            this.jTextArea_HoursOutput.setText(null);
            String gender = genderSelectionModel.getActionCommand();
            String dept = selectedDepartment.toString();

            PreparedStatementDemo1(gender, dept);
        }
    }//GEN-LAST:event_jComponents_Demo3

    private void jButton_ConnectDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConnectDBActionPerformed
        final String msg = "Connecting. Please wait...";
        try {
            // Get the old window title text and use the title to display a message...
            String UrlString = this.jTextField_DbUrl.getText();
            String oldTitle = this.getTitle();
            this.setTitle(msg);

            // Create the database connection demo object.  NNOTE: This may actually
            // cause the entire GUI to "freeze" for a few seconds.  This behavior
            // can be avoided, but doing so requires a bit more involved handling 
            // of "long running events" which would simply complicate the example.
            this.dba = new DbAccess(UrlString);

            // Reset the window.
            this.setTitle(oldTitle);

            // Prep the rest of the GUI.
            guiSetup();
        } catch (SQLException ex) {
            // Geneerate a simple error message dialog.
            JOptionPane.showMessageDialog(this, ex, "Error Connecting...", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
        }
    }//GEN-LAST:event_jButton_ConnectDBActionPerformed

    private void applicationClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_applicationClosing
        // Close the database connection if it exists.
        if (this.dba != null) {
            this.dba.close();
        }

        String msg = "No one likes a quitter...\n"
                + "This is just here to show how to clean-up when the user quits.";
        JOptionPane.showMessageDialog(this, msg, "Terminating...", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_applicationClosing

    /**
     * Add departments to the combo box based on those stored in the database.
     */
    private void populateDepartmentComboBox() {
        List<Integer> departments = this.dba.getDepartmentList();

        for (Integer dept : departments) {
            this.jComboBox_DepartmentNumber.addItem(dept);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ParseException {

        // Set the "Look and Feel" to the platform default.  If that fails
        // (though it really never should on a PC) use the Java Default decorations.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  // FOR DEBUGGING ONLY (*always* do better logging in a "real" program!)
            javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        }


        // This technique is just used to allow the JVM to fully initilize the
        // Application before actually displaying the interface.  (For instance,
        // you could have a "splash screen" inplace until the interface is ready).
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                (new GuiModeDemo()).setVisible(true);
            }
        });


        // This effectively does the same thing as above, but the interface may not
        // be ready until until a few moments after the display.  The danger is that
        // a user may attempt to interact with the application *before* a critical
        // initialization is complete and then get errors which are VERY hard to trace!
        // (Trust me... I know this hard-learned from experience!)

//        GuiModeDemo demo = new GuiModeDemo();
//        demo.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_Gender;
    private javax.swing.JButton jButton_ConnectDB;
    private javax.swing.JButton jButton_Demo1;
    private javax.swing.JButton jButton_Demo2;
    private javax.swing.JComboBox jComboBox_DepartmentNumber;
    private javax.swing.JLabel jLabel_DbUrl;
    private javax.swing.JLabel jLabel_Department;
    private javax.swing.JLabel jLabel_Salary;
    private javax.swing.JPanel jPanel_Demo1;
    private javax.swing.JPanel jPanel_Demo2;
    private javax.swing.JPanel jPanel_Demo3;
    private javax.swing.JRadioButton jRadioButton_Men;
    private javax.swing.JRadioButton jRadioButton_Women;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_TableContainer;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JTable jTable_EmployeeData;
    private javax.swing.JTextArea jTextArea_HoursOutput;
    private javax.swing.JTextField jTextField_DbUrl;
    private javax.swing.JTextField jTextField_Salary;
    // End of variables declaration//GEN-END:variables
}
