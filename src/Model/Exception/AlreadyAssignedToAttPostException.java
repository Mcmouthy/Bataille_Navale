package Model.Exception;

public class AlreadyAssignedToAttPostException extends Exception {
    private String message;
    public AlreadyAssignedToAttPostException(){
        message="Matelot déjà affecté à ce bateau en tant qu'attaquant !";
    }
    public String getMessage(){
        return message;
    }
}
