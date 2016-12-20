package teamwap.wap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Tabi on 2016-12-07.
 */


/* 현재 Activity를 인식하지 못하는 오류가 있음 */
    // 현재 실패한 액티비티
    // 목적은 License 정보를 보여주기 위한 액티비티
public class License extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher2);
    }
}
