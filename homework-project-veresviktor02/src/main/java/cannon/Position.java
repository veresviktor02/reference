package cannon;

import java.util.Objects;

/**
 * Egy 2D pozíciót ábrázol.
 */
public class Position implements Cloneable {
    private int row;
    private int col;

    /**
     * Létrehoz egy {@code Position} objektumot.
     *
     * @param row sor koordinátája a pozíciónak
     * @param col oszlop koordinátája a pozíciónak
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * {@return a sor koordinátáját a pozíciónak.}
     */
    public int row() {
        return row;
    }

    /**
     * {@return az oszlop koordinátáját a pozíciónak.}
     */
    public int col() {
        return col;
    }

    /**
     * {@return azt a pozíciót, amelynek vízszintes és függőleges távolsága ettől a
     * pozíciótól egyenlő a megadott irány koordinátaváltozásaival.}
     *
     * @param direction egy irány, ami leír egy változást a koordinátákban
     */
    public Position getPosition(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * {@return a Fel irány pozícióját.}
     */
    public Position getUp() {
        return getPosition(Direction.UP);
    }

    /**
     * {@return a Jobbra irány pozícióját.}
     */
    public Position getRight() {
        return getPosition(Direction.RIGHT);
    }

    /**
     * {@return a Le irány pozícióját.}
     */
    public Position getDown() {
        return getPosition(Direction.DOWN);
    }

    /**
     * {@return a Balra irány pozícióját.}
     */
    public Position getLeft() {
        return getPosition(Direction.LEFT);
    }

    /**
     * Megváltoztatja a pozíciót a megadott irány koordinátaváltozásaival.
     *
     * @param direction egy irány, ami leír egy változást a koordinátákban
     */
    public void setTo(Direction direction) {
        row += direction.getRowChange();
        col += direction.getColChange();
    }

    /**
     * Beállítja a Fel irány pozícióját.
     */
    public void setUp() {
        setTo(Direction.UP);
    }

    /**
     * Beállítja a Jobbra irány pozícióját.
     */
    public void setRight() {
        setTo(Direction.RIGHT);
    }

    /**
     * Beállítja a Le irány pozícióját.
     */
    public void setDown() {
        setTo(Direction.DOWN);
    }

    /**
     * Beállítja a Balra irány pozícióját.
     */
    public void setLeft() {
        setTo(Direction.LEFT);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return true;
        }
        return (o instanceof  Position p) && p.row == row && p.col == col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public Position clone() {
        Position copy;
        try {
            copy = (Position) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new AssertionError();
        }
        return copy;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}
