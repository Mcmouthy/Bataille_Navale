package Model.Exception;

public class CantShootException extends Exception {
    public CantShootException(){

        System.out.println("Vous n'avez pas le droit de tirer !");

    }
}
