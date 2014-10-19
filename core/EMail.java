package core;

import java.util.ArrayList;

/**
 *
 * @author Uday Kandpal
 */
public class EMail {

    private String SMTPEmailData;
    private String senderAddress;
    private String receiverAddress;
    private String message;

    public static EMail createEmail(String senderAddress, String receiverAddress, String message) {
        EMail e = new EMail();
        e.senderAddress = senderAddress;
        e.receiverAddress = receiverAddress;
        e.message = message;
        e.SMTPEmailData = "MAIL From: " + senderAddress + "\r\nRCPT To: " + receiverAddress + "\r\nSIZE: " + (2 * message.length()) + "\r\nDATA: " + message + "\r\nQUIT";
        return e;
    }

    public static EMail createEmail(String senderAddress, String receiverAddress, String subject, String Cc, String replyto, String message) {
        EMail e = new EMail();
        e.senderAddress = senderAddress;
        e.receiverAddress = receiverAddress;
        e.message = "Subject: " + subject + "\r\nCc: " + Cc + "\r\nReply-To: " + replyto + "\r\n\r\n" + message;
        e.SMTPEmailData = "MAIL From: " + senderAddress + "\r\nRCPT To: " + receiverAddress + "\r\nSIZE: " + (2 * message.length()) + "\r\nDATA: " + message + "\r\nQUIT";
        return e;
    }

    public String filter(String s){
        if(s.contains(":"))
            return s.split(":").trim();
        return s;
    }
    
    public static EMail fromString(String mail) {
        EMail e = new EMail();
        String keyPairs[] = mail.split("\n");
        e.senderAddress = filter(keyPairs[0]);
        e.receiverAddress = filter(keyPairs[1]);
        e.subject = filter(keyPairs[2]);
        e.message = filter(keyPairs[3]);
        return e;
    }

    @Override
    public String toString() {
        return getSMTPEmailData();
    }

    private EMail decodeMessage() {
        return null;
    }

    public static void setupMailServer(String mailServerName, String type, String ipAddress, String portNumber, String message) {
        String data = "type : " + type + "\r\nip : " + ipAddress + "\r\nport number : " + portNumber;
    }

    
    public static void main(String args[]) {
        System.out.println(EMail.createEmail("usk@myvpn.com", "sid@myvpn.com", "Hello").getSMTPEmailData());
    }

    /**
     * @return the SMTPEmailData
     */
    public String getSMTPEmailData() {
        return SMTPEmailData;
    }

    /**
     * @return the senderAddress
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * @return the receiverAddress
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
