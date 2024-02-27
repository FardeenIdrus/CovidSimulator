import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class mapTableController extends AbstractController {
    private static String filename = "newMapWindow.fxml";
    private Scene scene;
    private Stage stage;
    private TableView table;
    public mapTableController(String borough, String dateFrom, String dateTo) throws IOException {
        super(filename);

        scene = new Scene(getView());
        stage = new Stage();
        stage.setTitle(borough);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();

        table = (TableView) scene.lookup("#mainTable");

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn retailRecreationColumn = new TableColumn("Retail + Recreation");
        retailRecreationColumn.setCellValueFactory(new PropertyValueFactory<>("retailRecreationGMR"));

        TableColumn groceryPharmacyColumn = new TableColumn("Grocery + Pharmacy");
        groceryPharmacyColumn.setCellValueFactory(new PropertyValueFactory<>("groceryPharmacyGMR"));

        TableColumn parksColumn = new TableColumn("Parks");
        parksColumn.setCellValueFactory(new PropertyValueFactory<>("parksGMR"));

        TableColumn transitColumn = new TableColumn("Transit");
        transitColumn.setCellValueFactory(new PropertyValueFactory<>("transitGMR"));

        TableColumn workplacesColumn = new TableColumn("Workplaces");
        workplacesColumn.setCellValueFactory(new PropertyValueFactory<>("workplacesGMR"));

        TableColumn residentialColumn = new TableColumn("Residential");
        residentialColumn.setCellValueFactory(new PropertyValueFactory<>("residentialGMR"));

        TableColumn newCasesColumn = new TableColumn("New Cases");
        newCasesColumn.setCellValueFactory(new PropertyValueFactory<>("newCases"));

        TableColumn totalCasesColumn = new TableColumn("Total Cases");
        totalCasesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCases"));

        TableColumn newDeathsColumn = new TableColumn("New Deaths");
        newDeathsColumn.setCellValueFactory(new PropertyValueFactory<>("newDeaths"));

        table.getColumns().addAll(dateColumn, retailRecreationColumn, groceryPharmacyColumn, parksColumn, transitColumn,
                workplacesColumn, residentialColumn, newCasesColumn, totalCasesColumn, newDeathsColumn);

        ComboBox dropDown = (ComboBox) scene.lookup("#dropDown");
        for (Object column : table.getColumns()) {
            TableColumn newColumn = (TableColumn) column;
            dropDown.getItems().add(newColumn.getText());
        }

        ArrayList<CovidData> boroughData = getDataLoader().getBoroughRecords(borough, dateFrom, dateTo);
        for (CovidData record : boroughData) {
            table.getItems().add(record);
        }
    }

    public void sortSelected(ActionEvent event) {
        ComboBox comboBox = (ComboBox) event.getSource();
        String comboBoxValue = (String) comboBox.getValue();
        table.getSortOrder().clear();

        for (Object column : table.getColumns()) {
            TableColumn newColumn = (TableColumn) column;
            if (newColumn.getText().equals(comboBoxValue)) {
                table.getSortOrder().add(newColumn);
                break;
            }
        }

    }
}
