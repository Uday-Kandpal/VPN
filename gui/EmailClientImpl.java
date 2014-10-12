package gui;

import File.Functions;
import core.AddressFactory;
import core.Client;
import core.EMail;
import core.EmailClient;
import core.EmailServer;
import core.PathStore;
import core.Server;
import gui.List.Items;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Timer;

/**
 *
 * @author Uday Kandpal
 */
public class EmailClientImpl extends javax.swing.JFrame {

    PopupMenu p = new PopupMenu();
    static Choice c = new Choice();
    Timer t;
    long poll = 0;
    long MAX_WAIT = 3000;
    private String host;
    ActionListener ac = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            poll++;
            if (poll == MAX_WAIT) {
//                EmailClient c = new EmailClient();
                poll = 0;
                t.stop();
            }
        }
    };
    EmailClient.MailBox mailBox;

    private static class Choice {

        private int startIndex = 0;
        private int stopIndex = 0;
        private int index = 0;
        private boolean restart = false;
        private char separator = ',';

        public void push(char c) {
            if (c == separator && restart) {
                restart = false;
            } else if (c != separator && !restart) {
                startIndex = index;
                stopIndex = index;
                restart = true;
            } else if (c != separator && restart) {
                stopIndex++;
            }
            index++;
        }

        public void push(String s) {
            for (int i = 0; i < s.length(); i++) {
                push(s.charAt(i));
            }
        }

        private void pull(int i) {
            index = startIndex = stopIndex = i;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getStopIndex() {
            return stopIndex;
        }

    }

    public void setMessageType() {

    }

    /**
     * Creates new form EmailClientImpl
     */
    public EmailClientImpl() {
        initComponents();
        this.add(p);
        mailBox = new EmailClient("ukandpal2@myvpn.com").getMailBox();
        jList2.removeAll();
        Display.setVisible(false);
        jScrollPane3.setVisible(true);
        send.setVisible(false);
        save.setVisible(false);
        t = new Timer(1, ac);
    }

    public void loadReceivedMails() {
        java.util.List<String> list = new ArrayList();
        while (mailBox.hasNextIncoming()) {
            EmailClient.MailBox.Mail next = mailBox.getNextIncoming();
            list.add("from: " + next.getFromField() + "   subject: " + next.getSubjectField() + "  mailbox: " + next.getToField());
        }
        mailBox.reset();
        adaptList(list);

    }

    public void loadSentMails() {
        java.util.List<String> list = new ArrayList();
        while (mailBox.hasNextOutgoing()) {
            EmailClient.MailBox.Mail next = mailBox.getNextOutgoing();
            list.add("from: " + next.getFromField() + "   subject: " + next.getSubjectField() + "  mailbox: " + next.getToField());
        }
        mailBox.reset();
        adaptList(list);
    }

    private void adaptList(java.util.List<String> list) {
        jList2.setModel(new javax.swing.AbstractListModel() {

            @Override
            public int getSize() {
                return list.size();
            }

            @Override
            public Object getElementAt(int i) {
                return list.get(i);
            }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    public static void attachFromSuggestions(String suggest) {
        c.pull(c.getStartIndex());
        String email = suggest.substring(0, suggest.indexOf("<")) + "@myvpn.com";
        mBox1.setText(email);
        c.push(email);
    }

    public class Display extends javax.swing.JFrame {

        public Display() {
        }

        public Display(String s) {
            super(s);
        }

    }

    void load(String type, int index) {
        System.out.println("type: " + type);
        jList2.removeAll();
        if (null != type) {
            switch (type) {
                case "inbox": {
                    loadReceivedMails();
                    if (!mailBox.hasNextIncoming()) {
                        System.out.println(mailBox.getNextIncoming().toString());
                        Functions.showMessageDialog("ERROR!!!", "No incoming message !!!");
                    } else {
                        EmailClient.MailBox.Mail mailAt = mailBox.getIncomingMailAt(index);
                        lab1.setText("From:");
                        lab2.setText("Subject:");
                        mailbox.setText(mailAt.getToField());
                        mBox1.setText(mailAt.getFromField() + ".com");
                        mBox2.setText(mailAt.getSubjectField());
                        body.setText(mailAt.getBodyField());
                        send.setVisible(false);
                        save.setVisible(false);
                    }
                    break;
                }
                case "sent": {
                    loadSentMails();
                    if (!mailBox.hasNextOutgoing()) {
                        Functions.showMessageDialog("ERROR!!!", "No Outgoing Mails");
                    } else {
                        EmailClient.MailBox.Mail mailAt = mailBox.getOutgoingMailAt(index);
                        lab1.setText("To:");
                        lab2.setText("Subject:");
                        mailbox.setText(mailAt.getFromField() + ".com");
                        mBox1.setText(mailAt.getToField());
                        mBox2.setText(mailAt.getSubjectField());
                        body.setText(mailAt.getBodyField());
                        send.setVisible(false);
                        save.setVisible(false);
                    }
                    break;
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        Display = new javax.swing.JPanel();
        displayScroll = new javax.swing.JScrollPane();
        displayPane = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        mBox2 = new javax.swing.JTextArea();
        mBox1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        body = new javax.swing.JTextArea();
        lab1 = new javax.swing.JLabel();
        lab2 = new javax.swing.JLabel();
        send = new javax.swing.JButton();
        save = new javax.swing.JButton();
        mailbox = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EMail Client");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jList1.setBackground(new java.awt.Color(0, 204, 0));
        jList1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jList1.setForeground(new java.awt.Color(255, 255, 0));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Inbox", "Sent" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setBackground(new java.awt.Color(204, 204, 204));
        jList2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setToolTipText("");
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jList2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jList2PropertyChange(evt);
            }
        });
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        mBox2.setEditable(false);
        mBox2.setColumns(20);
        mBox2.setRows(5);
        jScrollPane4.setViewportView(mBox2);

        mBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mBox1KeyReleased(evt);
            }
        });

        body.setColumns(20);
        body.setRows(5);
        jScrollPane2.setViewportView(body);

        lab1.setText("To :");

        lab2.setText("Subject :");

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        save.setText("Save to Drafts");

        javax.swing.GroupLayout displayPaneLayout = new javax.swing.GroupLayout(displayPane);
        displayPane.setLayout(displayPaneLayout);
        displayPaneLayout.setHorizontalGroup(
            displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPaneLayout.createSequentialGroup()
                        .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                            .addComponent(mBox1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(displayPaneLayout.createSequentialGroup()
                        .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(save)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        displayPaneLayout.setVerticalGroup(
            displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lab2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(displayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(send)
                    .addComponent(save))
                .addGap(17, 17, 17))
        );

        displayScroll.setViewportView(displayPane);

        mailbox.setText("Mailbox :");

        javax.swing.GroupLayout DisplayLayout = new javax.swing.GroupLayout(Display);
        Display.setLayout(DisplayLayout);
        DisplayLayout.setHorizontalGroup(
            DisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mailbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap())
        );
        DisplayLayout.setVerticalGroup(
            DisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mailbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Compose mail");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Load Mail (GMAIL)");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(63, 63, 63))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Display, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
String senderIp = "";
    private void mBox1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mBox1KeyReleased
        // TODO add your handling code here:
        p.removeAll();
        c.push(evt.getKeyChar());
        Items s = List.getDefaultItem();
        java.util.List<String> adresses = AddressFactory.getAddresses();
        adresses.stream().forEach((String content) -> {
            String name1 = AddressFactory.toName(content);
            String ip = AddressFactory.toIPAddress(content);
            if (mBox1.getText().contains(name1) || mBox1.getText().contains(ip) || mBox1.getText().contains(ip.substring(0, 3))) {
                s.addItem(name1, AddressFactory.toIPAddress(content));
                MenuItem m = new MenuItem();
                senderIp = ip;
                m.setLabel(name1 + "<" + ip + ">");
                m.addActionListener((ActionEvent e) -> {
                    attachFromSuggestions(m.getLabel());
                });
                p.add(m);
            }
        });
        if (s.getItems().size() > 0) {
            p.show(mBox1, 0, 30);
        }
    }//GEN-LAST:event_mBox1KeyReleased

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
        int ind = jList2.getMaxSelectionIndex();
        System.out.println(jList2.getSelectionMode());
        jScrollPane3.setVisible(false);
        jList2.setModel(new DefaultListModel());
        System.out.println(ind + " " + jList2.getSelectedValue());
        load(jList1.getSelectedValue().toString().toLowerCase(), ind < 0 ? 0 : ind);
        Display.setVisible(true);
    }//GEN-LAST:event_jList2ValueChanged

    private void jList2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jList2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jList2PropertyChange

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList2MouseClicked

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        jScrollPane3.setVisible(true);
        Display.setVisible(false);
        load(jList1.getSelectedValue().toString().toLowerCase(), 0);
        jList2.requestFocus();
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        send.setVisible(true);
        jScrollPane3.setVisible(false);
        mBox1.setText(PathStore.CLIENT_NAME + "@myvpn.com");
        mBox2.setEditable(true);
        Display.setVisible(true);
        mBox1.setText("");
        mBox2.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    @SuppressWarnings("empty-statement")
    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        // TODO add your handling code here:
        try {
            Client client = new Client(senderIp, PathStore.CLIENT_PORT_FTP);
            client.receive();
            EMail createEmail = EMail.createEmail(mBox1.getText(), PathStore.CLIENT_NAME + "@myvpn.com", body.getText());
            client.send("email://" +createEmail);
            client.flushSent();
            client.send("BYE");
            client.flushSent();
           // Functions.File_Input(createEmail,PathStore.OUTBOX+"/"+createEmail.getSenderAddress());
//System.out.println("x");
        } catch (Exception e) {
            Functions.showMessageDialog("ERROR!!", e.getMessage() + "\n" + Arrays.deepToString(e.getStackTrace()));
        }
    }//GEN-LAST:event_sendActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    @SuppressWarnings("empty-statement")
    private void sendMail(Client s, String addr, String sends, String sub, String bodY) {
        try {
            s = new Client(AddressFactory.emailIP(addr), PathStore.CLIENT_PORT_FTP);
            if (t.isRunning()) {
                s.send("email://");
                // s.send(Functions.getName(addr));
                // s.flushSent();
                // while (!"start".equals(s.receive()) && t.isRunning());
//                ifServerUnavailable(sends, sub, bodY, addr);
//                s.sendFile(Functions.getName(addr));
//                s.flushSent();
//                //while (!"BYE".equals(s.receive()));
            }
        } catch (IOException ex) {
            Functions.showMessageDialog("ERROR!", "Unable to connect");
//Logger.getLogger(EmailClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ifServerUnavailable(String sends, String sub, String bodY, String addr) {
        if (!t.isRunning()) {
            Functions.showMessageDialog("ERROR!", "The mail address is not available for connection!!!");
            Functions.File_Input(EMail.createEmail(sends, "", sub, "", "", bodY).toString(), addr, save);
            Functions.deleteFile(addr);
            Functions.deleteFile("downloads/" + Functions.getName(addr));
            EmailClientImpl e = new EmailClientImpl();
            e.setVisible(true);
            dispose();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmailClientImpl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmailClientImpl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmailClientImpl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmailClientImpl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmailClientImpl().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Display;
    private javax.swing.JTextArea body;
    private javax.swing.JPanel displayPane;
    private javax.swing.JScrollPane displayScroll;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lab1;
    private javax.swing.JLabel lab2;
    public static javax.swing.JTextField mBox1;
    private static javax.swing.JTextArea mBox2;
    private javax.swing.JLabel mailbox;
    private javax.swing.JButton save;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables
}
