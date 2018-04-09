package Model.Game;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private Amiral amiral;
    private List<Matelot> lesJoueurs;
    private boolean placementBateaux=true;
    private boolean pret = false;
    private boolean abandon = false;
    private Plateau plateau;
    private List<Bateau> bateauxEquipe;

    public Equipe(List<Matelot> lesJoueurs, boolean pret, boolean abandon, List<Bateau> bateauxEquipe) {
        this.lesJoueurs = lesJoueurs;
        this.pret = pret;
        this.abandon = abandon;
        this.bateauxEquipe = bateauxEquipe;
    }

    public Equipe(Amiral amiral,List<Matelot> lesJoueurs) {
        this.amiral= amiral;
        this.lesJoueurs = lesJoueurs;
        this.plateau = new Plateau();
        this.bateauxEquipe = new ArrayList<>();
    }

    public Equipe(Amiral amiral,List<Matelot> lesJoueurs, Plateau plateau) {
        this.amiral= amiral;
        this.lesJoueurs = lesJoueurs;
        this.plateau = plateau;
        this.bateauxEquipe = new ArrayList<>();
    }


    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public List<Matelot> getLesJoueurs() {
        return lesJoueurs;
    }

    public void setLesJoueurs(List<Matelot> lesJoueurs) {
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

    public Amiral getAmiral() {
        return amiral;
    }

    public void setAmiral(Amiral amiral) {
        this.amiral = amiral;
    }

    public void addJoueur(Matelot joueur) {
        lesJoueurs.add(joueur);
    }

    public void addBateau(Bateau bateau) {
        bateauxEquipe.add(bateau);
    }

    public String toString(){
        String str = "";
        str+= amiral.getPseudo()+" \n";
        for (Bateau b: bateauxEquipe) {
            str += b.toString()+" \n";
        }
        return str;
    }
}
