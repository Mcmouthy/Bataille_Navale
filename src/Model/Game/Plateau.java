package Model.Game;

public class Plateau {
    private Case[][] plateau;

    public Plateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public Case[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public Case getPreciseCase(Case lacase) {
        return plateau[lacase.getX()][lacase.getY()];
    }

    public Etat changeEtatCase(Case lacase, Etat etat) {
        getPreciseCase(lacase).setState(etat);
        return getPreciseCase(lacase).getState();
    }
}
