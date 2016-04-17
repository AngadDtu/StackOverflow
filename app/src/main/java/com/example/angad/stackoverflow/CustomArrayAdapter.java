package com.example.angad.stackoverflow;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Angad on 17/04/16.
 */
public class CustomArrayAdapter extends ArrayAdapter<item> {
Context context;
    ArrayList<item>items=null;
    public CustomArrayAdapter(Context context, ArrayList<item> items) {
        super(context,0, items);
        this.context=context;
        this.items=items;
    }
    public static class MyViewHolder{
        ImageView profile_pic;
        TextView title;
        TextView tags;
        TextView name;
        TextView count;
        TextView mins;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context,R.layout.cardview,null);
            MyViewHolder vh=new MyViewHolder();
            vh.profile_pic= (ImageView) convertView.findViewById(R.id.profilePic);
            vh.title=(TextView)convertView.findViewById(R.id.title);
            vh.tags=(TextView)convertView.findViewById(R.id.tag);
            vh.name=(TextView)convertView.findViewById(R.id.name);
            vh.count=(TextView)convertView.findViewById(R.id.scoreCount);
            vh.mins=(TextView)convertView.findViewById(R.id.time);
            convertView.setTag(vh);
        }
       final item i=getItem(position);
        MyViewHolder v=(MyViewHolder)convertView.getTag();
        String s=null;
        if(i.getTitle().length()>=25){
             s=i.getTitle().substring(0,24);
            v.title.setText(s+"...");
        }

        String s1=i.getDisplay_name();
        if(s1.length()>=10){
            s1=s1.substring(0,9);
            v.name.setText(s1+"...");
        }
        else{
            v.name.setText(s1);
        }

        v.tags.setText(i.getTags());
        v.count.setText(String.valueOf(i.getScore()));
        long time1 = System.currentTimeMillis()/1000;
        long uptime=time1+i.getLast_activity_date();
        int minutes = (int) ((uptime % 3600) / 60);

        v.mins.setText(String.valueOf(minutes));
        Picasso.with(context).load(i.getProfile_image()).into(v.profile_pic);
ImageView link=(ImageView)convertView.findViewById(R.id.linkButton);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(i.getLink()));
                context.startActivity(myIntent);
            }
        });
        ImageView like=(ImageView)convertView.findViewById(R.id.likeButton);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagerHelper helper = new FileManagerHelper(context, null, 1);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(FileManagerHelper.DISPLAY_NAME,i.getDisplay_name());
                cv.put(FileManagerHelper.LINK,i.getLink());
                cv.put(FileManagerHelper.PROFILE_PIC,i.getProfile_image());
                cv.put(FileManagerHelper.TAGS,i.getTags());
                cv.put(FileManagerHelper.SCORE,i.getScore());
                cv.put(FileManagerHelper.TIME,i.getLast_activity_date());
                db.insert(FileManagerHelper.FAV_TABLE, null, cv);

            }
        });

    return convertView;
    }
}
