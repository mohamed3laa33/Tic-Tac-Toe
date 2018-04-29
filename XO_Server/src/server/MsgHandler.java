package server;

import java.net.*;
import java.io.*;
import java.util.Vector;
import network.Msg;
import model.User;
import database.DBM;


class MsgHandler extends Thread
{
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	static Vector<MsgHandler> clientsVector = new Vector<MsgHandler>();
	User user;
	Msg msg;

	public MsgHandler(Socket s)
	{
		try{
			this.s = s;
		}catch(Exception ex){
			System.out.println("err");
		}

		clientsVector.add(this);
		start();
	}

	public void run()
	{
		while(true)
		{
			try{
				ois = new ObjectInputStream(s.getInputStream());
				msg = (Msg) ois.readObject();
				if (msg != null) {
					printMsg(msg);
					switch(msg.type){
						case "USER":
							user = msg.user;
							System.out.println(user.getUserName() + " is now online!");
							sendToAllExceptMe( new Msg("NOTIFICATION", user.getUserName() + " is now online!", 0, 0));
							DBM.exec("UPDATE user SET status = 1 WHERE id = ?", "" + user.getId());
							sendMessageToAll(new Msg("REFRESH"));
							break;
						case "MESSAGE":
							sendMessageToAll(msg);
							break;
						case "REQUEST":
							sendMessageTo(msg);
							break;
						case "ACCEPT":
							sendMessageTo(msg);
							break;
						case "REJECT":
							sendMessageTo(msg);
							break;
						case "MOVE":
							DBM.exec("INSERT INTO move(position, `from`, `to`, `game_id`) values (?, ?, ?, ?)", msg.position + "", msg.from + "", msg.to + "", msg.message); // here msg.message holds the game ID
							sendMessage(msg);
							break;
						// case "NOTIFICATION":
						// 	// sendMessage(msg);
						// 	//sendToAllExceptMe(msg);
						// 	break;
						// case "REFRESH":// Refresh all users list
						// 	// sendMessage(msg);
						// 	//sendToAllExceptMe(msg);
						// 	break;

					}
				}
			}catch(Exception ex){
				System.out.println(user.getUserName() + " is now offline!");
                DBM.exec("UPDATE user SET status = 0 WHERE id = ?", "" + user.getId());
				// Update user status in DB to Offline
				// Send Message to all online users to 	refresh users list
				clientsVector.remove(this);
				sendMessageToAll(new Msg("REFRESH"));
				sendMessageToAll( new Msg("NOTIFICATION", user.getUserName() + " is now offline!", 0, 0));
				ex.printStackTrace();
				stop();
			}
		}

	}

	// Send Message to all including sender
	void sendMessageToAll(Msg msg)
	{
		for(MsgHandler mh : clientsVector)
		{
			System.out.println(mh.user.getUserName());
			try{
				oos = new ObjectOutputStream(mh.s.getOutputStream());
				oos.writeObject(msg);
			}catch(Exception ex){

			}
		}
	}

	// Send Message to all except the message sender
	void sendToAllExceptMe(Msg msg)
	{
		for(MsgHandler mh : clientsVector)
		{
			if (mh.user.getId() != user.getId()) {
				System.out.println(mh.user.getUserName());
				try{
					oos = new ObjectOutputStream(mh.s.getOutputStream());
					oos.writeObject(msg);
				}catch(Exception ex){

				}
			}
		}
	}

	// Send Message to both sender and receiver
	void sendMessage(Msg msg)
	{
		for(MsgHandler mh : clientsVector)
		{
			if (mh.user.getId() == msg.to || mh.user.getId() == msg.from) {
				System.out.println(mh.user.getUserName());
				try{
					oos = new ObjectOutputStream(mh.s.getOutputStream());
					oos.writeObject(msg);
				}catch(Exception ex){

				}
			}
		}
	}

	// Send Message only to the receiver
	// Send Message to someone and don't send it to me
	void sendMessageTo(Msg msg)
	{
		for(MsgHandler mh : clientsVector)
		{
			if (msg.to == mh.user.getId()) {
				System.out.println(mh.user.getUserName());
				try{
					oos = new ObjectOutputStream(mh.s.getOutputStream());
					oos.writeObject(msg);
				}catch(Exception ex){

				}
			}
		}
	}

	void printMsg(Msg msg){
		if (msg.user != null) {
			System.out.println("{ type: " + msg.type + ", message: " + msg.message + ", to: " + msg.to + ", from: " + msg.from + ", position: " + msg.position + ", user: " + msg.user.getUserName() + " }");
		}else{
			System.out.println("{ type: " + msg.type + ", message: " + msg.message + ", to: " + msg.to + ", from: " + msg.from + ", position: " + msg.position + ", user: " + "null }");
		}
		
	}
}