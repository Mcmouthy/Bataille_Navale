import Controller.AmiralController;
import Model.Game.*;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Arrays;


public class Appli extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Amiral amiral = new Amiral("Paulo");
        Matelot m1 = new Attaquant("polux",new ArrayList<>());
        Matelot m2 = new Defenseur("toto",new ArrayList<>());
        Matelot m3 = new Attaquant("titi",new ArrayList<>());
        Matelot m4 = new Attaquant("tata",new ArrayList<>());


        Bateau s1= new Bateau("S1",false,true,1);
        Bateau s2= new Bateau("S2",false,true,1);
        Bateau s3= new Bateau("S3",false,true,1);
        Bateau s4= new Bateau("S4",false,true,1);
        Bateau t1= new Bateau("T1",false,true,2);
        Bateau t2= new Bateau("T2",false,true,2);
        Bateau t3= new Bateau("T3",false,true,2);
        Bateau c1= new Bateau("C1",false,true,3);
        Bateau c2= new Bateau("C2",false,true,3);
        Bateau cu= new Bateau("Cu",false,true,4);

        amiral.addAssignation(m1,s1,Amiral.ATT);
        amiral.addAssignation(m1,s2,Amiral.DEF);
        amiral.addAssignation(m3,s1,Amiral.DEF);
        //amiral.addAssignation(m4,s1,Amiral.ATT);
        ArrayList<Matelot> mat= new ArrayList<>();
        mat.addAll(Arrays.asList(new Matelot[]{m1, m2, m3, m4}));
        Equipe equipe1 = new Equipe(amiral,mat);
        equipe1.getPlateau().initPlateau();
        equipe1.setBateauxEquipe(Arrays.asList( new Bateau[]{s1,s2,s3,s4,t1,t2,t3,c1,c2,cu}));
        equipe1.setaPlacer(equipe1.getBateauxEquipe().get(0));
        Partie model =new Partie(equipe1,equipe1);

        AmiralController amiralController = new AmiralController(primaryStage,model);
    }
}
