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
    private Bateau aPlacer;

    public Equipe(List<Matelot> lesJoueurs, boolean pret, boolean abandon, List<Bateau> bateauxEquipe) {
        this.lesJoueurs = lesJoueurs;
        this.pret = pret;
        this.abandon = abandon;
        this.bateauxEquipe = bateauxEquipe;
        this.aPlacer = bateauxEquipe.get(0);
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

    public boolean isPlacementBateaux() {
        return placementBateaux;
    }

    public void setPlacementBateaux(boolean placementBateaux) {
        this.placementBateaux = placementBateaux;
    }

    public Bateau getaPlacer() {
        return aPlacer;
    }

    public void setaPlacer(Bateau aPlacer) {
        this.aPlacer = aPlacer;
    }

    public Bateau getBateauByName(String name){
        for (Bateau b : bateauxEquipe)
        {
            if (b.getNomNavire().equals(name))return b;
        }
        return null;
    }

    public String toString(){
        String str = "";
        str+= amiral.getPseudo()+" \n";
        for (Bateau b: bateauxEquipe) {
            str += b.toString()+" \n";
        }
        return str;
    }

    public int getNextPlacement() {
        Bateau b = getBateauByName(aPlacer.getNomNavire());
        for (int i=0; i<bateauxEquipe.size();i++)
        {
            if (bateauxEquipe.get(i).equals(b)){
                if (bateauxEquipe.get(i+1)==null)return -1;
                else return i+1;
            }
        }
        return -1;
    }
}
