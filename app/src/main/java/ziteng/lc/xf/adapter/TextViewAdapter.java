package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ziteng.lc.xf.R;
import ziteng.lc.xf.bean.NewsList;

/**
 * Created by luochao on 2017/4/14.
 * 首页所有项目适配器
 */

public class TextViewAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;
    private LayoutInflater inflater;

    public TextViewAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void clerData(ArrayList<NewsList.ProjectListBean> list){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_textview, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(list.get(position).toString());

        return convertView;
    }
    class ViewHolder {
        TextView tvTitle;

    }

}
