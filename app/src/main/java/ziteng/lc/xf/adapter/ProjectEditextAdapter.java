package ziteng.lc.xf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ziteng.lc.xf.R;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectMessage;

/**
 * Created by luochao on 2017/4/18.
 * 各种项目列表的适配器
 */

public class ProjectEditextAdapter extends BaseAdapter {
    private ProjectMessage projectMessage;
    private Context context;
    private LayoutInflater inflater;

    public ProjectEditextAdapter(Context context, ProjectMessage projectMessage) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.projectMessage = projectMessage;
    }

    @Override
    public int getCount() {
        return projectMessage.getProjectList().size();
    }

    @Override
    public Object getItem(int position) {
        return projectMessage.getProjectList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = inflater.inflate(R.layout.item_projectmg, null);
            holder.tvtatil= (TextView) convertView.findViewById(R.id.item_tvtatil);
            holder.tvcompany= (TextView) convertView.findViewById(R.id.item_tvcompany);
            holder.tvtime= (TextView) convertView.findViewById(R.id.item_tvtime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
            holder.tvtatil.setText((position+1+"、")+projectMessage.getProjectList().get(position).getProject_name());
            holder.tvcompany.setText(projectMessage.getProjectList().get(position).getProject_descrip());
            holder.tvtime.setText(projectMessage.getProjectList().get(position).getTime());
        return convertView;
    }

    class ViewHolder {
        TextView tvtatil;
        TextView tvcompany;
        TextView tvtime;
    }
}
