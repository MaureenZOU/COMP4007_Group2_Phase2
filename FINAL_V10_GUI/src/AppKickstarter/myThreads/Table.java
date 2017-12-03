package AppKickstarter.myThreads;

import AppKickstarter.TableGUI;
import AppKickstarter.misc.*;
import AppKickstarter.Main;

import java.util.ArrayList;

/**
 * Table class object table threads are responsible for sending out TicketCall when the table becomes empty.
 * In actual situation, there are in total 32 table threads each representing only one table.
 * A table thread has the attributes of table number (which is set to be equal to table thread id), number of seats, the state of empty or not.
 *
 * @author sunjingxuan
 */
public class Table extends AppThread{

    public int tid;
    public int seats;
    boolean isEmpty, isWaitingSendMsg;
    Ticket theTicket;
    TableGUI t;

    /**
     * Class constructor.
     *
     * @param id the id of table thread, in actual situation table number is set to be equal to this id
     * @param main the "server" object
     * @param tid table number
     * @param seats number of seats
     * @param t TableGUI
     */
    public Table(String id, Main main, int tid, int seats, TableGUI t) {
        super(id, main);
        this.tid = tid;
        this.seats = seats;
        this.isEmpty = true;
        this.isWaitingSendMsg = false;
        this.t=t;
    }

    /**
     * Gets table number for the purpose of displaying in GUI.
     * @return tid table number
     */
    public int getTID(){
        return this.tid;
    }

    /**
     * Loops and keeps receiving CheckOut message from main thread.
     * By CheckOut, client assigned to this table left and the table becomes empty again.
     * So on receiving CheckOut, the table changes its state to empty and adds itself to corresponding empty table list.
     * Checks the waiting queue whose nPersons is same as table's number of seats,
     * gets the ticket and lets the client eats,
     * waits current client to finish eating and send CheckOut to server, thus comes back to the start of the loop.
     *
     * When the server first starts,
     * all table thread directly add themselves to corresponding empty table list and check queues
     * because the initial state is empty.
     * Once getting a client, it enters the loop to wait for CheckOut and then checkQueue again.
     */
    public void run() {
        log.info("table " + id  + " created; tid = " + Integer.toString(tid));
        this.t.getTextAreaClients().append("table " + id  + " created; tid = " + Integer.toString(tid)+"\n");

        main.addToEmptyTableList(this);
        checkQueue(seats);

        for (boolean quit = false; !quit; ) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");
            this.t.getTextAreaClients().append(msg+"\n");

            switch (msg.getType()) {
                case CheckOut:
                    log.info(tid + ": Client at Table " + tid + " has CheckOut.");
                    this.t.getTextAreaClients().append(tid + ": Client at Table " + tid + " has CheckOut."+"\n");
                    isEmpty = true;
                    main.addToEmptyTableList(this);
                    checkQueue(seats);
                    break;

                case Terminate:
                    quit = true;
                    break;

                default:
                    log.severe(id + ": unknown message type!!");
                    this.t.getTextAreaClients().append(id + ": unknown message type!!"+"\n");
                    break;
            }
        }

        // declaring our departure
        main.unregThread(this);
        log.info(id + ": terminating...");
        this.t.getTextAreaClients().append(id + ": terminating..."+"\n");
    }



    /**
     * The first empty table in an empty table list is responsible for calling the next waiting client
     * whose nPerons is same as this table's number of seats.
     * Other empty tables should not compete to call.
     * This first table gets the corresponding waiting queue, gets the first Ticket in the queue and starts calling.
     * TicketCall message is prepared and sent to outThread.
     * After calling the Ticket, the table will wait for ACK for certain time.
     * <p>
     * There are 2 situations while waiting for ACK:
     * Normal situation is the client with the Ticket sends ACK,
     * main thread helps to set this Ticket's isACKed to true thus this table can know the Ticket ACKed indirectly,
     * then the table stops checking, sets itself to full state and removes itself from corresponding empty table list,
     * it removes the ACKed Ticket from corresponding client waiting queue;
     * the client now is considered to be eating and the table just waits for this client's CheckOut.
     * <p>
     * Unideal situation is while table sending call to the found ticket, the client cannot wait and leaves,
     * thus no ACK of this Ticket is received and this table will keep waiting until the certain period of time is up;
     * but it will removes the Ticket anyways
     * because that client is considered to be left by the system and its ticket would never be referred to afterwards;
     * the state of this table keeps as empty, so that is comes back to the start of the checking loop;
     * it will get the new first ticket in the queue (as the original first ticket is removed),
     * and starts calling this ticket as the procedures mentioned above;
     * it will keep calling next ticket if no one ACKed yet, until the waiting queue is empty;
     * if the queue becomes empty, the table keeps waiting until someone is added to the queue,
     * thus begins the new round of calling.
     *
     * @param nPersons number of seats, which is equal to the corresponding nPerons of the tickets the table will be calling
     */
    public void checkQueue(int nPersons) {
        ArrayList<Table> etl;
        long startTime;

        //cheat (bug solved. no cheating any more!)
//        int printnum = 0;

        while(isEmpty){
//            System.out.println("table " + tid + " still running!");

            do {
                //keep checking
                try {
                    Thread.currentThread().sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(main.getQueue(nPersons).isEmpty());

//            theTicket = main.removeFromQueue(nPersons);
            etl = main.getEmptyTableList(nPersons);
            theTicket = main.getQueue(nPersons).get(0);

            if(etl.get(0) == this && !theTicket.isACKed){
            	main.removeFromQueue(theTicket);
                System.out.println(tid + " is the first in etl!");
                main.out.getMBox().send(new Msg(id, null, Msg.Type.OutMsg, "TicketCall: " + theTicket.ticketNo + " " + tid + "\r\n"));
                isWaitingSendMsg = true;
                
                while (isWaitingSendMsg) {
					try {
						Thread.currentThread().sleep(5);
						} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

                startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime <= 100)){
                    try {
                        Thread.currentThread().sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(theTicket.isACKed) {
                        System.out.println(tid + " got the client!");
//                            theTicket.isACKed = false;
                        isEmpty = false;
                        main.removeFromEmptyTableList(this);
                        break;
                    }
                }
            }
//            if(etl.get(0) == this && printnum > 0){
//                System.out.println(tid + " unfortunately time out!");
//                printnum++;
//            }
        }
    }
}
