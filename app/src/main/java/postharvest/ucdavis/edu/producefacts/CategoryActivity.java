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

    public static Commodity commoditySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Button maturityButton = (Button) findViewById(R.id.maturityButton);
        maturityButton.setText(MainActivity.maturityLanguages[MainActivity.languageSelected]);

        Button temperatureButton = (Button) findViewById(R.id.temperatureButton);
        /*temperatureButton.setText(MainActivity.temperatureLanguages[MainActivity.languageSelected]);*/

        Button disorderButton = (Button) findViewById(R.id.disorderButton);
        if (commoditySelected instanceof Ornamental) {
            temperatureButton.setVisibility(View.GONE);
            disorderButton.setVisibility(View.GONE);
        }
        else {
            temperatureButton.setVisibility(View.VISIBLE);
            temperatureButton.setText(MainActivity.temperatureLanguages[MainActivity.languageSelected]);

            disorderButton.setVisibility(View.VISIBLE);
            disorderButton.setText(MainActivity.disorderLanguages[MainActivity.languageSelected]);
        }
    }


    public void maturityClicked(View view){
        String information = "";
        if (commoditySelected instanceof Fruit){
            Fruit fruit = (Fruit) commoditySelected;
            information = fruit.maturity;
        }
        else if (commoditySelected instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) commoditySelected;
            information = vegetable.maturity;
        }
        else if (commoditySelected instanceof Ornamental) {
            Ornamental ornamental = (Ornamental) commoditySelected;
            information = ornamental.maturity;
        }
        Intent intent = new Intent(this, InformationActivity.class);
        InformationActivity.information = information;
        startActivity(intent);
    }

    public void temperatureClicked(View view){
        String information = "";
        if (commoditySelected instanceof Fruit){
            Fruit fruit = (Fruit) commoditySelected;
            information = fruit.temperature;
        }
        else if (commoditySelected instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) commoditySelected;
            information = vegetable.temperature;
        }
        /*else if (commoditySelected instanceof Ornamental) {
            Ornamental ornamental = (Ornamental) commoditySelected;
            information = ornamental.maturity;
        }*/

        Intent intent = new Intent(this, InformationActivity.class);
        InformationActivity.information = information;
        startActivity(intent);
    }

    public void disordersClicked(View view) {
        String information = "";
        if (commoditySelected instanceof Fruit){
            Fruit fruit = (Fruit) commoditySelected;
            information = fruit.disorders;
        }
        else if (commoditySelected instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) commoditySelected;
            information = vegetable.disorders;
        }
        /*else if (commoditySelected instanceof Ornamental) {
            Ornamental ornamental = (Ornamental) commoditySelected;
            information = ornamental.maturity;
        }*/

        Intent intent = new Intent(this, InformationActivity.class);
        InformationActivity.information = information;
        startActivity(intent);
    }
}
