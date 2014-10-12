package core;

import File.Functions;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JProgressBar;
import sun.misc.IOUtils;

/**
 *
 * @author Uday Kandpal
 */
public class Server extends Thread {

    public static int DEFAULT_PORT = PathStore.SERVER_PORT_FTP;//55000;
    private final String docroot = "System/docroot.usk";
    private int portNumber = PathStore.SERVER_PORT_FTP;
    private BufferedOutputStream bow;
    private PrintWriter pw;
    private DataInputStream dis;
    private BufferedReader br;
    private ServerSocket serverSocket;
    private BufferedInputStream bis;
    private Socket clientSocket;
    private String getConnectedHost = "";

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverSocket = new ServerSocket(portNumber);
        //serverSocket.setReuseAddress(true);
    }

    public void connect() throws IOException {
        clientSocket = serverSocket.accept();
        System.out.println("Connected");
        pw = new PrintWriter(clientSocket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String output = "Connected to the server " + serverSocket.getInetAddress().getHostAddress();
        pw.println(output);
        pw.flush();
        System.out.println(output);
    }

    public void disconnect() throws IOException {
        clientSocket.close();
        pw.close();
        br.close();
    }

    public void flushSent() throws IOException {
        pw.flush();
    }

    public void send(String message) {
        pw.println(message);
    }

    public void send(char message) {
        pw.print(message);
    }

    @SuppressWarnings("empty-statement")
    public String receive() throws IOException {
        String x;
        while ((x = br.readLine()) == null);
        return x;
    }

    public int receiveChar() throws IOException {
        return br.read();
    }

    public void sendFile(String name) throws FileNotFoundException, IOException {
        String x = Functions.File_Output(docroot);
        File f;
        FileReader fr = new FileReader(f = new File(x + "/" + name));
        BufferedReader br = new BufferedReader(fr);
        int c;
        send("Length://" + f.length());
        flushSent();
        while ((c = br.read()) != -1) {
            send((char) c);
            flushSent();
        }
        send("file://");
        br.close();
        fr.close();
    }

    public void sendFile(String name, JProgressBar j, boolean octet) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        String x = Functions.File_Output(docroot);
        File f;
        FileInputStream fos = new FileInputStream(f = new File(x + "/" + name));
        BufferedInputStream bos = new BufferedInputStream(fos);
        int c;
        long l = f.length();
        send("Length://" + l);
        flushSent();
        long i = 0;
        l = f.length();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
        while ((c = bos.read()) != -1) {
            if (octet) {
                bufferedOutputStream.write(c);
                bufferedOutputStream.flush();
            } else {
                send((char) c);
                flushSent();
            }
            j.setValue((int) ((100 * i++) / l));
            if (i >= l) {
                break;
            }
        }
        j.setValue(100);

        send("file://");
        disconnect();
        br.close();
    }

    public void sendMailFile(String name) throws FileNotFoundException, IOException {
        String x = PathStore.OUTBOX;
        File f;
        FileInputStream fis = new FileInputStream((f = new File(x + "/" + name)));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        int c;
        send("Length://" + f.length());
        flushSent();
        StringBuilder b = new StringBuilder();
        while ((c = br.read()) != -1) {
            b.append(c);

           send((char) c);
            flushSent();
        }
        disconnect();
        send("file://");
        br.close();
    }

    public String getConnectedAddress() {
        return getConnectedHost;
    }

}
