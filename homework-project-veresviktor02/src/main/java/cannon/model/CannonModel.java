package cannon.model;

import cannon.Position;
import javafx.beans.property.*;
import static cannon.CannonState.*;

/**
 * Az ágyújáték modellje.
 */
public class CannonModel {
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
    private Position player = new Position(1,1);

    /**
     * A játékos gettere.
     *
     * @return a játékos pozícióját
     */
    public Position getPlayer() {
        return player;
    }

    /**
     * A játékos settere.
     *
     * @param player a játékos pozíciója
     */
    public void setPlayer(Position player) {
        this.player = player;
    }

    /**
     * Konstruktor, amit a kezdet után használunk fel.
     *
     * @param player a játékos pozíciója
     */
    public CannonModel(Position player) {
        this.player = player;
        setConstructor(board, player);
    }

    /**
     * Kezdeti konstruktor (0 paraméterrel).
     */
    public CannonModel() {
        setConstructor(board, player);
    }

    private void setConstructor(ReadOnlyObjectWrapper<Square>[][] board, Position player){
        for(var i = 0; i < BOARD_SIZE; i++){
            for(var j = 0; j < BOARD_SIZE; j++){
                board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.CLEAR);
            }
        }

        for(int i = 0; i < cannonPositions.length; i++){
            if(cannonPositions[i].row() == 0 & cannonPositions[i].isActive() == true){
                board[cannonPositions[i].row()][cannonPositions[i].col()] = new ReadOnlyObjectWrapper<Square>(Square.COL_LASER_ON);
            }
            else if(cannonPositions[i].row() == 0 & cannonPositions[i].isActive() == false){
                board[cannonPositions[i].row()][cannonPositions[i].col()] = new ReadOnlyObjectWrapper<Square>(Square.COL_LASER_OFF);
            }
            else if(cannonPositions[i].col() == 0 & cannonPositions[i].isActive() == true){
                board[cannonPositions[i].row()][cannonPositions[i].col()] = new ReadOnlyObjectWrapper<Square>(Square.ROW_LASER_ON);
            }
            else if(cannonPositions[i].col() == 0 & cannonPositions[i].isActive() == false){
                board[cannonPositions[i].row()][cannonPositions[i].col()] = new ReadOnlyObjectWrapper<Square>(Square.ROW_LASER_OFF);
            }
        }

        board[14][14] = new ReadOnlyObjectWrapper<Square>(Square.BLACK);
        board[15][7] = new ReadOnlyObjectWrapper<Square>(Square.BLACK);
        board[15][15] = new ReadOnlyObjectWrapper<Square>(Square.GOAL);

        for(int i = 1; i < BOARD_SIZE; i++){
            for(int j = 1; j < BOARD_SIZE; j++){
                for(int k = 0; k < cannonPositions.length; k++){
                    if( (cannonPositions[k].row() == i || cannonPositions[k].col() == j) & cannonPositions[k].isActive() == true){
                        board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.LASER_ON);
                    }
                }
            }
        }

        board[player.row()][player.col()] = new ReadOnlyObjectWrapper<Square>(Square.PLAYER);
    }

    /**
     * A tábla egy adott négyzetének a gettere.
     *
     * @param i sorkoordináta
     * @param j oszlopkoordináta
     * @return a megadott koordinátán lévő négyzetet ({@code Square})
     */
    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }

    /**
     * A megadott négyzet {@code ReadOnlyObjectProperty} gettere.
     *
     * @param i sorkoordináta
     * @param j oszlopkoordináta
     * @return egy {@code Square} típusú {@code ReadOnlyObjectProperty}-t
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
