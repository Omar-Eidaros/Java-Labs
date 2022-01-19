/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Omar Samir
 */
public class ServerApp {
    ServerSocket myServerSocket;
    Socket s;
    DataInputStream dis;
    PrintStream ps;

    public ServerApp(){
    try{
    myServerSocket = new ServerSocket(5005);
    s = myServerSocket.accept();
    dis = new DataInputStream(s.getInputStream());
    ps =  new PrintStream (s.getOutputStream());
    String message = dis.readLine();
    System.out.println(message);
    ps.println("Hi From Server");
    ps.close();
    dis.close();
    s.close();
    myServerSocket.close();
}
    catch(IOException e){}
    
}
}
