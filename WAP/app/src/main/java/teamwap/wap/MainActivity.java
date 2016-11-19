package teamwap.wap;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Button button1;
    Button button2;
    int toonNum = 1;

    ArrayList<teamwap.wap.webtoonIn> webtoonInL = new ArrayList<teamwap.wap.webtoonIn>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher2);

        button1 = (Button) findViewById(R.id.button1);
        button1.setBackgroundColor(Color.BLACK);
        button1.setTextColor(Color.WHITE);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "네이버 웹툰 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://comic.naver.com/webtoon/weekday.nhn"));
                startActivity(mIntent);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setBackgroundColor(Color.BLACK);
        button2.setTextColor(Color.WHITE);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "가우스 전자 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                Intent mIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.comic.naver.com/webtoon/list.nhn?titleId=675554&week=thu"));
                startActivity(mIntent2);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("웹툰 이름 입력");
                final EditText etURL = new EditText(MainActivity.this);
                etURL.setHint("웹툰 URL 입력");

                layout.addView(etName);
                layout.addView(etURL);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("웹툰 추가")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etName.getText().toString();
                                String url = etURL.getText().toString();

                                webtoonIn webtoon = new webtoonIn(toonNum, name, url);
                                toonNum++;
                                webtoonInL.add(webtoon);

                                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Intent set = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(set);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
