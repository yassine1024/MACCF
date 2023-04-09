package launch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;

import java.util.logging.SimpleFormatter;

import javax.mail.MessagingException;

import loaddata.Mail;

public class Launch {

	/*SERVEUR =192.168.195.48
	PORT =21
	USER =FRC
	PASS =FTP2016dgc*/
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stu	
		
		
		Message message = new Message() ;
		
		
			try 
			{
				Traitement.fh = new FileHandler("MaccLog.log");
				Traitement.fh.setFormatter(new SimpleFormatter());
				Traitement.logger.addHandler(Traitement.fh);
				
				Parametrage.Init();
				
				Traitement.logger.info("SYSTEM PARAMETER AVEC SUCCEE.");

				Traitement.Launch();
				
				message.setMsg("LE MACC A MIS A JOUR LA BASE DE DONNEE AVEC SUCCEE.");
			} 
			catch (SecurityException | ClassNotFoundException | IOException | SQLException | MessagingException e) 
			{
				Traitement.logger.warning(e.getMessage());
				message.setMsg("LE MACC A RENCONTRER UNE ERREUR L'AURE DE SANS EXECUTION, VOICI LE MESSAGE D'ERREUR : \n"+e.getMessage());		
			
			}
			
		for(String s : Parametrage.EmailAdmin)
		{
			message.setDESTINATAIRE(s);
			try 
			{
				Mail.EnvoyerMail(message);
			} 
			catch (MessagingException e) 
			{
				Traitement.logger.warning(e.getMessage());
			}
		}
		
		Traitement.fh.close();
		
	}

}
