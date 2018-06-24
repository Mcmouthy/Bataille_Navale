package Model.Game;

import Model.Exception.AlreadyAssignedToAttPostException;
import Model.Exception.AlreadyAssignedToDefPostException;
import Model.Exception.AlreadyAssignedToOtherMatelotException;
import Model.Exception.NoPlaceAvailableOnShipException;

import java.util.*;

public class Amiral extends Joueur {
    public static final int ATT = 0;
    public static final int DEF =1;
    private List<Bateau> lesBateaux;
    private TreeMap<Bateau, Matelot[]> assignations;

    public Amiral(String pseudo, List<Bateau> lesBateaux, TreeMap<Bateau, Matelot[]> assignations) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
        this.assignations = assignations;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
        this.assignations = new TreeMap<Bateau,Matelot[]>();
    }

    @Override
    public boolean isMatelot() {
        return false;
    }

    @Override
    public boolean isAttaquant() {
        return false;
    }

    @Override
    public boolean isDefenseur() {
        return false;
    }

    @Override
    public String returnType() {
        return "amiral";
    }

    public Map<Bateau, Matelot[]> getAssignations() {
        return assignations;
    }

    public void setAssignations(TreeMap<Bateau, Matelot[]> assignations) {
        this.assignations = assignations;
    }

    public List<Bateau> getLesBateaux() {
        return lesBateaux;
    }

    public void setLesBateaux(List<Bateau> lesBateaux) {
        this.lesBateaux = lesBateaux;
    }

    public void addAssignation(Matelot matelot, Bateau bateau,int poste) throws NoPlaceAvailableOnShipException, AlreadyAssignedToDefPostException, AlreadyAssignedToOtherMatelotException, AlreadyAssignedToAttPostException {
        if (!assignations.containsKey(bateau)){
            assignations.put(bateau,new Matelot[2]);
            assignations.get(bateau)[poste]=matelot;
        }else{

            if (!checkMatelotBeforeAddition(assignations.get(bateau),matelot,poste)){
                    //ADD matelot
                    addMatelotsInArray(assignations.get(bateau),matelot,poste);
                    matelot.addBateau(bateau);
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
                if (matelots[DEF].equals(matelot)) throw new AlreadyAssignedToDefPostException();
            }else{// if already in att place
                if (matelots[ATT].equals(matelot)) throw new AlreadyAssignedToAttPostException();
            }
            return false;
        }throw new AlreadyAssignedToOtherMatelotException();
    }

    private void removeMatelotsInArray(Matelot[] matelots, Matelot matelot, int poste) {

        if (matelots[poste].equals(matelot)){
            matelots[poste]=null;
        }

    }
    public void removeAssignation(Matelot matelot, Bateau bateau,int poste) {
        if (assignations.containsKey(bateau)){

            if ( assignations.get(bateau)[poste].equals(matelot)){
                //REMOVE matelot
                removeMatelotsInArray(assignations.get(bateau), matelot,poste);
                matelot.removeBateau(bateau);
            }
        }
    }

    public void redoAssignation(Matelot matelot, Bateau bateau,int poste)
    {
        if (!assignations.containsKey(bateau)){
            assignations.put(bateau,new Matelot[2]);
            assignations.get(bateau)[poste]=matelot;
        }else{
                //ADD matelot
                assignations.get(bateau)[poste]=matelot;
                matelot.addBateau(bateau);


        }
    }

}
