package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pryu on 5/27/2017.
 */

public class InformationActivity extends AppCompatActivity {
    public static String label;
    public static String information;
    private List<CommodityImage> image_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(label);
        setContentView(R.layout.activity_information);

        System.out.println("Information Activity...");
        System.out.println(information);

        parseInformation();
        System.out.println("after parsing...");
        System.out.println(information);

        WebView infoWebView = (WebView) findViewById(R.id.info_web);
        infoWebView.loadData(information, "text/html", null);

        GridView gridview = (GridView) findViewById(R.id.info_grid);
        gridview.setAdapter(new ImageAdapter(this, "InformationActivity", image_arr));
        gridview.setNumColumns(gridview.getAdapter().getCount());

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println("Commodity Image clicked = ");
                System.out.println(image_arr.get(position).image_name);

                Intent intent = new Intent(InformationActivity.this, FullImageActivity.class);
                FullImageActivity.imageName = image_arr.get(position).image_name;
                startActivity(intent);
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

    protected void parseInformation(){
        image_arr = new ArrayList<CommodityImage>();

        information = processText(information);

    }

    // Input of file names:
    // <tag = "stuff">file*1902831*</tag>
    protected String parseFileName(String str){
        String output = "";
        boolean rightarrow = false;

        for (char c : str.toCharArray()) {
            if (c == '>'){
                rightarrow = true;
                continue;
            }
            if (!rightarrow) {
                continue;
            }
            if (c >= '0' && c <= '9' && rightarrow == true) {
                output += c;
            }
            if (!output.isEmpty() && !(c >= '0' && c <= '9')) {
                return output;
            }
        }

        return output;
    }

    protected String parse(String str){
        String [] output = str.split(": ");
        return output[1];
    }

    protected String processText(String info){
        String output = "";

        String[] splitting = info.split("\n");
        for(String str : splitting){
            if (str.contains("<table ")){
                output += "<table>";
                continue;
            }

            if (str.contains("<p>*file")){
                CommodityImage comImage = new CommodityImage();
                comImage.image_name = parseFileName(str);
                if (comImage.image_name != ""){
                    comImage.image_name = getResources().getString(R.string.photoFile) + comImage.image_name;
                    image_arr.add(comImage);
                }
            }
            else if (str.contains("<p>Title: ")) {
                if (!image_arr.isEmpty()){
                    CommodityImage temp = image_arr.get(image_arr.size() - 1);
                    temp.title = parse(str);
                }
            }
            else if (str.contains("<p>Photo Credit: ")){
                if (!image_arr.isEmpty()){
                    CommodityImage temp = image_arr.get(image_arr.size() - 1);
                    temp.photo_credit = parse(str);
                }
            }
            else if(str.contains("Photos")){
                //skip
            }
            else {
                output += str;
            }
        }

        output = "<font face=\"verdana\">" + output + "</font>";

        return output;
    }

}
