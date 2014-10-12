package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Uday Kandpal
 */
public class Client {

    public static int DEFAULT_PORT = PathStore.CLIENT_PORT_FTP;
    private int portNumber = PathStore.CLIENT_PORT_FTP;
    private BufferedOutputStream bow;
    private BufferedReader br;
    private DataInputStream dis;
    private PrintWriter pw;
    private ServerSocket serverSocket;
    private BufferedInputStream bis;
    private Socket socket;
    private String host = "127.0.0.1";
    private String docroot = "Downloads/";

    public Client(String host, int port) throws IOException {
        this.host = host;
        portNumber = port;
        InetAddress x = InetAddress.getByName(host);
        socket = new Socket(x, portNumber);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream());
    }

    public void send(String data) {
        pw.println(data);
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

    public char receiveChar() throws IOException {
        return (char) br.read();
    }

    public void flushSent() throws IOException {
        pw.flush();
    }

    public void receiveFile(String name, boolean octet) throws IOException, NoSuchAlgorithmException {
        int c;
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128);
        //SecretKey key = kgen.generateKey();
        //byte[] encoded = key.getEncoded();
        File f;
        FileOutputStream fos = new FileOutputStream(f = new File(docroot + name));
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bis = new BufferedInputStream(new DataInputStream(socket.getInputStream()));

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
        long l = Long.parseLong(receive().replace("Length://", "")), i = 0;
        while (true) {
            if (octet) {
                c = bis.read();
                bos.write(c);
            } else {
                bufferedWriter.write(br.read());
            }
            if (i++ == l) {
                break;
            }
        }
        bos.close();
        bufferedWriter.close();
    }

    public void receiveMailFile(String name) throws IOException {
        int c;

        FileWriter fw = new FileWriter(new File(PathStore.INBOX + "/" + name));
        BufferedWriter bw = new BufferedWriter(fw);
        long l = Long.parseLong(receive().replace("Length://", "")), i = 0;
        System.out.println("Length of the file is : " + l);
        while (true) {
            c = br.read();
            System.out.print((char) c);
            bw.write("" + (char) c);
            if (i++ == l) {
                break;
            }
        }
        bw.close();
        fw.close();
    }
}
