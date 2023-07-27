package cannon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CannonTest {
    Cannon cannon;

    void assertCannon(int expectedRow, int expectedCol, boolean expectedActive) {
        assertAll("position",
                () -> assertEquals(expectedRow, cannon.row()),
                () -> assertEquals(expectedCol, cannon.col()),
                () -> assertEquals(expectedActive, cannon.isActive())
        );
    }

    @BeforeEach
    void init() {
        cannon = new Cannon(0, 1, true);
    }

    @Test
    void isActive() {
        assertCannon(0, 1, cannon.isActive());
    }

    @Test
    void setActive() {
        assertFalse(cannon.isActive() == !cannon.isActive());
        assertTrue(cannon.isActive() == cannon.isActive());
        assertFalse(cannon.isActive() == new Cannon(cannon.row(), cannon.col(), !cannon.isActive()).isActive());
    }

    @Test
    void testEquals() {
        assertTrue(cannon.equals(cannon));
        assertTrue(cannon.equals(new Cannon(cannon.row(), cannon.col(), cannon.isActive())));
        assertFalse(cannon.equals(new Cannon(Integer.MIN_VALUE, cannon.col(), cannon.isActive())));
        assertFalse(cannon.equals(new Cannon(cannon.row(), Integer.MAX_VALUE, cannon.isActive())));
        assertNotEquals(null, cannon);
        assertFalse(cannon.equals("Hello, World!"));
    }

    @Test
    void testHashCode() {
        assertTrue(cannon.hashCode() == cannon.hashCode());
        assertTrue(cannon.hashCode() == new Cannon(cannon.row(), cannon.col(), cannon.isActive()).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("(0,1) - Aktív", cannon.toString());
        assertNotEquals("Hello, World!", cannon.toString());
    }
}