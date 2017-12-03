package AppKickstarter.myThreads;

import AppKickstarter.TableGUI;
import AppKickstarter.misc.*;
import AppKickstarter.Main;
import java.io.*;

/**
 * InThread class object inThread is for receiving the message sent through the network by ClientStream.
 * It doesn't care about the content of the message.
 * It only formats the raw message and passes it to the main thread.
 * It is an interface between ClientStream and our server.
 *
 * @author sunjingxuan
 */
public class InThread extends AppThread {

    DataInputStream in;
    String inmsg;
    TableGUI t;

    /**
     * Class constructor.
     * Gets input stream from main object.
     *
     * @param id the id of InThread thread, no actual use
     * @param main the "server" object
     * @param in the DataInputStream created from connecting socket
     * @param t TableGUI
     */
    public InThread(String id, Main main, DataInputStream in, TableGUI t){
        super(id, main);
        this.in = in;
        this.t=t;
    }

    /**
     * Takes in bytes from DataInputStream,
     * changes it into string for the convenience of main thread to deal with the message,
     * sends the formatted message string to main thread through MBox.
     * Display log messages in GUI.
     */
    public void run(){
        main.getLogger().info("in thread starting...");
        this.t.getTextAreaClients().append("in thread starting...\n");
        while(true){
            try {
                byte b[] = new byte[1024];
                in.read(b);
                inmsg = new String(b).split("\n")[0];

            } catch(IOException ex){
                ex.printStackTrace();
            }
            main.mt.getMBox().send(new Msg(id, null, Msg.Type.InMsg, inmsg));
        }
    }
}
