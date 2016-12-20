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

    int btnnum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher2);

        backPressCloseHandler = new BackPressCloseHandler(this);

        webtoonIn test0 = new webtoonIn("네이버 웹툰", "http://comic.naver.com/webtoon/weekday.nhn");
        webtoonInL.add(test0);
        webtoonIn test1 = new webtoonIn("가우스 전자", "http://m.comic.naver.com/webtoon/list.nhn?titleId=675554&week=thu");
        webtoonInL.add(test1);

        ListView listView;
        ListViewBtnAdapter adapter;
        final ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>();

        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, this);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        loadItemsFromDB(items);


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

        // 샘플용 알림 발생 버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSomethings();
            }
        });


        //웹툰을 등록할 플로팅 액션 버튼 설정
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 입력창을 받기 위해 레이아웃 형성
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // 에딧 텍스트로 입력 값을 받아올 수 있도록 설정
                // 입력 받는 창 나누고 이름 붙여놓기
                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("웹툰 이름 입력");
                final EditText etURL = new EditText(MainActivity.this);
                etURL.setHint("웹툰 URL 입력");
                // 레이아웃에 입력창 추가
                layout.addView(etName);
                layout.addView(etURL);

                // AlertDialog로 입력 텍스트창 구현
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("웹툰 추가")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            // 등록을 누르면 실행되는 행동
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 입력된 이름과 url을 스트링값으로 지정
                                String name = etName.getText().toString();
                                String url = etURL.getText().toString();

                                // 버튼 추가를 위해 어레이리스트에 해당 값을 추가
                                webtoonIn webtoon = new webtoonIn(name, url);
                                webtoonInL.add(webtoon);

                                // 저장된 어레이리스트를 핸드폰에 저장하는 부분
                                f = new File(getFilesDir(), "webtoonInfor.dat");
                                try {
                                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                                    oos.writeObject(webtoonInL);
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }

                                //등록 되었음을 메시지로 띄워줌
                                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();

                                // listview를 다시 띄우기 위해 items 초기화
                                // items을 다시 불러오는 함수를 호출하고, listview를 재시작
                                items.clear();
                                //webtoonInL.clear();
                                loadItemsFromDB(items);
                                restarLlistView(items);

                                // 새로고침을 딜레이줄 경우를 위해 제작해둠
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
                            // 취소를 눌렀을 경우 실행
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show(); //만든 레이아웃을 다 만들고 보여줌.
            }
        });

        //웹툰을 삭제를 도와줄 플로팅 액션 버튼을 설정
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 입력창을 받기 위해 레이아웃 형성
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                //웹툰 이름을 받을 수 있도록 에딧 텍스트를 이용
                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("웹툰 이름 입력");
                // 만든 레이아웃을 추가
                layout.addView(etName);

                // AlertDialog로 입력 텍스트창 구현
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("웹툰 삭제")
                        .setView(layout)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            // 확인을 눌렀을 때 실행할 것
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 조사를 위한 변수 i값과, 웹툰 이름을 가져옴.
                                int i;
                                String srname = etName.getText().toString();

                                // for문을 이용하여 웹툰 정보 어레이리스트를 뒤져 입력된 이름과 같은 정보를 찾음
                                for (i = 0; i < webtoonInL.size(); i++) {
                                    webtoonIn search = webtoonInL.get(i);
                                    String name = search.get_name();

                                    // 같은걸 발견하면 정보 삭제
                                    if (name.equals(srname)) {
                                        webtoonInL.remove(i);
                                    }

                                    // 변경된 정보를 다시 핸드폰에 저장
                                    f = new File(getFilesDir(), "webtoonInfor.dat");
                                    try {
                                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                                        oos.writeObject(webtoonInL);
                                    } catch (IOException ioe) {
                                    }
                                }

                                // 삭제되었음을 알림으로 띄워줌
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                // listview를 다시 띄우기 위해 items 초기화
                                // items을 다시 불러오는 함수를 호출하고, listview를 재시작
                                items.clear();
                                loadItemsFromDB(items);
                                restarLlistView(items);

                                // 리스트뷰 새로고침에 딜레이를 줄 경우를 고려해 만든 함수
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
                            // 취소를 눌렀을 경우 행동
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show(); // 여지껏 만든 레이아웃을 만들어서 보여줌
            }
        });
        // 액션바 제작.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //ListView 아이템 클릭 이벤트에 대한 처리. (핸들러 정의)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                // TODO : item click
            }
        });

    }

    //환경설정 창띄우는 함수
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 환경설정이 눌렸을 때 행동할 것
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // 세팅 액티비티로 이동하도록 인텐트로 구현
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent set = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(set);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 알림 함수
    public void NotificationSomethings() {

        // 리소스를 받아옴
        Resources res = getResources();

        // NotificationSomething 클래스로 이동
        Intent notificationIntent = new Intent(this, NotificationSomething.class);
        notificationIntent.putExtra("notificationId", 9999); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // 알림창의 설정 내역
        // 타이틀, 내용, 틱커, 아이콘 설정 등
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

    // 뒤로가기를 두번 눌렀을 때 앱이 꺼지도록 설계
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


    // 웹툰 정보 어레이리스트에 핸드폰에 저장된 정보를 가져오기 위해 만든 함수
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

    // ListViewBtnItem 어레이에 버튼을 넣고 가져오는 함수
    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list){
        // 저 버튼 3에 임시로 적어놓은 불러오기 코드를 수정해서
        // 여기로 가져오면 로드해서 자동으로 리스트뷰 아이템에 추가하도록 설계.
        // loadFromData로 구현했음.
        //webtoonInL.clear();
        //loadFromData(webtoonInL);

        ListViewBtnItem item;
        int i;

        // 만약 넣어진 items가 비어있으면 만듬.
        if (list == null){
            list = new ArrayList<ListViewBtnItem>();
        }
        i = 0;

        // 웹툰 정보 어레이를 뒤져서 데이터를 넣음
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

    // 리스트뷰의 버튼을 누르면 작동하는 함수
    @Override
    public void onListBtnClick(int position) {
        webtoonIn webtoonIn1 = webtoonInL.get(position);
        String name = webtoonIn1.get_name();
        String urll = webtoonIn1.get_url();
        Toast.makeText(getApplicationContext(), name + " 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent mIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(urll));
        startActivity(mIntent2);
    }

    // 리스트뷰를 재부팅(?)하기 위해 만든 함수.
    // 리스트뷰를 시작하는 부분을 다시 가져왔다.
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