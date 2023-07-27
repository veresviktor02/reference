package cannon.scene;

import cannon.jackson.JsonManager;
import cannon.jackson.PlayerData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A top10.fxml állományt irányító osztály.
 */
public class LeaderboardController {

    @FXML
    private Text successfulText;

    /**
     * A {@code successfulText} settere.
     *
     * @param successful a beállítandó szöveg
     */
    public void setSuccessfulText(String successful) {
        successfulText.setText(successful);
    }

    @FXML
    private TableView<PlayerData> topTable;
    @FXML
    private TableColumn<PlayerData, String> playerName;
    @FXML
    private TableColumn<PlayerData, Boolean> successful;
    @FXML
    private TableColumn<PlayerData, Integer> steps;
    @FXML
    private TableColumn<PlayerData, Duration> duration;
    @FXML
    private TableColumn<PlayerData, ZonedDateTime> created;

    private JsonManager jsonManager = new JsonManager(new PlayerData());

    @FXML
    private void initialize() throws IOException {
        playerName.setText("Játékos");
        playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        successful.setText("Nyertél-e");
        successful.setCellValueFactory(new PropertyValueFactory<>("solved"));
        steps.setText("Lépések");
        steps.setCellValueFactory(new PropertyValueFactory<>("moves"));
        duration.setText("Játékhossz");
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setText("Létrehozás");
        created.setCellValueFactory(new PropertyValueFactory<>("created"));

        ObservableList<PlayerData> observableList = FXCollections.observableArrayList();
        observableList.addAll(jsonManager.load());
        topTable.setItems(observableList);
    }
}
