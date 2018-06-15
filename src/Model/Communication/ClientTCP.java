package Model.Communication;

import Controller.AmiralController;
import Controller.Controller;
import Controller.MatelotController;
import Model.Game.*;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientTCP {
    Socket commReq;
    public ObjectInputStream oisReq;
    public ObjectOutputStream oosReq;
    String nomClient;
    Controller ctrl;


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
                ctrl = new AmiralController(new Stage(),p,e,this);
                Thread listenToServer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listenToServerMethod();
                    }
                });
                listenToServer.setDaemon(true);
                listenToServer.start();
            }else{
                Joueur j = (Matelot) oisReq.readObject();
                ctrl = new MatelotController(new Stage(),p,e,j,this);
                Thread listenToServer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listenToServerMethod();
                    }
                });
                listenToServer.setDaemon(true);
                listenToServer.start();
            }
            ok = true;
        }
    }

    private void listenToServerMethod() {
        while(true){
            try {
                String action = (String) oisReq.readObject();
                System.out.println(action);
                switch(action){
                    case "update":
                        List<Bateau> list = (List<Bateau>) oisReq.readObject();
                        if (list!=null) {
                            if (ctrl instanceof AmiralController) {
                                System.out.println(list.get(0).toString());
                                ((AmiralController) ctrl).equipeInView.setBateauxEquipe(list);
                            } else {
                                System.out.println(list.get(1).toString());
                                ((MatelotController) ctrl).equipeInview.setBateauxEquipe(list);
                                ((MatelotController) ctrl).test();
                            }
                        }
                        break;
                    case "test":
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
