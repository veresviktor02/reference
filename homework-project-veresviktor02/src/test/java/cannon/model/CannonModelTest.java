package cannon.model;

import cannon.Position;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CannonModelTest {
    CannonModel model = new CannonModel();
    Position player = model.getPlayer();
    @Test
    void getPlayer() {
        assertEquals(player, model.getPlayer());
        assertNotEquals(new Position(player.row()+1, player.row()-1), model.getPlayer());
    }

    @Test
    void setPlayer() {
        model.setPlayer(new Position(player.row()+2, player.col()));
        assertNotEquals(player, model.getPlayer());
        model.setPlayer(new Position(player.row(), player.col()));
        assertEquals(player, model.getPlayer());
    }

    @Test
    void getSquare() {
        assertEquals(Square.PLAYER, model.getSquare(player.row(), player.col()));
        assertNotEquals(Square.BLACK, model.getSquare(player.row(), player.col()));
    }
}