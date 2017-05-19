package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ziteng.lc.xf.R;
import ziteng.lc.xf.bean.ProjectEditor;


/**
 * Created by luochao on 2017/5/5.
 * 项目编辑列表
 */

public class ProjectEditorListAdapter extends BaseAdapter{
    private ProjectEditor projectEditor;
    private Context context;
    private LayoutInflater inflater;

    public ProjectEditorListAdapter(Context context, ProjectEditor projectEditor) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.projectEditor = projectEditor;
    }

    @Override
    public int getCount() {
        return projectEditor.getProjectList().size();
    }

    @Override
    public Object getItem(int position) {
        return projectEditor.getProjectList().get(position);
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
        holder.tvtatil.setText((position+1+"、")+projectEditor.getProjectList().get(position).getProject_name());
        holder.tvcompany.setText(projectEditor.getProjectList().get(position).getOrg_name());
        holder.tvtime.setText(projectEditor.getProjectList().get(position).getCreatedate());
        return convertView;
    }

    class ViewHolder {
        TextView tvtatil;
        TextView tvcompany;
        TextView tvtime;
    }
}
