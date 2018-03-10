package Model.Game;

import java.util.List;

class Equipe {
    private List<Joueur> lesJoueurs;
    private boolean pret = false;
    private boolean abandon = false;
    private List<Bateau> bateauxEquipe;

    public Equipe(List<Joueur> lesJoueurs, boolean pret, boolean abandon, List<Bateau> bateauxEquipe) {
        this.lesJoueurs = lesJoueurs;
        this.pret = pret;
        this.abandon = abandon;
        this.bateauxEquipe = bateauxEquipe;
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

    public List<Bateau> getBateauxEquipe() {
        return bateauxEquipe;
    }

    public void setBateauxEquipe(List<Bateau> bateauxEquipe) {
        this.bateauxEquipe = bateauxEquipe;
    }

    public void addJoueur(Joueur joueur) {
    }

    public void addBateau(Bateau bateau) {
    }
}
