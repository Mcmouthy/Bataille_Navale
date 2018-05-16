package Model.Communication;

import Model.Game.Equipe;
import Model.Game.Partie;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTCP {
    ServerSocket conn;
    List<Socket> listeSocket;
    Partie partie;
    public ServerTCP(int serverPort) throws IOException {
        conn = new ServerSocket(serverPort,50,InetAddress.getByName("192.168.43.231"));
        System.out.println(conn.getInetAddress().toString());
        listeSocket = new ArrayList<>();
        partie=new Partie(new Equipe(),new Equipe());
    }

    public void mainloop() throws IOException {
        while (true) {
            Socket s=conn.accept();
            listeSocket.add(s);
            (new ServeurThread(listeSocket.size()-1,s,partie)).start();

        }
    }
}
