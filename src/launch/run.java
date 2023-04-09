package launch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;



public class run {

    private FTPClient ftpClient = new FTPClient();
    private String server ;
    private int port;
    private String user ;
    private String pass ;
 
    
    
	
	public void Download() throws SocketException, IOException
	{
		
		ftpClient.setControlKeepAliveTimeout(2); // set timeout to 2 seconds
		ftpClient.connect(this.server,this.port);
		ftpClient.enterLocalPassiveMode();
		//showServerReply(ftpClient);

		
		
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) 
		{
		    //JOptionPane.showMessageDialog(null, "Connect failed");
			Traitement.logger.warning("Connect failed");
	   	    return;
		}

		boolean success = ftpClient.login(this.user,this.pass);
		//showServerReply(ftpClient);

		if (!success) 
		{
		    //JOptionPane.showMessageDialog(null, "Could not login to the server");
			Traitement.logger.warning("Could not login to the server");
		    return;
		} 
		
		/*FTPFile[] DOSSIER_GLOBALE = ftpClient.listFiles("/");
		
		
		if (DOSSIER_GLOBALE != null && DOSSIER_GLOBALE.length > 0) 
		{
			for (FTPFile DOSSIER: DOSSIER_GLOBALE) 
			{
				if (DOSSIER.isDirectory())
				{
					FTPFile[] files = ftpClient.listFiles("/"+DOSSIER.getName());
					
					Vector<String> NomFiles = new Vector<String>();
					 
					for (FTPFile FICHIER: files) 
					{
						NomFiles.add("/"+DOSSIER.getName()+"/"+FICHIER.getName());
					}
					
					
					downloadFiles(this.ftpClient,NomFiles);
					
				}
				
			}
		}*/
		
		FTPFile[] files = ftpClient.listFiles("/");
		
		Vector<String> NomFiles = new Vector<String>();
		 
		for (FTPFile FICHIER: files) 
		{
			NomFiles.add("/"+FICHIER.getName());
		}
		
		Traitement.logger.info("*************************************BEGIN DOWNLOAD*************************************");
		downloadFiles(this.ftpClient,NomFiles);
		Traitement.logger.info("*************************************END DOWNLOAD*************************************");
		
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	private void writeFusionFiles(String destFile, String...sourceFiles) throws IOException {

		// On ouvre (ou on crée) le fichier de destination :

		OutputStream out = new FileOutputStream(destFile);

		try {

		byte[] buf = new byte [8192]; // buffer de copie par bloc

		int len; // compteur de byte lu

		for (String filename : sourceFiles) {

		InputStream in = new FileInputStream(filename);

		try {

		// On lit dans le buffer (bloc de 8192 bytes max)

		while ( (len=in.read(buf)) >= 0 ) {

		out.write(buf, 0, len); // et on copie ce qu'on a lu

		}

		} finally {

		in.close();

		}

		}

		} finally {

		out.close();

		}

		}
    private void downloadFiles(FTPClient ftpClient, Vector<String> files) throws IOException 
    {
    	
    	
    		if (files != null && files.size() > 0) 
    		{
    			
    			
            for (String filefullname: files) 
            {
            	
            	String remoteFile = filefullname;
            	
            	//System.out.println("remotefile : "+remoteFile);
            	String[] path = filefullname.split("/");
            	String file = path[path.length -1];
            	
            	
            	
            	/*String search1 = Parametrage.BlocMontant ;
            	String search2 = Parametrage.CompteClient;
            	String search3 = Parametrage.Operation ;
            	String search4 = Parametrage.CompteBloc ;
            	//String search5 = Parametrage.Abside ;
*/            	
            	//String search5 = Parametrage.Abside ;
            	
            	File downloadFile  = null ;
            	
            	if(file.indexOf(Parametrage.BlocMontant )!= -1)
            	{
            		downloadFile = new File("CorpusTw/"+Parametrage.BlocMontant +"/"+file);
            	}
            	else if((file.indexOf( Parametrage.CompteClient)!= -1) && (file.indexOf( Parametrage.Abside)== -1))
            	{
            		downloadFile = new File("CorpusTw/"+ Parametrage.CompteClient+"/"+file);
            	}
            	else if(file.indexOf(Parametrage.Operation)!= -1)
            	{
            		downloadFile = new File("CorpusTw/"+Parametrage.Operation+"/"+file);
            	}
            	else if(file.indexOf(Parametrage.CompteBloc)!= -1)
            	{
            		downloadFile = new File("CorpusTw/"+Parametrage.CompteBloc+"/"+file);
            	}
            	
            	else
            	{
            		downloadFile = new File("CorpusTw/"+Parametrage.Abside+"/"+file);
            	}
            	
            	
               
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));

                boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
               
                outputStream.close();
                
                

                if (success) 
                {
                	
                	Traitement.logger.info("remotefile : "+remoteFile+" : DOWNLOAD");
                	
                    //System.out.println(" : DOWNLOAD");
                } 
                else 
                {
                	
                	Traitement.logger.warning("remotefile : "+remoteFile+" : FAILED");
                	
                	// System.out.println(" : FAILED");
                }
            }
            
        }
    		
    		
    }
	public void FUSION() throws IOException
	{
		String GlobalFiles ,  CorpusPath ;
		
		
		GlobalFiles = Parametrage.GlobalFiles ;
		CorpusPath = Parametrage.CorpusPath ;
		
		File file = new File(CorpusPath);
		String [] listefichiers=file.list();
		
		Traitement.logger.info("*************************************BEGIN FUSION*************************************");
		
		for(String ff : listefichiers)
		{	
			File dossier = new File(CorpusPath+"/"+ff);
			
			String [] liste=dossier.list();
			
			for(int i = 0; i < liste.length; i++)
			{
				liste[i] = CorpusPath+"/"+ff+"/"+liste[i] ;
			}
			writeFusionFiles(GlobalFiles+"/"+ff+".txt", liste) ;
			
		}
		
		Traitement.logger.info("*************************************END FUSION*************************************");
		
		
	}
}
