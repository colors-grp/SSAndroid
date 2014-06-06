package com.colors.supersaym;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public GuideAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
    	Log.v("data size", data.size()+"");
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.activity_guide_list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.title); // title
        TextView time = (TextView)vi.findViewById(R.id.time); // time
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> mosalsal = new HashMap<String, String>();
        mosalsal = data.get(position);
        
        // Setting all values in listview
        name.setText(mosalsal.get(GuideActivity.KEY_NAME));
        time.setText(mosalsal.get(GuideActivity.KEY_TIME));
        imageLoader.displayImage(mosalsal.get(GuideActivity.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}