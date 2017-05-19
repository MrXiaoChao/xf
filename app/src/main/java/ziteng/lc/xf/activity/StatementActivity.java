package ziteng.lc.xf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectStatementAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectStatement;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;

/**
 * Created by luochao on 2017/3/21.
 * 项目统计
 */

public class StatementActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_statement)
    ListView lvStatement;

    @Override
    protected int getLayout() {
        return R.layout.activity_statement;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("项目统计");
        getDataFromService();
    }

    //获取数据
    private void getDataFromService() {
        TypeToken type = new TypeToken<ArrayList<ProjectStatement>>() {
        };
        GsonRequest<List<ProjectStatement>> request = new GsonRequest<List<ProjectStatement>>(Url.ProjectStatement, type, new Response.Listener<List<ProjectStatement>>() {
            @Override
            public void onResponse(List<ProjectStatement> response) {
                setAdapter(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //设置adapter
    private void setAdapter(List<ProjectStatement> response) {
        if (response != null) {
            ProjectStatementAdapter adapter = new ProjectStatementAdapter(StatementActivity.this, response);
            if (adapter != null) {
                lvStatement.setAdapter(adapter);
            } else {
                ToastUtils.showShortToast("该用户数据为空");
            }
            lvEven(response);
        }
    }

    //listview的点击事件
    private void lvEven(final List<ProjectStatement> response) {
        lvStatement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //项目总数是直接跳转到项目列表的activity所以加以下判断
                if (position > 0) {
                    Intent intent = new Intent(StatementActivity.this, StatementOneActivity.class);
                    intent.putExtra("ProjectStatement", response.get(position));
                    intent.putExtra("classifyName", response.get(position).getClassifyName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(StatementActivity.this, ProjectStatementListActivity.class);
                    intent.putExtra("classifyName", response.get(position).getClassifyName());
                    intent.putExtra("flag",true);
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

}
