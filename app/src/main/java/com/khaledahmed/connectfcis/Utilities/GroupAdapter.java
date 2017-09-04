package com.khaledahmed.connectfcis.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khaledahmed.connectfcis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khaled Ahmed on 2/24/2017.
 */
public class GroupAdapter extends ArrayAdapter {

    List list = new ArrayList();

    static class GroupDataHandler {
        ImageView photo;
        TextView name;
        TextView price;
    }

    public GroupAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void addItem(Object object) {
        super.add(object);
        list.add(object);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        View row;
        row = view;
        GroupDataHandler dataHandler = new GroupDataHandler();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.group_item, viewGroup, false);
            dataHandler.photo = (ImageView) row.findViewById(R.id.group_photo);
            dataHandler.name = (TextView) row.findViewById(R.id.groupItemNameTxt);
            dataHandler.price = (TextView) row.findViewById(R.id.groupItemTitleTxt);
            row.setTag(dataHandler);
        } else {
            dataHandler = (GroupDataHandler) row.getTag();
        }
        GroupDataProvider groupDataProvider;
        groupDataProvider = (GroupDataProvider) this.getItem(position);
        Bitmap img = BitmapFactory.decodeByteArray(groupDataProvider.getGroupPhoto(), 0, groupDataProvider.getGroupPhoto().length);
        dataHandler.photo.setImageBitmap(img);
        dataHandler.name.setText(groupDataProvider.getGroupName());
        dataHandler.price.setText(String.valueOf(groupDataProvider.getGroupDescription()));
        return row;
    }
}
