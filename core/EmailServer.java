package core;

import File.Functions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Uday Kandpal
 */
public class EmailServer extends Server {

    String name = "";

    public final class Settings {

        private int portNo;
        private String serverName;
        private String userName;
        private String password;
        private String proto;

        /**
         * @return the portNo
         */
        public int getPortNo() {
            return portNo;
        }

        /**
         * @return the portNo
         */
        public String getProtocol() {
            return proto;
        }

        /**
         * @return the serverName
         */
        public String getServerName() {
            return proto + "." + serverName.replace("@", "") + "com";
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        public Settings(String username, String pass, String sname, int portno) {
            serverName = sname;
            password = pass;
            portNo = portno;
            userName = username;
            proto = "smtp";
        }

        public Settings(String username, String pass, String sname, int portno, String protocol) {
            serverName = sname;
            password = pass;
            portNo = portno;
            userName = username;
            proto = protocol;
        }
    }

    public Settings getIncomingSettingsFor(String mailClientName, String protoType, String user, String pass) {
        System.out.println(mailClientName);
        String getServer = Functions.File_Output(PathStore.INCOMING_MAIL_SERVER + "/" + mailClientName);
        String[] property = getServer.split("\n");
        Map<String, Properties> map = new HashMap<>();
        int i = 0;
        String split[] = null;
        for (String s : property) {
            split = s.split(";");
            Properties p = new Properties();
            for (String y : split) {
                String z[] = y.split(":");
                p.setProperty(z[0], z[1]);
            }
            map.put(split[0].split(":")[1], p);
        }
        Settings s = new Settings(user, pass, map.get(protoType).getProperty("server-name"), Integer.parseInt(map.get(protoType).getProperty("port-no")), split[0]);
        return s;
    }

    public ArrayList<Settings> getAllIncomingSettings() {
        ArrayList<Settings> list = new ArrayList<>();
        String list1[] = Functions.list_Of_Files_Inside_Folder(PathStore.INCOMING_MAIL_SERVER + "/");
        for (String d : list1) {
            list.add(getIncomingSettingsFor(d, "imaps", "", ""));
            list.add(getIncomingSettingsFor(d, "pop3", "", ""));
        }
        return list;
    }

    public Settings getOutgoingSettingsFor(String mailClientName, String protoType, String user, String pass) {
        String getServer = Functions.File_Output(PathStore.OUTGOING_MAIL_SERVER + "/" + mailClientName);
        String[] property = getServer.split("\n");
        Map<String, Properties> map = new HashMap<>();
        int i = 0;
        String[] split = null;
        for (String s : property) {
            split = s.split(";");
            Properties p = new Properties();
            for (String y : split) {
                String z[] = y.split(":");
                p.setProperty(z[0], z[1]);
            }
            map.put(split[0].split(":")[1], p);
        }
        Settings s = new Settings(user, pass, map.get(protoType).getProperty("server-name"), Integer.parseInt(map.get(protoType).getProperty("port-no")), split[0]);
        return s;
    }

    public ArrayList<Settings> getAllOutgoingSettings() {
        ArrayList<Settings> list = new ArrayList<>();
        String list1[] = Functions.list_Of_Files_Inside_Folder(PathStore.OUTGOING_MAIL_SERVER + "/");
        for (String d : list1) {
            list.add(getIncomingSettingsFor(d, "smtp", "", ""));
        }
        return list;
    }

    public EmailServer(String serverName, int portNumber) throws IOException {
        super(portNumber);
        name = serverName;
    }

    public static ArrayList<String> getOutgoingMailServerNames() {
        ArrayList<String> l = new ArrayList();
        l.addAll(Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.OUTGOING_MAIL_SERVER)));
        return l;
    }

    public static ArrayList<String> getIncomingMailServerNames() {
        ArrayList<String> l = new ArrayList();
        l.addAll(Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.INCOMING_MAIL_SERVER)));
        return l;
    }

    public static class OutgoingMailServer extends EmailServer {

        public static int SMTP_PORT = 25;
        public static int SSMTP_PORT = 465;
        public static int defaultPort = SMTP_PORT;

        public static OutgoingMailServer getSMTPOutgoingMailServer(String name) throws IOException {
            return new OutgoingMailServer(name, SMTP_PORT);
        }

        public static OutgoingMailServer getSSMTPOutgoingMailServer(String name) throws IOException {
            return new OutgoingMailServer(name, SSMTP_PORT);
        }

        public static OutgoingMailServer getDefaultOutgoingMailServer(String name) throws IOException {
            return OutgoingMailServer.getSMTPOutgoingMailServer(name);
        }

        private OutgoingMailServer(String name) throws IOException {
            super(name, defaultPort);
        }

        private OutgoingMailServer(String serverName, int portNumber) throws IOException {
            super(serverName, portNumber);
        }

    }

    public static class IncomingMailServer extends EmailServer {

        public final static int IMAP_PORT = 143;
        public final static int POP3_PORT = 110;
        public final static int IMAPS_PORT = 993;
        public final static int IMAP4_SSL_PORT = 585;
        public final static int SSL_POP_PORT = 995;

        public static int defaultPort = POP3_PORT;

        @Override
        public void connect() {

        }

        public static IncomingMailServer getIMAPIncomingMailServer(String name) throws IOException {
            return new IncomingMailServer(name, IMAP_PORT);
        }

        public static IncomingMailServer getPOP3IncomingMailServer(String name) throws IOException {
            return new IncomingMailServer(name, POP3_PORT);
        }

        public static IncomingMailServer getIMAPSIncomingMailServer(String name) throws IOException {
            return new IncomingMailServer(name, IMAPS_PORT);
        }

        public static IncomingMailServer getIMAP4SSLIncomingMailServer(String name) throws IOException {
            return new IncomingMailServer(name, IMAP4_SSL_PORT);
        }

        public static IncomingMailServer getSSLPOPIncomingMailServer(String name) throws IOException {
            return new IncomingMailServer(name, SSL_POP_PORT);
        }

        public static IncomingMailServer getDefaultIncomingMailServer(String name) throws IOException {
            return IncomingMailServer.getPOP3IncomingMailServer(name);
        }

        private IncomingMailServer(String name) throws IOException {
            super(name, defaultPort);
        }

        private IncomingMailServer(String serverName, int portNumber) throws IOException {
            super(serverName, portNumber);
        }
    }

    public ArrayList<String> loadDrafts() {
        String[] list = Functions.list_Of_Files_Inside_Folder(PathStore.DRAFTS + "/");
        return new ArrayList(Arrays.asList(list));
    }

    public static void main(String args[]) {
        System.out.println(EmailServer.getIncomingMailServerNames());
        System.out.println(EmailServer.getOutgoingMailServerNames());
    }

}
