package Model.Exception;

public class AlreadyAssignedToOtherMatelotException extends Throwable {
    private String message;
    public AlreadyAssignedToOtherMatelotException(){
        message="Poste déjà assigné à un autre matelot !";
    }
    public String getMessage (){
        return message;
    }
}
