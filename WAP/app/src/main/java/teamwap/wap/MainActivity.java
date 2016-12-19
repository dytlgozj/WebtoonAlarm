package teamwap.wap;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import java.util.List;

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

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.ClassNotFoundException;


public class MainActivity extends AppCompatActivity implements ListViewBtnAdapter.ListBtnClickListener{
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

        ListView listView;
        ListViewBtnAdapter adapter;
        final ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>();

        loadItemsFromDB(items);

        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, this);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


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
        /*button3.setOnClickListener(new View.OnClickListener(){

        @Override public void onClick(View v){
        int i;

        f = new java.io.File(getFilesDir(),"webtoonInfor.dat");
        ObjectInputStream ois = null;
        ArrayList list;

        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            list = (ArrayList) ois.readObject();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        for (i=0; i < list.size(); i++){
        webtoonInL.add(list.get(i));
        }

        Toast.makeText(getApplicationContext(), webtoonInL.get(0).get_name(), Toast.LENGTH_SHORT).show();
        }
        });
        */
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
                                items.clear();
                                webtoonInL.clear();
                                loadItemsFromDB(items);
                                restarLlistView(items);
                                /*new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        items.clear();
                                        loadItemsFromDB(items);
                                    }
                                }, 500);//*/
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
                                items.clear();
                                webtoonInL.clear();
                                loadItemsFromDB(items);
                                restarLlistView(items);
                                /*new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        items.clear();
                                        loadItemsFromDB(items);
                                    }
                                }, 500);//*/
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

        //ListView 아이템 클릭 이벤트에 대한 처리. (핸들러 정의)
        // 통째로 오류남 ㅠㅠ;
        /*
        listView.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                // TODO : item click
            }
        });
        */
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

    public boolean loadFromData(ArrayList<webtoonIn> list){
        int i;

        f = new java.io.File(getFilesDir(),"webtoonInfor.dat");
        ObjectInputStream ois = null;
        ArrayList list2;

        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            try{
                list2 = (ArrayList) ois.readObject();

                for (i=0; i < list2.size(); i++){
                    webtoonIn input = (webtoonIn)list2.get(i);
                    list.add(input);
                }
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return true;
    }

    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list){
        // 저 버튼 3에 임시로 적어놓은 불러오기 코드를 수정해서
        // 여기로 가져오면 로드해서 자동으로 리스트뷰 아이템에 추가하도록 설계.
        // loadFromData로 구현했음.
        webtoonInL.clear();
        loadFromData(webtoonInL);

        ListViewBtnItem item;
        int i;

        if (list == null){
            list = new ArrayList<ListViewBtnItem>();
        }
        i = 0;

        while(i < webtoonInL.size()){
            item = new ListViewBtnItem();
            // 아이콘 설정 파싱해서 가져온걸로 넣도록 하면 됨.
            item.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            webtoonIn webtoonIn1 = webtoonInL.get(i);
            String name = webtoonIn1.get_name();
            item.setText(name);
            list.add(item);

            i++;
        }

        return true;
    }

    @Override
    public void onListBtnClick(int position) {
        webtoonIn webtoonIn1 = webtoonInL.get(position);
        String name = webtoonIn1.get_name();
        String urll = webtoonIn1.get_url();
        Toast.makeText(getApplicationContext(), name + " 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent mIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
        startActivity(mIntent2);
    }

    // 왠지모르게 안돌아간다..?
    public void restarLlistView(ArrayList<ListViewBtnItem> items){
        ListView listView;
        ListViewBtnAdapter adapter;
        items = new ArrayList<ListViewBtnItem>();

        loadItemsFromDB(items);

        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, this);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }
}