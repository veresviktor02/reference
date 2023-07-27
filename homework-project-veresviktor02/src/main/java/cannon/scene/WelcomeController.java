package cannon.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * A welcome.fxml állományt irányító osztály.
 */
public class WelcomeController {

    @FXML
    private TextField player_name;

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        String name;
        if(player_name.getLength() == 0){
            name = "The nameless";
        }
        else{
            name = player_name.getText();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ui.fxml"));
        Parent root = loader.load();

        CannonController cannonController = loader.getController();
        cannonController.displayName(name);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Logger.info("Kilépés...");
        Platform.exit();
    }
}
