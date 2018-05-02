package Model.Exception;

public class UnAuthorizedPlacementException extends Throwable {

    private String message;
    public UnAuthorizedPlacementException(){
        message = "Placement interdit, sélectionnez les cases rouges pour placer la poupe du navire !";
    }

    @Override
    public String getMessage() {
            return message;
        }

}
