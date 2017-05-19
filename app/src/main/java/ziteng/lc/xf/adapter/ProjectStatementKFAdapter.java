package ziteng.lc.xf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ziteng.lc.xf.R;
import ziteng.lc.xf.bean.ProjectStatement;

/**
 * Created by luochao on 2017/5/4.
 * 扣分统计
 */

public class ProjectStatementKFAdapter extends BaseAdapter {

    private Context context;
    private List<ProjectStatement.SubclassificationBeanX> list;

    public ProjectStatementKFAdapter(Context context, List<ProjectStatement.SubclassificationBeanX> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_projectstatmentkf, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_statment_title);
            viewHolder.count = (TextView) convertView.findViewById(R.id.tv_statment_count);
            viewHolder.jian = (TextView) convertView.findViewById(R.id.tv_jian);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getClassifyName());
        viewHolder.count.setText(list.get(position).getCount());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView count;
        TextView jian;
    }
}
