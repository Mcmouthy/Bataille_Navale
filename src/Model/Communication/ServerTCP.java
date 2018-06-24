package Model.Communication;

import Model.Game.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerTCP {
    ServerSocket conn;
    List<Socket> listeSocket;
    List<ServeurThread> listThread;
    Partie partie;
    public ServerTCP(int serverPort,String ip) throws IOException {
        conn = new ServerSocket(serverPort,50,InetAddress.getByName(ip));
        listeSocket = new ArrayList<>();
        listThread = new ArrayList<>();
        partie=new Partie(new Equipe(),new Equipe());

    }

    private void generateAllShip() {
        Bateau s1= new Bateau("S1",false,true,1);
        Bateau s2= new Bateau("S2",false,true,1);
        Bateau s3= new Bateau("S3",false,true,1);
        Bateau s4= new Bateau("S4",false,true,1);
        Bateau t1= new Bateau("T1",false,true,2);
        Bateau t2= new Bateau("T2",false,true,2);
        Bateau t3= new Bateau("T3",false,true,2);
        Bateau c1= new Bateau("C1",false,true,3);
        Bateau c2= new Bateau("C2",false,true,3);
        Bateau cu= new Bateau("Cu",false,true,4);
        Bateau s1b= new Bateau("S1",false,true,1);
        Bateau s2b= new Bateau("S2",false,true,1);
        Bateau s3b= new Bateau("S3",false,true,1);
        Bateau s4b= new Bateau("S4",false,true,1);
        Bateau t1b= new Bateau("T1",false,true,2);
        Bateau t2b= new Bateau("T2",false,true,2);
        Bateau t3b= new Bateau("T3",false,true,2);
        Bateau c1b= new Bateau("C1",false,true,3);
        Bateau c2b= new Bateau("C2",false,true,3);
        Bateau cub= new Bateau("Cu",false,true,4);
        partie.getEquipeA().setBateauxEquipe(Arrays.asList( new Bateau[]{s1,s2,s3,s4,t1,t2,t3,c1,c2,cu}));
        partie.getEquipeB().setBateauxEquipe(Arrays.asList(new Bateau[]{s1b,s2b,s3b,s4b,t1b,t2b,t3b,c1b,c2b,cub}));
        partie.getEquipeA().setaPlacer(partie.getEquipeA().getBateauxEquipe().get(0));
        partie.getEquipeB().setaPlacer(partie.getEquipeB().getBateauxEquipe().get(0));
    }

    public void mainloop() throws IOException {
        generateAllShip();
        partie.getEquipeA().getPlateau().initPlateau();
        partie.getEquipeB().getPlateau().initPlateau();
        while (true) {
            Socket s=conn.accept();
            listeSocket.add(s);
            ServeurThread newThread =new ServeurThread(listeSocket.size()-1,s,partie,this);
            newThread.start();
            listThread.add(newThread);

        }
    }

    public synchronized void updateAllThread(String equipe,List<Bateau> list){
        for (ServeurThread s: listThread) {
            try {
                s.oosReq.writeObject("update");
                s.oosReq.flush();
                if (equipe.equals("A")){
                    if (s.game.getEquipeA().getLesJoueurs().contains(s.myPlayer)){
                        s.oosReq.writeObject(list);
                        s.oosReq.flush();
                    }
                    else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }
                else{
                    if (s.game.getEquipeB().getLesJoueurs().contains(s.myPlayer)){
                        s.oosReq.writeObject(list);
                        s.oosReq.flush();
                    }
                    else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public synchronized void updateAllThreadJoueur(Joueur toAdd, String equipe){
        for (ServeurThread s: listThread) {
            try {
                s.oosReq.writeObject("updateJoueur");
                s.oosReq.flush();
                if (equipe.equals("A")){
                    if(s.game.getEquipeA().getLesJoueurs().contains(s.myPlayer)){
                        s.oosReq.writeObject(toAdd);
                        s.oosReq.flush();
                    }else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }else{
                    if (s.game.getEquipeB().getLesJoueurs().contains(s.myPlayer)){
                        s.oosReq.writeObject(toAdd);
                        s.oosReq.flush();
                    }else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void updateAllThreadAssignations(String equipe, TreeMap<Bateau, Matelot[]> newAssignations)
    {
        for (ServeurThread s: listThread)
        {
            try {
                s.oosReq.writeObject("updateAmiralAssignation");
                s.oosReq.flush();
                if (equipe.equals("A")){
                    if(s.game.getEquipeA().getLesJoueurs().contains(s.myPlayer)){
                        if (s.myPlayer instanceof Matelot)
                        {
                            s.oosReq.writeObject(newAssignations);
                            s.oosReq.flush();
                        }
                    }else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }else{
                    if(s.game.getEquipeB().getLesJoueurs().contains(s.myPlayer)){
                        if (s.myPlayer instanceof Matelot)
                        {
                            s.oosReq.writeObject(newAssignations);
                            s.oosReq.flush();
                        }
                    }else{
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Sended new Assignations");

    }

    public synchronized void updateBateauThread(String equipe, Bateau bateauPotentiel) {
        for (ServeurThread s : listThread) {
            try {
                s.oosReq.writeObject("updateBateau");
                s.oosReq.flush();
                if (equipe.equals("A")) {
                    if (s.game.getEquipeA().getLesJoueurs().contains(s.myPlayer)) {
                        s.oosReq.writeObject(bateauPotentiel);
                        s.oosReq.flush();
                    } else {
                        s.oosReq.writeObject(null);
                        s.oosReq.flush();
                    }
                } else {
                    if (s.game.getEquipeB().getLesJoueurs().contains(s.myPlayer)) {
                        if (s.myPlayer instanceof Matelot) {
                            s.oosReq.writeObject(bateauPotentiel);
                            s.oosReq.flush();
                        }

                    } else {
                        if (s.myPlayer instanceof Matelot) {
                            s.oosReq.writeObject(null);
                            s.oosReq.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void isShottedShip(String equipe, int isTouched, String positionShooted) {
        for (ServeurThread s : listThread) {
            try {
                s.oosReq.writeObject("isShooted");
                s.oosReq.flush();

                s.oosReq.writeInt(isTouched);
                s.oosReq.flush();
                s.oosReq.writeObject(positionShooted);
                s.oosReq.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
