package Model.Communication;

import java.io.*;
import java.net.Socket;

public class ClientTCP {
    Socket commReq;
    ObjectInputStream oisReq;
    ObjectOutputStream oosReq;

    BufferedReader consoleIn; // flux de lecture lignes depuis clavier

    public ClientTCP(String serverIp, int serverPort) throws IOException {
	/* A COMPLETER :
	   - instanciation commReq pour se connecter au serveur
	   - instanciation des flux oosReq et oisReq
	*/
        commReq= new Socket(serverIp,serverPort);
        oosReq = new ObjectOutputStream(commReq.getOutputStream());
        oisReq = new ObjectInputStream(commReq.getInputStream());

        consoleIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void initLoop() throws IOException,ClassNotFoundException {

        String line = null;
        boolean ok = false;

        while (!ok) {

	    /* A COMPLETER :
	    - saisir pseudo au clavier
	    - envoyer pseudo
	    - recevoir booléen dans ok
	    - éventuellement afficher msg en fonction de la valeur de ok
	    */
            System.out.println("Entrez un pseudo :");
            line= consoleIn.readLine();
            oosReq.writeObject(line);
            oosReq.flush();
            System.out.println("envoyé !");

        }
    }
}
