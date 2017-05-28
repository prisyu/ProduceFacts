package postharvest.ucdavis.edu.producefacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

/**
 * Created by pryu on 5/21/2017.
 */

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_loading);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
