package Model.Game;

import java.io.Serializable;

public class Plateau implements Serializable {
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

    public Case getCaseByCoord(int x, int y){return plateau[x][y];}

    public void initPlateau(){
        plateau = new Case[10][10];
        for (int i=0;i<plateau.length;i++){
            for (int j=0;j<plateau[i].length;j++){
                plateau[i][j]= new Case(i,j,Etat.EAU);
            }
        }
    }

    public Etat changeEtatCase(int x,int y, Etat etat) {
        Case c = getCaseByCoord(x,y);
        c.setState(etat);
        return c.getState();
    }

    public Case getCaseById(String id){
        return getCaseByCoord(Integer.parseInt(id.split("#")[0]),Integer.parseInt(id.split("#")[1]));
    }
}
