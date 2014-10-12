package gui;

import core.AddressFactory;
import core.EmailClient.MailBox.Mail;
import core.EmailServer.IncomingMailServer;
import core.EmailServer.OutgoingMailServer;
import core.Server;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Uday Kandpal
 */
public class GenericServer {

    ArrayList<IncomingMailServer> out = new ArrayList();

    public GenericServer() throws IOException {
        for (String x : AddressFactory.getIncomingMailServers()) {
            out.add(IncomingMailServer.getDefaultIncomingMailServer(x));
        }

    }

    public void receiveMail() throws IOException {
        for (IncomingMailServer x : out) {
            x.connect();
            String data = "", recv = "";
            while (true) {
                recv = x.receive();
                if (data.length() > 2500000 || recv.equals("" + Character.NON_SPACING_MARK)) {
                    x.disconnect();
                    break;
                }
                data = data.concat(recv);
                Mail.IncomingMail s;
            }
        }
    }
}
