# COMP4007_Group2_Phase2

## Environment Requirement
This project is originally written on Mac, please feel free to directly run it on a mac machine.

However, for **PC USERS**, please do some changes: 
1. Go to the folder "./ProjPhase2-Grp2/FINAL_V10_GUI/src/AppKickstarter/"
2. Change **"\r\n"** to **"\n"** in the following lines of the following java files:

### Client.java 
#### line 482:
Change
  ```
  req=ticketReq+cidP+Integer.toString(cidDft)+' '+numString+"\r\n";
  ```
To
  ```
  req=ticketReq+cidP+Integer.toString(cidDft)+' '+numString+"\n";
  ```

### myThread.MainThread.java 

#### line 109 
Change
  ```
  details = "TicketRep: " + cid + " " + nPersons + " " + ticketIssueNo + "\r\n";
  ```
To
  ```
  details = "TicketRep: " + cid + " " + nPersons + " " + ticketIssueNo + "\n";
  ```
#### line 116 
Change
  ```
  details = "QueueTooLong: " + cid + " " + nPersons+ "\r\n";
  ```
To
  ```
  details = "QueueTooLong: " + cid + " " + nPersons+ "\n";
  ```
#### line 141 
Change
  ```
  details = "TableAssign: " + ticketAckNo + " " + tidstr + "\r\n";
  ```
To
  ```
  details = "TableAssign: " + ticketAckNo + " " + tidstr + "\n";
  ```
#### line 150 
Change
  ```
  details = "Client CheckOut!" + "\r\n";
  ```
To
  ```
  details = "Client CheckOut!" + "\n";
  ```

## Class Info: Package AppKickStarter
| Class Name | Description |
| --- | --- |
| Main | The creation of a Main class object, main, is the starting point of the RQS system. A main object is in charge of creating global fields of the system, which will further referenced by main thread and table threads. Such global objects includes: 5 client waiting queues, for keeping the not-yet-called ticket objects on a first-come-first-serve basis;5 empty table lists, for queueing and deciding the order of which table to get the waiting clients; input thread, output thread, main thread for internal and client-server communication; GUI classes. |
| AppKickstarter | The class which Main class extends. |
| TableGUI | TableGUI class object tGUI is the Server Panel. Please start the project here. You are supposed to see 4 panels in the GUI: Empty Tables, Occupied Tables, Staff Miscs and Statistics. Panel Empty Tables show the tables which are empty. Panel Occupied Tables show the tables which are occupied. Panel Staff Miscs is for displaying log information. Panel Statistics is for displaying statistics including successful rate, total income, etc. |
| Client | Client is a class simulates the real-life client-side RQS. It contains a GUI for client to click and get ticket. |

### AppKickStarter.myThreads
AppKickStarter.myThreads contains several classes that inherit AppThread classes.

| Class Name | Description |
| --- | --- |
| MainThread | MainThread class object main thread contains the logic of preparing out message base on in message. It is encapsulated inside our server and has no direct connection with network. It is the real handler of messages. |
| InThread | It takes in bytes from DataInputStream; changes it into string for the convenience of main thread to deal with the message; sends the formatted message string to main thread through MBox. Log messages are displayed in GUI and printed in console. |
| OutThread | OutThread class object outThread is for sending the message through the network to ClientStream. It doesn't care about the content of the message. It only takes in formatted messages from main thread and sends it through network to ClientStream. It is an interface between ClientStream and our server. |
| Table | Table class object table threads are responsible for sending out TicketCall when the table becomes empty. In actual situation, there are in total 32 table threads each representing only one table. A table thread has the attributes of table number (which is set to be equal to table thread id), number of seats, the state of empty or not. |

### AppKickStarter.misc
AppKickStarter.misc contains several misc classes...

| Class Name | Description |
| --- | --- |
| Ticket | A class for storing ticket information. |
| LogFormatter | A class for better format log information. |
| AppThread | A class which classes in AppKickStarter.myThreads extends. |
| Msg | A class for storing message type and details sending between the classes in AppKickStarter.myThreads |
| MBox | A class for handling messages. |

### AppKickStarter.timer

| Class Name | Description |
| --- | --- |
| Timer | It is a timer class which defines a timer. |

## Usage

```sh
git clone https://github.com/MaureenZOU/COMP4007_Group2_Phase2.git
```

Run
```sh
./FINAL_V10_GUI/src/AppKickstarter/TableGUI.java
```

Click on [Start Server], and Run the client Stream.

## Compatibility
Adapt to both Mac and PC

