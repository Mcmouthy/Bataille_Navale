package Model.Game;

import java.util.List;

public class Matelot extends Joueur {
    private List<Bateau> bateauxAssignes;

    public Matelot(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo);
        this.bateauxAssignes = bateauxAssignes;
    }

    public Matelot(String pseudo) {
        super(pseudo);
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


    @Override
    public boolean isMatelot() {
        return true;
    }

    @Override
    public boolean isAttaquant() {
        return false;
    }

    @Override
    public boolean isDefenseur() {
        return false;
    }

    @Override
    public String returnType() {
        return "Matelot";
    }
}

