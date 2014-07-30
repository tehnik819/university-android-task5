package com.noveogroup.task5;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
}
