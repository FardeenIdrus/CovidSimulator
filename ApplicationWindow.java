import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Date;


/**
 * Write a description of JavaFX class ApplicationWindow here.
 *
 *
 * @author (Benjamin Morka, Fardeen Idrus)
 * @version (14/03/2023)
 */
public class ApplicationWindow extends Application
{
    MainController control = new MainController();

    public ApplicationWindow() throws IOException {}

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        URL url = getClass().getResource("windowGUI.fxml");
        Pane root = FXMLLoader.load(url);
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("welcome.css").toExternalForm());
        stage.setTitle("COVID-19 stats viewer");
        stage.setScene(scene);
        // Maintains minimum size of window when resizing
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }
}
