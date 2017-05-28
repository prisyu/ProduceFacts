package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ListProduceActivity extends AppCompatActivity {

    public static List<Commodity> commodities = new ArrayList<Commodity>(MainActivity.fruitArray);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produce);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, "ListProduceActivity", commodities));
        gridview.setNumColumns(2);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println("Commodity clicked = ");
                System.out.println(commodities.get(position).name);
                Intent intent = new Intent(ListProduceActivity.this, CategoryActivity.class);
                CategoryActivity.commoditySelected = commodities.get(position);
                startActivity(intent);
            }
        });
    }
}
