package com.noveogroup.task5;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MimimiActivity extends ListActivity {

    private MimimiAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<Bitmap> bitmaps = new ArrayList<Bitmap>();

        try {
            for (String path: getAssets().list("")) {
                if (path.endsWith(".jpg")) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(getAssets().open(path), null, options);

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int reqWidth = displayMetrics.widthPixels;
                    int reqHeight = getResources().getDimensionPixelSize(R.dimen.item_height);
                    options.inSampleSize = calcInSampleSize(options, reqWidth, reqHeight);

                    options.inJustDecodeBounds = false;
                    bitmaps.add(BitmapFactory.decodeStream(getAssets().open(path)));
                }
            }
        } catch (Exception e) {
            Log.e("Mimimi", "Something strange: ", e);
        } catch (OutOfMemoryError err) {
            Log.e("Mimimi", "Houston, we have an out of…");
        }

        adapter = new MimimiAdapter(this, bitmaps);
        setListAdapter(adapter);
    }

    public static int calcInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
