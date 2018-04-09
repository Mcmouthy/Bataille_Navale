package Model.Game;

import java.util.List;

public class Matelot extends Joueur {
    private List<Bateau> bateauxAssignes;

    public Matelot(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo);
        this.bateauxAssignes = bateauxAssignes;
    }

    public List<Bateau> getBateauxAssignes() {
        return bateauxAssignes;
    }

    public void setBateauxAssignes(List<Bateau> bateauxAssignes) {
        this.bateauxAssignes = bateauxAssignes;
    }

    public void addBateau(Bateau bateau) {
        bateauxAssignes.add(bateau);
    }


    public void removeBateau(Bateau bateau) {
        bateauxAssignes.remove(bateau);
    }

    public String getTypeMatelot(){return "";}
}

