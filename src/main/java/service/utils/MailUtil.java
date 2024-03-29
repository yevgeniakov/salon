package service.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * provides functionality for sending email via SMTP
 * 
 * @author yevgenia.kovalova
 *
 */
public class MailUtil {

	public static void sendMessage(String address, String subject, String body)
			throws AddressException, MessagingException {
		String username = PropertiesService.getProperty("mailusername");
		String password = PropertiesService.getProperty("mailpassword");;
		Properties props = getProps();
		Session session = Session.getInstance(props);
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
		msg.setSubject(subject);
		msg.setText(body);
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
