import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MapController extends AbstractController {
    private static String filename = "mapPane.fxml";
    private Color worstColor = new Color (1.0, 0, 0, 1.0);
    private Color bestColor = new Color (0, 1.0, 0, 1.0);

    public MapController() throws IOException {
        super(filename);
    }


    public void polygonClick(MouseEvent event) throws IOException {
        Polygon hexagon = (Polygon) event.getSource();
        String borough = hexagon.getId();
        new mapTableController(borough, getDateFrom(), getDateTo());
    }

    public void dateUpdate(String date1, String date2) {
        super.dateUpdate(date1, date2);
        Pane hexagonPane = (Pane) getView().lookup("#hexagonPane");

        Map<String, Integer> boroughData = getDataLoader().getBoroughDeaths(date1, date2);

        int minDeaths = Integer.MAX_VALUE;
        int maxDeaths = 0;
        for (Map.Entry<String, Integer> borough : boroughData.entrySet()) {
            int death = borough.getValue();
            maxDeaths = max(maxDeaths, death);
            minDeaths = min(minDeaths, death);
        }

        int difference = maxDeaths - minDeaths;

        for (Node hexagon : hexagonPane.getChildren()) {
            int boroughDeaths = boroughData.get(hexagon.getId());
            int deathMinExcess = boroughDeaths - minDeaths;

            float deathColor = (float) deathMinExcess / (float) difference;
            Color newColor = blendColors(worstColor, bestColor, deathColor);

            Polygon polygonHexagon = (Polygon) hexagon;
            polygonHexagon.setFill(newColor);
        }


    }

    private Color blendColors(Color color1, Color color2, float percentage) {
        double redDifference = (color2.getRed() - color1.getRed()) * percentage;
        double blueDifference = (color2.getBlue() - color1.getBlue()) * percentage;
        double greenDifference = (color2.getGreen() - color1.getGreen()) * percentage;

        double newRed = color1.getRed() + redDifference;
        double newBlue = color1.getBlue() + blueDifference;
        double newGreen = color1.getGreen() + greenDifference;

        return new Color (newRed, newGreen, newBlue, 1.0);
    }

}

