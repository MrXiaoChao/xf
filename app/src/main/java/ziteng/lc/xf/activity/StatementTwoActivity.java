package ziteng.lc.xf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectStatementOneAdapter;
import ziteng.lc.xf.adapter.ProjectStatementTwoAdapter;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectStatement;


/**
 * Created by luochao on 2017/5/16.
 * 项目统计点进去的第二级
 */

public class StatementTwoActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_statement)
    ListView lvStatement;
    private ProjectStatement.SubclassificationBeanX subclassificationBeanX;

    @Override
    protected int getLayout() {
        return R.layout.activity_statement;
    }

    @Override
    protected void initData() {
        getIntentAndsetTitle();
        setAdapter();
        lvEven();
    }

    //Listview的点击事件
    private void lvEven() {
        lvStatement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2=new Intent(StatementTwoActivity.this,ProjectStatementListActivity.class);
                intent2.putExtra("title",subclassificationBeanX.getSubclassification().get(position).getClassifyName());
                intent2.putExtra("classifyName1",subclassificationBeanX.getClassifyName());
                intent2.putExtra("mark",subclassificationBeanX.getSubclassification().get(position).getMark());
                startActivity(intent2);
            }
        });
    }

    //设置adapter
    private void setAdapter() {
        if (subclassificationBeanX !=null && subclassificationBeanX.getSubclassification().size()>0){
            ProjectStatementTwoAdapter adapter =new ProjectStatementTwoAdapter(StatementTwoActivity.this, subclassificationBeanX.getSubclassification());
            lvStatement.setAdapter(adapter);
        }
    }

    //获取传过来的值并且设置标题
    private void getIntentAndsetTitle() {
        final Intent intent =getIntent();
        String classifyName=intent.getStringExtra("classifyName");
        subclassificationBeanX = (ProjectStatement.SubclassificationBeanX) intent.getSerializableExtra("SubclassificationBeanX");
        if (classifyName!=null){
            tvTooltarTitle.setText(classifyName);
        }
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }
}
