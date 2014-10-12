package core;

import gui.EmailClientImpl;
import gui.FTPClientImpl;
import gui.FTPServerImpl;
import gui.GUIMain;
import gui.Profile;

/**
 *
 * @author Uday Kandpal
 */
public class CommandInterpreter {

    private final String helpString = "Welcome to the Help Utility !!!\nHere are a list of commands you can try over this utility.\n-server : starts a server\n-client : starts a client\n-email : open an email client";

    public void startServer() {
        FTPServerImpl impl = new FTPServerImpl();
        impl.setVisible(true);
    }

    public void startServerSilently() {
        FTPServerImpl impl = new FTPServerImpl(false);
    }

    public void startClient() {
        FTPClientImpl impl = new FTPClientImpl();
        impl.setVisible(true);
    }

    public void startEmailClient() {
        EmailClientImpl impl = new EmailClientImpl();
        impl.setVisible(true);
    }

    public String getServerHelpString() {
        return "\nOpens a server that will wait for the client to receive the connection.This must be open for the clients to receive connections.";
    }

    public static void main(String args[]) {
        CommandInterpreter c = new CommandInterpreter();

        if (args.length == 0) {
            System.out.println(c.helpString);
        } else if (args[0].equals("-server")) {
            c.startServer();
        } else if (args[0].equals("-server--silently")) {
            c.startServerSilently();
        } else if (args[0].equals("-client")) {
            c.startClient();
        } else if (args[0].equals("-client")) {
            c.startClient();
        } else if (args[0].equals("-start")) {
            Profile p = new Profile();
            p.setVisible(true);
        } else if (args[0].equals("-notifications")) {
            GUIMain g = new GUIMain();
        }
    }
}
