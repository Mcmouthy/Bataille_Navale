package Model.Exception;

public class NotDeplacableException extends Exception {
    private String message;
    public NotDeplacableException(){
        message="Impossible de déplacer ce bateau, il a été touché par l'ennemi !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
