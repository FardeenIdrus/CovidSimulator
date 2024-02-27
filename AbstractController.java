import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {
    private static final CovidDataLoader dataLoader = new CovidDataLoader();
    private Pane fxmlRoot;
    private String dateFrom;
    private String dateTo;

    public AbstractController(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
        System.out.println(this);
        fxmlLoader.setController(this);
        fxmlRoot = fxmlLoader.load();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public CovidDataLoader getDataLoader() { return dataLoader; };
    public Pane getView() { return (Pane) fxmlRoot.lookup("#mainPane"); }
    public void dateUpdate(String date1, String date2) {
        dateFrom = date1;
        dateTo = date2;
    };

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
}
