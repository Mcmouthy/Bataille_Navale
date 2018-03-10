package Model.Game;

class Bateau {
    private Case[] positions;
    private boolean recharge;
    private boolean deplacable = true;

    public Bateau(Case[] positions, boolean recharge)
    {
        this.positions = positions;
        this.recharge = recharge;
    }

    public Bateau(Case[] positions, boolean recharge,boolean deplacable)
    {
        this.positions = positions;
        this.recharge = recharge;
        this.deplacable = deplacable;
    }

    public Case[] getPositions() {
        return positions;
    }

    public void setPositions(Case[] positions) {
        this.positions = positions;
    }

    public boolean isRecharge() {
        return recharge;
    }

    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }

    public boolean isDeplacable() {
        return deplacable;
    }

    public void setDeplacable(boolean deplacable) {
        this.deplacable = deplacable;
    }

    public boolean deplacement(Case caseDepart, Case caseArrivee) {
        return false;
    }
}
