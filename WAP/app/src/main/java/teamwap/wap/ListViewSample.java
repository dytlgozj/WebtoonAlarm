package teamwap.wap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by JoengUm Lee on 2016-12-16.
 */


public class ListViewSample extends Activity {
    String[] data = {"A", "B", "C", "D"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewsample);

        // ListView list = (ListView) findViewById(R.id.list);
        // ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        // list.setAdapter(adapter);
    }
}