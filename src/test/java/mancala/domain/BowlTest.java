package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BowlTest {

    // Testing initialization

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13 })
    void testAllBowlsHaveInitialized(int number) {
        Bowl bowlTest = new Bowl();

        Container result = bowlTest.getContainer(number);

        assertTrue(result instanceof Bowl);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13 })
    void testAllBowlsHaveFourStones(int number) {
        Bowl bowlTest = new Bowl();

        int result = bowlTest.getContainer(number).getNumberOfStones();

        assertEquals(4, result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13 })
    void testBowlsHaveNextContainer(int number) {
        Bowl bowlTest = new Bowl();

        Container result = bowlTest.getContainer(number).getNextContainer();

        assertNotNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6 })
    void testBowlsOneThroughSixHaveSameOwner(int number) {
        Bowl bowlTest = new Bowl();

        Player result = bowlTest.getContainer(number).getOwner();

        assertEquals(bowlTest.getOwner(), result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 9, 10, 11, 12, 13 })
    void testBowlsSevenThroughTwelveHaveSameOwner(int number) {
        Bowl bowlTest = new Bowl();

        Player result = bowlTest.getContainer(number).getOwner();

        assertEquals(bowlTest.getContainer(8).getOwner(), result);
    }

    @Test
    void testBowlOneHasDifferentOwnerThanBowlTwelve() {
        Bowl bowlTest = new Bowl();

        Player result = bowlTest.getContainer(12).getOwner();

        assertNotEquals(bowlTest.getOwner(), result);
    }

    @Test
    void testBowlOneHasSameOwnerAsKalahaOne() {
        Bowl bowlTest = new Bowl();

        Player result = bowlTest.getContainer(7).getOwner();

        assertEquals(bowlTest.getOwner(), result);
    }

    @Test
    void testBowlSevenHasSameOwnerAsKalahaTwo() {
        Bowl bowlTest = new Bowl();

        Player result = bowlTest.getContainer(14).getOwner();

        assertEquals(bowlTest.getContainer(8).getOwner(), result);
    }

    // Testing functionality

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6 })
    void takeStonesFromBowlPlayerOne(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.playBowl(number);

        assertEquals(0, bowlTest.getContainer(number).getNumberOfStones());
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 9, 10, 11, 12, 13 })
    void takeStonesFromBowlPlayerTwo(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.getContainer(number).getOwner().switchTurn(1);
        bowlTest.playBowl(number);

        assertEquals(0, bowlTest.getContainer(number).getNumberOfStones());
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 4, 5, 6 })
    void testPlayBowlTwoAndNextFourBowlsReceiveOneStone(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.playBowl(2);
        int result = bowlTest.getContainer(number).getNumberOfStones();

        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 10, 11, 12, 13 })
    void testPlayBowlEightAndNextFourBowlsReceiveOneStone(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.getContainer(number).getOwner().switchTurn(1);
        bowlTest.playBowl(9);
        int result = bowlTest.getContainer(number).getNumberOfStones();

        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 9, 11, 12, 13, 26 })
    void testPlayerTwoCanPlayBowl(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.getContainer(number).getOwner().switchTurn(1);

        bowlTest.playBowl(number);

        assertEquals(5, bowlTest.getContainer(number + 4).getNumberOfStones());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6 })
    void testPlayerTwoCannotPlayBowlsOfPlayerOne(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.getContainer(number).getOwner().switchTurn(1);
        bowlTest.playBowl(number);

        assertEquals(4, bowlTest.getContainer(number).getNumberOfStones());
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 9, 10, 11, 12, 13 })
    void testPlayerOneCannotPlayBowlsOfPlayerTwo(int number) {
        Bowl bowlTest = new Bowl();

        bowlTest.playBowl(number);

        assertEquals(4, bowlTest.getContainer(number).getNumberOfStones());
    }

    @Test
    void testFindOwnKalahaIsActivated() {
        Bowl bowlTest = new Bowl();
        bowlTest.getContainer(1).setNumberOfStones(1);

        bowlTest.playBowl(1);
        int result = bowlTest.getContainer(2).findOwnKalahaForSteal(0);

        assertEquals(5, result);
    }

    @Test
    void testFindOppositeBowl() {
        Bowl bowlTest = new Bowl();
        bowlTest.getContainer(1).setNumberOfStones(1);

        Bowl bowlOnOpposite = (Bowl) bowlTest.getContainer(12);

        bowlTest.playBowl(1);
        Container result = bowlTest.getContainer(2).getOppositeBowl();

        assertSame(result, bowlOnOpposite);
    }

    @Test
    void testNoStealingIfNotOwnBowls() {
        Bowl bowlTest = new Bowl();
        Container KalahaTest = (Kalaha) bowlTest.getContainer(7);
        bowlTest.getContainer(5).setNumberOfStones(3);
        bowlTest.getContainer(8).setNumberOfStones(0);
        bowlTest.getContainer(5).getOwner().setIsMyTurn(true);

        bowlTest.playBowl(5);
        int result = KalahaTest.getNumberOfStones();

        assertEquals(1, result);
    }

    @Test
    void testStealingAndOppositeBowlsAreEmptyAfterStealingPlayerOne() {
        Bowl bowlTest = new Bowl();
        Bowl initialBowl = (Bowl) bowlTest.getContainer(1);
        Bowl endBowl = (Bowl) bowlTest.getContainer(2);
        Bowl opposite = (Bowl) bowlTest.getContainer(12);
        initialBowl.setNumberOfStones(1);
        endBowl.setNumberOfStones(0);
        opposite.setNumberOfStones(4);
        initialBowl.getOwner().setIsMyTurn(true);

        bowlTest.playBowl(1);
        int result = (endBowl.getNumberOfStones() + opposite.getNumberOfStones());

        assertEquals(0, result);
    }

    @Test
    void testStealingAndOppositeBowlsAreEmptyAfterStealingPlayerTwo() {
        Bowl bowlTest = new Bowl();
        Bowl initialBowl = (Bowl) bowlTest.getContainer(8);
        Bowl endBowl = (Bowl) bowlTest.getContainer(9);
        Bowl opposite = (Bowl) bowlTest.getContainer(5);
        initialBowl.setNumberOfStones(1);
        endBowl.setNumberOfStones(0);
        opposite.setNumberOfStones(10);
        initialBowl.getOwner().setIsMyTurn(true);

        bowlTest.playBowl(8);
        int result = (endBowl.getNumberOfStones() + opposite.getNumberOfStones());

        assertEquals(0, result);
    }

    @Test
    void testcheckEndGame() {
        Bowl bowlTest = new Bowl();
        Bowl initialBowl = (Bowl) bowlTest.getContainer(2);
        Kalaha kalaha = (Kalaha) bowlTest.getContainer(7);
        bowlTest.getContainer(8).setNumberOfStones(0);
        bowlTest.getContainer(9).setNumberOfStones(0);
        bowlTest.getContainer(10).setNumberOfStones(0);
        bowlTest.getContainer(11).setNumberOfStones(0);
        bowlTest.getContainer(12).setNumberOfStones(0);
        bowlTest.getContainer(13).setNumberOfStones(0);
        initialBowl.getOwner().setIsMyTurn(true);

        bowlTest.playBowl(2);
        int result = kalaha.getNumberOfStones();

        assertEquals(24, result);

    }
}