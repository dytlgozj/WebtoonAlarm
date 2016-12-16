/* 하드코딩해서 오류랑 코딩스타일같은거  안잡음 */
/*바깥에서 접근시 ButtonView bv = new ButtonView(getContext()); 등으로 접근 */
/* 동적레이아웃임 참고하셈 */

import android.content.*;
import android.view.*;
import android.widget.*;
import android.util.*;
public ButtonView extends LinearLayout implements View.OnClickListener{
    public void onClick(View v){
    }
public static class EtcView extends LinearLayout{
  
    public EtcView(Context ctx){//코드내에서만 생성되는 동적 생성자
        super(ctx);
        setOrientation(VERTICAL);
        TextView tv = new TextView(ctx);
        tv.setText("sub text");
        addView(tv);
    }
    public EtcView(Context ctx,AttributeSet attrs){//xml 에서 사용할수있는 생성자
        super(ctx);
        setOrientation(VERTICAL);
        TextView tv = new TextView(ctx);
        tv.setText("sub text");
        addView(tv);
    }
}

public ButtonView(Context ctx){//코드내에서만 생성되는 동적 생성자
    super(ctx);
    setOrientation(HORIZONTAL);
    Button btn = new Button(ctx);
    btn.setText("haha");
    btn.setOnClickListener(this);
    addView(btn);
    addView(new EtcView(ctx));
}
public ButtonView(Context ctx,AttributeSet attrs){//xml 에서 사용할수있는 생성자 
    super(ctx);
    setOrientation(HORIZONTAL);
    Button btn = new Button(ctx);
    btn.setText("haha");
    btn.setOnClickListener(this);
    addView(btn);
    addView(new EtcView(ctx));
}
public ButtonView(Context ctx,AttributeSet attrs,int themeResId){//xml 에서 사용할수있는 생성자이고 android:style 였던가 태그속성을 사용할때 사용하는 생성자 
    super(ctx,attrs,themeResId);
    setOrientation(HORIZONTAL);
    Button btn = new Button(ctx);
    btn.setText("haha");
    btn.setOnClickListener(this);
    addView(btn);
    addView(new EtcView(ctx));
}

}
