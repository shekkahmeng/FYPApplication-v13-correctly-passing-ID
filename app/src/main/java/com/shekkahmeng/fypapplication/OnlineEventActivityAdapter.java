package com.shekkahmeng.fypapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.shekkahmeng.fypapplication.rest.OnlineEvent;
import com.shekkahmeng.fypapplication.utility.ImageUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by shekkahmeng on 7/16/2015.
 */
public class OnlineEventActivityAdapter extends BaseAdapter implements Filterable {

    LayoutInflater inflater;
    List<OnlineEvent> items;
    List<OnlineEvent> mitems;
    Set<Long> downloadedEventIDs;
    ValueFilter valueFilter;
    Bitmap defaultBitmap;

    public OnlineEventActivityAdapter(Activity context, List<OnlineEvent> items, Set<Long> downloadedEventIDs) {
        defaultBitmap = ImageUtility.getDefaultCircleBitmap(context.getResources());

        this.items = items;
        this.mitems = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.downloadedEventIDs = downloadedEventIDs;
    }

    public void setItems(List<OnlineEvent> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public OnlineEvent getEvent(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_online_event_row, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.online_event_row_image);
        TextView tvName = (TextView) convertView.findViewById(R.id.online_event_row_name);
        TextView tvDate = (TextView) convertView.findViewById(R.id.online_event_row_date);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.online_event_row_location);
        TextView tvDownloaded = (TextView) convertView.findViewById(R.id.online_event_row_downloaded);

        OnlineEvent item = items.get(position);
        tvName.setText(item.getEventName());
        tvDate.setText(item.getEventDate());
        tvLocation.setText(item.getEventLocation());

        if (downloadedEventIDs.contains(item.getEventId())) {
            tvDownloaded.setVisibility(View.VISIBLE);
        } else {
            tvDownloaded.setVisibility(View.INVISIBLE);
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
                List<OnlineEvent> filterList = new ArrayList<OnlineEvent>();
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
            items = (List<OnlineEvent>) results.values;
            notifyDataSetChanged();
        }
    }
}
