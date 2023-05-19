package mancala.domain;

public class Kalaha extends Container {

    public Kalaha(Container lastBowl, int numberOfStones, int counterContainer, Player owner) {
        this.setNumberOfStones(0);
        this.setOwner(owner);

        if (counterContainer == 7) {
            this.setNextContainer(
                    new Bowl(lastBowl, getNumberOfStones(), counterContainer + 1, getOwner().getOpponent()));
        }

        if (counterContainer == 14) {
            this.setNextContainer(lastBowl);
        }
    }

    @Override
    void receiveStones(int receivedStones) {
        if (!this.getOwner().getIsMyTurn()) {
            this.getNextContainer().receiveStones(receivedStones);
        } else if ((this.getOwner().getIsMyTurn()) && (receivedStones == 1)) {
            this.setNumberOfStones(getNumberOfStones() + 1);
            extraTurn();
        } else if ((this.getOwner().getIsMyTurn()) && (receivedStones > 1)) {
            this.setNumberOfStones(getNumberOfStones() + 1);
            this.getNextContainer().receiveStones(--receivedStones);
        }
    }

    void getStonesFromOwnBowl(int stonesFromStealingBowl) {
        this.setNumberOfStones(getNumberOfStones() + stonesFromStealingBowl);
    }

    void getStonesFromBowlOpposite(int stonesFromBowlOpposite) {
        this.setNumberOfStones(getNumberOfStones() + stonesFromBowlOpposite);

    }

    void decideWinner() {
        int myStones = this.getNumberOfStones();
        int opponentStones = this.getNextContainer().goToOwnKalaha().getNumberOfStones();
        if (myStones > opponentStones) {
            System.out.println("I win, goog game!");
        } else if ((myStones < opponentStones)) {
            System.out.println("You win, good game!");
        } else {
            System.out.println("It's a draw, good game!");
        }
    }

    private void extraTurn() {
        this.getOwner().switchTurn(0);
    }

    void endTurn() {
        this.getOwner().switchTurn(1);
    }

    @Override
    void checkEndGame() {
        throw new UnsupportedOperationException("Unimplemented method 'checkEndGame'");
    }

    @Override
    int findOwnKalahaForSteal(int stepsToTake) {
        throw new UnsupportedOperationException("Unimplemented method 'findOwnKalaha'");
    }

    @Override
    Container getOppositeBowl() {
        throw new UnsupportedOperationException("Unimplemented method 'getOppositeBowl'");
    }

    @Override
    Container takeStepsToOppositeBowl(int stepsToTake) {
        if (((stepsToTake * 2) - 1) == 0) {
            return this.getNextContainer();
        } else {
            return this.getNextContainer().takeStepsToOppositeBowl(--stepsToTake);
        }
    }

    @Override
    Kalaha goToOppositeKalaha() {
        throw new UnsupportedOperationException("Unimplemented method 'goToOppositeKalaha'");
    }

    @Override
    Kalaha goToOwnKalaha() {
        return this;
    }

    @Override
    void emptyForEndGame() {
        emptyBowl();

    }

    void emptyBowl() {
        ((Bowl) this.getNextContainer()).emptyBowl();
    }

}
