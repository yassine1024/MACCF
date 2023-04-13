package loaddata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import launch.Message;
import launch.Parametrage;
import launch.Traitement;

public class MAJDB {
	
	public MAJDB(String PathClient,String PathCompteBloquer,String PathMontantBloquer,String PathMouvement ) throws SQLException, AddressException, MessagingException 
	{	
		Traitement.logger.info("*************************************BEGIN LOAD DATA*************************************");
	       
		LoadFileClient(PathClient);
		LoadFileCompteBloquer(PathCompteBloquer);
		LoadFileMontantBloquer(PathMontantBloquer);
		LoadFileMouvement(PathMouvement);
		
		Traitement.logger.info("*************************************END LOAD DATA*************************************");
	          
		EnvoyerMail();
		 
	}
	private  void LoadFileClient(String Path) throws SQLException
	{
		Traitement.logger.info("*************************************BEGIN LOAD CLIENT*************************************");
   
		String req = "CREATE TEMPORARY TABLE temporary_CLIENT LIKE CLIENT;" ;
		PreparedStatement ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req = "DROP INDEX `PRIMARY` ON temporary_CLIENT;" ;
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req ="LOAD DATA INFILE '"+Path+"'" + 
				"	INTO TABLE temporary_CLIENT" + 
				"	FIELDS TERMINATED BY '|' OPTIONALLY ENCLOSED BY ''" + 
				"	(NUM_CPT," + 
				"	 NUM_CPT_G," + 
				"	 CLE," + 
				"	 NOM_CLI," + 
				"	 DATE_NAIS," + 
				"	 LIEU_NAIS," + 
				"	 NUM_PHONE," + 
				"	 PROFESSION," + 
				"	 EMPLOYEUR," + 
				"	 SOLDE_ACTUEL," + 
				"	 SI_BLOQUE," + 
				"	 SI_SOLDE," + 
				"	 SI_MT_BLOQUE," + 
				"	 NBR_CAR," + 
				"	 MT_BLOC" + 
				"	 );";
		
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req = "INSERT INTO CLIENT" + 
				"	SELECT * FROM temporary_CLIENT" + 
				"	ON DUPLICATE KEY UPDATE " + 
				"	NUM_CPT = VALUES(NUM_CPT)," + 
				"	NUM_CPT_G = VALUES(NUM_CPT_G)," + 
				"	CLE = VALUES(CLE)," + 
				"	NOM_CLI = VALUES(NOM_CLI)," + 
				"	DATE_NAIS = VALUES(DATE_NAIS)," + 
				"	LIEU_NAIS = VALUES(LIEU_NAIS)," + 
				"	NUM_PHONE = VALUES(NUM_PHONE)," + 
				"	PROFESSION = VALUES(PROFESSION)," + 
				"	EMPLOYEUR = VALUES(EMPLOYEUR)," + 
				"	SOLDE_ACTUEL = VALUES(SOLDE_ACTUEL)," + 
				"	SI_BLOQUE = VALUES(SI_BLOQUE)," + 
				"	SI_SOLDE = VALUES(SI_SOLDE)," + 
				"	SI_MT_BLOQUE = VALUES(SI_MT_BLOQUE)," + 
				"	NBR_CAR = VALUES(NBR_CAR)," + 
				"	MT_BLOC = VALUES(MT_BLOC)" + 
				"	;" ;
		
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req="DROP TEMPORARY TABLE temporary_CLIENT;" ;
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		Traitement.logger.info("*************************************END LOAD CLIENT*************************************");
				
}
	private  void LoadFileCompteBloquer(String Path) throws SQLException
	{
		Traitement.logger.info("*************************************BEGIN LOAD COMPTE BLOQUER*************************************");
       
		String req = "CREATE TEMPORARY TABLE temporary_COMPTE_BLOQUER LIKE COMPTE_BLOQUER;" ;
		PreparedStatement ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req = "DROP INDEX `PRIMARY` ON temporary_COMPTE_BLOQUER;" ;
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req ="LOAD DATA INFILE '"+Path+"'" + 
				"	INTO TABLE temporary_COMPTE_BLOQUER" + 
				"	FIELDS TERMINATED BY '|' OPTIONALLY ENCLOSED BY ''" + 
				"	(NUM_CPT," + 
				"	 DT_BLOC," + 
				"	 DT_DEBLOC," + 
				"	 MOTIF_BLOC," + 
				"	 MT_OBJET_BLOC" + 
				"	 );";
		
		
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();

		req = "INSERT INTO COMPTE_BLOQUER" + 
				"	SELECT * FROM temporary_COMPTE_BLOQUER" + 
				"	ON DUPLICATE KEY UPDATE " + 
				"	NUM_CPT = VALUES(NUM_CPT)," +
				"	DT_BLOC = DATE_FORMAT(STR_TO_DATE(VALUES(DT_BLOC), '%d-%b-%y'), '%Y-%m-%d')," +
				"	DT_DEBLOC = DATE_FORMAT(STR_TO_DATE(VALUES(DT_DEBLOC), '%d-%b-%y'), '%Y-%m-%d')" +
				"	MOTIF_BLOC = VALUES(MOTIF_BLOC)," +
				"	MT_OBJET_BLOC = VALUES(MT_OBJET_BLOC)" +
				"	;" ;
		
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		req="DROP TEMPORARY TABLE temporary_COMPTE_BLOQUER;" ;
		ps = Parametrage.Connection.prepareStatement(req);
		ps.executeUpdate();
		
		Traitement.logger.info("*************************************END LOAD COMPTE BLOQUER*************************************");

}
	private  void LoadFileMontantBloquer(String Path) throws SQLException {
		// TODO Auto-generated method stub
		
		 Traitement.logger.info("*************************************BEGIN LOAD MONTANT BLOQUER*************************************");
		
			String req = "CREATE TEMPORARY TABLE temporary_MONTANT_BLOQUER LIKE MONTANT_BLOQUER;" ;
			PreparedStatement ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req = "DROP INDEX `PRIMARY` ON temporary_MONTANT_BLOQUER;" ;
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req ="LOAD DATA INFILE '"+Path+"'" + 
					"	INTO TABLE temporary_MONTANT_BLOQUER" + 
					"	FIELDS TERMINATED BY '|' OPTIONALLY ENCLOSED BY ''" + 
					"	(NUMLIGNE," + 
					"	 NUM_CPT," +
					"	 DT_BLOC," + 
					"	 MT_BLOC," + 
					"	 MOTIF_BLOC," + 
					"	 DT_DEBLOC," + 
					"	 MT_DEBLOC," + 
					"	 MOTIF_DEBLOC," + 
					"	 SI_CERTIF" + 
					"	 );";
			
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();

			req = "INSERT INTO solde_bloquer" +
					"	SELECT * FROM temporary_MONTANT_BLOQUER" + 
					"	ON DUPLICATE KEY UPDATE " + 
					"	NUM_CPT = VALUES(NUM_CPT)," + 
					"	DT_BLOC = DATE_FORMAT(STR_TO_DATE(VALUES(DT_BLOC), '%d-%b-%y'), '%Y-%m-%d')," +
					"	MT_BLOC = VALUES(MT_BLOC)," + 
					"	MOTIF_BLOC = VALUES(MOTIF_BLOC)," +
					"	DT_DEBLOC = DATE_FORMAT(STR_TO_DATE(VALUES(DT_DEBLOC), '%d-%b-%y'), '%Y-%m-%d')," +
					"	MT_DEBLOC = VALUES(MT_DEBLOC)," +
					"	MOTIF_DEBLOC = VALUES(MOTIF_DEBLOC)," + 
					"	SI_CERTIF = VALUES(SI_CERTIF)" + 						
					"	;" ;
			
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req="DROP TEMPORARY TABLE temporary_MONTANT_BLOQUER;" ;
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
		Traitement.logger.info("*************************************END LOAD MONTANT BLOQUER*************************************");

	}
	private  void LoadFileMouvement(String Path) throws SQLException {
		// TODO Auto-generated method stub
		
		Traitement.logger.info("*************************************BEGIN LOAD MOUVEMENT*************************************");
		
			
		
			String req = "CREATE TEMPORARY TABLE temporary_MOUVEMENT LIKE MOUVEMENT;" ;
			PreparedStatement ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req = "DROP INDEX `PRIMARY` ON temporary_MOUVEMENT;" ;
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req ="LOAD DATA INFILE '"+Path+"'" + 
					"	INTO TABLE temporary_MOUVEMENT" + 
					"	FIELDS TERMINATED BY '|' OPTIONALLY ENCLOSED BY ''" + 
					"	(CODE_OPER," + 
					"	 NUM_CPT_G," + 
					"	 CODE_NAT," + 
					"	 AN_GEST," + 
					"	 MT_CREDIT," + 
					"	 JOUR_OPER," + 
					"	 MOIS_OPER," + 
					"	 MT_DEBIT," + 
					"	 NUM_CPT," + 
					"	 DT_OPER," + 
					"	 SI_ANNULE," + 
					"	 DT_ANNUL," + 
					"	 MT_OPER" + 
					"	 );";
			
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();

			req = "INSERT INTO MOUVEMENT" + 
					"	SELECT * FROM temporary_MOUVEMENT" + 
					"	ON DUPLICATE KEY UPDATE " + 
					"	CODE_OPER = VALUES(CODE_OPER)," + 
					"	NUM_CPT_G = VALUES(NUM_CPT_G)," + 
					"	CODE_NAT = VALUES(CODE_NAT)," + 
					"	AN_GEST = VALUES(AN_GEST)," + 
					"	MT_CREDIT = VALUES(MT_CREDIT)," + 
					"	JOUR_OPER = VALUES(JOUR_OPER)," + 
					"	MOIS_OPER = VALUES(MOIS_OPER)," + 
					"	MT_DEBIT = VALUES(MT_DEBIT)," + 
					"	NUM_CPT = VALUES(NUM_CPT)," + 
					"	DT_OPER = DATE_FORMAT(STR_TO_DATE(VALUES(DT_OPER), '%d-%b-%y'), '%Y-%m-%d')," +
					"	SI_ANNULE = VALUES(SI_ANNULE)," + 
					"	DT_ANNUL = DATE_FORMAT(STR_TO_DATE(VALUES(DT_ANNUL), '%d-%b-%y'), '%Y-%m-%d')," +
					"	MT_OPER = VALUES(MT_OPER)" + 
					"	;" ;
			
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			req="DROP TEMPORARY TABLE temporary_MOUVEMENT;" ;
			ps = Parametrage.Connection.prepareStatement(req);
			ps.executeUpdate();
			
			Traitement.logger.info("*************************************END LOAD MOUVEMENT*************************************");
	}
	private  void EnvoyerMail() throws SQLException, AddressException, MessagingException
	{
		 List<Message> MSGS = MSGS() ;
		 
		 for(Message m : MSGS)
		 {  
			 Mail.EnvoyerMail(m);
		 }
	}
	private List<Message> MSGS() throws SQLException
	{
		List<Message> MSGS = new ArrayList<Message>() ;
		
		PreparedStatement ps = Parametrage.Connection.prepareStatement
					("SELECT * FROM MESSAGE ");
			
			
			ResultSet resultSet = ps.executeQuery() ;
			
			while(resultSet.next())
			{
				Message m = new Message() ;
				m.setMsg(resultSet.getString("MSG"));
				m.setDESTINATAIRE(resultSet.getString("DESTINATAIRE"));
				
				
				MSGS.add(m);
				
			}
			
			ps = Parametrage.Connection.prepareStatement
					("DELETE FROM MESSAGE");
			ps.executeUpdate();
			
		return MSGS ;
	}
}
