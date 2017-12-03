package AppKickstarter.myThreads;

import AppKickstarter.TableGUI;
import AppKickstarter.misc.*;
import AppKickstarter.Main;

import java.io.*;

/**
 * OutThread class object outThread is for sending the message through the network to ClientStream.
 * It doesn't care about the content of the message.
 * It only takes in formatted messages from main thread and sends it through network to ClientStream.
 * It is an interface between ClientStream and our server.
 *
 * @author sunjingxuan
 */
public class OutThread extends AppThread{

    DataOutputStream out;
    TableGUI t;

    /**
     * Class constructor.
     * Gets output stream from main object.
     *
     * @param id the id of OutThread thread, no actual use
     * @param main the "server" object
     * @param out the DataOutputStream created from connecting socket
     * @param t TableGUI
     */
    public OutThread(String id, Main main, DataOutputStream out, TableGUI t){
        super(id, main);
        this.out = out;
        this.t=t;
    }


    /**
     * Takes in message from main thread or table threads through MBox,
     * sends the message to ClientStream through DataOutputStream.
     * Display log messages in GUI.
     */
    public void run(){

        log.info(id + ": out thread starting...");
        this.t.getTextAreaClients().append(id + ": out thread starting...\n");

        for (boolean quit = false; !quit;) {
            Msg msg = mbox.receive();
            log.info(id + ": message received: [" + msg + "].");
            this.t.getTextAreaClients().append(id + ": message received: [" + msg + "].\n");

            switch (msg.getType()) {
                case OutMsg:
                    try {
                        out.writeBytes(msg.getDetails());
                        out.flush();
                        if (msg.getDetails().contains("TicketCall")){
                        	//TicketCall should notify table too
                        	int tableNo = Integer.parseInt(msg.getDetails().split("\r")[0].split(" ")[2]);
                        	main.tables[tableNo].isWaitingSendMsg = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case Terminate:
                    quit = true;
                    break;

                default:
                    log.severe(id + ": unknown message type!!");
                    break;
            }
        }

        // declaring our departure
        main.unregThread(this);
        log.info(id + ": terminating...");
    }
}
