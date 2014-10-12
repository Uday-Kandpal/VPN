package core;

import File.Functions;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import registry.RegistryEditor;

/**
 *
 * @author Uday Kandpal
 */
public class NetStatus {

    public NetStatus() throws IOException {
        Process p = Runtime.getRuntime().exec("netstat -nao");
    }

    public static boolean isPingedSuccessfully(String ping)  {
        try {
            System.out.print("pinging " + ping + " ... ");
            ping = ping.trim();
            if ("127.0.0.0".equals(ping)) {
                ping = "127.0.0.1";
            }
            Process p = Runtime.getRuntime().exec("ping " + ping);
            RegistryEditor.ConsoleDataReader processStreamReaderInstance = RegistryEditor.getProcessStreamReaderInstance(p.getInputStream());
            processStreamReaderInstance.start();
            p.waitFor(100, TimeUnit.MICROSECONDS);
            processStreamReaderInstance.join();
            String result = processStreamReaderInstance.getResult();
            //System.out.println(result);
            if (!result.toLowerCase().contains("lost = 0 (0% loss)")) {
                System.out.print(false + "\n");
                return false;
            }
            System.out.print(true + "\n");

        } catch (IOException | InterruptedException ex) {
            System.out.print(false + "\n");
            Functions.showMessageDialog("ERROR!!", ex.getMessage());
        }
        return true;
    }
}
