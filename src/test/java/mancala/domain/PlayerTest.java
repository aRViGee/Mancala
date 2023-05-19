package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PlayerTest {

    @ParameterizedTest
    @ValueSource(ints = { 1, 8 })
    void testPlayersAreInitialized(int number) {
        Bowl bowlTest = new Bowl();

        assertTrue(bowlTest.getContainer(number).getOwner() instanceof Player);
    }

    @Test
    void testPlayerOneIsStarter() {
        Bowl bowlTest = new Bowl();

        assertTrue(bowlTest.getOwner().getIsMyTurn());
    }

    @Test
    void testPlayerTwoIsNotStarter() {
        Bowl bowlTest = new Bowl();

        boolean result = bowlTest.getContainer(8).getOwner().getIsMyTurn();

        assertFalse(result);
    }

    @Test
    void testPlayerOneOpponentIsPlayerTwo() {
        Bowl bowlTest = new Bowl();

        Player playerActual = bowlTest.getOwner().getOpponent();
        Player playerExpected = bowlTest.getContainer(8).getOwner();

        assertSame(playerExpected, playerActual);
    }

    @Test
    void testPlayerTwoOpponentIsPlayerOne() {
        Bowl bowlTest = new Bowl();

        Player playerExpected = bowlTest.getOwner();
        Player playerActual = bowlTest.getContainer(8).getOwner().getOpponent();

        assertSame(playerExpected, playerActual);
    }

    @Test
    void testSwitchTurnOnPlayerOne() {
        Bowl bowlTest = new Bowl();

        bowlTest.getOwner().switchTurn(1);
        boolean result = bowlTest.getOwner().getIsMyTurn();

        assertFalse(result);
    }

    @Test
    void testSwitchTurnOnPlayerTwo() {
        Bowl bowlTest = new Bowl();

        bowlTest.getOwner().switchTurn(1);
        boolean result = bowlTest.getContainer(8).getOwner().getIsMyTurn();

        assertTrue(result);
    }
}
