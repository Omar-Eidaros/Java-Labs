/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatHandlerPkg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Omar Samir
 */
public class ServerMulti {

    ServerSocket mysrvsocket;

    public ServerMulti() {
        try {
            mysrvsocket = new ServerSocket(5005);
            while (true) {
                Socket s = mysrvsocket.accept();
                new ChatHandler(s);
            }
        } catch (IOException e) {
        }
    }

}
