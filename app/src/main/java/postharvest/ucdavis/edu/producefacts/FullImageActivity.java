package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pryu on 5/27/2017.
 */

public class FullImageActivity extends AppCompatActivity {
    public static String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_commodity_item);

        final ImageView imageView = (ImageView) findViewById(R.id.commodity_image);
        final TextView textView = (TextView) findViewById(R.id.commodity_name);

        textView.setText("");
        //imageView.setImageBitmap(ImageAdapter.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imageName, "drawable", getPackageName()), 150, 150 ));
        imageView.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));

    }
}
