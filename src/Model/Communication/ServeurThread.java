package Model.Communication;

import Model.Game.Amiral;
import Model.Game.Joueur;
import Model.Game.Matelot;
import Model.Game.Partie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServeurThread extends Thread {
    Socket commReq;
    ObjectInputStream oisReq;
    ObjectOutputStream oosReq;
    int id;
    Partie game;
    Joueur myPlayer; // l'objet player associé à ce thread

    public ServeurThread(int id, Socket s, Partie partie) {
        this.id = id;
        this.commReq = s;
        this.game = partie;
        myPlayer = null;
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
            e.getMessage();
            e.getCause();
            e.printStackTrace();
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
                    }else{
                        myPlayer.setPseudo(myPlayer.getPseudo()+""+game.getEquipeB().getLesJoueurs().indexOf(myPlayer));
                        game.getEquipeB().getLesJoueurs().add(myPlayer);
                        System.out.println(nom+" added to Team B");
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeB());
                        oosReq.writeObject(myPlayer);
                    }
                    break;
                }
            }
        }

    }

    private void requestLoop() throws IOException, ClassNotFoundException {
        while (true) switch (oisReq.readInt()) {
            case -2:
                abandon();
                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    public synchronized void abandon() {
        if (game.getEquipeA().getLesJoueurs().contains(myPlayer)){
            game.getEquipeA().setAbandon(true);
            System.out.println("equipe A abandonne");
        }else{
            game.getEquipeB().setAbandon(true);
            System.out.println("equipe B abandonne");
        }
    }
}
