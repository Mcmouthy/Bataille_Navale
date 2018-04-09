package Model.Exception;

public class NoPlaceAvailableOnShipException extends Exception {
    private String message;
    public NoPlaceAvailableOnShipException(){
        message="Aucune place disponible sur ce bateau !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
