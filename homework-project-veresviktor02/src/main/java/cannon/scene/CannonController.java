package cannon.scene;

import cannon.CannonState;
import cannon.Direction;
import cannon.Position;
import cannon.jackson.JsonManager;
import cannon.jackson.PlayerData;
import cannon.model.CannonModel;
import cannon.model.Square;
import cannon.util.EnumImageStorage;
import cannon.util.ImageStorage;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.tinylog.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * A ui.fxml állományt irányító osztály.
 */
public class CannonController {
    @FXML
    private Label player_name;
    @FXML
    private TextField numberOfMovesField;

    /**
     * Beállítja a játékos nevének a feliratát.
     *
     * @param name A szöveg, ami a nevet tartalmazza
     */
    public void displayName(String name) {
        player_name.setText(name);
    }

    @FXML
    private GridPane board;
    @FXML
    private Button giveUpButton;

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/top10.fxml"));
        Parent root = loader.load();

        LeaderboardController leaderboardController = loader.getController();
        setText(leaderboardController, state.isGoal());
        saveGame(player_name.getText(), state.isGoal(), numberOfMoves.getValue().intValue(), null, null);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void setText(LeaderboardController leaderboardController, boolean isGoal) {
        if(isGoal){
            leaderboardController.setSuccessfulText("Sikeres próba");
        }
        else{
            leaderboardController.setSuccessfulText("Sikertelen próba");
        }
    }

    private CannonState state;
    private CannonModel model = new CannonModel();
    private IntegerProperty numberOfMoves = new SimpleIntegerProperty(0);
    private ImageStorage<Square> imageStorage = new EnumImageStorage<>(Square.class);

    @FXML
    private void initialize() {
        createControlBindings();
        restartGame();
        populateGrid();
    }

    private void createControlBindings() {
        numberOfMovesField.textProperty().bind(numberOfMoves.asString());
    }

    private void populateGrid() {
        for(int i = 0; i < board.getRowCount(); i++){
            for(int j = 0; j < board.getColumnCount(); j++){
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    private void restartGame() {
        state = new CannonState();
        model = new CannonModel();
        numberOfMoves.set(0);
        populateGrid();
    }

    @FXML
    private void restartGameWithButton() {
        state.changeActiveWhen();
        restartGame();
        Logger.info("Játék újraindítva");
    }

    private void performMove(Direction direction) {
        if(state.canMove(direction)){
            Logger.info("Mozgás {}", direction);
            state.move(direction);
            Logger.trace("Ide mozgott: {}", state);
            numberOfMoves.set(numberOfMoves.get() + 1);
            Logger.info("numberOfMoves: " + numberOfMoves.get());
            move(model.getPlayer(), direction);
        }
        else {
            Logger.warn("Érvénytelen lépés {}", direction);
        }
    }

    private void move(Position player, Direction direction) {
        if(direction == Direction.UP){
            playerMoveUp(player);
        }
        else if(direction == Direction.RIGHT){
            playerMoveRight(player);
        }
        else if(direction == Direction.DOWN){
            playerMoveDown(player);
        }
        else if(direction == Direction.LEFT){
            playerMoveLeft(player);
        }
        if(state.isGoal()){
            Logger.info("A játékos beért a célba.");
            giveUpButton.setText("Nyertél! Ranglistáért katt");
        }
    }

    private void playerMoveUp(Position player) {
        player.setUp();
        model.setPlayer(player);
        model = new CannonModel(player);
        populateGrid();
    }

    private void playerMoveRight(Position player) {
        player.setRight();
        model.setPlayer(player);
        model = new CannonModel(player);
        populateGrid();
    }

    private void playerMoveDown(Position player) {
        player.setDown();
        model.setPlayer(player);
        model = new CannonModel(player);
        populateGrid();
    }

    private void playerMoveLeft(Position player) {
        player.setLeft();
        model.setPlayer(player);
        model = new CannonModel(player);
        populateGrid();
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");

        var imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.imageProperty().bind(
                new ObjectBinding<Image>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }
                    @Override
                    protected Image computeValue() {
                        return imageStorage.get(model.getSquare(i, j));
                    }
                }
        );
        square.getChildren().add(imageView);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        Logger.debug("Kattintás a ({},{}) négyzetre", row, col);
        var direction = getDirectionFromClick(row, col);
        direction.ifPresentOrElse(this::performMove, () -> Logger.warn("Egyik irány se felel meg a kattintásnak."));
    }

    private Optional<Direction> getDirectionFromClick(int row, int col) {
        var position = state.getPosition(CannonState.PLAYER);
        Direction direction = null;
        try{
            direction = Direction.of(row - position.row(), col - position.col());
        } catch(IllegalArgumentException e) {
            Logger.warn("IllegalArgumentException e");
        }
        return Optional.ofNullable(direction);
    }

    private void saveGame(String playerName, boolean successful, int steps, Duration duration, ZonedDateTime created) {
        PlayerData playerData = new PlayerData(playerName, successful, steps, duration, created);
        JsonManager savePlayerData = new JsonManager(playerData);
        savePlayerData.save();
    }
}
