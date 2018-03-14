package Model.Game;

import Model.Exception.CantShootException;

import java.util.List;

public class Attaquant extends Matelot {
    public Attaquant(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo, bateauxAssignes);
    }

    public boolean tirer(Bateau bateau,Case lacase) throws CantShootException {
        if (getBateauxAssignes().contains(bateau)){
            if (bateau.isRecharge()){
                throw new CantShootException();
            }
            return true;
        }
        return false;
    }
}
