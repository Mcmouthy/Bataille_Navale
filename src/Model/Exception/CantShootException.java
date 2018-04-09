package Model.Exception;

public class CantShootException extends Exception {
    private String message;
    public CantShootException(){
        message="impossible de tirer, le canon est en cours de rechargement !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
