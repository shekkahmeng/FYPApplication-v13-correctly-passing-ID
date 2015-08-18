package com.shekkahmeng.fypapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.shekkahmeng.fypapplication.db.DownloadedEvent;
import com.shekkahmeng.fypapplication.utility.ImageUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shekkahmeng on 7/16/2015.
 */
public class MainActivityAdapter extends BaseAdapter implements Filterable {

    LayoutInflater inflater;
    List<DownloadedEvent> items;
    List<DownloadedEvent> mitems;
    ValueFilter valueFilter;
    Bitmap defaultBitmap;

    public MainActivityAdapter(Activity context, List<DownloadedEvent> items) {
        defaultBitmap = ImageUtility.getDefaultCircleBitmap(context.getResources());

        this.items = items;
        this.mitems = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void remove(DownloadedEvent item) {
        items.remove(item);
        mitems.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DownloadedEvent getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_main_row, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.main_row_image);
        TextView tvName = (TextView) convertView.findViewById(R.id.main_row_name);
        TextView tvDate = (TextView) convertView.findViewById(R.id.main_row_date);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.main_row_location);
        TextView tvJoined = (TextView) convertView.findViewById(R.id.main_row_join);

        DownloadedEvent item = items.get(position);
        tvName.setText(item.getEventName());
        tvDate.setText(item.getEventDate());
        tvLocation.setText(item.getEventLocation());

        if (item.isEventJoin()) {
            tvJoined.setVisibility(View.VISIBLE);
        } else {
            tvJoined.setVisibility(View.INVISIBLE);
        }

        byte[] imageAsBytes = Base64.decode(item.getEventImage().getBytes(), Base64.DEFAULT);
        imageView.setImageBitmap(ImageUtility.createCircleBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length), defaultBitmap));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<DownloadedEvent> filterList = new ArrayList<DownloadedEvent>();
                for (int i = 0; i < mitems.size(); i++) {
                    if ((mitems.get(i).getEventName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mitems.get(i));
                    }
                }

                filterResults.count = filterList.size();
                filterResults.values = filterList;
            } else {
                filterResults.count = mitems.size();
                filterResults.values = mitems;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<DownloadedEvent>) results.values;
            notifyDataSetChanged();
        }
    }
}
