package AppKickstarter;

import AppKickstarter.misc.*;
import AppKickstarter.myThreads.*;
import AppKickstarter.timer.Timer;


import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The creation of a Main class object, main, is the starting point of the RQS system.
 * A main object is in charge of creating global fields of the system
 * which will further referenced by main thread and table threads.
 * Such global objects includes:
 * 5 client waiting queues, for keeping the not-yet-called ticket objects on a first-come-first-serve basis;
 * 5 empty table lists, for queueing and deciding the order of which table to get the waiting clients;
 * input thread, output thread, main thread for internal and client-server communication;
 * GUI classes.
 * <p>
 * The number of each type of tables are read from RQS.cfg file
 *
 * @author      SUN Jingxuan
 * @author      LI Shiying
 * @author      ZHOU Yunchang
 * @author      HUANG Hao
 * @author      ZOU Xueyan
 * @author      LIU Ruipeng
 * @version     %I%, %G%
 * @since       1.0
 */
public class Main extends AppKickstarter{
    public String ServerIP;
    public int portNum;
    public Table[] tables;
    public Socket cSocket;

    DataOutputStream outToClient;
    DataInputStream inFromClient;

    public InThread in;
    public OutThread out;
    public MainThread mt;
    TableGUI t;
    int[] tableMax;

    private static ArrayList<Ticket> queue1 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue2 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue3 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue4 = new ArrayList<Ticket>();
    private static ArrayList<Ticket> queue5 = new ArrayList<Ticket>();
    private static ArrayList<Table> etl1 = new ArrayList<Table>();
    private static ArrayList<Table> etl2 = new ArrayList<Table>();
    private static ArrayList<Table> etl3 = new ArrayList<Table>();
    private static ArrayList<Table> etl4 = new ArrayList<Table>();
    private static ArrayList<Table> etl5 = new ArrayList<Table>();


