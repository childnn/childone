package org.anonymous;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Title:        Pyrmont Servlet Container
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Brainy Software
 *
 * @author Budi Kurniawan
 * @version 1.0
 */
public class HttpSniffer extends JFrame {
    JTextArea response = new JTextArea();
    JTextField address = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField port = new JTextField();
    JButton send = new JButton();
    JLabel jLabel3 = new JLabel();
    JTextField command = new JTextField();
    JScrollPane jScrollPane1 = new JScrollPane();

    public HttpSniffer() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpSniffer frame = new HttpSniffer();
        frame.setName("客户端");
        frame.setBounds(0, 0, 600, 400);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void jbInit() {
        this.getContentPane().setLayout(null);
        address.setText("127.0.0.1");
        address.setBounds(new Rectangle(77, 7, 143, 19));
        jLabel1.setText("Address");
        jLabel1.setBounds(new Rectangle(6, 5, 69, 18));
        jLabel2.setText("Port");
        jLabel2.setBounds(new Rectangle(230, 7, 30, 20));
        port.setText("8080");
        port.setBounds(new Rectangle(265, 7, 72, 18));
        send.setText("Send Request");
        send.setBounds(new Rectangle(347, 6, 117, 58));
        send.addActionListener(this::sendActionPerformed);
        jLabel3.setText("Command");
        jLabel3.setBounds(new Rectangle(2, 39, 74, 24));
        command.setText("GET /index.html HTTP/1.1");
        command.setBounds(new Rectangle(74, 42, 265, 24));
        jScrollPane1.setBounds(new Rectangle(8, 76, 560, 270));
        this.getContentPane().add(address, null);
        this.getContentPane().add(address, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(port, null);
        this.getContentPane().add(send, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(command, null);
        this.getContentPane().add(jScrollPane1, null);
        jScrollPane1.getViewport().add(response, null);
    }

    void sendActionPerformed(ActionEvent e) {
        response.setText("");
        String host = "";
        int portNumber = 0;
        boolean ok = true;
        try {
            host = address.getText();
            portNumber = Integer.parseInt(port.getText());
        } catch (Exception ex) {
            ok = false;
        }

        if (ok) {
            try {
                // 客户端
                Socket socket = new Socket(host, portNumber);
                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os, true);
                String message = command.getText();
                out.println(message);
                out.println("Host: localhost:8080");
                out.println("Connection: Close");
                out.println();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                boolean loop = true;
                StringBuilder sb = new StringBuilder(8096);
                while (loop) {
                    if (in.ready()) {
                        int i = 0;
                        while (i != -1) {
                            i = in.read();
                            sb.append((char) i);
                        }
                        loop = false;
                    }
                    Thread.sleep(50);
                }
                response.setText(sb.toString());
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}