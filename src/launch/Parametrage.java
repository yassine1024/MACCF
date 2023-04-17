package launch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import loaddata.Mail;

public class Parametrage {

    public static String CorpusPath;
    public static String GlobalFiles;
    public static String Abside;
    public static String BlocMontant;
    public static String CompteBloc;
    public static String CompteClient;
    public static String Operation;
    public static String SERVEUR;
    public static Integer PORT;
    public static String USER;
    public static String PASS;
    public static String PathClient;
    public static String PathCompteBloquer;
    public static String PathMontantBloquer;
    public static String PathMouvement;
    public static String SERVEURSMTP;
    public static String MAIL_USERNAME;
    public static String MAIL_PASSWORD;
    public static String FROM;
    public static List<String> EmailAdmin;
    public static Connection Connection;


    public static void Init() throws IOException, SQLException, ClassNotFoundException {

        Properties prop = new Properties();
        FileInputStream in = new FileInputStream("Configuration.properties");
        prop.load(in);
        in.close();

        Parametrage.CorpusPath = prop.getProperty("CorpusPath");
        Parametrage.GlobalFiles = prop.getProperty("GlobalFiles");
        Parametrage.Abside = prop.getProperty("Abside");
        Parametrage.BlocMontant = prop.getProperty("BlocMontant");
        Parametrage.CompteBloc = prop.getProperty("CompteBloc");
        Parametrage.CompteClient = prop.getProperty("CompteClient");
        Parametrage.Operation = prop.getProperty("Operation");
        Parametrage.SERVEUR = prop.getProperty("SERVEUR");
        Parametrage.PORT = Integer.parseInt(prop.getProperty("PORT"));
        Parametrage.USER = prop.getProperty("USER");
        Parametrage.PASS = prop.getProperty("PASS");
        Parametrage.PathClient = prop.getProperty("PathClient");
        Parametrage.PathCompteBloquer = prop.getProperty("PathCompteBloquer");
        Parametrage.PathMontantBloquer = prop.getProperty("PathMontantBloquer");
        Parametrage.PathMouvement = prop.getProperty("PathMouvement");
        Parametrage.SERVEURSMTP = prop.getProperty("SERVEURSMTP");
        Parametrage.MAIL_USERNAME = prop.getProperty("MAIL_USERNAME");
        Parametrage.MAIL_PASSWORD = prop.getProperty("MAIL_PASSWORD");
        Parametrage.FROM = prop.getProperty("FROM");
        Parametrage.EmailAdmin = new ArrayList<String>();

        String[] t = prop.getProperty("EmailAdmin").split(",", -1);
        for (int i = 0; i < t.length; i++) {
            Parametrage.EmailAdmin.add(t[i]);
        }

        Class.forName("com.mysql.jdbc.Driver");

        String ChaineConnection = prop.getProperty("ConnectionDataBase");

        Connection = DriverManager.getConnection(ChaineConnection.split(",", -1)[0], ChaineConnection.split(",", -1)[1], ChaineConnection.split(",", -1)[2]);

    }

}
