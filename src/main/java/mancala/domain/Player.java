package mancala.domain;

public class Player {

    private Player opponent;
    private boolean isMyTurn = false;

    public Player() {
        setIsMyTurn(true);
        setOpponent(new Player(this));
    }

    public Player(Player opponent) {
        this.setOpponent(opponent);
    }

    void switchTurn(int ZeroOrOne) {
        switch (ZeroOrOne) {
            case 1:
                this.setIsMyTurn(!this.getIsMyTurn());
                this.getOpponent().setIsMyTurn(!this.getIsMyTurn());
                break;

            case 0:
                break;
        }

    }

    Player getOpponent() {
        return this.opponent;
    }

    void setOpponent(Player player) {
        this.opponent = player;
    }

    boolean getIsMyTurn() {
        return this.isMyTurn;
    }

    void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }
}
