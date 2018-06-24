package Model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipe implements Serializable {
    private Amiral amiral;
    private List<Joueur> lesJoueurs;
    private boolean placementBateaux=true;
    private boolean pret = false;
    private boolean abandon = false;
    private Plateau plateau;
    private List<Bateau> bateauxEquipe;
    private Bateau aPlacer;

    public Equipe(List<Joueur> lesJoueurs, boolean pret, boolean abandon, List<Bateau> bateauxEquipe) {
        this.lesJoueurs = lesJoueurs;
        this.pret = pret;
        this.abandon = abandon;
        this.bateauxEquipe = bateauxEquipe;
        this.aPlacer = bateauxEquipe.get(0);
        this.plateau = new Plateau();
    }

    public Equipe() {
        this.lesJoueurs = new ArrayList<>();
        this.placementBateaux = true;
        this.pret = true;
        this.abandon = false;
        this.bateauxEquipe = new ArrayList<>();
        this.plateau = new Plateau();
    }

    public Equipe(Amiral amiral, List<Joueur> lesJoueurs) {
        this.amiral= amiral;
        this.lesJoueurs = lesJoueurs;
        this.plateau = new Plateau();
        this.bateauxEquipe = new ArrayList<>();
    }

    public Equipe(Amiral amiral,List<Joueur> lesJoueurs, Plateau plateau) {
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

    public Bateau getBateauByPosition(String position)
    {
        for (Bateau b:bateauxEquipe)
        {
            for (Case c:b.getPositions())
            {
                if ((c.getX()+"#"+c.getY()).equals(position)){
                    return b;
                }
            }
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
                try{
                    if (bateauxEquipe.get(i+1)!=null)return i+1;
                }catch (ArrayIndexOutOfBoundsException e){
                    return -1;
                }

            }
        }
        return -1;
    }

    public Joueur getMatelotByName(String name)
    {
        for (Joueur m: lesJoueurs)
        {
            if (m.getPseudo().equals(name)) return m;
        }
        return null;
    }

    public Bateau getBateauToRemoveByName(String name){
        for (Bateau b : bateauxEquipe){
            if (b.getNomNavire().equals(name)){
                return b;
            }
        }
        return null;
    }
}
