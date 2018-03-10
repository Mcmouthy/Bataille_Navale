package Model.Game;

import java.util.List;

public class Defenseur extends Matelot {
    public Defenseur(String pseudo, List<Bateau> bateauxAssignes) {
        super(pseudo, bateauxAssignes);
    }

    public boolean deplacement(Case caseDepart, Case caseArrivee) {
        return false;
    }
}
