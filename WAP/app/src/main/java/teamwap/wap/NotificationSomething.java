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


public class NotificationSomething extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_something);
        CharSequence s = "전달 받은 값은 ";

        Intent main = new Intent(getApplicationContext(), MainActivity.class); // 클릭하면 메인으로 가도록 설정하는 코드.
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
