package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    public static final String menuItemLanguages[][] = {{"Fruit", "Vegetables", "Ornamentals"},{"Frutas", "Vegetales", "Ornamentales"},{"Fruit", "Les Légume", "Ornamentals"}};
    //menu item is ONLY for set title.  The arrays below are for the actual parsing
    public static final String recommendationLanguages[] = {"Recommendations for Maintaining Postharvest Quality","Recomendaciones para Mantener la Calidad Postcosecha", "Recommendations for Maintaining Postharvest Quality"};

    public static final String languages[] = {"English", "Spanish", "French"};
    public static final int languageSelected = 0;
    public static final int produceSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void produceSelected(View view){
        Button button = (Button) view;
        String produceType = button.getText().toString();
        System.out.println("Selected " + produceType);

        Intent intent = new Intent(this, ListProduceActivity.class);
        intent.putExtra(EXTRA_MESSAGE, produceType);
        startActivity(intent);
    }
}
