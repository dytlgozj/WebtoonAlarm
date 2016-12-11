package teamwap.wap;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static teamwap.wap.R.id.textView;


public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;

    ArrayList<webtoonIn> webtoonInL = new ArrayList<webtoonIn>();
    File f;
    private GoogleApiClient client;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher2);

        backPressCloseHandler = new BackPressCloseHandler(this);

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


        /*
        데이터 불러오는 샘플용
        콜렉션 오류. 해결 요망
         */
        /**button3.setOnClickListener(new View.OnClickListener(){

        @Override public void onClick(View v){
        int i;

        f = new java.io.File(getFilesDir(),"webtoonInfor.dat");

        try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        ArrayList list = ois.readObject();

        for (i=0; i < list.size(); i++){
        webtoonInL.add(list.get(i));
        }

        }catch(IOException ioe){
        }

        Toast.makeText(getApplicationContext(), webtoonInL.get(0).get_name(), Toast.LENGTH_SHORT).show();
        }
        });*/

        /* 지금은 테스트 버튼이지만 새롭게 웹툰이 올라오면 NotificationSomethings 함수 호출하도록 수정하면 됨 */
        button3 = (Button) findViewById(R.id.button3);
        button3.setBackgroundColor(Color.BLACK);
        button3.setTextColor(Color.WHITE);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSomethings();
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

                                webtoonIn webtoon = new webtoonIn(name, url);
                                webtoonInL.add(webtoon);

                                f = new File(getFilesDir(), "webtoonInfor.dat");
                                try {
                                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                                    oos.writeObject(webtoonInL);
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("웹툰 이름 입력");

                layout.addView(etName);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("웹툰 삭제")
                        .setView(layout)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int i;
                                String srname = etName.getText().toString();

                                for (i = 0; i < webtoonInL.size(); i++) {
                                    webtoonIn search = webtoonInL.get(i);
                                    String name = search.get_name();

                                    if (name.equals(srname)) {
                                        webtoonInL.remove(i);
                                    }

                                    f = new File(getFilesDir(), "webtoonInfor.dat");
                                    try {
                                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                                        oos.writeObject(webtoonInL);
                                    } catch (IOException ioe) {
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show();
            }
        });
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
            Intent set = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(set);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void NotificationSomethings() {

        Resources res = getResources();

        Intent notificationIntent = new Intent(this, NotificationSomething.class);
        notificationIntent.putExtra("notificationId", 9999); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("새로운 웹툰이 올라왔습니다.")
                .setContentText("지금 눌러 확인하세요!")
                .setTicker("상태바 메시지 수정은 여기입니다.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234, builder.build());
    }
    
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    /**
     *텍스트뷰에 현재 시간과 날짜를 출력하는 코드*/
    /*
     String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

     //텍스트뷰에 현재 시간과 날짜를 출력
     // 버튼에 입력이 되도록 수정할 필요가 있음.
     textView.setText(currentDateTimeString); */
}