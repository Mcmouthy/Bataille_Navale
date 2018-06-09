package Model.Communication;

import Controller.AmiralController;
import Controller.MatelotController;
import Model.Game.Equipe;
import Model.Game.Joueur;
import Model.Game.Matelot;
import Model.Game.Partie;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientTCP {
    Socket commReq;
    ObjectInputStream oisReq;
    ObjectOutputStream oosReq;
    String nomClient;


    public ClientTCP(String serverIp, int serverPort, String nomClient) throws IOException {
	/* A COMPLETER :
	   - instanciation commReq pour se connecter au serveur
	   - instanciation des flux oosReq et oisReq
	*/
        commReq= new Socket(serverIp,serverPort);
        oosReq = new ObjectOutputStream(commReq.getOutputStream());
        oisReq = new ObjectInputStream(commReq.getInputStream());
        this.nomClient = nomClient;
    }

    public void initLoop() throws IOException,ClassNotFoundException {
        boolean ok = false;
        while (!ok) {

            oosReq.writeObject(nomClient);
            oosReq.flush();
            String typeMatelot=(String) oisReq.readObject(); //get a typeMatelot string
            Partie p = (Partie) oisReq.readObject();
            Equipe e = (Equipe) oisReq.readObject();
            if (typeMatelot.equals("amiral"))
            {
                AmiralController ctrl = new AmiralController(new Stage(),p,e);
            }else{
                Joueur j = (Matelot) oisReq.readObject();
                MatelotController ctrl = new MatelotController(new Stage(),p,e,j);
            }
            ok = true;
        }
    }
}
