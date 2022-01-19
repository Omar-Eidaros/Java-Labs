import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerApp {
    public ServerApp() throws IOException {
        try {
            ServerSocket myServerSocket = new ServerSocket(7777);
            while(true){
                Socket s =myServerSocket.accept();
                new ChatHandler(s);
            }
        }catch(IOException e){
            e.printStackTrace();

        }
    }





    public class  ChatHandler extends Thread{
        DataInputStream dis;
        PrintStream ps;
        static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();

        public ChatHandler(Socket cs){
            try {
                dis = new DataInputStream(cs.getInputStream());
                ps = new PrintStream(cs.getOutputStream());
                clientsVector.add(this);
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public  void run(){
            while (true) {
                String str = null;
                try {
                    str = dis.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendMessageToAll(str);
            }
        }
        void sendMessageToAll(String msg){
            for(ChatHandler ch : clientsVector){
                ch.ps.println("["+ch.getId()+"]:"+msg);
            }
        }
    }





    public static void main(String[] args) throws IOException {
        new ServerApp();
    }
}

