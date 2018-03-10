package Model.Game;

class Case {
    private int x;
    private int y;
    private Etat state;

    public Case(int x, int y, Etat state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Etat getState() {
        return state;
    }

    public void setState(Etat state) {
        this.state = state;
    }
}
