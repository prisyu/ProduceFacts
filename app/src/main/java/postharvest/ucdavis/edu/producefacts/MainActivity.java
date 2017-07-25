package postharvest.ucdavis.edu.producefacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static List<Commodity> commodityArray;
    public static List<Fruit> fruitArray;
    public static List<Vegetable> vegetableArray;
    public static List<Ornamental> ornamentalArray;

    public static final String menuItemLanguages[][] = {{"Fruit", "Vegetables", "Ornamentals"}, {"Frutas", "Vegetales", "Ornamentales"}, {"Fruit", "Les Légume", "Ornamentals"}};
    //menu item is ONLY for set title.  The arrays below are for the actual parsing
    public static final String recommendationLanguages[] = {"Recommendations for Maintaining Postharvest Quality", "Recomendaciones para Mantener la Calidad Postcosecha", "Recommendations for Maintaining Postharvest Quality"};
    public static final String maturityLanguages[] = {"Maturity & Quality", "Cosecha y Calidad", "Maturity & Quality"};
    public static final String temperatureLanguages[] = {"Temperature & Controlled Atmosphere", "Temperatura y Atmosfera Controlada", "Temperature & Controlled Atmosphere"};
    public static final String disorderLanguages[] = {"Disorders", "Desordenes", "Disorders"};

    public static final String languages[] = {"English", "Spanish", "French"};
    public static int languageSelected = Language.English;
    private AlertDialog languageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButtons();
        TextView websiteLink = (TextView) findViewById(R.id.websiteLink);
        websiteLink.setMovementMethod(LinkMovementMethod.getInstance());
        websiteLink.setText(Html.fromHtml(getResources().getString(R.string.websiteLink)));

        // set up search bar
        SearchView searchView = (SearchView) findViewById(R.id.search_main);
        searchView.setQueryHint("search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchCommodities(query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // load csv files
        loadFiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_language) {
            if (languageDialog == null) {
                // Set up language dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Please Select a Language")
                        .setSingleChoiceItems(languages, languageSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item

                                if (which != languageSelected) {
                                    //loadProgressScreen();
                                    switch (which) {
                                        case Language.English:
                                            languageSelected = Language.English;
                                            break;
                                        case Language.Spanish:
                                            languageSelected = Language.Spanish;
                                            break;
                                        case Language.French:
                                            languageSelected = Language.French;
                                            break;
                                        default:
                                            System.out.println("cannot choose new language");
                                    }

                                    loadFiles();
                                    updateButtons();
                                    dialog.dismiss();
                                }
                            }
                        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                languageDialog = builder.create();
            }

            languageDialog.show();
            return true;
        }
        return false;
    }

    /*
        Determine which produce button was clicked and prepare for next activity.
        Input: The view clicked by the user.
     */
    public void produceSelected(View view) {
        Button button = (Button) view;
        String produceType = button.getText().toString();

        Intent intent = new Intent(this, ListProduceActivity.class);
        if (produceType == menuItemLanguages[languageSelected][0]) {
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

    /*
        Update the language of the three button labels.
     */
    public void updateButtons(){
        Button fruitButton = (Button) findViewById(R.id.fruitButton);
        Button vegetableButton = (Button) findViewById(R.id.vegetableButton);
        Button ornamentalButton = (Button) findViewById(R.id.ornamentalButton);

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
    }

    /*
        Load the csv files into the Fruit, Vegetable, and Ornamtenal arrays.
     */
    public void loadFiles() {
        // actual csv parsing
        try {
            fruitArray = new ArrayList<Fruit>();
            String filename =   getResources().getString(R.string.fruitFile) + languages[languageSelected].toLowerCase();
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier(filename,
                            "raw", getPackageName()));

            CSVFile csvFile = new CSVFile(inputStream);
            for (String[] row : csvFile.keyedRows) {
                Fruit aFruit = new Fruit();
                aFruit.name = row[csvFile.headerIndex.get("Name")];
                aFruit.recommendations = row[csvFile.headerIndex.get(recommendationLanguages[languageSelected])];
                aFruit.maturity = row[csvFile.headerIndex.get(maturityLanguages[languageSelected])];
                aFruit.temperature = row[csvFile.headerIndex.get(temperatureLanguages[languageSelected])];
                aFruit.disorders = row[csvFile.headerIndex.get(disorderLanguages[languageSelected])];
                aFruit.image = (getResources().getString(R.string.fruitFile) + row[csvFile.headerIndex.get("Image Name")]).replace(' ', '_').replace('-', '_').replace("'", "").toLowerCase();
                fruitArray.add(aFruit);
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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
            for (String[] row : csvFile.keyedRows) {
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

    /*
        Search for all of the commodities that contain the input string and start next activity.
        Input: The string inputted by user to search for.
     */
    protected void searchCommodities(String searchString) {
        List<Commodity> commodityArrayForSearchResult = new ArrayList<Commodity>();
        for (Commodity commodity : commodityArray) {
            if (commodity.name.toLowerCase().contains(searchString.toLowerCase())) {
                commodityArrayForSearchResult.add(commodity);
            }
        }

        Intent intent = new Intent(this, ListProduceActivity.class);
        ListProduceActivity.commodities = commodityArrayForSearchResult;
        startActivity(intent);
    }
}
