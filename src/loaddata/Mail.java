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

    public static boolean envoyerMailSMTP(String serveur, boolean debug) {
        boolean result = false;
        try {
            Properties prop = System.getProperties();
            prop.put("mail.smtp.host", serveur);
            Session session = Session.getDefaultInstance(prop, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("redouane.boulbari@mf.gov.dz"));
            InternetAddress[] internetAddresses = new InternetAddress[1];
            internetAddresses[0] = new InternetAddress("abdenacer.djarrah@mf.gov.dz");
            message.setRecipients(Message.RecipientType.TO, internetAddresses);
            message.setSubject("Test");
            message.setText("test mail");
            message.setHeader("X-Mailer", MAILER_VERSION);
            message.setSentDate(new Date());
            //session.setDebug(debug);
            Transport.send(message);
            result = true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void EnvoyerMail(launch.Message m, String privilege) throws AddressException, MessagingException {

        Traitement.logger.info("*************************************BEGIN SEND MAIL*************************************");

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", Parametrage.SERVEURSMTP);
//			Session session = Session.getDefaultInstance(prop,null);
        prop.put("mail.smtp.auth", "true"); // Enable authentication
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Parametrage.MAIL_USERNAME, Parametrage.MAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Parametrage.FROM));
            InternetAddress[] internetAddresses = new InternetAddress[1];
            internetAddresses[0] = new InternetAddress(m.getDESTINATAIRE());
            message.setRecipients(Message.RecipientType.TO, internetAddresses);
            String emailContent = "Cher(e) " + m.getNom() + " " + m.getPrenom() + ",\n\n";

            if (privilege.equals("USER")) {
                message.setSubject("Notification");
                emailContent += "Honneur de vous informer que votre compte " + m.getMsg() + " dinars algériens.\n\n" +
                        "Si vous avez des questions ou des préoccupations concernant votre compte" +
                        " de dépôt de fonds, n'hésitez pas à nous contacter à l'adresse \"contact.dgtc@mf.gov.dz\"\n" +
                        "Cordialement" + "\n" +
                        "El_Racid" + "\n" +
                        "Trésor public";
            } else {
                message.setSubject("INFORMATION");
                emailContent += m.getMsg() + " \n\n" +
                        "Cordialement" + "\n" +
                        "El_Racid" + "\n" +
                        "Trésor public";
            }

            message.setSentDate(new Date());

            // Construct the email content
            // Construct the email content


            // Set the email content
            message.setText(emailContent);


//            message.setContent("BONJOUR Mr/Mm : " + m.getNom() + " " + m.getPrenom() + "\n" + m.getMsg(), "text/plain");
//        message.setText("BONJOUR Mr/Mm : " + m.getNom() + " " + m.getPrenom() + "\n" + m.getMsg());
//        message.setHeader("X-Mailer", MAILER_VERSION);


            Transport.send(message);

            Traitement.logger.info("MAIL INFORMATION FOR :" + m.getDESTINATAIRE());

            Traitement.logger.info("*************************************END SEND MAIL*************************************");
        } catch (MessagingException e) {

            Traitement.logger.warning("Failed to send email. Error: " + e.getMessage());
        }
    }

}