package launch;

public class Message {

    private String DESTINATAIRE;
    private String Msg;
    private String nom;
    private String prenom;


    public String getDESTINATAIRE() {
        return DESTINATAIRE;
    }

    public void setDESTINATAIRE(String dESTINATAIRE) {
        DESTINATAIRE = dESTINATAIRE;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getNom() {

        if ((this.DESTINATAIRE.split("@", -1)[0]).split("\\.", -1).length < 2) {
            return this.DESTINATAIRE.split("@", -1)[0];
        }
        return ((this.DESTINATAIRE.split("@", -1)[0]).split("\\.", -1)[1]).toUpperCase();
    }

    public String getPrenom() {

        if ((this.DESTINATAIRE.split("@", -1)[0]).split("\\.", -1).length < 2) {
            return "";
        }
        return ((this.DESTINATAIRE.split("@", -1)[0]).split("\\.", -1)[0]).toUpperCase();
    }

    @Override
    public String toString() {
        return "Message [DESTINATAIRE=" + DESTINATAIRE + ", Msg=" + Msg + "]";
    }


}
