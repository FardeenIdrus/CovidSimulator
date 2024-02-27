import java.io.IOException;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.time.LocalDate;

public class StatisticsController extends AbstractController {
    private static String filename = "statisticPanel.fxml";
    private int paneCounter = 0; 
    private String startDate = getDateFrom();
    private String endDate = getDateTo();
 
    @FXML
    Label titleLabel, dataLabel;
    
    public StatisticsController() throws IOException {
        super(filename);
    }

    public void dateUpdate(String date1, String date2) {
        super.dateUpdate(date1, date2);
        System.out.println(getDataLoader().getAllBoroughDeath(date1, date2));
        switchPanes();
    }
    
    @FXML
    public void prevDataBtn(ActionEvent event)
    {
        if(paneCounter > 0){
            paneCounter --;
        }
        switchPanes(); 
    }
    
    @FXML
    // 1st data : getAllBoroughDeath()
    //2nd date : getAverageOfTotalCases()
    //3rd: date with highest day()
    //4th : getAverageOfWorkPlaces() ;  mobility of work places
    //5th averageOfResidential(); mobility of residential 
    public void nextDataBtn(ActionEvent event)
    {
       paneCounter ++;
       if (paneCounter == 5) {
           paneCounter = 0;
       }
       switchPanes();
    }
       
    public void switchPanes()
    {
        switch(paneCounter)
       {
           case 0:
               titleLabel.setText("Total Borough Deaths");
               dataLabel.setText(getDataLoader().getAllBoroughDeath(getDateFrom(), getDateTo()));
               break;
            
            case 1:
                titleLabel.setText("Average of Total Cases");
                dataLabel.setText(getDataLoader().getAverageOfTotalCases(getDateFrom(), getDateTo()));
                //dataLabel.setText("test2");
                break;
            case 2:
                titleLabel.setText("Date with Hightest Deaths");
                dataLabel.setText(getDataLoader().dateWithHighestDeath(getDateFrom(), getDateTo()));
                break;
            case 3:
                titleLabel.setText("Average of Workplaces(GMT)");
                dataLabel.setText(getDataLoader().averageOfWorkplaces(getDateFrom(), getDateTo()));
                break;
            case 4:
                titleLabel.setText("Average of Residential(GMT)");
                dataLabel.setText(getDataLoader().averageOfResidential(getDateFrom(), getDateTo()));
        
        }
    }
}
