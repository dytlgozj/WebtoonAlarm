package teamwap.wap;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Tabi on 2016-11-26.
 */

// 두번 뒤로가기를 누르면 앱이 꺼지는 함수
public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    // 함수가 적용될 액티비티의 컨텍스트를 받음
    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        // 뒤로가기를 다시 누른 시간이 2초보다 크면 가이드한번 보여주고 말도록 설정
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        // 2초보다 작으면 시스템 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            SystemExit();
        }
    }
    // 시스템 종료 함수
    public void SystemExit() {
        // 액티비티를 finish하도록 만듬.
        activity.moveTaskToBack(true);
        activity.finish();
        toast.cancel();
        // 프로세스 죽이기
        android.os.Process.killProcess(android.os.Process.myPid() );
        System.exit(0);
    }
    //알림 가이드를 주는 함수
    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로가기\' 버튼을 두 번 누르면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
