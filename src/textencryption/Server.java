/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textencryption;

import java.io.*;
import java.net.*;

/**
 *
 * @author KingLM
 */
public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket sersock = new ServerSocket(6969);
        System.out.println("Server  ready for chatting");
        Socket sock = sersock.accept();
        // reading from keyboard (keyRead object)
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        // sending to client (pwrite object)
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        // receiving from server ( receiveRead  object)
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

        String key = "VIGENERECIPHER";
        String receiveMessage, sendMessage;
        while (true) {
            if ((receiveMessage = receiveRead.readLine()) != null) {
                receiveMessage = decrypt(receiveMessage, key);
           
                System.out.println("Decrypted message: " + receiveMessage + "\n");
            }
            sendMessage = keyRead.readLine();
            sendMessage = encrypt(sendMessage, key);
           System.out.println("Encrypt message: " + sendMessage + "\n");
            pwrite.println(sendMessage);
            pwrite.flush();
        }
    }

     public static String encrypt(String text, final String key) {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                continue;
            }
            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    public static String decrypt(String text, final String key) {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') {
                continue;
            }
            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

}
