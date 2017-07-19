package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
        setTitle(commoditySelected.name);
        setContentView(R.layout.activity_category);

        Button maturityButton = (Button) findViewById(R.id.maturityButton);
        maturityButton.setText(MainActivity.maturityLanguages[MainActivity.languageSelected]);

        Button temperatureButton = (Button) findViewById(R.id.temperatureButton);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    /*
        Onclick function to prepare next activity with maturity information.
        Input: View of the maturity button.
     */
    public void maturityClicked(View view) {
        String information = "";
        if (commoditySelected instanceof Fruit) {
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
        InformationActivity.label = MainActivity.maturityLanguages[MainActivity.languageSelected];
        InformationActivity.information = information;
        startActivity(intent);
    }

    /*
        Onclick function to prepare next activity with temperature information.
        Input: View of the temperature button.
     */
    public void temperatureClicked(View view) {
        String information = "";
        if (commoditySelected instanceof Fruit) {
            Fruit fruit = (Fruit) commoditySelected;
            information = fruit.temperature;
        }
        else if (commoditySelected instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) commoditySelected;
            information = vegetable.temperature;
        }

        Intent intent = new Intent(this, InformationActivity.class);
        InformationActivity.label = MainActivity.temperatureLanguages[MainActivity.languageSelected];
        InformationActivity.information = information;
        startActivity(intent);
    }

    /*
        Onclick function to prepare next activity with disorder information.
        Input: View of the disorder button.
     */
    public void disordersClicked(View view) {
        String information = "";
        if (commoditySelected instanceof Fruit) {
            Fruit fruit = (Fruit) commoditySelected;
            information = fruit.disorders;
        }
        else if (commoditySelected instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) commoditySelected;
            information = vegetable.disorders;
        }

        Intent intent = new Intent(this, InformationActivity.class);
        InformationActivity.label = MainActivity.disorderLanguages[MainActivity.languageSelected];
        InformationActivity.information = information;
        startActivity(intent);
    }
}
