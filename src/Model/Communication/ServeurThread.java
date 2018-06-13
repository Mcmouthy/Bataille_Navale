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
            e.printStackTrace();
        }
    }

    private void initLoop() throws IOException, ClassNotFoundException {
        /* A COMPLETER :
	   faire une boucle qui s'arrête dès que la chaine reçu n'indique pas le pseudo du joueur

	    */
        while (true){
            String nom =oisReq.readObject().toString();
            System.out.println(game.getEquipeA().getLesJoueurs().size());
            System.out.println(game.getEquipeB().getLesJoueurs().size());
            if (game.getEquipeA().getLesJoueurs().size()==0)
            {
                myPlayer=new Amiral(nom);
                game.getEquipeA().setAmiral((Amiral)myPlayer);
                game.getEquipeA().getLesJoueurs().add(myPlayer);
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
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeA());
                        oosReq.writeObject(myPlayer);
                    }else{
                        myPlayer.setPseudo(myPlayer.getPseudo()+""+game.getEquipeB().getLesJoueurs().indexOf(myPlayer));
                        game.getEquipeB().getLesJoueurs().add(myPlayer);
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeB());
                        oosReq.writeObject(myPlayer);
                    }
                    break;
                }
            }
        }
        requestLoop();
    }

    private void requestLoop() throws IOException, ClassNotFoundException {
        while (true){
            Object o = oisReq.readObject();
            System.out.println(o.toString());
        }
    }
}
