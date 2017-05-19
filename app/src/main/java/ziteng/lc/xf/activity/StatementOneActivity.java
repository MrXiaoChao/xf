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
import ziteng.lc.xf.adapter.ProjectStatementKFAdapter;
import ziteng.lc.xf.adapter.ProjectStatementOneAdapter;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectStatement;
import ziteng.lc.xf.http.Url;


/**
 * Created by luochao on 2017/5/16.
 * 项目统计点进去的第一级
 */

public class StatementOneActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_statement)
    ListView lvStatement;
    private ProjectStatement projectStatement;

    @Override
    protected int getLayout() {
        return R.layout.activity_statement;
    }

    @Override
    protected void initData() {
        getintentandsettitle();
        setAdapter();
        initEven();
    }
    //listview的点击事件
    private void initEven() {
        lvStatement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectStatement.SubclassificationBeanX subclassificationBeanX = projectStatement.getSubclassification().get(position);
                //这个判断是在有下一级的情况下进去下一级`没有的话`进入ProjectStatementListActivity
                if (subclassificationBeanX.getSubclassification().size() > 0) {
                    Intent intent1 = new Intent(StatementOneActivity.this, StatementTwoActivity.class);
                    intent1.putExtra("SubclassificationBeanX", subclassificationBeanX);
                    intent1.putExtra("classifyName", projectStatement.getSubclassification().get(position).getClassifyName());
                    startActivity(intent1);
                } else {
                    //按扣分统计是直接进入webview所以加这个判断
                    if (projectStatement.getClassifyName().equals("按扣分统计")) {
                        String url = Url.KFTJ + "mark=" + projectStatement.getSubclassification().get(position).getMark();
                        Intent intent = new Intent(StatementOneActivity.this, WebViewProjectActivity.class);
                        intent.putExtra("URL", url);
                        startActivity(intent);
                    } else {
                        Intent intent2 = new Intent(StatementOneActivity.this, ProjectStatementListActivity.class);
                        intent2.putExtra("flag",true);
                        intent2.putExtra("classifyName", projectStatement.getClassifyName());
                        intent2.putExtra("mark", projectStatement.getSubclassification().get(position).getMark());
                        startActivity(intent2);
                    }
                }
            }
        });
    }

    //设置adapter
    private void setAdapter() {
        //因为按扣分统计使用的adapter跟别的布局是不同的所以加一个判断
        if (projectStatement != null && projectStatement.getSubclassification().size() > 0) {
            if (!projectStatement.getClassifyName().equals("按扣分统计")) {
                ProjectStatementOneAdapter adapter = new ProjectStatementOneAdapter(StatementOneActivity.this, projectStatement.getSubclassification());
                lvStatement.setAdapter(adapter);
            } else {
                ProjectStatementKFAdapter adapter = new ProjectStatementKFAdapter(StatementOneActivity.this, projectStatement.getSubclassification());
                lvStatement.setAdapter(adapter);
            }
        }
    }

    //获取传过来的值，并且设置标题
    private void getintentandsettitle() {
        Intent intent = getIntent();
        String classifyName = intent.getStringExtra("classifyName");
        projectStatement = (ProjectStatement) intent.getSerializableExtra("ProjectStatement");
        if (classifyName != null) {
            tvTooltarTitle.setText(classifyName);
        }
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

}
