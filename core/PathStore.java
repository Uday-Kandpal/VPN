package core;

import File.Functions;

/**
 *
 * @author Uday Kandpal
 */
public class PathStore {

    public static final String DRAFTS = "mails/messages/drafts";
    public static final String INCOMING_MAIL_SERVER = "mails/servers/incoming";
    public static final String OUTGOING_MAIL_SERVER = "mails/servers/outgoing";
    public static final String OUTBOX = "mails/messages/sent";
    public static final String INBOX = "mails/messages/received";
    public static final String MAILBOX = "mails/mailbox";
    public static final String MAIL_SERVER_HOST = "127.0.0.0";
    public static final String DOCROOT = "docroot";
    public static final String CLIENT_NAME = Functions.File_Output("System/server-name.usk");

    public static final int CLIENT_PORT_FTP = Integer.parseInt(Functions.File_Output("System/client-port-FTP.usk"));
    public static final int SERVER_PORT_FTP = Integer.parseInt(Functions.File_Output("System/server-port-FTP.usk"));

}
