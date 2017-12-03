package AppKickstarter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.Enumeration;
import java.util.Hashtable;

import AppKickstarter.timer.Timer;
import AppKickstarter.misc.*;



//======================================================================
// AppKickstarter
public class AppKickstarter {
    private String cfgFName = null;
    private Properties cfgProps = null;
    private Hashtable<String, AppThread> appThreads = null;
    public String id = null;
    public Logger log = null;
    private ConsoleHandler logConHd = null;
    private FileHandler logFileHd = null;
    public Timer timer = null;
	public static int [] tableValue;


    //------------------------------------------------------------
    // main
    public static void main(String [] args) throws IOException {
	AppKickstarter appKickstarter = new AppKickstarter("AppKickstarter", "etc/RQS.cfg");
	appKickstarter.startApp();
	try {
	    Thread.sleep(30 * 1000);
	} catch (Exception e) {}
	appKickstarter.stopApp();
    } // main


    //------------------------------------------------------------
    // AppKickstarter
	public AppKickstarter(String id) {
	this(id, "etc/RQS.cfg");
    } // AppKickstarter


    //------------------------------------------------------------
    // AppKickstarter
    private AppKickstarter(String id, String cfgFName) {
	this(id, cfgFName, false);
    } // AppKickstarter


    //------------------------------------------------------------
    // AppKickstarter
    private AppKickstarter(String id, String cfgFName, boolean append) {
	this.id = id;
	this.cfgFName = cfgFName;
	logConHd = null;
	logFileHd = null;
	id = getClass().getName();

	// set my thread name
	Thread.currentThread().setName(this.id);

	// read system config from property file
	try {
	    cfgProps = new Properties();
	    FileInputStream in = new FileInputStream(cfgFName);
	    cfgProps.load(in);
	    in.close();
	    logConHd = new ConsoleHandler();
	    logConHd.setFormatter(new LogFormatter());
	    logFileHd = new FileHandler("etc/" + id + ".log", append);
	    logFileHd.setFormatter(new LogFormatter());
	} catch (FileNotFoundException e) {
	    System.out.println("Failed to open config file ("+cfgFName+").");
	    System.exit(-1);
	} catch (IOException e) {
	    System.out.println("Error reading config file ("+cfgFName+").");
	    System.exit(-1);
	}

		Enumeration keys = cfgProps.keys();
		tableValue = new int[6];
		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = (String)cfgProps.get(key);
			String lineValue = key + " = " + value;
			//System.out.println(key + " = " + value);
			String [] splits = lineValue.split("_");
			if(splits[0].equals("NTables"))
			{
				String [] splits2 = splits[1].split("=");
				int index = Integer.parseInt(splits2[0].replaceAll("[^0-9.]", ""));
				int valueOfNum = Integer.parseInt(splits2[1].replaceAll("[^0-9.]", ""));
				tableValue[index] = valueOfNum;
			}
		}

	// get and configure logger
	log = Logger.getLogger(id);
	log.addHandler(logConHd);
	log.addHandler(logFileHd);
	log.setUseParentHandlers(false);
	log.setLevel(Level.FINER);
	logConHd.setLevel(Level.INFO);
	logFileHd.setLevel(Level.INFO);
	appThreads = new Hashtable<String, AppThread>();
    } // AppKickstarter


    //------------------------------------------------------------
    // startApp
    public void startApp() {
	// start our application
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Starting...");


    } // startApp


    //------------------------------------------------------------
    // stopApp
    private void stopApp() {
	log.info("");
	log.info("");
	log.info("============================================================");
	log.info(id + ": Application Stopping...");

    } // stopApp


    //------------------------------------------------------------
    // regThread
    public void regThread(AppThread appThread) {
	log.fine(id + ": registering " + appThread.getID());
	synchronized (appThreads) { appThreads.put(appThread.getID(), appThread); }
    } // regThread


    //------------------------------------------------------------
    // unregThread
    public void unregThread(AppThread appThread) {
	log.fine(id + ": unregistering " + appThread.getID());
	synchronized (appThreads) { appThreads.remove(appThread.getID()); }
    } // unregThread


    //------------------------------------------------------------
    // getThread
    public AppThread getThread(String id) {
	synchronized (appThreads) { return appThreads.get(id); }
    } // getThread

    //------------------------------------------------------------
    // getLogger
    public Logger getLogger() {
	return log;
    } // getLogger


    //------------------------------------------------------------
    // getLogConHd
    public ConsoleHandler getLogConHd() {
	return logConHd;
    }
    // getLogConHd


    //------------------------------------------------------------
    // getLogFileHd
    public FileHandler getLogFileHd() {
	return logFileHd;
    } // getLogFileHd


    //------------------------------------------------------------
    // getProperty
    public String getProperty(String property) {
	String s = cfgProps.getProperty(property);

	if (s == null) {
	    log.severe(id + ": getProperty(" + property + ") failed.  Check the config file (" + cfgFName + ")!");
	}
	return s;
    } // getProperty


    //------------------------------------------------------------
    // getSimulationTime (in seconds)
    public long getSimulationTime() {
	return timer.getSimulationTime();
    } // getSimulationTime
} // AppKickstarter
