package Model.Game;

import java.util.List;

class Equipe {
    private List<Joueur> lesJoueurs;
    private boolean pret = false;
    private boolean abandon = false;

    public Equipe(List<Joueur> lesJoueurs, boolean pret, boolean abandon) {
        this.lesJoueurs = lesJoueurs;
        this.pret = pret;
        this.abandon = abandon;
    }

    public Equipe(List<Joueur> lesJoueurs) {
        this.lesJoueurs = lesJoueurs;
    }

    public List<Joueur> getLesJoueurs() {
        return lesJoueurs;
    }

    public void setLesJoueurs(List<Joueur> lesJoueurs) {
        this.lesJoueurs = lesJoueurs;
    }

    public boolean isPret() {
        return pret;
    }

    public void setPret(boolean pret) {
        this.pret = pret;
    }

    public boolean isAbandon() {
        return abandon;
    }

    public void setAbandon(boolean abandon) {
        this.abandon = abandon;
    }

    public void addJoueur(Joueur joueur) {
    }
}
