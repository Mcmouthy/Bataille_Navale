package Model.Exception;

public class UnAuthorizeDeplacementException extends Exception {
    private String message;
    public UnAuthorizeDeplacementException(){
        message = "Déplacement interdit !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
