package Model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Amiral extends Joueur {
    private List<Bateau> lesBateaux;
    private List<Matelot> lesMatelots;

    public Amiral(String pseudo, List<Bateau> lesBateaux, List<Matelot> assignations) {
        super(pseudo);
        this.lesBateaux = lesBateaux;
        this.lesMatelots = assignations;
    }

    public Amiral(String pseudo) {
        super(pseudo);
        this.lesBateaux = new ArrayList<>();
        this.lesMatelots = new ArrayList<>();
    }

    public List<Bateau> getLesBateaux() {
        return lesBateaux;
    }

    public void setLesBateaux(List<Bateau> lesBateaux) {
        this.lesBateaux = lesBateaux;
    }

    public List<Matelot> getLesMatelots() {
        return lesMatelots;
    }

    public void setLesMatelots(List<Matelot> lesMatelots) {
        this.lesMatelots = lesMatelots;
    }

    public boolean addAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }

    public boolean removeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }

    public boolean changeAssignation(Matelot matelot, Bateau bateau) {
        return false;
    }
}
