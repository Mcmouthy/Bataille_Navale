package Model.Game;

public class Plateau {
    private Case[][] plateau;

    public Plateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public Plateau(){}

    public Case[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public Case getPreciseCase(Case lacase) {
        return plateau[lacase.getX()][lacase.getY()];
    }

    public void initPlateau(){
        plateau = new Case[10][10];
        for (int i=0;i<plateau.length;i++){
            for (int j=0;j<plateau[i].length;j++){
                plateau[i][j]= new Case(i,j,Etat.EAU);
            }
        }
    }

    public Etat changeEtatCase(Case lacase, Etat etat) {
        getPreciseCase(lacase).setState(etat);
        return getPreciseCase(lacase).getState();
    }
}
