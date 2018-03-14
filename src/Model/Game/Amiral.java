package Model.Game;

import Model.Exception.NoPlaceAvailableOnShipException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
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

    public boolean addAssignation(Matelot matelot, Bateau bateau) throws NoPlaceAvailableOnShipException {
        if (assignations.containsKey(bateau)){
            if (checkMatelotInArray(assignations.get(bateau),matelot)){
                addMatelotsInArray(assignations.get(bateau), matelot);
                matelot.addBateau(bateau);
                return true;
            }

        }
        return false;
    }

    private void addMatelotsInArray(Matelot[] matelots, Matelot matelot)throws NoPlaceAvailableOnShipException {
        for (int i=0;i<matelots.length;i++) {
            if (matelots[i]==null){
                matelots[i]=matelot;
                return;
            }
        }
        throw new NoPlaceAvailableOnShipException();
    }

    private boolean checkMatelotInArray(Matelot[] matelots, Matelot matelot) {
        for (Matelot m: matelots) {
            if (m.equals(matelot)) return true;
        }
        return false;
    }

    public boolean removeAssignation(Matelot matelot, Bateau bateau) {

        return false;
    }

    public boolean changeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }
}
