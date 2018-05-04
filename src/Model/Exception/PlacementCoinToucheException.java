package Model.Exception;

public class PlacementCoinToucheException extends Exception {
    private String message;
    public PlacementCoinToucheException(){
        message="Placement impossible, vos bateaux se toucheraient !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
