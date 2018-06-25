package Model.Communication;

import Controller.AmiralController;
import Controller.Controller;
import Controller.MatelotController;
import Model.Game.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.*;

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
                if (action !=null) {
                    switch (action) {
                        case "update":
                            List<Bateau> list = (List<Bateau>) oisReq.readObject();
                            if (list != null) {
                                if (ctrl instanceof AmiralController) {
                                    System.out.println(list.get(0).toString());
                                    ((AmiralController) ctrl).equipeInView.setBateauxEquipe(list);
                                    ((AmiralController) ctrl).updateShipPlacement();
                                } else {
                                    System.out.println(list.get(1).toString());
                                    ((MatelotController) ctrl).equipeInview.setBateauxEquipe(list);
                                    ((MatelotController) ctrl).updateShipPlacement();
                                }
                            }
                            break;
                        case "updateJoueur":
                            Joueur j = (Matelot) oisReq.readObject();
                            if (j != null) {
                                if (ctrl instanceof AmiralController) {
                                    ((AmiralController) ctrl).equipeInView.getLesJoueurs().add(j);
                                } else {
                                    ((MatelotController) ctrl).equipeInview.getLesJoueurs().add(j);
                                }
                            }
                            break;
                        case "updateAmiralAssignation":
                            if (ctrl instanceof MatelotController) {
                                TreeMap<Bateau, Matelot[]> map = (TreeMap<Bateau, Matelot[]>) oisReq.readObject();
                                if (map != null) {
                                    ((MatelotController) ctrl).equipeInview.getAmiral().setAssignations(map);
                                    System.out.println("got new assignations");
                                }
                            }
                            break;
                        case "updateBateau":
                            Bateau bateau = (Bateau) oisReq.readObject();
                            if (bateau != null) {
                                if (ctrl instanceof AmiralController) {
                                    Bateau toremove = ((AmiralController) ctrl).equipeInView.getBateauToRemoveByName(bateau.getNomNavire());
                                    ((AmiralController) ctrl).equipeInView.getBateauxEquipe().remove(toremove);
                                    ((AmiralController) ctrl).equipeInView.getBateauxEquipe().add(bateau);
                                } else {
                                    Bateau toremove = ((MatelotController) ctrl).equipeInview.getBateauToRemoveByName(bateau.getNomNavire());
                                    ((MatelotController) ctrl).equipeInview.getBateauxEquipe().remove(toremove);
                                    ((MatelotController) ctrl).equipeInview.getBateauxEquipe().add(bateau);
                                }
                                System.out.println("maj bateau : " + bateau.getNomNavire());
                            }
                            break;
                        case "isShooted":
                            int isShoted = oisReq.readInt();
                            String id = (String) oisReq.readObject();
                            boolean equipeAllie = oisReq.readBoolean();
                            if (isShoted != 0) {
                                if (ctrl instanceof AmiralController) {
                                    ((AmiralController) ctrl).updateShotted(isShoted,id);
                                } else {
                                    if (equipeAllie){
                                        ((MatelotController) ctrl).updateShotted(isShoted,"");

                                    }else{
                                        ((MatelotController) ctrl).updateShotted(isShoted,id);
                                    }
                                }
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
