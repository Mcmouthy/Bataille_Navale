package Model.Game;

import java.util.List;

public class Attaquant extends Matelot {
    public Attaquant(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo, bateauxAssignes);
    }

    public boolean tirer(Case lacase) {
        return false;
    }
}
