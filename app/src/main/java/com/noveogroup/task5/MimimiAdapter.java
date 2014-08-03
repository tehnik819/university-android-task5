package com.noveogroup.task5;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class MimimiAdapter extends ArrayAdapter<Bitmap> {

    public MimimiAdapter(Context context, Bitmap[] bitmaps) {
        super(context, R.layout.list_item, 0, bitmaps);
    }

    public MimimiAdapter(Context context, List<Bitmap> bitmaps) {
        super(context, R.layout.list_item, 0, bitmaps);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.image_item);
            convertView.setTag(imageView);
        }

        Bitmap bitmap = getItem(position);
        if(imageView == null) {
            imageView = (ImageView) convertView.getTag();
        }

        if(bitmap != null) {
            imageView.setImageBitmap(getItem(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        return convertView;
    }
}
