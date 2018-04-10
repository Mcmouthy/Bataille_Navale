package Model.Exception;

public class AlreadyAssignedToDefPostException extends Throwable {
    private String message;
    public AlreadyAssignedToDefPostException(){
        message="Matelot déjà affecté à ce bateau en tant que défenseur !";
    }
    public String getMessage(){
        return message;
    }
}
