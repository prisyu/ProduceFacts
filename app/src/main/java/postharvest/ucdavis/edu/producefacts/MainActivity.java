package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    public static List<Commodity> commodityArray;
    public static List<Commodity> commodityArrayForSearchResult;
    public static List< Fruit > fruitArray;
    public static List< Vegetable > vegetableArray;
    public static List< Ornamental > ornamentalArray;

    public String searchString;

    public static final String menuItemLanguages[][] = {{"Fruit", "Vegetables", "Ornamentals"}, {"Frutas", "Vegetales", "Ornamentales"}, {"Fruit", "Les Légume", "Ornamentals"}};
    //menu item is ONLY for set title.  The arrays below are for the actual parsing
    public static final String recommendationLanguages[] = {"Recommendations for Maintaining Postharvest Quality", "Recomendaciones para Mantener la Calidad Postcosecha", "Recommendations for Maintaining Postharvest Quality"};
    public static final String maturityLanguages[] = {"Maturity & Quality", "Cosecha y Calidad", "Maturity & Quality"};
    public static final String temperatureLanguages[] = {"Temperature & Controlled Atmosphere", "Temperatura y Atmosfera Controlada", "Temperature & Controlled Atmosphere"};
    public static final String disorderLanguages[] = {"Disorders", "Desordenes", "Disorders"};

    public static final String languages[] = {"English", "Spanish", "French"};
    public static final int languageSelected = Language.English;
    public static final int produceSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFiles();
    }

    public void produceSelected(View view){
        Button button = (Button) view;
        String produceType = button.getText().toString();
        System.out.println("Selected " + produceType);

        Intent intent = new Intent(this, ListProduceActivity.class);
        if (produceType == menuItemLanguages[languageSelected][0]){
            ListProduceActivity.commodities = new ArrayList<Commodity>(fruitArray);
        }
        else if (produceType == menuItemLanguages[languageSelected][1]) {
            ListProduceActivity.commodities = new ArrayList<Commodity>(vegetableArray);
        }
        else if (produceType == menuItemLanguages[languageSelected][2]) {
            ListProduceActivity.commodities = new ArrayList<Commodity>(ornamentalArray);
        }
        startActivity(intent);
    }

    public void loadFiles(){
        System.out.println("loading csv files...");
        Button fruitButton = (Button) findViewById(R.id.fruitButton);
        Button vegetableButton = (Button) findViewById(R.id.vegetableButton);
        Button ornamentalButton = (Button) findViewById(R.id.ornamentalButton);

        System.out.println("updating language labels...");
        // update the button labels
        switch (languageSelected) {
            case Language.English:
                fruitButton.setText(menuItemLanguages[languageSelected][0]);
                vegetableButton.setText(menuItemLanguages[languageSelected][1]);
                ornamentalButton.setText(menuItemLanguages[languageSelected][2]);
                break;
            case Language.Spanish:
                fruitButton.setText(menuItemLanguages[languageSelected][0]);
                vegetableButton.setText(menuItemLanguages[languageSelected][1]);
                ornamentalButton.setText(menuItemLanguages[languageSelected][2]);
                break;
            case Language.French:
                fruitButton.setText(menuItemLanguages[languageSelected][0]);
                vegetableButton.setText(menuItemLanguages[languageSelected][1]);
                ornamentalButton.setText(menuItemLanguages[languageSelected][2]);
                break;
            default:
                fruitButton.setText(menuItemLanguages[0][0]);
                vegetableButton.setText(menuItemLanguages[0][1]);
                ornamentalButton.setText(menuItemLanguages[0][2]);
                break;
        }

        // actual csv parsing
        System.out.println("parsing fruit files...");
        try {
            fruitArray = new ArrayList<Fruit>();
            String filename =   getResources().getString(R.string.fruitFile) + languages[languageSelected].toLowerCase();
            System.out.println(filename);
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier(filename,
                            "raw", getPackageName()));

            CSVFile csvFile = new CSVFile(inputStream);
            for(String[] row : csvFile.keyedRows) {
                Fruit aFruit = new Fruit();
                aFruit.name = row[csvFile.headerIndex.get("Name")];
                aFruit.recommendations = row[csvFile.headerIndex.get(recommendationLanguages[languageSelected])];
                aFruit.maturity = row[csvFile.headerIndex.get(maturityLanguages[languageSelected])];
                aFruit.temperature = row[csvFile.headerIndex.get(temperatureLanguages[languageSelected])];
                aFruit.disorders = row[csvFile.headerIndex.get(disorderLanguages[languageSelected])];
                aFruit.image = (getResources().getString(R.string.fruitFile) + row[csvFile.headerIndex.get("Image Name")]).replace(' ', '_').replace('-', '_').replace("'", "").toLowerCase();
                fruitArray.add(aFruit);
            }
        } catch (Exception e){
            System.out.println("Error");
        }

        System.out.println("parsing vegetable files...");
        try {
            vegetableArray = new ArrayList<Vegetable>();
            String filename =   getResources().getString(R.string.vegetableFile) + languages[languageSelected].toLowerCase();
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier(filename,
                            "raw", getPackageName()));

            CSVFile csvFile = new CSVFile(inputStream);
            for(String[] row : csvFile.keyedRows) {
                Vegetable aVegetable = new Vegetable();
                aVegetable.name = row[csvFile.headerIndex.get("Name")];
                aVegetable.recommendations = row[csvFile.headerIndex.get(recommendationLanguages[languageSelected])];
                aVegetable.maturity = row[csvFile.headerIndex.get(maturityLanguages[languageSelected])];
                aVegetable.temperature = row[csvFile.headerIndex.get(temperatureLanguages[languageSelected])];
                aVegetable.disorders = row[csvFile.headerIndex.get(disorderLanguages[languageSelected])];
                aVegetable.image = (getResources().getString(R.string.vegetableFile) + row[csvFile.headerIndex.get("Image Name")]).replace(' ', '_').replace('-', '_').replace("'", "").toLowerCase();
                vegetableArray.add(aVegetable);
            }
        } catch (Exception e){
            System.out.println("Error");
        }

        System.out.println("pasring ornamental files...");
        try {
            ornamentalArray = new ArrayList<Ornamental>();
            String filename =   getResources().getString(R.string.ornamentalFile) + languages[languageSelected].toLowerCase();
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier(filename,
                            "raw", getPackageName()));

            CSVFile csvFile = new CSVFile(inputStream);
            for(String[] row : csvFile.keyedRows) {
                Ornamental aOrnamental = new Ornamental();
                aOrnamental.name = row[csvFile.headerIndex.get("Name")];
                aOrnamental.recommendations = row[csvFile.headerIndex.get(recommendationLanguages[languageSelected])];
                aOrnamental.maturity = row[csvFile.headerIndex.get(maturityLanguages[languageSelected])];
                aOrnamental.image = (getResources().getString(R.string.ornamentalFile) + row[csvFile.headerIndex.get("Image Name")]).replace(' ', '_').replace('-', '_').replace("'", "").toLowerCase();
                ornamentalArray.add(aOrnamental);
            }
        } catch (Exception e){
            System.out.println("Error");
        }

        commodityArray = new ArrayList<Commodity>(fruitArray);
        commodityArray.addAll(vegetableArray);
        commodityArray.addAll(ornamentalArray);

    }
}
