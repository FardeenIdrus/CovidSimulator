import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;

import javax.print.attribute.HashAttributeSet;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.net.URISyntaxException;
import java.util.*;
import java.time.LocalDate;

public class CovidDataLoader {
 
    //Fields
    private HashMap<String, ArrayList<CovidData>> records = new HashMap<String, ArrayList<CovidData>>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public CovidDataLoader() {
        load();
    }
    public HashMap<String, ArrayList<CovidData>> load() {
        
        System.out.println("Begin loading Covid London dataset...");
        
        try{
            URL url = getClass().getResource("covid_london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                
                String date    = line[0];
                String borough    = line[1];    
                int retailRecreationGMR    = convertInt(line[2]);    
                int groceryPharmacyGMR    = convertInt(line[3]);    
                int parksGMR    = convertInt(line[4]);    
                int transitGMR    = convertInt(line[5]);    
                int workplacesGMR    = convertInt(line[6]);    
                int residentialGMR    = convertInt(line[7]);    
                int newCases    = convertInt(line[8]);    
                int totalCases    = convertInt(line[9]);    
                int newDeaths    = convertInt(line[10]);    
                int totalDeaths    = convertInt(line[11]);                

                CovidData record = new CovidData(date,borough,retailRecreationGMR,
                    groceryPharmacyGMR,parksGMR,transitGMR,workplacesGMR,
                    residentialGMR,newCases,totalCases,newDeaths,totalDeaths);
                if (!records.containsKey(date)) {
                    records.put(date, new ArrayList<CovidData>());
                }
                ArrayList<CovidData> tempRecord = records.get(date);
                tempRecord.add(record);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        }
        System.out.println("Number of Loaded Records: " + records.size());
        return records;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }
    
    /**
     * Statistics
     */
    
    /** 
     * @param date1, date2: the date range that user selects.
     * @return totalDeath: Sum up all ‘new deaths’ within the period. 
     */ 
    public String getAllBoroughDeath(String date1, String date2) 
    { 
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);
        int totalDeaths = 0;

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; tempRecord.size() > i; i++) {
                totalDeaths += tempRecord.get(i).getNewDeaths();
            }
        }
                
        return "" + totalDeaths;
     }
    
    /**
     * @param date1, date2: the date range that user selects.
     * @return averageCases: The average number of cases from all boroughs.
     */
    public String getAverageOfTotalCases(String date1, String date2)
    {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);
        int totalCases = 0;
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; i < tempRecord.size(); i++) {
                totalCases += tempRecord.get(i).getNewCases();
            }
        }

        long dayDiffernce = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
        int averageCases = (int) (totalCases / dayDiffernce);

        return "" + averageCases;
    }

    /**
     * @param date1, date2: the date range that user selects.
     * @return stringDate: the date with highest deathcount.
     */
    public String dateWithHighestDeath(String date1, String date2)
    {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);

        int highestDeath = 0;
        LocalDate highestDeathDate = fromDate;

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));
            int dateDeathCount = 0;

            for (int i = 0; i < tempRecord.size(); i++) {
                dateDeathCount += tempRecord.get(i).getNewCases();
            }

            if (dateDeathCount > highestDeath) {
                highestDeath = dateDeathCount;
                highestDeathDate = date;
            }
        }

        String stringDate = highestDeathDate.format(dateFormatter);

        return "" + stringDate;
    }
    
    /**
     * @param date1, date2: the date range that user selects.
     * @return averageWorkspaces: average workplaces(google mobility measures) within date rage.
     */
    public String averageOfWorkplaces(String date1, String date2)
    {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);
        int totalWorkplaces = 0;
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; i < tempRecord.size(); i++) {
                totalWorkplaces += tempRecord.get(i).getWorkplacesGMR();
            }
        }

        long dayDiffernce = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
        int averageWorkspaces = (int) (totalWorkplaces / dayDiffernce);

        return "" + averageWorkspaces;
    }
    
    /**
     * @param date1, date2: the date range that user selects.
     * @return averageResidents: average residents(google mobility measures) within date rage.
     */
    public String averageOfResidential(String date1, String date2)
    {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);
        int totalResidents = 0;
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; i < tempRecord.size(); i++) {
                totalResidents += tempRecord.get(i).getResidentialGMR();
            }
        }

        long dayDiffernce = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
        int averageResidents = (int) (totalResidents / dayDiffernce);

        return "" + averageResidents;
    }

    public Map<String, Integer> getBoroughDeaths(String date1, String date2) {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);

        Map <String, Integer> boroughDeaths = new HashMap<String, Integer>();

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; i < tempRecord.size(); i++) {
                int deaths = tempRecord.get(i).getNewDeaths();
                String boroughName = tempRecord.get(i).getBorough();

                if (boroughDeaths.containsKey(boroughName)) {
                    deaths += boroughDeaths.get(boroughName);
                }

                boroughDeaths.put(boroughName, deaths) ;
            }
        }

        return boroughDeaths;
    }

    public ArrayList<CovidData> getBoroughRecords(String borough, String date1, String date2) {
        LocalDate fromDate = LocalDate.parse(date1);
        LocalDate toDate = LocalDate.parse(date2);

        ArrayList<CovidData> toReturn = new ArrayList<>();

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ArrayList<CovidData> tempRecord = records.get(date.format(dateFormatter));

            for (int i = 0; i < tempRecord.size(); i++) {
                CovidData oneRecord = tempRecord.get(i);
                if (oneRecord.getBorough().equals(borough)){
                    toReturn.add(oneRecord);
                }
            }
        }

        return toReturn;
    }

}
