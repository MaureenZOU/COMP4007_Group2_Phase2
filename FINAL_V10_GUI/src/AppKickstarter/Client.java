package AppKickstarter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class object client is the Client Panel.
 * Simulates the real version of RQS in daily life,
 * in which users can key in the number of persons by themselves and get tickets from the system.
 *
 * @author LI Shiying
 */
public class Client extends javax.swing.JFrame implements Runnable {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    static Client client;
    final String ticketReq="TicketReq: ";
    final String cidP="Client-";
    int numP;
    int cidDft=999;

    /**
     * Class constructor.
     */
    public Client() {
        initComponents();
    }

    /**
     * Initializes the GUI Client Panel.
     * <p>
     * Creates a start frame for performing actions to connect to the server.
     * Creates a label to display the title of client panel.
     * Creates a label to tell the user to start the server.
     * Creates a button "connect" to click and connect to the server.
     * Creates a text field to display the failed to connect server information.
     * <p>
     * Creates a main frame for sending ticket request to the server after connections.
     * Creates 10 buttons to let the client click and indicate the number of persons.
     * Creates a text field to display the number of persons the client input by clicking the buttons.
     * Creates a button to let the client to send the ticket request.
     * Creates a text fields to show the ticket reply or number of person format wrong.
     * Creates listeners to these buttons to catch the actions performed of the buttons.
     * <p>
     * This method is called within the constructor to initialize the form.
     */

