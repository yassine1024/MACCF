package loaddata;



import javax.mail.internet.*;

import launch.Parametrage;
import launch.Traitement;

import javax.mail.*;
import java.util.*;
/**
* Classe permettant d'envoyer un mail.
*/
public class Mail {
private final static String MAILER_VERSION = "Java";

	public static boolean envoyerMailSMTP(String serveur, boolean debug) 
	{
		boolean result = false;
		try 
		{
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", serveur);
			Session session = Session.getDefaultInstance(prop,null);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("redouane.boulbari@mf.gov.dz"));
			InternetAddress[] internetAddresses = new InternetAddress[1];
			internetAddresses[0] = new InternetAddress("abdenacer.djarrah@mf.gov.dz");
			message.setRecipients(Message.RecipientType.TO,internetAddresses);
			message.setSubject("Test");
			message.setText("test mail");
			message.setHeader("X-Mailer", MAILER_VERSION);
			message.setSentDate(new Date());
			//session.setDebug(debug);
			Transport.send(message);
			result = true;
		} 
		catch (AddressException e) 
		{
		e.printStackTrace();
		}
		catch (MessagingException e) 
		{
		e.printStackTrace();
		}
		
		return result;
	}
	
	public static void EnvoyerMail(launch.Message m) throws AddressException, MessagingException 
	{
		
		Traitement.logger.info("*************************************BEGIN SEND MAIL*************************************");
	      
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host",Parametrage.SERVEURSMTP);
			Session session = Session.getDefaultInstance(prop,null);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Parametrage.FROM));
			InternetAddress[] internetAddresses = new InternetAddress[1];
			internetAddresses[0] = new InternetAddress(m.getDESTINATAIRE());
			message.setRecipients(Message.RecipientType.TO,internetAddresses);
			message.setSubject("INFORMATION");
			message.setText("BONJOUR Mr/Mm : "+m.getNom()+" "+m.getPrenom()+"\n"+m.getMsg());
			message.setHeader("X-Mailer", MAILER_VERSION);
			message.setSentDate(new Date());
			
			Transport.send(message);
					
			Traitement.logger.info("MAIL INFORMATION FOR :"+m.getDESTINATAIRE());
	
		Traitement.logger.info("*************************************END SEND MAIL*************************************");
		      
	}
	
}