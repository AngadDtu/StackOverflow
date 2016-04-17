package com.example.angad.stackoverflow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAsyncTaskInterface{


    AutoCompleteTextView autocomplete;

    String[] arr = { "Android","Python","Java","C/C++"};
    ArrayList<item>items=new ArrayList<>();
    String urlString;
    ProgressDialog progress;
    CustomArrayAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(1);
        autocomplete.setAdapter(adapter);
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = adapter.getItem(position);
                // Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                  OnStackOverflow(s);



            }
        });

       ListView listView=(ListView)findViewById(R.id.listview);
        ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams)listView.getLayoutParams();
        listViewParams.height = items.size();
        listView.requestLayout();
       adapter1= new CustomArrayAdapter(MainActivity.this,items);
        listView.setAdapter(adapter1);

    }

    private void OnStackOverflow(String s) {
        urlString = getURLString(s);
        ItemAsyncTask task = new ItemAsyncTask();
        task.listener =MainActivity.this;
        task.execute(urlString);
        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Loading...Please Wait!!");
        progress.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OnStackOverflow("Android");
    }

    private String getURLString(String s) {
        if(s=="Android"){
            return "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=False&answers=0&tagged=android&site=stackoverflow";
        }
        else{
return "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&site=stackoverflow";
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_fav) {
            Intent i = new Intent();
            i.setClass(this, FavActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
//

    @Override
    public void fetchItem(item[] i) {

        items.clear();
        for(item object : i){
            items.add(object);
        }
        adapter1.notifyDataSetChanged();
        progress.dismiss();


    }
}