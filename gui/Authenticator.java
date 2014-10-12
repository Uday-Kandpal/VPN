package gui;

import Appearance.TransluscentWindow;
import File.Functions;
import core.EmailServer;
import core.EmailServer.Settings;
import core.SSLMailReceiver;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Uday Kandpal
 */
public class Authenticator extends Appearance.TransluscentWindow {

    Timer t;

    /**
     * Creates new form Authenticator
     */
    public Authenticator() throws IOException {
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("graphics/icon/bg.jpg")));
        setLayout(new FlowLayout());
        setUndecorated(true);
        setOpacity(0.85F);
        setLocation(0, 0);
// setShape(new java.awt.geom.Ellipse2D.Float(100, 100, 500, 600));
        initComponents();
        setSize(1368, 768);
        //System.out.println(Functions.File_Output("System/server-port-FTP.usk"));

    }

    ActionListener ac = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                int width = Toolkit.getDefaultToolkit().getScreenSize().width;
                for (int i = 0;; i += 10) {
                    if (i >= width) {
                        //i = 0;

                    }
                    setLocation(0, i);
                    if (!"a".equals(jTextField1.getText())) {
                        t.stop();
                        t = new Timer(1000, ac1);
                        t.start();
                    } else {

                    }
                }

            } catch (Exception e1) {
            }
        }
    };
    ActionListener ac1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                int width = Toolkit.getDefaultToolkit().getScreenSize().width;
                for (int i = width;; i -= 10) {
                    if (i >= 0) {
                        t.stop();
                    }
                    setLocation(0, i);
                }

            } catch (Exception e1) {
            }
        }
    };

    public void startShake() {
        primaryLocation = getLocation();
        startTime = System.currentTimeMillis();
        time = new Timer(UPDATE_TIME, timeListener);
        time.start();
    }

    //stops shake/puts back in original place
    public void stopShake() {
        //code to stop the screen shaking
        time.stop();
        setLocation(primaryLocation);
    }
    public static final int UPDATE_TIME = 40;
    public static final int DURATION = 500;

    private Point primaryLocation;
    private long startTime;
    private Timer time;
    int op = 0;

    private class Shaker implements ActionListener {

        private int xOffset, yOffset;

        //every interval the timer ticks, this is performed
        @Override
        public void actionPerformed(ActionEvent e) {
            //get elapsed time(running time)
            long elapsedTime = System.currentTimeMillis() - startTime;
            double spiral = 10;
            if (op == 0) {
                //change x and y offset then reallocate frame
                xOffset = (int) (primaryLocation.x + 10 + 0 * Math.exp(spiral / 4 - 0.5));
                yOffset = (int) (primaryLocation.y + 0 + 0 * Math.exp(spiral / 4 - 0.5));
                setLocation(xOffset, yOffset);
                //   setVisible(false);
                //   repaint();

            } else {
                //change x and y offset then reallocate frame
                xOffset = (int) (primaryLocation.x - 10 + 0 * Math.exp(spiral / 4 - 0.5));
                yOffset = (int) (primaryLocation.y - 0 + 0 * Math.exp(spiral / 4 - 0.5));
                setLocation(xOffset, yOffset);
                //    setVisible(true);
                //    repaint();

            }
            spiral -= 0.05;
            op++;
            op %= 2;
            //elapsedTime exceed  DURATION, so stop now
            if (elapsedTime > DURATION) {
                stopShake();
            }
        }
    }
    //listener/instance of ActionTime
    Shaker timeListener = new Shaker();
    EmailServer s;

    public void login() throws IOException {
        s = new EmailServer("", 0);
        System.out.println(s.getAllIncomingSettings());

      //  Settings incomingSettingsFor = s.getIncomingSettingsFor(jTextField1.getText(), "imaps", jTextField2.getText(), new String(jPasswordField1.getPassword()));
        System.out.println(s.getAllOutgoingSettings());
        SSLMailReceiver srcr = new SSLMailReceiver();
        // srcr.receive(incomingSettingsFor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        background.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("SimSun", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Set Server Name :");

        jLabel2.setFont(new java.awt.Font("SimSun", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Set password :");

        jLabel3.setFont(new java.awt.Font("SimSun", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Set Username :");

        jTextField1.setFont(new java.awt.Font("SimSun", 1, 36)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("SimSun", 1, 36)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Accept");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField1)
                        .addComponent(jTextField2)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(204, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boolean r1, r2 = false, r3 = false;
        if ((r1 = jTextField1.getText().isEmpty()) || (r2 = jTextField2.getText().isEmpty()) || (r3 = jPasswordField1.getText().isEmpty())) {
            if (r1) {
                jTextField1.setBackground(Color.red);
                jTextField1.setForeground(Color.yellow);
            }
            if (r2) {
                jTextField2.setBackground(Color.red);
                jTextField2.setForeground(Color.yellow);
            }
            if (r3) {
                jPasswordField1.setBackground(Color.red);
                jPasswordField1.setForeground(Color.yellow);
            }
            startShake();
        } else {
            try {
                login();
            } catch (Exception ex) {
                Functions.showMessageDialog("ERROR!!", "" + ex.getMessage() + "\n" + ex.getCause());
               // t = new Timer(1000, ac1);
               // t.start();
                // Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!t.isRunning()) {
             //   t = new Timer(1000, ac);
              //  t.start();
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        jTextField1.setBackground(Color.white);
        jTextField1.setForeground(Color.black);

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:

        jTextField2.setBackground(Color.white);
        jTextField2.setForeground(Color.black);

    }//GEN-LAST:event_jTextField2KeyReleased

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased
        // TODO add your handling code here:

        jPasswordField1.setBackground(Color.white);
        jPasswordField1.setForeground(Color.black);

    }//GEN-LAST:event_jPasswordField1KeyReleased

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
            java.util.logging.Logger.getLogger(Authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Authenticator().setVisible(true);
                } catch (IOException ex) {
                    System.out.println("error");
                    Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}
