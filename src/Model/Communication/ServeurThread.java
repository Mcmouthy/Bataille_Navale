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
            System.out.println(id + " - client disconnected");
        }
    }

    private void initLoop() throws IOException, ClassNotFoundException {
        /* A COMPLETER :
	   faire une boucle qui s'arrête dès que la chaine reçu n'indique pas le pseudo du joueur

	    */
        while (true){
            String nom =oisReq.readObject().toString();
            if (game.getEquipeA().getLesJoueurs().size()==0)
            {
                Joueur j=new Amiral(nom);
                game.getEquipeA().setAmiral((Amiral)j);
                oosReq.writeObject("amiral");
                oosReq.flush();
                oosReq.writeObject(game);
                oosReq.flush();
                oosReq.writeObject(game.getEquipeA());
                oosReq.flush();
                break;
            }else if (game.getEquipeB().getLesJoueurs().size()==0){
                Joueur j=new Amiral(nom);
                game.getEquipeB().setAmiral((Amiral)j);
                oosReq.writeObject("amiral");
                oosReq.writeObject(game);
                oosReq.writeObject(game.getEquipeB());
                break;
            }else {
                Joueur j=new Matelot(nom);
                if (game.getEquipeA().getLesJoueurs().size() == game.getEquipeB().getLesJoueurs().size())
                {
                    if (game.getEquipeA().getLesJoueurs().contains(j)){
                        j.setPseudo(j.getPseudo()+""+game.getEquipeA().getLesJoueurs().indexOf(j));
                    }
                    game.getEquipeA().getLesJoueurs().add((Matelot)j);
                    oosReq.writeObject("matelot");
                    oosReq.writeObject(game);
                    oosReq.writeObject(game.getEquipeA());
                    oosReq.writeObject(j);
                    break;
                }else{
                    if (game.getEquipeA().getLesJoueurs().size()<game.getEquipeB().getLesJoueurs().size())
                    {
                        j.setPseudo(j.getPseudo()+""+game.getEquipeA().getLesJoueurs().indexOf(j));
                        game.getEquipeA().getLesJoueurs().add((Matelot)j);
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeA());
                        oosReq.writeObject(j);
                    }else{
                        j.setPseudo(j.getPseudo()+""+game.getEquipeB().getLesJoueurs().indexOf(j));
                        game.getEquipeB().getLesJoueurs().add((Matelot)j);
                        oosReq.writeObject("matelot");
                        oosReq.writeObject(game);
                        oosReq.writeObject(game.getEquipeB());
                        oosReq.writeObject(j);
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