    /**
     * Class constructor.
     * Creates main thread for handling internal messages,
     * creates all table threads,
     * accomplishes connection with ClientStream,
     * creates input thread and output thread for getting and sending all prepared message to ClientStream.
     *
     * @param id main object id, no actual use
     * @param ServerIP
     * @param portNum
     * @param t TableGUI
     * @throws IOException
     */
    public Main(String id, String ServerIP, int portNum, TableGUI t) throws IOException {
        super(id);
        this.ServerIP = ServerIP;
        this.portNum = portNum;
        this.t=t;

        mt = new MainThread("mt", this, this.t);
        new Thread(mt).start();

        tableMax = new int[6];
        for (int i = 0; i < 6; i++) {
            tableMax[i] = 0;
        }
        for (int i = 1; i < 6; i++) {
            tableMax[i] = tableMax[i - 1] + AppKickstarter.tableValue[i];
        }

        int totalTables = 0;
        for (int i = 0; i < 6; i++) {
            totalTables += tableMax[i];
        }
        tables = new Table[totalTables];

        for (int i = 0; i < tableMax[1]; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 2, this.t);
            new Thread(tables[i]).start();
        }
        for (int i = 10; i < tableMax[2]; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 4, this.t);
            new Thread(tables[i]).start();
        }
        for (int i = 20; i < tableMax[3]; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 6, this.t);
            new Thread(tables[i]).start();
        }
        for (int i = 28; i < tableMax[4]; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 8, this.t);
            new Thread(tables[i]).start();
        }
        for (int i = 30; i < tableMax[5]; i++) {
            tables[i] = new Table(Integer.toString(i), this, i, 10, this.t);
            new Thread(tables[i]).start();
        }

        ServerSocket sSocket = new ServerSocket(portNum);
        System.out.println(String.format("Listening at port %d... ", portNum));

        cSocket = sSocket.accept();
        outToClient=new DataOutputStream(new BufferedOutputStream(cSocket.getOutputStream()));
        inFromClient=new DataInputStream(new BufferedInputStream(cSocket.getInputStream()));

        in = new InThread("in", this, inFromClient, this.t);
        out = new OutThread("out", this, outToClient, this.t);
        new Thread(in).start();
        new Thread(out).start();

    }
    // gui

    /**
     * Gets table number for occupied tables for the purpose of displaying in GUI.
     *
     * @param tables the list of all tables of corresponding number of persons
     * @param np number of persons
     * @return
     */
    public String getOccTid(ArrayList<Table> tables,int np){
        ArrayList<Integer> emptyTid = new ArrayList<Integer>();
        ArrayList<Integer> occuTid = new ArrayList<Integer>();
        for (int i=0;i< tables.size();i++){
            emptyTid.add(tables.get(i).getTID());
        }

        int minn=np-1;
        String thelist="Table for "+minn +" to " + np+ " persons:\n";
        if(np==2){
            for (int i = 0; i < tableMax[1]; i++){
                boolean isEmpty=false;
                for (int j=0;j< emptyTid.size();j++){
                    if(i==emptyTid.get(j)){
                        isEmpty=true;
                        break;
                    }
                }
                if(!isEmpty){
                    occuTid.add(i);
                }
            }
        }else if (np==4){
            for (int i = 10; i < tableMax[2]; i++){
                boolean isEmpty=false;
                for (int j=0;j< emptyTid.size();j++){
                    if(i==emptyTid.get(j)){
                        isEmpty=true;
                        break;
                    }
                }
                if(!isEmpty){
                    occuTid.add(i);
                }
            }
        }else if (np==6){
            for (int i = 20; i < tableMax[3]; i++){
                boolean isEmpty=false;
                for (int j=0;j< emptyTid.size();j++){
                    if(i==emptyTid.get(j)){
                        isEmpty=true;
                        break;
                    }
                }
                if(!isEmpty){
                    occuTid.add(i);
                }
            }
        }
        else if (np==8){
            for (int i = 28; i < tableMax[4]; i++){
                boolean isEmpty=false;
                for (int j=0;j< emptyTid.size();j++){
                    if(i==emptyTid.get(j)){
                        isEmpty=true;
                        break;
                    }
                }
                if(!isEmpty){
                    occuTid.add(i);
                }
            }
        }else if (np==10){
            for (int i = 30; i < tableMax[5]; i++){
                boolean isEmpty=false;
                for (int j=0;j< emptyTid.size();j++){
                    if(i==emptyTid.get(j)){
                        isEmpty=true;
                        break;
                    }
                }
                if(!isEmpty){
                    occuTid.add(i);
                }
            }
        }

        for (int i=0;i<occuTid.size();i++){
            thelist=thelist+"table "+occuTid.get(i)+", ";
        }

        return thelist;
    }

    /**
     * Gets table number for currently empty tables for the purpose of displaying in GUI.
     *
     * @param tables the list of all tables of corresponding number of persons
     * @param np number of persons
     * @return
     */
    public String setEmpDis(ArrayList<Table> tables, int np){
        int minn=np-1;
        String dis="Table for "+minn +" to " + np+ " persons:\n";
        for(int i =0;i<tables.size();i++){
            dis= dis+"table "+Integer.toString(tables.get(i).getTID())+", ";}
        return dis;
    }
    //gui


    /**
     * Returns the demanded empty table list,
     * possibly for the purpose of checking if a table is the first one in the empty list,
     * because only the first table in each empty list will carry out TicketCall.
     *
     * @param seats for identifying an empty table list with number of seats
     * @return
     */
    public ArrayList<Table> getEmptyTableList(int seats) {
        if (seats <= 2) {
            return etl1;
        } else if (seats <= 4) {
            return etl2;
        } else if (seats <= 6) {
            return etl3;
        } else if (seats <= 8) {
            return etl4;
        } else {
            return etl5;
        }

    }

    /**
     * Adds a newly-emptied table back to the corresponding empty table list,
     * after the last client assigned to that table sends CheckOut.
     * When the system starts for the first time,
     * this method is called to add all the tables into empty lists.
     * <p>
     * Server panel displays initialized empty tables.
     *
     * @param table an empty table
     */
    public synchronized void addToEmptyTableList(Table table) {
        int seats = table.seats;
        if (seats <= 2) {
            etl1.add(table);
        } else if (seats <= 4) {
            etl2.add(table);
        } else if (seats <= 6) {
            etl3.add(table);
        } else if (seats <= 8) {
            etl4.add(table);
        } else {
            etl5.add(table);
        }

        String table1=setEmpDis(etl1,2);
        String oTable1=getOccTid(etl1,2);
        String table2=setEmpDis(etl2,4);
        String oTable2=getOccTid(etl2,4);
        String table3=setEmpDis(etl3,6);
        String oTable3=getOccTid(etl3,6);
        String table4=setEmpDis(etl4,8);
        String oTable4=getOccTid(etl4,8);
        String table5=setEmpDis(etl5,10);
        String oTable5=getOccTid(etl5,10);

        t.getEmptyTablePanel().setText(table1+"\n\n"+table2+"\n\n"+table3+"\n\n"+table4+"\n\n"+table5);
        t.getFullTablePanel().setText(oTable1+"\n\n"+oTable2+"\n\n"+oTable3+"\n\n"+oTable4+"\n\n"+oTable5);
    }

    /**
     * Removes the table which called some ticket and get assigned with the client who got that ticket
     * from the corresponding empty table list.
     * After deleting the ticket, release the position and move the queueing tickets forward for one position,
     * which makes the arraylist work as a queue.
     * <p>
     * Server panel displays changes between empty/occupied tables.
     *
     * @param table the full table to be removed
     */
    public synchronized void removeFromEmptyTableList(Table table) {
        int seats = table.seats;
        if (seats <= 2) {
            etl1.remove(table);
            etl1.trimToSize();
        } else if (seats <= 4) {
            etl2.remove(table);
            etl2.trimToSize();
        } else if (seats <= 6) {
            etl3.remove(table);
            etl3.trimToSize();
        } else if (seats <= 8) {
            etl4.remove(table);
            etl4.trimToSize();
        } else {
            etl5.remove(table);
            etl5.trimToSize();
        }

        String table1=setEmpDis(etl1,2);
        String oTable1=getOccTid(etl1,2);
        String table2=setEmpDis(etl2,4);
        String oTable2=getOccTid(etl2,4);
        String table3=setEmpDis(etl3,6);
        String oTable3=getOccTid(etl3,6);
        String table4=setEmpDis(etl4,8);
        String oTable4=getOccTid(etl4,8);
        String table5=setEmpDis(etl5,10);
        String oTable5=getOccTid(etl5,10);

        t.getEmptyTablePanel().setText(table1+"\n\n"+table2+"\n\n"+table3+"\n\n"+table4+"\n\n"+table5);
        t.getFullTablePanel().setText(oTable1+"\n\n"+oTable2+"\n\n"+oTable3+"\n\n"+oTable4+"\n\n"+oTable5);
        System.out.println(table.tid + " removed.");
    }


    /**
     * Returns the demanded client waiting queue,
     * possibly for the purpose of checking queue size by a table.
     *
     * @param nPersons for identifying a client waiting queue with number of persons per client
     * @return the demanded client waiting queue
     */
    public ArrayList<Ticket> getQueue(int nPersons) {

        if (nPersons <= 2) {
            return queue1;
        } else if (nPersons <= 4) {
            return queue2;
        } else if (nPersons <= 6) {
            return queue3;
        } else if (nPersons <= 8) {
            return queue4;
        } else {
            return queue5;
        }
    }


    /**
     * Removes the ticket being called by a table from the client waiting queue.
     * After deleting the ticket, release the position and move the queueing tickets forward for one position,
     * which makes the arraylist work as a queue.
     *
     * @param t the ticket being called by a table
     */
    public synchronized void removeFromQueue(Ticket t) {

        System.out.println("ticket " + t.ticketNo + " removed from queue.");
        ArrayList<Ticket> theQueue = getQueue(t.nPersons);
        theQueue.remove(t);
        theQueue.trimToSize();
    }

    /**
     * Adds a newly created ticket object to the corresponding waiting queue.
     *
     * @param ticket the newly created ticket object after receiving TicketRep from ClientStream
     */
    public synchronized void addToQueue(Ticket ticket) {
        int nPersons = ticket.nPersons;
        getQueue(nPersons).add(ticket);
        System.out.println("ticket No. " + ticket.ticketNo + " of " + nPersons + " added to queue.");
//        System.out.println("current queue size: " + getQueue(nPersons).size());

    }

    /**
     * This method is called on receiving a TicketReq from ClientStream, before returning a TicketRep.
     * It looks up the size of the waiting queue and compares it with default longest queue size.
     * Returns the checking result of whether demanded queue has too many ticket waiting.
     *
     * @param nPersons for identifying a client waiting queue with number of persons per client
     * @return a boolean indicating whether the demanded queue has too many client ticket waiting
     */
    public boolean checkTooLong(int nPersons) {
        boolean isTooLong;
//        if (queues[nPersons-1].size() > 10)
        if (getQueue(nPersons).size()>=10){
            isTooLong = true;
        } else {
            isTooLong = false;
        }
        return isTooLong;
    }


    /**
     * Creates a system global timer after a main object is created.
     * Global timer is for the use of calculating simulation time.
     */
    public void startApp() {
        // start our application
        log.info("");
        this.t.getTextAreaClients().append("\n");
        this.t.getTextAreaClients().append("\n");
        log.info("");
        log.info("============================================================");
        this.t.getTextAreaClients().append("============================================================\n");
        log.info(id + ": Application Starting...");
        this.t.getTextAreaClients().append(id + ": Application Starting...\n");

        timer = new Timer("timer", this);
        new Thread(timer).start();
    }

}
