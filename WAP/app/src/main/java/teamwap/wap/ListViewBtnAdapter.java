package teamwap.wap;

/**
 * Created by Tabi on 2016-12-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class ListViewBtnAdapter extends ArrayAdapter implements View.OnClickListener {

    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의
    public interface  ListBtnClickListener{
        void onListBtnClick(int position);
    }

    // 생성자로 부터 전달된 두 변수 지정
    int resourceId;
    private ListBtnClickListener listBtnClickListener;

    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가
    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list, ListBtnClickListener clickListener){
        super(context, resource, list);

        // resource id 값 복사
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId에 해당하는 Layout을 inflate하여 convertView 참조
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        // 화면에 표시될 View로부터 위젯에 대한 참조 획득
        final ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        // 텍스트뷰 추가시 사용
        final TextView textTextView = (TextView) convertView.findViewById(R.id.textView1);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());

        // TAG에 position값 지정. Adapter를 click listener로 지정.
        Button button1 = (Button) convertView.findViewById(R.id.buttonb);
        button1.setTag(position);
        button1.setOnClickListener(this);

        return convertView;
    }

    // MainActivity에 있는 리스너랑 상호작용 하는 코드임
    public void onClick(View v){
        if (this.listBtnClickListener != null){
            this.listBtnClickListener.onListBtnClick((int)v.getTag());
        }
    }
}
