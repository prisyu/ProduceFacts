package postharvest.ucdavis.edu.producefacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by pryu on 5/27/2017.
 */

public class FullImageActivity extends AppCompatActivity {
    public static String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(InformationActivity.label);
        setContentView(R.layout.activity_full_image);

        System.out.println("displaying image = " + imageName);
        final PhotoView imageView = (PhotoView) findViewById(R.id.full_image);
        imageView.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
