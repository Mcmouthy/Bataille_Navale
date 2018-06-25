package Model.Communication;

import Model.Game.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.TreeMap;

public class ServeurThread extends Thread {
    Socket commReq;
    public ObjectInputStream oisReq;
    public ObjectOutputStream oosReq;
    public ServerTCP serverTcp;
    int id;
    Partie game;
    Joueur myPlayer; // l'objet player associé à ce thread

    public ServeurThread(int id, Socket s, Partie partie,ServerTCP tcp) {
        this.id = id;
        this.commReq = s;
        this.game = partie;
        myPlayer = null;
        serverTcp = tcp;
    }


    public void run() {

        try {
            oisReq = new ObjectInputStream(commReq.getInputStream());
            oosReq = new ObjectOutputStream(commReq.getOutputStream());
            initLoop();
            requestLoop();


        } catch (Exception e) {
            System.out.println(myPlayer.getPseudo() + " - client disconnected");
            if (game.getEquipeA().getLesJoueurs().contains(myPlayer))
            {
                game.getEquipeA().getLesJoueurs().remove(myPlayer);
            }else
            {
                game.getEquipeB().getLesJoueurs().remove(myPlayer);
            }
        }
    }

    private void initLoop() throws IOException, ClassNotFoundException {
        /* A COMPLETER :
	   faire une boucle qui s'arrête dès que la chaine reçu n'indique pas le pseudo du joueur

	    */
        while (true){
            String nom =oisReq.readObject().toString();
            System.out.println(nom+" - connected ");
            if (game.getEquipeA().getLesJoueurs().size()==0)
            {
                myPlayer=new Amiral(nom);
                game.getEquipeA().setAmiral((Amiral)myPlayer);
                game.getEquipeA().getLesJoueurs().add(myPlayer);
                System.out.println(nom+" added to Team A");
                System.out.println(nom+" amiral of Team A");
                oosReq.writeObject("amiral");
                oosReq.flush();
                oosReq.writeObject(game);
                oosReq.flush();
                oosReq.writeObject(game.getEquipeA());
                oosReq.flush();
                //serverTcp.updateAllThreadJoueur(myPlayer,"A");
                break;
            }else if (game.getEquipeB().getLesJoueurs().size()==0){
                myPlayer=new Amiral(nom);
                game.getEquipeB().setAmiral((Amiral)myPlayer);
                game.getEquipeB().getLesJoueurs().add(myPlayer);
                System.out.println(nom+" added to Team B");
                System.out.println(nom+" amiral of Team B");
                oosReq.writeObject("amiral");
                oosReq.writeObject(game);
                oosReq.writeObject(game.getEquipeB());
                //serverTcp.updateAllThreadJoueur(myPlayer,"B");
                break;
            }else {
                myPlayer=new Matelot(nom);
                if (game.getEquipeA().getLesJoueurs().size() == game.getEquipeB().getLesJoueurs().size())
                {
                    if (game.getEquipeA().getLesJoueurs().contains(myPlayer)){
                        myPlayer.setPseudo(myPlayer.getPseudo()+""+game.getEquipeA().getLesJoueurs().indexOf(myPlayer));
                    }
                    game.getEquipeA().getLesJoueurs().add(myPlayer);
                    System.out.println(nom+" added to Team A");
                    oosReq.writeObject("matelot");
                    oosReq.writeObject(game);
                    oosReq.writeObject(game.getEquipeA());
                    oosReq.writeObject(myPlayer);
                    serverTcp.updateAllThreadJoueur(myPlayer,"A");
                    break;
                }else{
                    if (game.getEquipeA().getLesJoueurs().size()<game.getEquipeB().getLesJoueurs().size())
                    {
                        myPlayer.setPseudo(myPlayer.getPseudo()+""+game.getEquipeA().getLesJoueurs().indexOf(myPlayer));
                        game.getEquipeA().getLesJoueurs().add(myPlayer);
                        System.out.println(nom+" added to Team A");
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeA());
                        oosReq.writeObject(myPlayer);
                        serverTcp.updateAllThreadJoueur(myPlayer,"A");
                    }else{
                        myPlayer.setPseudo(myPlayer.getPseudo()+""+game.getEquipeB().getLesJoueurs().indexOf(myPlayer));
                        game.getEquipeB().getLesJoueurs().add(myPlayer);
                        System.out.println(nom+" added to Team B");
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeB());
                        oosReq.writeObject(myPlayer);
                        serverTcp.updateAllThreadJoueur(myPlayer,"B");
                    }
                    break;
                }
            }
        }

    }

    private void requestLoop() throws IOException, ClassNotFoundException {

        while (true){
            int valueOfFunction = oisReq.readInt();
            System.out.println("reçu : "+valueOfFunction);
            switch (valueOfFunction){
                case -2:
                    abandon();
                    break;
                case 1:
                    //engager le combat
                    if (game.getEquipeA().getLesJoueurs().contains(myPlayer))
                    {
                        game.getEquipeA().setPret(true);
                        List<Bateau> list = (List<Bateau>)oisReq.readObject();
                        game.getEquipeA().setBateauxEquipe(list);
                        serverTcp.partie.getEquipeA().setBateauxEquipe(list);
                        System.out.println(myPlayer.getPseudo()+" got it");
                        serverTcp.updateAllThread("A",list);
                    }else{
                        game.getEquipeB().setPret(true);
                        List<Bateau> list = (List<Bateau>)oisReq.readObject();
                        game.getEquipeB().setBateauxEquipe(list);
                        serverTcp.partie.getEquipeB().setBateauxEquipe(list);
                        System.out.println(game.getEquipeB().getBateauxEquipe().get(0).toString());
                        System.out.println(myPlayer.getPseudo()+" got it");
                        serverTcp.updateAllThread("B",list);
                    }
                    break;
                case 2:
                    //tirer
                    System.out.println("shout");
                    if (game.getEquipeA().getLesJoueurs().contains(myPlayer))
                    {
                        String positionShooted = (String)oisReq.readObject();
                        Bateau bateauPotentiel =serverTcp.partie.getEquipeB().getBateauByPosition(positionShooted);
                        if (bateauPotentiel == null)
                        {
                            //y a rien du tout la ou t'as tire
                            serverTcp.isShottedShip("A",-1,positionShooted,"B");
                        }else{
                            //on a touche
                            System.out.println("touche");
                            bateauPotentiel.getPositionById(positionShooted).setState(Etat.TOUCHE);
                            System.out.println("update en cours");
                            serverTcp.isShottedShip("A",1,positionShooted,"B");

                        }

                    }else{
                        String positionShooted = (String)oisReq.readObject();
                        Bateau bateauPotentiel =serverTcp.partie.getEquipeA().getBateauByPosition(positionShooted);
                        if (bateauPotentiel == null)
                        {
                            //y a rien du tout la ou t'as tire
                            serverTcp.isShottedShip("B",-1, positionShooted,"A");
                        }else{
                            //on a touche
                            System.out.println("touche");
                            bateauPotentiel.getPositionById(positionShooted).setState(Etat.TOUCHE);
                            System.out.println("update en cours");
                            serverTcp.isShottedShip("B",1, positionShooted,"A");

                        }
                    }
                    break;
                case 3:
                    //deplacer
                    break;
                case 4:
                    //update les assignations
                    TreeMap<Bateau,Matelot[]> newAssignations = (TreeMap<Bateau, Matelot[]>) oisReq.readObject();
                    if (game.getEquipeA().getLesJoueurs().contains(myPlayer))
                    {
                        serverTcp.updateAllThreadAssignations("A",newAssignations);
                    }else{
                        serverTcp.updateAllThreadAssignations("B",newAssignations);
                    }
                    break;
            }
        }
    }

    public synchronized void abandon() {
        if (game.getEquipeA().getLesJoueurs().contains(myPlayer)){
            game.getEquipeA().setAbandon(true);
            System.out.println("equipe A abandonne");
            try {
                commReq.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            game.getEquipeB().setAbandon(true);
            System.out.println("equipe B abandonne");
        }
    }
}
