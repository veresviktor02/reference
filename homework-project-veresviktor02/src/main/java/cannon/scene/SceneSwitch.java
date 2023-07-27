package cannon.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Az applikáció indítását végző osztály.
 */
public class SceneSwitch extends Application {
    /**
     * Indítást végző metódus.
     *
     * @param stage színpad
     * @throws Exception Nem található a megadott állomány (welcome.fxml)
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcome.fxml"));
        stage.setTitle("JavaFX Scene Switching");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
