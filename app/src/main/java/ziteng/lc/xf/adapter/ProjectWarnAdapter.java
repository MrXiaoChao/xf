package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ziteng.lc.xf.R;
import ziteng.lc.xf.bean.ProjectWarn;

/**
 * Created by luochao on 2017/5/5.
 * 项目提醒
 */

public class ProjectWarnAdapter extends BaseAdapter {
    private Context context;
    private ProjectWarn projectWarn;

    public ProjectWarnAdapter(Context context,ProjectWarn projectWarn) {
        this.context=context;
        this.projectWarn=projectWarn;
    }

    @Override
    public int getCount() {
        return projectWarn.getProjectList().size();
    }

    @Override
    public Object getItem(int position) {
        return projectWarn.getProjectList().get(position);
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
            if (projectWarn.getProjectList().get(position).getStatus().equals("超期")){
                holder.tvtatil.setTextColor(context.getResources().getColor(R.color.red));
            }
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
            holder.tvtatil.setText((position+1+"、")+projectWarn.getProjectList().get(position).getProject_name());
            holder.tvcompany.setText(projectWarn.getProjectList().get(position).getOrg_name());
            holder.tvtime.setText(projectWarn.getProjectList().get(position).getCreatedate());
        return convertView;
    }
    class ViewHolder{
        TextView tvtatil;
        TextView tvcompany;
        TextView tvtime;
    }
}
