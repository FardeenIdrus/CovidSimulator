import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.Bindings;
import java.time.LocalDate;
import javafx.css.PseudoClass;

/**
 * @author (Benjamin Morka, Fardeen Idrus)
 * @version (14/03/2023)
 */

public class MainController implements Initializable{
    private ArrayList<AbstractController> scenes = new ArrayList<>(Arrays.asList(
            new WelcomeController(),
            new MapController(),
            new StatisticsController(),
            new FourthController()
    ));
    private String fromDate, toDate;
    public static final PseudoClass PSEUDO_CLASS_VALIDFROMDATE = PseudoClass.getPseudoClass("fromDateValid");
    public static final PseudoClass PSEUDO_CLASS_VALIDTODATE = PseudoClass.getPseudoClass("toDateValid");
    public static final PseudoClass PSEUDO_CLASS_INVALIDFROMDATE = PseudoClass.getPseudoClass("fromDateInvalid");
    public static final PseudoClass PSEUDO_CLASS_INVALIDTODATE = PseudoClass.getPseudoClass("toDateInvalid");
    private int sceneCount = 0;
    private LocalDate maxDate = LocalDate.of(2023, 02, 9);
    private LocalDate minDate = LocalDate.of(2020, 02, 15);
    private AbstractController currentController;
    @FXML
    private BorderPane borderpane;
    @FXML
    private DatePicker datePicker1,datePicker2;
    @FXML
    private Button backBtn, nextBtn;


    public MainController() throws IOException 
    {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {    
        try
        {
            goToScene(scenes.get(0));
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
        buttonDisableCheck();
    }
    
    @FXML
    private void goToPrevious(ActionEvent event) throws java.io.IOException
    {
        if(sceneCount > 0){
            sceneCount--;
        }
        goToScene(scenes.get(sceneCount));
    }
    @FXML
    private void goToNext(ActionEvent event) throws java.io.IOException
    {
        sceneCount++;
        if(sceneCount == scenes.size()){
            sceneCount = 0;
        }
        goToScene(scenes.get(sceneCount));
    }
    @FXML
    private void goToScene(AbstractController controller) throws java.io.IOException{
        currentController = controller;
        controller.dateUpdate(fromDate, toDate);
        borderpane.setCenter(controller.getView());
    }
    /**
     * Check if date to view up to is before the date to from, 
     * or if the date to from is outside the data range
     */
    @FXML
    private void checkFromDate(ActionEvent event){
        if(datePicker1.getValue().isAfter(maxDate) || datePicker1.getValue().isBefore(minDate)){
           showSecondError(datePicker1);
        }
        else if(datePicker2.getValue() !=null){ 
            if(datePicker2.getValue().isBefore(datePicker1.getValue())){
                showError(datePicker1);
            }
            else{
                fromDate = datePicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                showValid(datePicker1);
            }
        }
        else{
                fromDate = datePicker1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                showValid(datePicker1);
        }
        currentController.dateUpdate(fromDate, toDate);
    }
    /**
     * Check if date to view from is after date to view up to, 
     * or if the date to view up to is outside the data range
     */ 
    @FXML
    private void checkToDate(ActionEvent event){
       if(datePicker2.getValue().isAfter(maxDate) || datePicker2.getValue().isBefore(minDate)){
           showSecondError(datePicker2);
       }
       else if(datePicker1.getValue() != null){   
           if(datePicker1.getValue().isAfter(datePicker2.getValue())){
               showError(datePicker2);
           }
           else{
               toDate = datePicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
               showValid(datePicker2);
           }
       }
       else{
            toDate = datePicker2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            showValid(datePicker2);
       }
       currentController.dateUpdate(fromDate, toDate);
    }
    private void buttonDisableCheck(){
        BooleanBinding buttonEnabled = Bindings.createBooleanBinding(() -> {
            return(datePicker1.getValue() == null || datePicker2.getValue() == null);
        }, datePicker1.valueProperty(), datePicker2.valueProperty());
        
        backBtn.disableProperty().bind(buttonEnabled);
        nextBtn.disableProperty().bind(buttonEnabled);
    }
    private void showError(DatePicker datepicker){
        if(datepicker == datePicker1){
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDFROMDATE, false);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDFROMDATE, true);
        }
        else{
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDTODATE, false);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDTODATE, true);
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("INVALID CHOICE");
        alert.setContentText("Please select a valid date range");
        datepicker.setValue(null);
        alert.showAndWait();
        
    }
    
    private void showSecondError(DatePicker datepicker){
        if(datepicker == datePicker1){
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDFROMDATE, false);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDFROMDATE, true);
        }
        else{
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDTODATE, false);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDTODATE, true);
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("INVALID CHOICE");
        alert.setContentText("No info available for this date");
        datepicker.setValue(null);
        alert.showAndWait();
    }

    private void showValid(DatePicker datepicker){
        if(datepicker == datePicker1){
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDFROMDATE, true);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDFROMDATE, false);
        }
        else{
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_VALIDTODATE, true);
            datepicker.pseudoClassStateChanged(PSEUDO_CLASS_INVALIDTODATE, false);
        }
    }
}