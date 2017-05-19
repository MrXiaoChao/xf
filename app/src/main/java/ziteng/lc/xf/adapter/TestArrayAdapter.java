package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ziteng.lc.xf.R;

/**
 * Created by luochao on 2017/3/24.
 * 改变系统Spinner 默认字体与颜色
 * 两个构造函数，传入为True 数据为String[] ,反之为List<String>
 */

public class TestArrayAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] mStringArray;
    private List<String> list;
    private boolean flag;

    public TestArrayAdapter(Context context, String[] stringArray, boolean flag) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mStringArray = stringArray;
        this.flag = flag;
    }

    public TestArrayAdapter(Context context, List<String> list, boolean flag) {
        super(context, android.R.layout.simple_spinner_item, list);
        mContext = context;
        this.list = list;
        this.flag = flag;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        if (flag) {
            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(mStringArray[position]);
            tv.setTextSize(11f);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        } else {
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(list.get(position));
            tv.setTextSize(11f);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        if (flag) {
            //此处text1是Spinner默认的用来显示文字的TextView
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(mStringArray[position]);
            tv.setTextSize(12f);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }else {
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(list.get(position));
            tv.setTextSize(12f);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }
        return convertView;
    }

}
