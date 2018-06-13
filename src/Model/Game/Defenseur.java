package Model.Game;

import java.util.List;

public class Defenseur extends Matelot {

    static final boolean horizontal = false;
    static final boolean vertical = true;
    public Defenseur(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo, bateauxAssignes);
    }

    public void deplacement(Bateau bateau, boolean type, int deplace)
    {
        if (type==horizontal){
            bateau.deplacementHorizontal(deplace);
        }else{
            bateau.deplacementVertical(deplace);
        }

    }

    @Override
    public boolean isDefenseur() {
        return true;
    }

    @Override
    public String returnType() {
        return "Defenseur";
    }
}
