package ziteng.lc.xf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class NewsListAdapter extends BaseAdapter {
    private ArrayList<NewsList.ProjectListBean> list;
    private Context context;
    private LayoutInflater inflater;

    public NewsListAdapter(Context context, ArrayList<NewsList.ProjectListBean> list) {
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
        NewsList.ProjectListBean  projectListBean=list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_projectmsg, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(projectListBean.getNews_name());
        holder.tvContent.setText(projectListBean.getNews_content());
        holder.tvTime.setText(projectListBean.getNews_time());
        Glide.with(context).load(projectListBean.getImgUrl()).error(R.mipmap.banner).into(holder.ivImg);
        return convertView;
    }
    class ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        TextView tvContent;
        ImageView ivImg;
    }

}
