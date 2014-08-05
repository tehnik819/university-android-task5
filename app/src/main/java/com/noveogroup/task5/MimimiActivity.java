package com.noveogroup.task5;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MimimiActivity extends ListActivity {

    private MimimiAdapter adapter;
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ListView listView;
    private ArrayList<String> data;
    private int reqWidth;
    private int reqHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = getListView();

        final BitmapFactory.Options options = new BitmapFactory.Options();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        reqWidth = displayMetrics.widthPixels;
        reqHeight = getResources().getDimensionPixelSize(R.dimen.item_height);

        adapter = new MimimiAdapter(this, bitmaps);
        setListAdapter(adapter);

        data = getValidPaths();
        for(int i = 0;i < data.size();i++) {
            bitmaps.add(i, null);
        }
        adapter.notifyDataSetChanged();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                changeImages(options, firstVisibleItem, firstVisibleItem + visibleItemCount, totalItemCount);
            }
        });
    }

    @Override
    protected void onDestroy() {
        for(Bitmap bitmap : bitmaps) {
            if(bitmap != null) {
                bitmap.recycle();
            }
        }
        adapter.clear();
        super.onDestroy();
    }

    private void changeImages(BitmapFactory.Options options ,int firstPosition, int lastPosition, int totalItemCount) {
        try {
            int prevPosition = firstPosition - 1;
            if(prevPosition >= 0 && bitmaps.get(prevPosition) != null) {
                bitmaps.get(prevPosition).recycle();
                bitmaps.set(prevPosition, null);
            }
            if(lastPosition < totalItemCount && bitmaps.get(lastPosition) != null) {
                bitmaps.get(lastPosition).recycle();
                bitmaps.set(lastPosition, null);
            }
            for(int i = firstPosition; i < lastPosition;i++) {
                if (bitmaps.get(i) == null) {
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(getAssets().open(data.get(i)), null, options);
                    options.inSampleSize = calcInSampleSize(options, reqWidth, reqHeight);
                    options.inJustDecodeBounds = false;
                    bitmaps.set(i, BitmapFactory.decodeStream(getAssets().open(data.get(i))));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e("Mimimi", "Something strange: ", e);
        } catch (OutOfMemoryError err) {
            Log.e("Mimimi", "Houston, we have an out of…");
        }
    }

    private ArrayList<String> getValidPaths() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            String[] paths = getAssets().list("");
            for(String str : paths) {
                if(str.endsWith(".jpg")) {
                    result.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
