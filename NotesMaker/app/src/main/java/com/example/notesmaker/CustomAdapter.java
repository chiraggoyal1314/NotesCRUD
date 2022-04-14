package com.example.notesmaker;//package com.example.notesmaker;
//
//import android.content.ClipData;
//import android.content.Context;
//import android.media.Image;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.zip.Inflater;
//
//public class CustomAdapter extends BaseAdapter {
//    Context context;
//    List<String> headings, subheadings;
//
//    public CustomAdapter(Context context, List<String> headings, List<String> subheadings) {
//        this.context = context;
//        this.headings = headings;
//        this.subheadings = subheadings;
////        List<String> headings = notes.stream().map(x -> x.title).collect(Collections.toList());
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return headings.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
////        view = inflter.inflate(R.layout.activity_listview, null);
////        TextView heading = (TextView) view.findViewById(R.id.heading);
////        TextView notes = (TextView) view.findViewById(R.id.subheading);
////        return view;
//        if (view == null) {
//            view = LayoutInflater.from(context).
//                    inflate(R.layout.activity_listview, viewGroup, false);
//        }
//
//
//        // get the TextView for item name and item description
//        TextView heading = (TextView) view.findViewById(R.id.heading);
//        TextView notes = (TextView) view.findViewById(R.id.subheading);
//
//        //sets the text for item name and item description from the current item object
//        heading.setText(headings);
//        notes.setText(subheadings);
//
//        // returns the view for the current row
//        return view;
//    }
//};


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Note> implements View.OnClickListener{

    Context mContext;
    ArrayList<Note> notes;

    // View lookup cache
    private static class ViewHolder {
        TextView heading;
        TextView subheading;
    }

    public CustomAdapter(ArrayList<Note> data, Context context) {
        super(context, R.layout.activity_listview, data);
        this.mContext=context;
        this.notes = data;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Note dataModel= getItem(position);
    }

    public void updateNotes(ArrayList<Note> data){
        notes.clear();
        notes.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Note dataModel = notes.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.heading);
            viewHolder.subheading = (TextView) convertView.findViewById(R.id.subheading);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.heading.setText(dataModel.getTitle());
        viewHolder.subheading.setText(dataModel.getNotes());
        return result;
    }
}
