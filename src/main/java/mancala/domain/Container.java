package mancala.domain;

public abstract class Container {
    private int numberOfStones;
    private Player owner;
    private Container nextContainer;

    public Container() {

    }

    Container getContainer(int containerNumber) {
        if ((containerNumber - 1) > 0) {
            return this.getNextContainer().getContainer(--containerNumber);
        }
        return this;
    }

    abstract void receiveStones(int stones);

    abstract void checkEndGame();

    abstract void emptyForEndGame();

    abstract void emptyBowl();

    abstract void endTurn();

    abstract Container getOppositeBowl();

    abstract int findOwnKalahaForSteal(int stepsToKalaha);

    abstract Container takeStepsToOppositeBowl(int stepsToTake);

    abstract void getStonesFromOwnBowl(int stonesFromStealingBowl);

    abstract Kalaha goToOwnKalaha();

    abstract Kalaha goToOppositeKalaha();

    Container getNextContainer() {
        return this.nextContainer;
    }

    void setNextContainer(Container container) {
        this.nextContainer = container;
    }

    int getNumberOfStones() {
        return this.numberOfStones;
    }

    void setNumberOfStones(int value) {
        this.numberOfStones = value;
    }

    Player getOwner() {
        return this.owner;
    }

    void setOwner(Player player) {
        this.owner = player;
    }

}