    private void initComponents() {

        usrFrame = new javax.swing.JFrame();
        alert = new javax.swing.JTextField();
        connect = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        getTicket = new javax.swing.JButton();
        nPersons = new javax.swing.JTextField();
        response = new javax.swing.JTextField();
        numberPanel = new javax.swing.JPanel();
        button01 = new javax.swing.JButton();
        button02 = new javax.swing.JButton();
        button03 = new javax.swing.JButton();
        button04 = new javax.swing.JButton();
        button05 = new javax.swing.JButton();
        button06 = new javax.swing.JButton();
        button07 = new javax.swing.JButton();
        button08 = new javax.swing.JButton();
        button09 = new javax.swing.JButton();
        button00 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();

        usrFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        usrFrame.setBounds(new java.awt.Rectangle(0, 0, 465, 400));

        alert.setEditable(false);
        alert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertActionPerformed(evt);
            }
        });

        connect.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Restaurant Queuing System: Client");

        jLabel2.setText("Please Connect to the Server!");

        javax.swing.GroupLayout usrFrameLayout = new javax.swing.GroupLayout(usrFrame.getContentPane());
        usrFrame.getContentPane().setLayout(usrFrameLayout);
        usrFrameLayout.setHorizontalGroup(
                usrFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(usrFrameLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(usrFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(usrFrameLayout.createSequentialGroup()
                                                .addComponent(alert, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35)
                                                .addComponent(connect))
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(usrFrameLayout.createSequentialGroup()
                                                .addGap(199, 199, 199)
                                                .addComponent(jLabel2)))
                                .addContainerGap(43, Short.MAX_VALUE))
        );
        usrFrameLayout.setVerticalGroup(
                usrFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(usrFrameLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addGroup(usrFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(alert, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(connect))
                                .addGap(49, 49, 49))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 680, 380));

        label.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        label.setText("Welcome, please input the number of people and get ticket.");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("RQS: Client");

        getTicket.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        getTicket.setText("Get Ticket!");
        getTicket.setActionCommand("getTicket");
        getTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTicketActionPerformed(evt);
            }
        });

        nPersons.setEditable(false);
        nPersons.setBackground(new java.awt.Color(238, 238, 238));
        nPersons.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        nPersons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nPersonsActionPerformed(evt);
            }
        });

        response.setEditable(false);
        response.setBackground(new java.awt.Color(238, 238, 238));
        response.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        response.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                responseActionPerformed(evt);
            }
        });

        button01.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button01.setText("1");

        button02.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button02.setText("2");

        button03.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button03.setText("3");

        button04.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button04.setText("4");

        button05.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button05.setText("5");

        button06.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button06.setText("6");

        button07.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button07.setText("7");

        button08.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button08.setText("8");

        button09.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button09.setText("9");

        button00.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        button00.setText("0");

        button00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button00ActionPerformed(evt);
            }
        });

        button01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button01ActionPerformed(evt);
            }
        });

        button02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button02ActionPerformed(evt);
            }
        });

        button03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button03ActionPerformed(evt);
            }
        });

        button04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button04ActionPerformed(evt);
            }
        });

        button05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button05ActionPerformed(evt);
            }
        });

        button06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button06ActionPerformed(evt);
            }
        });

        button07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button07ActionPerformed(evt);
            }
        });

        button08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button08ActionPerformed(evt);
            }
        });

        button09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button09ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout numberPanelLayout = new javax.swing.GroupLayout(numberPanel);
        numberPanel.setLayout(numberPanelLayout);
        numberPanelLayout.setHorizontalGroup(
                numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(numberPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(numberPanelLayout.createSequentialGroup()
                                                .addComponent(button01, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(button02, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(button03, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(numberPanelLayout.createSequentialGroup()
                                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(numberPanelLayout.createSequentialGroup()
                                                                .addComponent(button04, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(button05, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(numberPanelLayout.createSequentialGroup()
                                                                .addComponent(button07, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(button08, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(button09, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(button06, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(button00, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(25, Short.MAX_VALUE))
        );
        numberPanelLayout.setVerticalGroup(
                numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(numberPanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button01, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button02, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button03, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button04, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button05, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button06, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(numberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button09, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button08, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button07, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(button00, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(40, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel1.setText("Num of People");

        cancel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        cancel.setText("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(numberPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(response)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(getTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel1)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(nPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(label)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(numberPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(nPersons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(39, 39, 39)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(getTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(46, 46, 46)
                                                .addComponent(response, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(16, 16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>


    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText("");
    }

    private void button01ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"1");
    }
    private void button02ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"2");
    }
    private void button03ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"3");
    }
    private void button04ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"4");
    }
    private void button05ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"5");
    }
    private void button06ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"6");
    }
    private void button07ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"7");
    }
    private void button08ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"8");
        System.out.println(nPersons.getText());
    }
    private void button09ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"9");
    }
    private void button00ActionPerformed(java.awt.event.ActionEvent evt) {
        nPersons.setText(nPersons.getText()+"0");
    }

    /**
     * Reads message sent by server to display TicketRep
     */
    public void run() {
        while (true) {
            try {
                System.out.println("run: ");
                byte b[] = new byte[1024];
                in.read(b);
                String inmsg = new String(b);
                response.setText("Server Reply: "+inmsg);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * Connects client to server.
     *
     * @param serverIP
     * @param port
     * @throws UnknownHostException
     * @throws IOException
     */
    public void connect(String serverIP, int port) throws UnknownHostException, IOException {
        socket = new Socket(InetAddress.getByName(serverIP), port);
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        Thread thread = new Thread(this);
        thread.start();
    }


    /**
     * Creates a client thread for network connection (after pressing Connect button).
     *
     * @param evt
     */
    private void connectActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            client.connect("127.0.0.1", 54321);
            client.setVisible(true);
            client.usrVisible(false);
        } catch (IOException ex) {
            alert.setText("Cannot Find Server!");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Generates TicketReq and sends it to server.
     *
     * @param evt
     */
    private void getTicketActionPerformed(java.awt.event.ActionEvent evt) {
        String req="";
        String numString=nPersons.getText();
        numP = Integer.parseInt(numString);
        if (numP<=10&&numP>0){

            try {cidDft++;
//                req="TicketReq: Client-0001 2\r\n";
                req=ticketReq+cidP+Integer.toString(cidDft)+' '+numString+"\r\n";
                System.out.println("req is "+req);
                response.setText("Please Wait... "+req);
                out.writeBytes(req);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            response.setText("Num should be > 0 and <= 10.");
        }
    }


    private void alertActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void nPersonsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void responseActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void usrVisible(boolean b) {
        usrFrame.setVisible(b);
    }

    /**
     * Creates client's Start Panel in GUI, for network connection.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                client = new Client();
                client.usrVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField alert;
    private javax.swing.JButton connect;
    private javax.swing.JButton getTicket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel label;
    private javax.swing.JTextField nPersons;
    private javax.swing.JTextField response;
    private javax.swing.JFrame usrFrame;

    private javax.swing.JButton button00;
    private javax.swing.JButton button02;
    private javax.swing.JButton button01;
    private javax.swing.JButton button03;
    private javax.swing.JButton button04;
    private javax.swing.JButton button05;
    private javax.swing.JButton button06;
    private javax.swing.JButton button07;
    private javax.swing.JButton button08;
    private javax.swing.JButton button09;
    private javax.swing.JPanel numberPanel;
    private javax.swing.JButton cancel;
    // End of variables declaration
}
