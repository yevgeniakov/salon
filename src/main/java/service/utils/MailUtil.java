package service.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void sendMessage(String adress, String subject, String body) throws AddressException, MessagingException  {
		
		
	    String username = "yevheniia.kov@ukr.net";
	    String password = "jS4gAP9k7ZdQ4toP";
	    Properties props = getProps();
	    // set any other needed mail.smtp.* properties here
	    Session session = Session.getInstance(props);
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("yevheniia.kov@ukr.net"));
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress("yevgenia.kov@gmail.com"));
	    msg.setSubject("Java test 2");
	    msg.setText("Hi,\n\nHow are you?");
	    // set the message content here
	    Transport.send(msg, username, password);
	}

	private static Properties getProps() {
		Properties props = new Properties();
	    props.setProperty("mail.smtp.ssl.enable", "true");
	    props.setProperty("mail.smtp.starttls.enable", "true");
	    props.setProperty("mail.smtp.host", "smtp.ukr.net");
	    props.setProperty("mail.smtp.port", "2525");
	    props.setProperty("mail.smtp.ssl.trust", "*");
	    props.setProperty("mail.smtp.socketFactory.port", "587");
	    props.setProperty("mail.smtp.starttls.required", "true");
	    props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		return props;
	}

}
