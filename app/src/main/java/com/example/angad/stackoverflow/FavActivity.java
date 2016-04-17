package com.example.angad.stackoverflow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {
    CustomArrayAdapter1 adapter1;
    public item[] getFavFiles() {
        FileManagerHelper helper = new FileManagerHelper(this, null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
String [] columns={FileManagerHelper.DISPLAY_NAME,FileManagerHelper.PROFILE_PIC,FileManagerHelper.TITLE,FileManagerHelper.TAGS,FileManagerHelper.SCORE,FileManagerHelper.LINK,FileManagerHelper.TIME};
        Cursor c = db.query(FileManagerHelper.FAV_TABLE, null, null, null, null, null, null);

        item[] output = new item[c.getCount()];
        int i = 0;
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(FileManagerHelper.DISPLAY_NAME));
            String pic=c.getString(c.getColumnIndex(FileManagerHelper.PROFILE_PIC));
            String title=c.getString(c.getColumnIndex(FileManagerHelper.TITLE));
           String tags=c.getString(c.getColumnIndex(FileManagerHelper.TAGS));
            String link=c.getString(c.getColumnIndex(FileManagerHelper.LINK));
            int score=c.getInt(c.getColumnIndex(FileManagerHelper.SCORE));
            long time=c.getLong(c.getColumnIndex(FileManagerHelper.TIME));
           // profile_image, display_name, score, last_activity_date, link, title,tags[]
            item k=new item(pic,name,score,time,link,title);
            output[i++]=k;
        }
        return output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Intent getIn=getIntent();
        item[] items = getFavFiles();
        ArrayList<item> files = new ArrayList<>();
        String image;
        String name;
        int score;
        String link;
        String title;
        long last_date;
        String tags;
        for (int i = 0; i < items.length; i++) {
            image=items[i].getProfile_image();
            name=items[i].getDisplay_name();
            score=items[i].getScore();
            link=items[i].getLink();
            title=items[i].getTitle();
            last_date=items[i].getLast_activity_date();
            //tags=items[i].getTags();
            item j=new item(image,name,score,last_date,link,title);
            files.add(j);
        }
        adapter1=new CustomArrayAdapter1(this,files);
        ListView lv = (ListView) findViewById(R.id.likedList);
        lv.setAdapter(adapter1);

    }
}
