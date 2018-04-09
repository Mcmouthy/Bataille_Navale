package Model.Game;

import Model.Exception.NotDeplacableException;
import Model.Exception.UnAuthorizeDeplacementException;

public class Bateau {
    private int taille;
    private Case[] positions;
    private boolean recharge;
    private boolean deplacable = true;
    private String nomNavire;

    public Bateau(String nomNavire,Case[] positions, boolean recharge, int taille)
    {
        this.nomNavire = nomNavire;
        this.positions = positions;
        this.recharge = recharge;
        this.taille = taille;
    }

    public Bateau(String nomNavire,Case[] positions, boolean recharge,boolean deplacable, int taille)
    {
        this.nomNavire = nomNavire;
        this.positions = positions;
        this.recharge = recharge;
        this.deplacable = deplacable;
        this.taille = taille;
    }

    public Bateau(String nomNavire,boolean recharge,boolean deplacable,int taille){
        this.nomNavire = nomNavire;
        this.recharge = recharge;
        this.deplacable = deplacable;
        this.taille = taille;
    }

    public Case[] getPositions() {
        return positions;
    }

    public void setPositions(Case[] positions) {
        this.positions = positions;
    }

    public boolean isRecharge() {
        return recharge;
    }

    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }

    public boolean isDeplacable() {
        return deplacable;
    }

    public void setDeplacable(boolean deplacable) {
        this.deplacable = deplacable;
    }

    public String getNomNavire() {
        return nomNavire;
    }

    public void setNomNavire(String nomNavire) {
        this.nomNavire = nomNavire;
    }

    public void deplacementHorizontal(int deplace){
        for (int i=0; i<positions.length;i++){
            positions[i].setX(positions[i].getX()+deplace);
        }

    }

    public void deplacementVertical(int deplace){
        for (int i=0; i<positions.length;i++){
            positions[i].setY(positions[i].getY()+deplace);
        }
    }

    public String toString(){
        return getNomNavire();
    }

    public int getTailleNavire() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    /*MOVE TO CONTROLLER*/
//    public boolean checkAuthorizedDeplacement(Case caseDepart, Case caseArrivee) throws NotDeplacableException,UnAuthorizeDeplacementException
//    {
//        if (deplacable){
//            /*si le premier sur le bateau et deuxieme sur autre chose que bateau -> continue*/
//            if (checkDepartureCase(caseDepart) && !checkArrivingCase(caseArrivee)){
//
//                return true;
//            }else{
//                return false;
//            }
//
//        }else{
//            throw new NotDeplacableException();
//        }
//
//    }
//
//    /*
//     * return true if caseDepart is in positions array
//     */
//    private boolean checkDepartureCase(Case caseDepart) {
//        if (checkCaseInPositions(positions,caseDepart)){
//            return true;
//        }
//        return false;
//    }
//
//    private boolean checkArrivingCase(Case caseArrivee) {
//        if (checkCaseInPositions(positions,caseArrivee)){
//            return true;
//        }
//        return false;
//    }
//
//    /*
//    * return true if case in positions
//    */
//    private boolean checkCaseInPositions(Case[] positions, Case caseDepart) {
//        for (Case c:positions)
//        {
//            if (caseDepart.equals(c))return true;
//        }
//        return false;
//    }

}
