/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Omar Samir
 */
public class ClientApp {
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    
    public ClientApp(){
    try{
    mySocket = new Socket("127.0.0.1", 5005);
    dis = new DataInputStream(mySocket.getInputStream());
    ps =  new PrintStream (mySocket.getOutputStream());
    ps.println("Hi From Client");
    String replymessage = dis.readLine();
    System.out.println(replymessage);
    ps.close();
    dis.close();
    mySocket.close();
}
    catch(IOException e){}
    
}
}
