package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class KalahaTest {

        // Testing initialization

        @ParameterizedTest
        @ValueSource(ints = { 7, 14 })
        void testIfAllBowlsHAveInitialized(int number) {
                Bowl bowlTest = new Bowl();

                Container result = bowlTest.getContainer(number);

                assertTrue(result instanceof Kalaha);

        }

        @ParameterizedTest
        @ValueSource(ints = { 7, 14 })
        void testKalahasHaveZeroStonesAtStart(int number) {
                Bowl bowlTest = new Bowl();

                int result = bowlTest.getContainer(number).getNumberOfStones();

                assertEquals(0, result);
        }

        @Test
        void testKalahasHaveDifferentOwners() {
                Bowl bowlTest = new Bowl();

                Player ownerKalahaOne = bowlTest.getContainer(7).getOwner();
                Player ownerKalahaTwo = bowlTest.getContainer(14).getOwner();

                assertNotEquals(ownerKalahaOne, ownerKalahaTwo);
        }

        @Test
        void testKalahaOneOwnerIsMyTurnIsTrue() {
                Bowl bowlTest = new Bowl();
                Kalaha KalahaOne = (Kalaha) bowlTest.getContainer(7);

                assertTrue(KalahaOne.getOwner().getIsMyTurn());
        }

        @Test
        void testKalahaTwoOwnerIsMyTurnIsFalse() {
                Bowl bowlTest = new Bowl();
                Kalaha KalahaTwo = (Kalaha) bowlTest.getContainer(14);

                assertFalse(KalahaTwo.getOwner().getIsMyTurn());
        }

        @ParameterizedTest
        @ValueSource(ints = { 7, 14 })
        void testKalahasNextContainerNotNull(int number) {
                Bowl bowlTest = new Bowl();
                Container KalahaNextContainer = (Bowl) bowlTest.getContainer(number).getNextContainer();

                assertNotNull(KalahaNextContainer);
        }

        // Testing functionality

        @Test
        void testKalahaOneCanAddStoneIfIsMyTurnIsTrue() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(7);

                KalahaTest.getOwner().setIsMyTurn(true);
                bowlTest.playBowl(4);
                int result = KalahaTest.getNumberOfStones();

                assertEquals(1, result);
        }

        @Test
        void testKalahaTwoCanAddStoneIfIsMyTurnIsTrue() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(14);

                KalahaTest.getOwner().setIsMyTurn(true);
                bowlTest.playBowl(11);
                int result = KalahaTest.getNumberOfStones();

                assertEquals(1, result);
        }

        @Test
        void testKalahaOnePassesAllStonesIfTurnPlayerTwo() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(7);
                bowlTest.getContainer(13).setNumberOfStones(8);

                KalahaTest.getOwner().switchTurn(1);
                bowlTest.playBowl(13);
                int result = bowlTest.getContainer(7).getNumberOfStones();

                assertEquals(0, result);
        }

        @Test
        void testKalahaTwoPassesAllStonesIfTurnPlayerOne() {
                Bowl bowlTest = new Bowl();
                bowlTest.getContainer(6).setNumberOfStones(8);

                bowlTest.playBowl(6);
                int result = bowlTest.getContainer(14).getNumberOfStones();

                assertEquals(0, result);
        }

        @Test
        void testIsMyTurnStaysTrueIfLastStoneIsInOwnKalahaOfPlayerOne() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(7);

                KalahaTest.getOwner().setIsMyTurn(true);
                bowlTest.playBowl(3);
                boolean result = KalahaTest.getOwner().getIsMyTurn();

                assertTrue(result);
        }

        @Test
        void testIsMyTurnStaysTrueIfLastStoneIsInOwnKalahaOfPlayerTwo() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(14);

                KalahaTest.getOwner().setIsMyTurn(true);
                bowlTest.playBowl(11);
                boolean result = KalahaTest.getOwner().getIsMyTurn();

                assertTrue(result);
        }

        @Test
        void testKalahaOneReceivesStolenStones() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(7);
                Bowl initialBowl = (Bowl) bowlTest.getContainer(1);
                Bowl endBowl = (Bowl) bowlTest.getContainer(2);
                Bowl opposite = (Bowl) bowlTest.getContainer(12);
                initialBowl.setNumberOfStones(1);
                endBowl.setNumberOfStones(0);
                opposite.setNumberOfStones(13);
                initialBowl.getOwner().setIsMyTurn(true);

                bowlTest.playBowl(1);
                int result = KalahaTest.getNumberOfStones();

                assertEquals(14, result);
        }

        @Test
        void testKalahaTwoReceivesStolenStones() {
                Bowl bowlTest = new Bowl();
                Container KalahaTest = (Kalaha) bowlTest.getContainer(14);
                Bowl intialBowl = (Bowl) bowlTest.getContainer(8);
                Bowl endBowl = (Bowl) bowlTest.getContainer(9);
                Bowl opposite = (Bowl) bowlTest.getContainer(5);
                intialBowl.setNumberOfStones(1);
                endBowl.setNumberOfStones(0);
                opposite.setNumberOfStones(4);
                intialBowl.getOwner().setIsMyTurn(true);

                bowlTest.playBowl(8);
                int result = KalahaTest.getNumberOfStones();

                assertEquals(5, result);
        }

        @Test
        void testKalahaOneWins() {
                Bowl bowlTest = new Bowl();
                Container KalahaOne = (Kalaha) bowlTest.getContainer(7);
                Container KalahaTwo = (Kalaha) bowlTest.getContainer(14);
                Bowl initialBowl = (Bowl) bowlTest.getContainer(2);
                bowlTest.getContainer(8).setNumberOfStones(0);
                bowlTest.getContainer(9).setNumberOfStones(0);
                bowlTest.getContainer(10).setNumberOfStones(0);
                bowlTest.getContainer(11).setNumberOfStones(0);
                bowlTest.getContainer(12).setNumberOfStones(0);
                bowlTest.getContainer(13).setNumberOfStones(0);
                initialBowl.getOwner().setIsMyTurn(true);

                bowlTest.playBowl(2);
                int KalahaOneStones = KalahaOne.getNumberOfStones();
                int KalahaTwoStones = KalahaTwo.getNumberOfStones();

                assertTrue(KalahaOneStones > KalahaTwoStones);
        }

}
