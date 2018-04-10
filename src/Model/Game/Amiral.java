package Model.Game;

import Model.Exception.AlreadyAssignedToAttPostException;
import Model.Exception.AlreadyAssignedToDefPostException;
import Model.Exception.AlreadyAssignedToOtherMatelotException;
import Model.Exception.NoPlaceAvailableOnShipException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
    public static final int ATT = 0;
    public static final int DEF =1;
    private List<Bateau> lesBateaux;
    private HashMap<Bateau, Matelot[]> assignations;

    public Amiral(String pseudo, List<Bateau> lesBateaux, HashMap<Bateau, Matelot[]> assignations) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
        this.assignations = assignations;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
        this.assignations = new HashMap<>();
    }

    public HashMap<Bateau, Matelot[]> getAssignations() {
        return assignations;
    }

    public void setAssignations(HashMap<Bateau, Matelot[]> assignations) {
        this.assignations = assignations;
    }

    public List<Bateau> getLesBateaux() {
        return lesBateaux;
    }

    public void setLesBateaux(List<Bateau> lesBateaux) {
        this.lesBateaux = lesBateaux;
    }

    public void addAssignation(Matelot matelot, Bateau bateau,int poste) throws NoPlaceAvailableOnShipException {
        if (!assignations.containsKey(bateau)){
            assignations.put(bateau,new Matelot[2]);
            assignations.get(bateau)[poste]=matelot;
        }else{
            try {
                if (!checkMatelotBeforeAddition(assignations.get(bateau),matelot,poste)){
                    //ADD matelot
                    addMatelotsInArray(assignations.get(bateau),matelot,poste);
                    matelot.addBateau(bateau);
                }
            } catch (AlreadyAssignedToAttPostException | AlreadyAssignedToOtherMatelotException | AlreadyAssignedToDefPostException e) {
                e.getMessage();
            }
        }
    }

    private void addMatelotsInArray(Matelot[] matelots, Matelot matelot, int poste)throws NoPlaceAvailableOnShipException {
        if (matelots!=null){

            if (matelots[poste]==null){
                matelots[poste]=matelot;
                return;
            }
            throw new NoPlaceAvailableOnShipException();
        }
    }

    private boolean checkMatelotBeforeAddition(Matelot[] matelots, Matelot matelot, int poste) throws AlreadyAssignedToAttPostException, AlreadyAssignedToDefPostException, AlreadyAssignedToOtherMatelotException {
        if (matelots[poste]==null){//if place not already used
            if (poste == ATT){ // if matelot is already in deff place
                if (matelots[DEF].equals(matelot)) throw new AlreadyAssignedToAttPostException();
            }else{// if already in att place
                if (matelots[ATT].equals(matelot)) throw new AlreadyAssignedToDefPostException();
            }
            return false;
        }throw new AlreadyAssignedToOtherMatelotException();
    }

    private void removeMatelotsInArray(Matelot[] matelots, Matelot matelot, int poste) {

        if (matelots[poste].equals(matelot)){
            matelots[poste]=null;
            return;
        }

    }
    public boolean removeAssignation(Matelot matelot, Bateau bateau,int poste) {
        if (assignations.containsKey(bateau)){

            if (assignations.get(bateau)[poste].equals(matelot)){
                //REMOVE matelot
                removeMatelotsInArray(assignations.get(bateau), matelot,poste);
                matelot.removeBateau(bateau);
                return true;
            }
        }
        return false;
    }

    /*public boolean changeAssignation(Matelot matelot, Bateau bateauOld, Bateau bateauNew,int poste) throws NoPlaceAvailableOnShipException {
        if (assignations.containsKey(bateauOld)){

                if (assignations.get(bateauOld)[ATT].equals(matelot) || assignations.get(bateauOld)[DEF].equals(matelot) )){
                    //REMOVE bateauOLD
                    removeMatelotsInArray(assignations.get(bateauOld), matelot);
                    matelot.removeBateau(bateauOld);

                    //ADD bateauNew
                    addMatelotsInArray(assignations.get(bateauNew),matelot, );
                    matelot.addBateau(bateauNew);

                    return true;
                }

        }
        return false;
    }*/
}
