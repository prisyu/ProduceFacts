package postharvest.ucdavis.edu.producefacts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.color;

/**
 * Created by pryu on 5/12/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String contextType;
    //private final List<Commodity> commodities;
    //private final List<CommodityImage> commodityImages;

    private final List images;

    public ImageAdapter(Context c, String ct, List commodities) {
        this.mContext = c;
        this.contextType = ct;
        this.images = commodities;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_commodity_item, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.commodity_image);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.commodity_name);

        if (contextType == "ListProduceActivity"){
            final Commodity commodity = (Commodity) images.get(position);

            imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), mContext.getResources().getIdentifier(commodity.image, "drawable", mContext.getPackageName()), 150, 150 ));
            imageView.setMaxHeight(150);
            imageView.setBackgroundColor(mContext.getResources().getColor(color.white));
            nameTextView.setText(commodity.name);
            nameTextView.setBackgroundColor(mContext.getResources().getColor(color.white));
        }
        else if (contextType == "InformationActivity"){
            final CommodityImage comImage = (CommodityImage)images.get(position);

            imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), mContext.getResources().getIdentifier(comImage.image_name, "drawable", mContext.getPackageName()), 100, 100 ));
            imageView.setMaxHeight(100);
            imageView.setBackgroundColor(mContext.getResources().getColor(color.holo_blue_dark));
        }

        return convertView;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
