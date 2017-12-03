package AppKickstarter;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * TableGUI class object tGUI is the Server Panel.
 *
 * @author LI Shiying
 */
public class TableGUI extends javax.swing.JFrame implements Runnable {

    /**
     * Class constructor
     *
     * Creates new form TableGUI
     */
    public TableGUI() {
        initComponents();
    }

    /**
     * Initializes the GUI Server Panel.
     * <p>
     * Creates a label for display title,
     * Initializes 4 panels for display different information.
     * Creates a button to let the user to start the Server,
     * a panel for display empty table information (Empty Tables),
     * a panel for display occupied table information (Occupied Tables),
     * a panel for display log information (Staff Miscs),
     * a panel for display transaction statistics (Statistics).
     * <p>
     * Creates 4 labels to indicate the information to be displayed in the transaction statistics panel:
     * one is Successful Transactions for display information related to successful transactions,
     * one is Uncertain Transactions for display information related to failed transactions or still eating transactions,
     * one is Queue Too Long Transactions for display information related to queue to long,
     * one is Total Transactions for display information related to total transactions,
     * <p>
     * All the 4 labels described above have at most 5 related text fields for display information:
     * number of transactions,
     * percentage of transactions,
     * average number of persons per transaction,
     * total income brought by the transaction and average cost of the meal per table.
     * <p>
     * Initializes average number of persons per transaction of successful transactions,
     * queue too long transactions,
     * uncertain transactions to "---";
     * initializes total income and average cost of the meal per table of queue too long transactions and uncertain transactions to "---";
     * initializes other fields to zero.
     * <p>
     * Creates listeners to these buttons to catch the actions performed of the buttons.
     * <p>
     * This method is called within the constructor to initialize the form.
     */
    private void initComponents() {

        //components for 3 tabs
        jLabel1 = new javax.swing.JLabel();
        startServer = new javax.swing.JButton();
        mainPanel = new javax.swing.JTabbedPane();
        emptyT = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        disEmptyT = new javax.swing.JTextArea();
        occuT = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        disOccuT = new javax.swing.JTextArea();
        staffP = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        disMiscs = new javax.swing.JTextArea();

        //components for statistics
        Statistics = new javax.swing.JPanel();
        sTrans = new javax.swing.JLabel();
        queue2long = new javax.swing.JLabel();
        overallTrans = new javax.swing.JLabel();
        count = new javax.swing.JLabel();
        failedTrans = new javax.swing.JLabel();
        percent = new javax.swing.JLabel();
        nPs = new javax.swing.JLabel();
        decoration = new javax.swing.JLabel();
        sTransText = new javax.swing.JTextField();
        queue2longText = new javax.swing.JTextField();
        failedTransText = new javax.swing.JTextField();
        overallTransText = new javax.swing.JTextField();
        overallTransText1 = new javax.swing.JTextField();
        queue2longText1 = new javax.swing.JTextField();
        failedTransText1 = new javax.swing.JTextField();
        sTransText1 = new javax.swing.JTextField();
        sTransText2 = new javax.swing.JTextField();
        failedTransText2 = new javax.swing.JTextField();
        overallTransText2 = new javax.swing.JTextField();
        queue2longText2 = new javax.swing.JTextField();
        sTransText3 = new javax.swing.JTextField();
        queue2longText3 = new javax.swing.JTextField();
        failedTransText3 = new javax.swing.JTextField();
        overallTransText3 = new javax.swing.JTextField();
        cost = new javax.swing.JLabel();
        costPerP = new javax.swing.JLabel();
        sTransText4 = new javax.swing.JTextField();
        queue2longText4 = new javax.swing.JTextField();
        failedTransText4 = new javax.swing.JTextField();
        overallTransText4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setText("RQS: Server Panel");

        startServer.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        startServer.setText("Start Server");
        startServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerActionPerformed(evt);
            }
        });

        mainPanel.setName(""); // NOI18N

        disEmptyT.setEditable(false);
        disEmptyT.setColumns(20);
        disEmptyT.setRows(5);
        jScrollPane1.setViewportView(disEmptyT);

        javax.swing.GroupLayout emptyTLayout = new javax.swing.GroupLayout(emptyT);
        emptyT.setLayout(emptyTLayout);
        emptyTLayout.setHorizontalGroup(
                emptyTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(emptyTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                                .addContainerGap())
        );
        emptyTLayout.setVerticalGroup(
                emptyTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(emptyTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                                .addContainerGap())
        );

        mainPanel.addTab("Empty Tables", emptyT);

        disOccuT.setEditable(false);
        disOccuT.setColumns(20);
        disOccuT.setRows(5);
        disEmptyT.setText("Table for 1 to 2 persons:\n\n\n" +
                "Table for 2 to 4 persons:\n\n\n"+
                "Table for 5 to 6 persons:\n\n\n"+
                "Table for 7 to 8 persons:\n\n\n"+
                "Table for 9 to 10 persons:\n\n\n");
        disOccuT.setText("Table for 1 to 2 persons:\n\n\n" +
                "Table for 2 to 4 persons:\n\n\n"+
                "Table for 5 to 6 persons:\n\n\n"+
                "Table for 7 to 8 persons:\n\n\n"+
                "Table for 9 to 10 persons:\n\n\n");
        jScrollPane2.setViewportView(disOccuT);

        javax.swing.GroupLayout occuTLayout = new javax.swing.GroupLayout(occuT);
        occuT.setLayout(occuTLayout);
        occuTLayout.setHorizontalGroup(
                occuTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(occuTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                                .addContainerGap())
        );
        occuTLayout.setVerticalGroup(
                occuTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(occuTLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                                .addContainerGap())
        );

        mainPanel.addTab("Occupied Tables", occuT);

        javax.swing.GroupLayout staffPLayout = new javax.swing.GroupLayout(staffP);
        staffP.setLayout(staffPLayout);
        staffPLayout.setHorizontalGroup(
                staffPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 630, Short.MAX_VALUE)
        );
        staffPLayout.setVerticalGroup(
                staffPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 357, Short.MAX_VALUE)
        );

        disMiscs.setEditable(false);
        disMiscs.setColumns(20);
        disMiscs.setText("Please Start Server\n");
        disMiscs.setRows(5);
        jScrollPane3.setViewportView(disMiscs);

        staffP.setLayout(staffPLayout);
        staffPLayout.setHorizontalGroup(
                staffPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
        );
        staffPLayout.setVerticalGroup(
                staffPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );

        mainPanel.addTab("Staff Miscs", staffP);


        // the last statistics panel

        sTrans.setText("Successful Transactions:");

        queue2long.setText("Queue Too Long:");

        overallTrans.setText("Overall Transactions:");

        count.setText("Count    ");

        failedTrans.setText("Uncertain Transactions:");

        percent.setText("Percent");

        nPs.setText("nPersons");

        decoration.setText("------------------------------------------------------------------------------------------");

        sTransText.setEditable(false);
        sTransText.setBackground(new java.awt.Color(238, 238, 238));
        sTransText.setText("0");

        queue2longText.setEditable(false);
        queue2longText.setBackground(new java.awt.Color(238, 238, 238));
        queue2longText.setText("0");

        failedTransText.setEditable(false);
        failedTransText.setBackground(new java.awt.Color(238, 238, 238));
        failedTransText.setText("0");

        overallTransText.setEditable(false);
        overallTransText.setBackground(new java.awt.Color(238, 238, 238));
        overallTransText.setText("0");

        overallTransText1.setEditable(false);
        overallTransText1.setBackground(new java.awt.Color(238, 238, 238));
        overallTransText1.setText("0");

        queue2longText1.setEditable(false);
        queue2longText1.setBackground(new java.awt.Color(238, 238, 238));
        queue2longText1.setText("0");

        failedTransText1.setEditable(false);
        failedTransText1.setBackground(new java.awt.Color(238, 238, 238));
        failedTransText1.setText("0");

        sTransText1.setEditable(false);
        sTransText1.setBackground(new java.awt.Color(238, 238, 238));
        sTransText1.setText("0");

        sTransText2.setEditable(false);
        sTransText2.setBackground(new java.awt.Color(238, 238, 238));
        sTransText2.setText("---");


        failedTransText2.setEditable(false);
        failedTransText2.setBackground(new java.awt.Color(238, 238, 238));
        failedTransText2.setText("---");

        overallTransText2.setEditable(false);
        overallTransText2.setBackground(new java.awt.Color(238, 238, 238));
        overallTransText2.setText("0");

        queue2longText2.setEditable(false);
        queue2longText2.setBackground(new java.awt.Color(238, 238, 238));
        queue2longText2.setText("---");

        sTransText3.setEditable(false);
        sTransText3.setBackground(new java.awt.Color(238, 238, 238));
        sTransText3.setText("0");


        queue2longText3.setEditable(false);
        queue2longText3.setBackground(new java.awt.Color(238, 238, 238));
        queue2longText3.setText("---");

        failedTransText3.setEditable(false);
        failedTransText3.setBackground(new java.awt.Color(238, 238, 238));
        failedTransText3.setText("---");

        overallTransText3.setEditable(false);
        overallTransText3.setBackground(new java.awt.Color(238, 238, 238));
        overallTransText3.setText("0");

        cost.setText("Total Income");

        costPerP.setText("Income/Table");

        sTransText4.setEditable(false);
        sTransText4.setBackground(new java.awt.Color(238, 238, 238));
        sTransText4.setText("0");

        queue2longText4.setEditable(false);
        queue2longText4.setBackground(new java.awt.Color(238, 238, 238));
        queue2longText4.setText("---");

        failedTransText4.setEditable(false);
        failedTransText4.setBackground(new java.awt.Color(238, 238, 238));
        failedTransText4.setText("---");

        overallTransText4.setEditable(false);
        overallTransText4.setBackground(new java.awt.Color(238, 238, 238));
        overallTransText4.setText("---");

        javax.swing.GroupLayout StatisticsLayout = new javax.swing.GroupLayout(Statistics);
        Statistics.setLayout(StatisticsLayout);
        StatisticsLayout.setHorizontalGroup(
                StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(StatisticsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(StatisticsLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(count)
                                                .addGap(30, 30, 30)
                                                .addComponent(percent)
                                                .addGap(38, 38, 38)
                                                .addComponent(nPs)
                                                .addGap(28, 28, 28)
                                                .addComponent(cost)
                                                .addGap(18, 18, 18)
                                                .addComponent(costPerP)
                                                .addGap(27, 27, 27))
                                        .addComponent(decoration, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, StatisticsLayout.createSequentialGroup()
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(queue2long, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(sTrans, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(failedTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(25, 25, 25)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(failedTransText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(queue2longText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(sTransText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(overallTransText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(sTransText1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                                        .addComponent(queue2longText1)
                                                        .addComponent(failedTransText1)
                                                        .addComponent(overallTransText1))
                                                .addGap(25, 25, 25)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(sTransText2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                                        .addComponent(queue2longText2)
                                                        .addComponent(failedTransText2)
                                                        .addComponent(overallTransText2))
                                                .addGap(30, 30, 30)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(sTransText3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                                        .addComponent(queue2longText3)
                                                        .addComponent(failedTransText3)
                                                        .addComponent(overallTransText3))
                                                .addGap(30, 30, 30)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(queue2longText4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                                        .addComponent(sTransText4)
                                                        .addComponent(failedTransText4)
                                                        .addComponent(overallTransText4))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        StatisticsLayout.setVerticalGroup(
                StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(StatisticsLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(count, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(percent, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nPs, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cost, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(costPerP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(StatisticsLayout.createSequentialGroup()
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(sTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTransText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTransText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTransText2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTransText3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTransText4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(queue2long, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(StatisticsLayout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(queue2longText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(queue2longText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(queue2longText2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(queue2longText3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(queue2longText4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(25, 25, 25)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(failedTransText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedTransText2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedTransText3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedTransText4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedTransText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(overallTransText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTransText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTransText2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTransText3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(overallTransText4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(128, Short.MAX_VALUE))
        );

        mainPanel.addTab("Statistics", Statistics);


        // Main jframe settings

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(startServer)
                                                .addGap(21, 21, 21))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(mainPanel)
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(startServer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mainPanel)
                                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>

    /**
     * Displays a Start button for starting server.
     *
     * @param evt
     */
    private void startServerActionPerformed(java.awt.event.ActionEvent evt) {
        new Thread(this).start();
    }

    /**
     * Creates Server Panel in GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TableGUI tGUI = new TableGUI();
                tGUI.setVisible(true);
            }
        });
    }


    public JTextArea getTextAreaClients(){
        return this.disMiscs;
    }
    public JTextArea getEmptyTablePanel(){
        return this.disEmptyT;
    }
    public JTextArea getFullTablePanel(){
        return this.disOccuT;
    }

    /**
     * GUI statistics formatter.
     *
     * @param d1
     * @param d2
     * @return
     */
    public String toPercentage(double d1, double d2){
        DecimalFormat df = new DecimalFormat("##.##%");
        double percent = (d1 / d2);
        String formattedPercent = df.format(percent);
        return formattedPercent;
    }

    /**
     * GUI statistics formatter.
     *
     * @param d
     * @return
     */
    public String to2Decimal(double d){
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedDeci=df.format(d);
        return formattedDeci;
    }

    /**
     * GUI statistics formatter.
     *
     * @param d
     * @return
     */
    public String noDecimal(double d){
        DecimalFormat df = new DecimalFormat("#");
        String formattedDeci=df.format(d);
        return formattedDeci;
    }


    /**
     * Displays statistics on GUI panels.
     *
     * @param type
     * @param stats
     * @param nPersons
     * @param spending
     */
    public void setStat(int type, double stats, int nPersons, int spending){
        String strStats=Double.toString(stats);
        if(type==0){
            double total=Double.parseDouble(overallTransText.getText());
            String sPerc=toPercentage(stats,total);
            sTransText.setText(noDecimal(stats));
            sTransText1.setText(sPerc);
            failedTransText.setText(noDecimal(Double.parseDouble(failedTransText.getText())-1));
            String fPerc=toPercentage(Double.parseDouble(failedTransText.getText()),total);
            failedTransText1.setText(fPerc);
            int sS=spending+Integer.parseInt(sTransText3.getText());
            sTransText3.setText(Integer.toString(sS));
            sTransText4.setText(to2Decimal(sS/stats));
            int totalS=spending+Integer.parseInt(overallTransText3.getText());
            overallTransText3.setText(Integer.toString(totalS));
        }else if(type==1){
            double total=Double.parseDouble(overallTransText.getText());
            String qPerc=toPercentage(stats,total);
            queue2longText.setText(noDecimal(stats));
            queue2longText1.setText(qPerc);
            failedTransText.setText(noDecimal(Double.parseDouble(failedTransText.getText())-1));
            String fPerc=toPercentage(Double.parseDouble(failedTransText.getText()),total);
            failedTransText1.setText(fPerc);
        }else if (type==3){
            overallTransText.setText(noDecimal(stats));
            overallTransText1.setText("100.00%");
            failedTransText.setText(noDecimal(stats-Double.parseDouble(sTransText.getText())-Double.parseDouble(queue2longText.getText())));
            String sPerc=toPercentage(Double.parseDouble(sTransText.getText()),stats);
            String pPerc=toPercentage(Double.parseDouble(queue2longText.getText()),stats);
            String fPerc=toPercentage(Double.parseDouble(failedTransText.getText()),stats);
            sTransText1.setText(sPerc);
            queue2longText1.setText(pPerc);
            failedTransText1.setText(fPerc);
            overallTransText2.setText(to2Decimal(nPersons/stats));
        }
    }


    /**
     * Starts server (after pressing Start button).
     */
    public void run() {
        try {
            Main main= new Main("sjx", "127.0.0.1", 54321,this);
            main.startApp();
            disMiscs.setText("Socket Server Started!");
            while(true){
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // conponents for the first 3 tabs
    private JTextArea disEmptyT;
    private JTextArea disOccuT;
    private JTextArea disMiscs;
    private JPanel emptyT;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTabbedPane mainPanel;
    private JPanel occuT;
    private JPanel staffP;
    private JButton startServer;


    // components for statistics
    private javax.swing.JPanel Statistics;
    private javax.swing.JLabel count;
    private javax.swing.JLabel decoration;
    private javax.swing.JLabel failedTrans;
    private javax.swing.JTextField failedTransText;
    private javax.swing.JLabel nPs;
    private javax.swing.JLabel overallTrans;
    private javax.swing.JTextField overallTransText;
    private javax.swing.JLabel queue2long;
    private javax.swing.JTextField queue2longText;
    private javax.swing.JLabel sTrans;
    private javax.swing.JTextField sTransText;
    private javax.swing.JLabel percent;

    private javax.swing.JTextField sTransText3;
    private javax.swing.JTextField sTransText4;
    private javax.swing.JTextField queue2longText4;
    private javax.swing.JTextField queue2longText3;
    private javax.swing.JTextField overallTransText3;
    private javax.swing.JTextField overallTransText4;
    private javax.swing.JTextField failedTransText3;
    private javax.swing.JTextField failedTransText4;
    private javax.swing.JTextField sTransText1;
    private javax.swing.JTextField sTransText2;
    private javax.swing.JTextField queue2longText1;
    private javax.swing.JTextField queue2longText2;
    private javax.swing.JTextField overallTransText1;
    private javax.swing.JTextField overallTransText2;
    private javax.swing.JTextField failedTransText1;
    private javax.swing.JTextField failedTransText2;
    private javax.swing.JLabel cost;
    private javax.swing.JLabel costPerP;

}
