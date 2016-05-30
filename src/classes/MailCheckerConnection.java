package classes;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailCheckerConnection {
	private String username;
	private String password;
	private String host;
	private String protocol;
	private final static String imapGmailHost = "imap.gmail.com";
	private final static String popGmailHost = "pop.gmail.com";
	private final static String imapYahooHost = "imap.mail.yahoo.com";
	private final static String popYahooHost = "pop.mail.yahoo.com";
	
	public MailCheckerConnection(String username, String password, String protocol){
		username = username.trim();
		password = password.trim();
		protocol = protocol.trim();
		this.username = username;
		this.password = password;
		if(username.contains("yahoo")){
			if(protocol.equals("imaps")){
				host = imapYahooHost;
			}else{
				host = popYahooHost;
			}
		}else{
			if(protocol.equals("imaps")){
				host = imapGmailHost;
			}else{
				host = popGmailHost;
			}
		}
		this.protocol = protocol;
	}
	
	public ArrayList<String> checkMail(){
		Properties props = null;
		Session session = null;
		Store store = null;
		Folder inbox = null;
		Message messages[] = null;
		Flags seen = new Flags(Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		ArrayList<String> newMessages = new ArrayList<String>();
		try {
			
			props = System.getProperties();
			session = Session.getDefaultInstance(props, null);
			store = session.getStore(protocol);
			store.connect(host, username, password);	
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			if(inbox.getUnreadMessageCount() == 0){
				newMessages.add("There are no new messages.");
				return newMessages;
			}
			messages = inbox.search(unseenFlagTerm);
			int maximum = 50, i = messages.length-1;
			if(maximum > messages.length){
				maximum = messages.length; 
			}
			while(maximum >= 1){
				Message msg = messages[i];
				newMessages.add("From: "+msg.getFrom()[0].toString()+" Subject: "+msg.getSubject());
				i--;
				maximum--;
			}
			return newMessages;
		} catch (NoSuchProviderException e) {
			newMessages.add("There was a problem with the provider: "+e);
			return newMessages;
		} catch (MessagingException e) {
			newMessages.add("There was a problem with the email address connection: "+e);
			return newMessages;
		}
	}
}
