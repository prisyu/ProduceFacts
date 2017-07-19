package postharvest.ucdavis.edu.producefacts;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ListProduceActivity extends AppCompatActivity {

    public static List<Commodity> commodities;

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

        // set up search bar
        SearchView searchView = (SearchView) findViewById(R.id.search_commodity);
        searchView.setQueryHint("search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    System.out.println("Searching for " + query);
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
        Search for all of the commodities that contain the input string and start next activity.
        Input: The string inputted by user to search for.
    */
    protected void searchCommodities(String searchString) {
        List<Commodity> commodityArrayForSearchResult = new ArrayList<Commodity>();
        for (Commodity commodity : commodities) {
            if (commodity.name.toLowerCase().contains(searchString.toLowerCase())) {
                commodityArrayForSearchResult.add(commodity);
            }
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, "ListProduceActivity", commodityArrayForSearchResult));
        gridview.setNumColumns(2);
    }
}
