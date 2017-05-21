package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by pryu on 5/12/2017.
 */

public class CategoryActivity extends AppCompatActivity {

    private int languageSelected;
    private static final String maturityLanguages[] = {"Maturity & Quality", "Cosecha y Calidad", "Maturity & Quality"};
    private static final String temperatureLanguages[] = {"Temperature & Controlled Atmosphere", "Temperatura y Atmosfera Controlada", "Temperature & Controlled Atmosphere"};
    private static final String disorderLanguages[] = {"Disorders", "Desordenes", "Disorders"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        languageSelected = MainActivity.languageSelected;

        Button maturityButton = (Button) findViewById(R.id.maturityButton);
        maturityButton.setText(maturityLanguages[languageSelected]);

        Button temperatureButton = (Button) findViewById(R.id.temperatureButton);
        temperatureButton.setText(temperatureLanguages[languageSelected]);

        Button disorderButton = (Button) findViewById(R.id.disorderButton);
        disorderButton.setText(disorderLanguages[languageSelected]);
    }

    protected void categorySelected(View view){
        Button button = (Button) view;
        String selected = (String) button.getText();


        if (selected == maturityLanguages[languageSelected]){

        }
        else if(selected == temperatureLanguages[languageSelected]) {

        }
        else if(selected == disorderLanguages[languageSelected]){

        }

        //Intent intent = new Intent(this, ListProduceActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, produceType);
        //startActivity(intent);
    }
}
