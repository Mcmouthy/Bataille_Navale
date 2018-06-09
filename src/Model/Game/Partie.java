package Model.Game;

import java.io.Serializable;

public class Partie implements Serializable {
    private Equipe equipeA;
    private Equipe equipeB;
    private boolean placementBateaux = true;


    public Partie(Equipe equipeA, Equipe equipeB) {
        this.equipeA = equipeA;
        this.equipeB = equipeB;
    }

    public Equipe getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(Equipe equipeA) {
        this.equipeA = equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(Equipe equipeB) {
        this.equipeB = equipeB;
    }

    public boolean isPlacementBateaux() {
        return placementBateaux;
    }

    public void setPlacementBateaux(boolean placementBateaux) {
        this.placementBateaux = placementBateaux;
    }
}
