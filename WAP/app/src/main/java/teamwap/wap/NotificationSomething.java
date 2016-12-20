package teamwap.wap;

/**
 * Created by Tabi on 2016-11-25.
 */

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

// 알림 발생 액티비티
public class NotificationSomething extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 본래 이동되도록 설계되어 있는데 필요없지만 제거하면 오류가 나서 내비둠
        setContentView(R.layout.notification_something);
        CharSequence s = "전달 받은 값은 ";

        // 클릭하면 메인 액티비티로 가도록 설정하는 코드.
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);

        int id=0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            s = "error";
        }
        else {
            id = extras.getInt("notificationId");
        }
        TextView t = (TextView) findViewById(R.id.textView);
        s = s+" "+id;
        t.setText(s);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //노티피케이션 제거
        nm.cancel(id);
    }
}
