package launch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import loaddata.MAJDB;

public class Traitement {
	
	public static Logger logger = Logger.getLogger("myPackage.mySubPackage.myClasse");
	public static Handler fh ;
	
	public static void Launch() throws SecurityException, IOException, SQLException, AddressException, MessagingException
	{
		run r = new run() ;
		r.setServer(Parametrage.SERVEUR);
		r.setPort(Parametrage.PORT);
		r.setUser(Parametrage.USER);
		r.setPass(Parametrage.PASS);
		
		r.Download();
		r.FUSION();
		
		new MAJDB(Parametrage.PathClient,
				  Parametrage.PathCompteBloquer,
				  Parametrage.PathMontantBloquer,
				  Parametrage.PathMouvement) ;
		
	}

}
