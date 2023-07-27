package cannon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CannonStateTest {
    CannonState cannon = new CannonState();
    CannonState cannon2 = new CannonState(new Position(15,15),
            new Position(14,14),
            new Position(15,7),
            new Position(15,15)
            );

    CannonState cannon3 = new CannonState(new Position(3,1),
            new Position(14,14),
            new Position(15,7),
            new Position(15,15)
    );

    Cannon[] cannonPositions = new Cannon[16];

    @BeforeEach
    void init() {
        cannonPositions[0] = new Cannon(0, 2, false);
        cannonPositions[1] = new Cannon(0, 3, true);
        cannonPositions[2] = new Cannon(0, 5, false);
        cannonPositions[3] = new Cannon(0, 8, true);
        cannonPositions[4] = new Cannon(0, 9, false);
        cannonPositions[5] = new Cannon(0, 11, false);
        cannonPositions[6] = new Cannon(0, 13, false);
        cannonPositions[7] = new Cannon(2, 0, true);
        cannonPositions[8] = new Cannon(3, 0, false);
        cannonPositions[9] = new Cannon(5, 0, true);
        cannonPositions[10] = new Cannon(7, 0, false);
        cannonPositions[11] = new Cannon(8, 0, true);
        cannonPositions[12] = new Cannon(9, 0, false);
        cannonPositions[13] = new Cannon(11, 0, false);
        cannonPositions[14] = new Cannon(12, 0, true);
        cannonPositions[15] = new Cannon(13, 0, false);
    }

    @Test
    void cannonGenerate() {
        assertTrue(cannonPositions[0].row() == 0 && cannonPositions[0].col() == 2 && cannonPositions[0].isActive() == false);
        assertTrue(cannonPositions[1].row() == 0 && cannonPositions[1].col() == 3 && cannonPositions[1].isActive() == true);
        assertTrue(cannonPositions[2].row() == 0 && cannonPositions[2].col() == 5 && cannonPositions[2].isActive() == false);
        assertTrue(cannonPositions[3].row() == 0 && cannonPositions[3].col() == 8 && cannonPositions[3].isActive() == true);
        assertTrue(cannonPositions[4].row() == 0 && cannonPositions[4].col() == 9 && cannonPositions[4].isActive() == false);
        assertTrue(cannonPositions[5].row() == 0 && cannonPositions[5].col() == 11 && cannonPositions[5].isActive() == false);
        assertTrue(cannonPositions[6].row() == 0 && cannonPositions[6].col() == 13 && cannonPositions[6].isActive() == false);
        assertTrue(cannonPositions[7].row() == 2 && cannonPositions[7].col() == 0 && cannonPositions[7].isActive() == true);
        assertTrue(cannonPositions[8].row() == 3 && cannonPositions[8].col() == 0 && cannonPositions[8].isActive() == false);
        assertTrue(cannonPositions[9].row() == 5 && cannonPositions[9].col() == 0 && cannonPositions[9].isActive() == true);
        assertTrue(cannonPositions[10].row() == 7 && cannonPositions[10].col() == 0 && cannonPositions[10].isActive() == false);
        assertTrue(cannonPositions[11].row() == 8 && cannonPositions[11].col() == 0 && cannonPositions[11].isActive() == true);
        assertTrue(cannonPositions[12].row() == 9 && cannonPositions[12].col() == 0 && cannonPositions[12].isActive() == false);
        assertTrue(cannonPositions[13].row() == 11 && cannonPositions[13].col() == 0 && cannonPositions[13].isActive() == false);
        assertTrue(cannonPositions[14].row() == 12 && cannonPositions[14].col() == 0 && cannonPositions[14].isActive() == true);
        assertTrue(cannonPositions[15].row() == 13 && cannonPositions[15].col() == 0 && cannonPositions[15].isActive() == false);
    }

    @Test
    void isGoal() {
        assertFalse(cannon.isGoal());
        assertTrue(cannon2.isGoal());
        assertFalse(cannon3.isGoal());
    }

    @Test
    void canMove_cannon() {
        assertFalse(cannon.canMove(Direction.UP));
        assertFalse(cannon.canMove(Direction.RIGHT));
        assertTrue(cannon.canMove(Direction.DOWN));
        assertFalse(cannon.canMove(Direction.LEFT));
    }

    @Test
    void canMove_cannon2() {
        assertTrue(cannon2.canMove(Direction.UP));
        assertFalse(cannon2.canMove(Direction.RIGHT));
        assertFalse(cannon2.canMove(Direction.DOWN));
        assertTrue(cannon2.canMove(Direction.LEFT));
    }

    @Test
    void canMove_cannon3() {
        assertTrue(cannon3.canMove(Direction.UP));
        assertFalse(cannon3.canMove(Direction.RIGHT));
        assertTrue(cannon3.canMove(Direction.DOWN));
        assertFalse(cannon3.canMove(Direction.LEFT));
    }

    @Test
    void changeActive() {
        for(int i = 0; i < cannonPositions.length; i++){
            assertFalse(Objects.equals(cannonPositions[i].isActive(), !cannonPositions[i].isActive()));
            assertTrue(Objects.equals(cannonPositions[i].isActive(), cannonPositions[i].isActive()));
        }
    }

    @Test
    void getLegalMoves() {
        assertEquals(EnumSet.of(Direction.DOWN), cannon.getLegalMoves());
        assertEquals(EnumSet.of(Direction.UP, Direction.LEFT), cannon2.getLegalMoves());
        assertEquals(EnumSet.of(Direction.UP, Direction.DOWN), cannon3.getLegalMoves());
    }

    /**
     * A mozgatás tesztelésénél a klnt mozgassuk vissza (vagy csináljunk vissza minden),
     * mert lefut a changeActive(), ezzel hibát okozva az egyébként jó tesztekben.
     */
    @Test
    void testEquals() {
        assertTrue(cannon.equals(cannon));

        var clone = cannon.clone();
        clone.move(Direction.RIGHT);
        assertFalse(clone.equals(cannon));
        clone.move(Direction.LEFT);
        assertTrue(clone.equals(cannon));

        assertFalse(cannon.equals(null));
        assertFalse(cannon.equals("Hello, World!"));
        assertFalse(cannon.equals(cannon2));
    }

    @Test
    void testHashCode() {
        assertTrue(cannon.hashCode() == cannon.hashCode());
        assertTrue(cannon.hashCode() == cannon.clone().hashCode());
    }

    @Test
    void testClone() {
        var clone = cannon.clone();
        assertTrue(clone.equals(cannon));
        assertNotSame(clone, cannon);
    }

    @Test
    void testToString() {
        assertEquals("[(1,1),(14,14),(15,7),(15,15)]", cannon.toString());
        assertEquals("[(15,15),(14,14),(15,7),(15,15)]", cannon2.toString());
    }
}