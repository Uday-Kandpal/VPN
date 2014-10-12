package core;

import File.Functions;
import core.EmailServer.IncomingMailServer;
import core.EmailServer.OutgoingMailServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Uday Kandpal
 */
public class AddressFactory {

    private static List<String> address = new ArrayList();

    public static List<String> getAddresses() {
        String output = Functions.File_Output("System/ipV4-address.usk");
        String x[] = output.split("\n");
        address.clear();
        address.addAll(Arrays.asList(x));
        return address;
    }

    public static List<String> getValidatedAddresses() {
        String output = Functions.File_Output("System/ipV4-address.usk");
        String x[] = output.split("\n");
        address.clear();
        address.addAll(Arrays.asList(x));
        return address;
    }

    public static String toName(String address) {
        return address.split(":")[0].trim();
    }

    public static String emailIP(String x) {
        if (x.contains("@")) {
            x = x.split("@")[0];
        }
        for (String y : getAddresses()) {
            if (toName(y).equals(x)) {
                return toIPAddress(x);
            }
        }
        return null;
    }

    public static String toIPAddress(String address) {
        return address.split(":")[1].trim();
    }

    public static void addAdress(String x) throws UnknownHostException {
        if (InetAddress.getByName(x) != null) {
            address.add(x);
        }
    }

    public static ArrayList<String> getOutgoingMailServers() {
        return OutgoingMailServer.getOutgoingMailServerNames();
    }

    public static String getDefaultOutgoingMailServer() {
        return getOutgoingMailServers().get(0);
    }

    public static ArrayList<String> getIncomingMailServers() {
        return IncomingMailServer.getOutgoingMailServerNames();
    }

    public static String getDefaultIncomingMailServer() {
        return getIncomingMailServers().get(0);
    }
}
