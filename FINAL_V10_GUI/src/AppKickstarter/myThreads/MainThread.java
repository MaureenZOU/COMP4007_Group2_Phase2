package AppKickstarter.myThreads;

import AppKickstarter.TableGUI;
import AppKickstarter.misc.*;
import AppKickstarter.Main;

import java.util.ArrayList;

/**
 * MainThread class object main thread contains the logic of preparing out message base on in message.
 * It is encapsulated inside our server and has no direct connection with network.
 * It is the real handler of messages.
 *
 * @author sunjingxuan
 */
public class MainThread extends AppThread {

    String[] instrs;
    String intype;
    String details;
    String cid;
    int nPersons;
    int ticketAckNo;
    int ticketIssueNo = 0;
    int tid;
    String tidstr;
    boolean isTooLong;
    TableGUI t;

    //for stat
    double successfulTran=0;
    double queue2long=0;
    double totalTrans=0;
    int totalPerson=0;
    int sPerson;
    int totalSpd;

    /**
     * Class constructor.
     *
     * @param id the id of main thread, no actual use
     * @param main the "server" object
     */
    public MainThread(String id, Main main, TableGUI t){
        super(id, main);
        this.t=t;
    }


    /**
     * Keeps receiving messages from inThread through MBox.
     * Separates the message into message types and keeps a reference of useful message tokens.
     * Responds differently according to message types.
     * There are in total 3 types of messages that a main thread will receive, namely:
     *
     * TicketReq: on receiving TicketReq, creates a Ticket object, then puts it into corresponding client waiting queue;
     * before actually putting it into queue, first checks if queue is too long;
     * if queue is too long, sends QueueTooLong to outThread; if not, sends TicketRep.
     *
     * TicketAck: on receiving TicketAck, finds the corresponding queue according to nPersons;
     * finds in the queue the Ticket according to ticketNo by looping through the queue;
     * announces to empty tables that the client ACKed by setting isACKed of this Ticket to true;
     * sends TableAssign to outThread.
     *
     * CheckOut: on receiving CheckOut, finds the checked-out table thread by table number (which is set to be equal to table thread's thread id);
     * the corresponding table thread will further handle the checked-out situation by itself.
     *
     * @see <a href="http://cslinux0.comp.hkbu.edu.hk/~comp4007/2017-18/project/ProjIntro-v03.0.pdf">"Term Project – Restaurant Queuing System (v3.0)"</a>
     */
    public void run(){
        log.info(id + ": main thread starting...");
        this.t.getTextAreaClients().append(id + ": main thread starting...\n");

        for (boolean quit = false; !quit;) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");
            this.t.getTextAreaClients().append(id + ": message received: [" + msg + "].\n");
            
            ArrayList<Ticket> temp = main.getQueue(2);

            instrs = msg.getDetails().split(": ");
            intype = instrs[0];
            if(instrs.length>=2) {
                instrs = instrs[1].split(" ");
            } else {
                //for WakeAndCall,
                //instr = instrs[1];
            }

            switch (intype) {
                case "TicketReq":
                    cid = instrs[0];
                    nPersons = Integer.parseInt(instrs[1].replaceAll("[^0-9.]", ""));

                    ticketIssueNo++;
                    totalPerson=totalPerson+nPersons;

                    totalTrans++;
                    this.t.setStat(3,totalTrans,totalPerson,0);
                    Ticket t = new Ticket(ticketIssueNo, cid, nPersons, false);


                    isTooLong=main.checkTooLong(nPersons);
                    if(!isTooLong){
                        //main.getQueue(nPersons).add(t);
                        main.addToQueue(t);

                      //TicketRep
                        details = "TicketRep: " + cid + " " + nPersons + " " + ticketIssueNo + "\r\n";
                        main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    }else{

                        queue2long++;
                        this.t.setStat(1,queue2long,totalPerson,0);

                        details = "QueueTooLong: " + cid + " " + nPersons+ "\r\n";
                        main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    }
                    break;


                case "TicketAck":
                    ticketAckNo = Integer.parseInt(instrs[0]);
                    tidstr = instrs[1];
                    nPersons = Integer.parseInt(instrs[2]);

                    main.tables[Integer.parseInt(tidstr)].theTicket.isACKed = true;
//                    ArrayList<Ticket> theQueue = main.getQueue(nPersons);
//                    for (Ticket ticket : theQueue) {
//                        if(ticket.ticketNo == ticketNo){
//                            ticket.isACKed = true;
//                            System.out.println("ticket " + ticketNo + " ACK set true!");
//
//                            break;
//                        }
//                    }

                    sPerson=sPerson+nPersons;

                    //TableAssign
                    details = "TableAssign: " + ticketAckNo + " " + tidstr + "\r\n";
                    main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, details));
                    break;

                case "CheckOut":
                    tidstr = instrs[0];
                    totalSpd=Integer.parseInt(instrs[1]);
                    //manipulate empty table list
                    //send nothing
                    details = "Client CheckOut!" + "\r\n";
                    successfulTran++;
                    this.t.setStat(0,successfulTran,sPerson,totalSpd);
                    main.getThread(tidstr).getMBox().send(new Msg(id, null, Msg.Type.CheckOut, details));
                    break;

                case "Terminate":
                    quit = true;
                    break;

                default:
                    log.severe(id + ": unknown message type!!");
                    this.t.getTextAreaClients().append(id + ": unknown message type!!\n");
                    break;
            }

        }

        // declaring our departure
        main.unregThread(this);
        log.info(id + ": terminating...");
        this.t.getTextAreaClients().append(id + ": terminating...\n");
    }
}
