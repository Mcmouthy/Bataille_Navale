package Model.Game;

import java.io.Serializable;

public abstract class Joueur implements Serializable {
    private String pseudo;

    Joueur(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
