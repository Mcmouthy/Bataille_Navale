package Model.Exception;

public class NotDeplacableException extends Exception {
    private String message;
    public NotDeplacableException(){
        message="Impossible de déplacer ce navire, il a subi des dégats !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
