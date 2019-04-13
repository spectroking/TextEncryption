/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textencryption;

/**
 *
 * @author KingLM
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author KingLM
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("192.168.221.1", 3000);
        // reading from keyboard (keyRead object)
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        // sending to client (pwrite object)
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        // receiving from server ( receiveRead  object)
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

        System.out.println("Start the chitchat, type and press Enter key");

        String key = "VIGENERECIPHER";
        String receiveMessage, sendMessage;
        while (true) {
            sendMessage = keyRead.readLine();  // keyboard reading
            sendMessage = encrypt(sendMessage, key);
            System.out.println("Encrypt message: " + sendMessage + "\n");
            pwrite.println(sendMessage);       // sending to server
            pwrite.flush();                    // flush the data
            if ((receiveMessage = receiveRead.readLine()) != null) //receive from server
            {
                receiveMessage = decrypt(receiveMessage, key);
                
                System.out.println("Decrypted message: " + receiveMessage + "\n"); // displaying at DOS prompt
            }
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
