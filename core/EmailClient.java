package core;

import File.Functions;
import core.EmailClient.MailBox.Mail.IncomingMail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Uday Kandpal
 */
public class EmailClient {

    public String myHost;

    public EmailClient(String host) {
        myHost = host;
        myMailBox = new MailBox(myHost);
    }

    private final MailBox myMailBox;

    public void EmailClient() {
        myHost = "localhost";
    }

    public MailBox getMailBox() {
        return myMailBox;
    }

    public class MailBox {

        List<Mail> incomingMails = new ArrayList();
        List<Mail> outgoingMails = new ArrayList();
        int len1 = 0, len2 = 0;
        int cur1 = 0, cur2 = 0;

        public MailBox(String name) {
            name = name.toLowerCase();
            System.out.println("available mailboxes are " + Arrays.toString(Functions.list_Of_Files_Inside_Folder(PathStore.MAILBOX)));
            if (Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.MAILBOX)).contains(name.split("@")[0])) {
                incomingMails = (new Mail()).getDefaultIncomingMail().getAllMails();
                System.out.println("Total of " + incomingMails.size() + " mails");
                for (int i = 0; i < incomingMails.size(); i++) {
                    if (!incomingMails.get(i).to.startsWith(name)) {
                        System.out.println(incomingMails.get(i).to + " removed a mail " + name);
                        incomingMails.remove(i);
                    }
                }
                for (int i = 0; i < incomingMails.size(); i++) {
                    Mail get = incomingMails.get(i);
                    //  System.out.println(get.to + "\n" + get.from + "\n" + get.subject + "\n" + get.body);
                }
            } else {
                System.out.println("No mails in the mailbox");
            }

            outgoingMails = (new Mail()).getDefaultOutgoingMail().getAllMails();
            System.out.println("Total of outgoing mails " + outgoingMails.size() + " mails");
            for (int i = 0; i < outgoingMails.size(); i++) {
                if (!outgoingMails.get(i).to.startsWith(name)) {
                    System.out.println(outgoingMails.get(i).to + " removed a mail " + name);
                    outgoingMails.remove(i);
                }
            }
            for (int i = 0; i < outgoingMails.size(); i++) {
                Mail get = outgoingMails.get(i);
                //  System.out.println(get.to + "\n" + get.from + "\n" + get.subject + "\n" + get.body);
            }
            len1 = incomingMails.size();
            len2 = outgoingMails.size();
        }

        public void reset() {
            if (cur1 == len1) {
                cur1 = 0;
            }
            if (cur2 == len2) {
                cur2 = 0;
            }
        }

        public boolean hasNextIncoming() {
            return cur1 < len1;
        }

        public Mail getNextIncoming() {
            if (hasNextIncoming()) {
                return incomingMails.get(cur1++);
            }
            return null;
        }

        public Mail getIncomingMailAt(int ind) {
            return (Mail) incomingMails.get(ind);
        }

        public boolean hasNextOutgoing() {
            return cur2 < len2;
        }

        public Mail getNextOutgoing() {
            if (hasNextOutgoing()) {
                return outgoingMails.get(cur2++);
            }
            return null;
        }

        public Mail getOutgoingMailAt(int ind) {
            return (Mail) outgoingMails.get(ind);
        }

        public class Mail {

            public String to;
            public String from;
            public String subject;
            public String body;

            public Mail() {
            }

            public Mail(String to, String from, String sub, String body) {
                this.to = to;
                this.from = from;
                this.body = body;
                this.subject = sub;
            }

            public String getToField() {
                return to;
            }

            public String getFromField() {
                return from;
            }

            public String getSubjectField() {
                return subject;
            }

            public String getBodyField() {
                return body;
            }

            public IncomingMail getDefaultIncomingMail() {
                return new IncomingMail();
            }

            public OutgoingMail getDefaultOutgoingMail() {
                return new OutgoingMail();
            }

            public class IncomingMail extends Mail {

                List<Mail> mails = new ArrayList();

                public IncomingMail() {
                    String path = PathStore.INBOX;
                    String[] names = Functions.list_Of_Files_Inside_Folder(path);
                    String data = "";
                    for (String name : names) {
                        if (isValidated(path + "/" + name)) {
                            data = Functions.File_Output(path + "/" + name);
                            String[] split = data.split("#\n");
                            to = split[0].split("#:#")[1].trim().toLowerCase();
                            subject = split[1].split("#:#")[1].trim();
                            body = split[2].split("#:#")[1].trim();
                            mails.add(new Mail(to, from, subject, body));
                        }
                    }
                }

                public List<Mail> getAllMails() {
                    return mails;
                }

                private boolean isValidated(String path) {
                    String name = Functions.getName(path);
                    System.out.println(path);
                    from = name.toLowerCase().split("\\.")[0];
                    if (name.contains("@")) {
                        String x[] = name.split("@");
                        x[0] = x[0].toLowerCase();
                        System.out.println(x[1]);
                        x[1] = x[1].toLowerCase().split("\\.")[0];

                        if (!Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.INCOMING_MAIL_SERVER)).contains("@" + x[1]) || !Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.MAILBOX)).contains(x[0])) {
                            //  Functions.deleteFile(path);
                            //System.out.println(Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.OUTGOING_MAIL_SERVER)) + " with @" + x[1]);
                            return false;
                        }

                    } else {
                        //    Functions.deleteFile(path);
                        return false;
                    }
                    //Functions.File_Output(path);
                    return true;
                }
            }

            public class OutgoingMail extends Mail {

                List<Mail> mails = new ArrayList();

                public OutgoingMail() {
                    String path = PathStore.OUTBOX;
                    String[] names = Functions.list_Of_Files_Inside_Folder(path);
                    String data = "";
                    for (String name : names) {
                        if (isValidated(path + "/" + name)) {
                            data = Functions.File_Output(path + "/" + name);
                            String[] split = data.split("\n");
                            to = split[0].split(":")[1].trim().toLowerCase();
                            subject = split[1].split(":")[1].trim();
                            body = split[2].split(":")[1].trim();
                            mails.add(new Mail(to, from, subject, body));
                        }
                    }
                }

                public List<Mail> getAllMails() {
                    return mails;
                }

                private boolean isValidated(String path) {
                    String name = Functions.getName(path);
                    System.out.println(path);
                    from = name.toLowerCase().split("\\.")[0];
                    if (name.contains("@")) {
                        String x[] = name.split("@");
                        x[0] = x[0].toLowerCase();
                        System.out.println(x[1]);
                        x[1] = x[1].toLowerCase().split("\\.")[0];
                        if (!Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.OUTGOING_MAIL_SERVER)).contains("@" + x[1]) || !Arrays.asList(Functions.list_Of_Files_Inside_Folder(PathStore.MAILBOX)).contains(x[0])) {
                            return false;
                        }

                    } else {
                        //    Functions.deleteFile(path);
                        return false;
                    }
                    //Functions.File_Output(path);
                    return true;
                }
            }

        }

    }
}
