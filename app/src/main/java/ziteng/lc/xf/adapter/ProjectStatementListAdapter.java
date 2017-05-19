package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ziteng.lc.xf.R;
import ziteng.lc.xf.bean.ProjectStatementList;
import ziteng.lc.xf.bean.ProjectWarn;

/**
 * Created by luochao on 2017/5/5.
 * 项目统计列表
 */

public class ProjectStatementListAdapter extends BaseAdapter {
    private Context context;
    private List<ProjectStatementList> list;

    public ProjectStatementListAdapter(Context context, List<ProjectStatementList> list) {
        this.context=context;
        this.list=list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_projectmg,parent,false);
            holder.tvtatil= (TextView) convertView.findViewById(R.id.item_tvtatil);
            holder.tvcompany= (TextView) convertView.findViewById(R.id.item_tvcompany);
            holder.tvtime= (TextView) convertView.findViewById(R.id.item_tvtime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
            holder.tvtatil.setText((position+1+"、")+list.get(position).getProject_name());
            holder.tvcompany.setText(list.get(position).getOrg_name());
            holder.tvtime.setText(list.get(position).getCreatedate());
        return convertView;
    }
    class ViewHolder{
        TextView tvtatil;
        TextView tvcompany;
        TextView tvtime;
    }
}
