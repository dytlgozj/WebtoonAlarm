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

    // Listener 인터페이스 정의
    public interface  ListBtnClickListener{
        void onListBtnClick(int position);
    }

    int resourceId;
    private ListBtnClickListener listBtnClickListener;

    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list, ListBtnClickListener clickListener){
        super(context, resource, list);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        // 텍스트뷰 추가시 사용
        final TextView textTextView = (TextView) convertView.findViewById(R.id.textView1);

        final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());

        ImageButton button1 = (ImageButton) convertView.findViewById(R.id.imageView1);
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
