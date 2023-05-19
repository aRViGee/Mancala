package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ContainerTest {

    @Test
    void testGetContainerGetsContainer6() {
        Bowl bowlTest = new Bowl();

        Container result = bowlTest.getContainer(6);

        assertSame(bowlTest.getNextContainer().getNextContainer().getNextContainer().getNextContainer()
                .getNextContainer(), result);
    }
}
