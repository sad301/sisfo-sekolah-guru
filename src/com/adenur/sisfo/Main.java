/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adenur.sisfo;

import com.adenur.sisfo.view.FrameLogin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author sadlie
 */
public class Main {
    
    private final Connection connection;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Util.setSystemLookAndFeel();
        String conf = new String();
        for(String arg: args) {
            String[] pair = arg.split("=");
            if(pair[0].equals("dbConfig")) {
                conf = pair[1];
                break;
            }
        }
        if(conf.equals("")) {
            String pesan = "Parameter konfigurasi database tidak ditemukan";
            JOptionPane.showMessageDialog(null, pesan, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Connection c = null;
        Properties p = new Properties();
        File f = new File(conf);
        try {
            if(f.exists()) {
                FileInputStream in = new FileInputStream(f);
                p.load(in);
                if(!p.isEmpty()) {
                    c = Util.getConnection(p);
                }
            }
            else {
                String pesan = "File konfigurasi tidak ditemukan";
                JOptionPane.showMessageDialog(null, pesan, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        catch(IOException | SQLException exc) {
            System.err.println(exc.toString());
        }
        finally {
            if(c != null) {
                (new Main(c)).start();
            }
        }
    }
    
    public Main(Connection connection) {
        this.connection = connection;
    }
    
    private void start() {
        SwingUtilities.invokeLater(() -> {
            (new FrameLogin(connection)).setVisible(true);
        });
    }
    
}
