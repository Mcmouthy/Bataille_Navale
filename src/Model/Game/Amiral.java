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
                //ADD bateau
                addMatelotsInArray(assignations.get(bateau),matelot);
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
        throw new NoPlaceAvailableOnShipException("Pas de place disponible dans ce bateau");
    }

    private boolean checkMatelotInArray(Matelot[] matelots, Matelot matelot) {
        for (Matelot m: matelots) {
            if (m.equals(matelot)) return true;
        }
        return false;
    }

    private void removeMatelotsInArray(Matelot[] matelots, Matelot matelot) {
        for (int i=0;i<matelots.length;i++) {
            if (matelots[i].equals(matelot)){
                matelots[i]=null;
                return;
            }
        }
    }
    public boolean removeAssignation(Matelot matelot, Bateau bateau) {
        if (assignations.containsKey(bateau)){
            if (checkMatelotInArray(assignations.get(bateau),matelot)){
                //REMOVE bateau
                removeMatelotsInArray(assignations.get(bateau), matelot);
                matelot.removeBateau(bateau);
                return true;
            }

        }
        return false;
    }

    public boolean changeAssignation(Matelot matelot, Bateau bateauOld, Bateau bateauNew) throws NoPlaceAvailableOnShipException {
        if (assignations.containsKey(bateauOld)){

            if (checkMatelotInArray(assignations.get(bateauOld),matelot)){
                //REMOVE bateauOLD
                removeMatelotsInArray(assignations.get(bateauOld), matelot);
                matelot.removeBateau(bateauOld);

                //ADD bateauNew
                addMatelotsInArray(assignations.get(bateauNew),matelot);
                matelot.addBateau(bateauNew);

                return true;
            }

        }
        return false;
    }
}
