package mancala.domain;

public class Bowl extends Container {

    public Bowl() {
        this.setNumberOfStones(4);
        this.setOwner(new Player());
        this.setNextContainer(new Bowl(this, 4, 2, getOwner()));
    }

    public Bowl(Container lastBowl, int numberOfStones, int counterContainer, Player owner) {
        this.setNumberOfStones(4);
        this.setOwner(owner);
        if ((counterContainer == 6) || (counterContainer == 13)) {
            this.setNextContainer(new Kalaha(lastBowl, getNumberOfStones(), counterContainer + 1, getOwner()));
        } else {
            this.setNextContainer(new Bowl(lastBowl, getNumberOfStones(), counterContainer + 1, getOwner()));
        }
    }

    public void playBowl(int containerNumber) {
        if (canPlayThisBowl(containerNumber)) {
            int stones = getContainer(containerNumber).getNumberOfStones();
            this.getContainer(containerNumber).getNextContainer().receiveStones(stones);
            this.getContainer(containerNumber).setNumberOfStones(0);

            checkEndGame();

        } else if (!isNotEmptyBowl(containerNumber)) {
            throw new ArithmeticException("You cannot choose an empty bowl");

        } else if (!isMyTurn(containerNumber)) {
            // TODO playBowl() - Exception throw if chosen bowl is not of own player
        }
    }

    private boolean canPlayThisBowl(int containerNumber) {
        return isMyTurn(containerNumber)
                && isNotEmptyBowl(containerNumber);
    }

    private boolean isMyTurn(int containerNumber) {
        return this.getContainer(containerNumber).getOwner().getIsMyTurn();
    }

    private boolean isNotEmptyBowl(int containerNumber) {
        return this.getContainer(containerNumber).getNumberOfStones() > 0;
    }

    @Override
    void receiveStones(int receivedStones) {
        this.setNumberOfStones(getNumberOfStones() + 1);
        if (receivedStones > 1) {
            this.getNextContainer().receiveStones(--receivedStones);
        } else {
            checkForSteal();
            endTurn();
        }

    }

    private void checkForSteal() {
        if ((this.getOwner().getIsMyTurn()) && (this.getNumberOfStones() == 1)) {
            this.goToOwnKalaha().getStonesFromOwnBowl((this.getNumberOfStones()));
            this.setNumberOfStones(0);

            this.goToOwnKalaha().getStonesFromBowlOpposite(getOppositeBowl().getNumberOfStones());
            this.getOppositeBowl().setNumberOfStones(0);
        }
    }

    Kalaha goToOppositeKalaha() {
        return getOppositeBowl().goToOwnKalaha();

    }

    Kalaha goToOwnKalaha() {
        return this.getNextContainer().goToOwnKalaha();
    }

    @Override
    Container getOppositeBowl() {
        return takeStepsToOppositeBowl(findOwnKalahaForSteal(0) * 2);
    }

    @Override
    Container takeStepsToOppositeBowl(int stepsToTake) {
        if (((stepsToTake) - 1) == 0) {
            return (Bowl) this.getNextContainer();
        } else {
            return this.getNextContainer().takeStepsToOppositeBowl(--stepsToTake);
        }
    }

    @Override
    int findOwnKalahaForSteal(int stepsToKalaha) {
        if (this.getNextContainer() instanceof Kalaha) {
            return ++stepsToKalaha;
        } else {
            return this.getNextContainer().findOwnKalahaForSteal(++stepsToKalaha);
        }
    }

    @Override
    void endTurn() {
        this.getOwner().switchTurn(1);
    }

    @Override
    void getStonesFromOwnBowl(int stonesFromStealingBowl) {
        throw new UnsupportedOperationException("Unimplemented method 'getStonesFromOwnBowl'");
    }

    @Override
    void checkEndGame() {
        if (getOwner().getIsMyTurn()) {
            Bowl myFirstBowl = (Bowl) goToOppositeKalaha().getNextContainer();
            myFirstBowl.emptyForEndGame();
        } else {
            Bowl myOpponentsFirstBowl = (Bowl) goToOwnKalaha().getNextContainer();
            myOpponentsFirstBowl.emptyForEndGame();
        }

    }

    void emptyForEndGame() {
        if (getNumberOfStones() == 0) {
            getNextContainer().emptyForEndGame();
        }
    }

    void emptyBowl() {
        this.goToOwnKalaha().setNumberOfStones(goToOwnKalaha().getNumberOfStones() + this.getNumberOfStones());
        this.setNumberOfStones(0);
        if (getNextContainer() instanceof Bowl) {
            ((Bowl) this.getNextContainer()).emptyBowl();
        }
    }

}
