package teamwap.wap;

/**
 * Created by Tabi on 2016-12-16.
 */

import android.graphics.drawable.Drawable;

// 리스트뷰 버튼 정보를 담는 코드
public class ListViewBtnItem {
    private Drawable iconDrawable;
    private String textStr;

    // 리스트의 아이콘 정보를 설정함
    public void setIcon(Drawable icon){
        iconDrawable = icon;
    }

    // 리스트의 텍스트 정보를 설정함
    public void setText(String text){
        textStr = text;
    }

    // 저장된 아이콘을 가져옴
    public Drawable getIcon(){
        return this.iconDrawable;
    }

    // 저장된 텍스트를 가져옴
    public String getText() {
        return this.textStr;
    }

}
