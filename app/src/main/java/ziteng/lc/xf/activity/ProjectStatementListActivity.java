package ziteng.lc.xf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectStatementListAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectStatementList;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;

/**
 * Created by luochao on 2017/5/16.
 * 项目统计展示列表
 */

public class ProjectStatementListActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_project)
    ListView lvProject;
    private String classifyName;
    private List<ProjectStatementList> list;
    private String mark;
    private String title;
    private boolean flag;
    private String classifyName1;
    private String classifyName2;

    @Override
    protected int getLayout() {
        return R.layout.activity_projectstatement_list;
    }

    @Override
    protected void initData() {
        getintent();
        setTitle();
        sendData();
        initEven();
    }

    private void getDataFromService(String classifyName, String mark) {
        TypeToken type = new TypeToken<List<ProjectStatementList>>() {
        };
        Map<String, String> map = new HashMap<>();
        map.put("classifyName", classifyName);
        map.put("mark", mark);
        GsonRequest<List<ProjectStatementList>> request = new GsonRequest<List<ProjectStatementList>>(Request.Method.POST, map, Url.STATEMENT_LIST, type, new Response.Listener<List<ProjectStatementList>>() {
            @Override
            public void onResponse(List<ProjectStatementList> response) {
                if (response != null && response.size() > 0) {
                    list = response;
                    ProjectStatementListAdapter adapter = new ProjectStatementListAdapter(ProjectStatementListActivity.this, response);
                    lvProject.setAdapter(adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    //点击事件
    private void initEven() {
        lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = Url.STATEMENT_XQ + "project_id=" + list.get(position).getProject_id();
                Intent intent = new Intent(ProjectStatementListActivity.this, WebViewProjectActivity.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });
    }

    //获取传过来的值
    private void getintent() {
        Intent intent = getIntent();
        //flag是区分是设置3级页面标题还是2级页面标题 2级页面传过来的是true
        flag = intent.getBooleanExtra("flag", false);
        classifyName = intent.getStringExtra("classifyName");
        classifyName1 = intent.getStringExtra("classifyName1");
        classifyName2 = intent.getStringExtra("classifyName2");
        mark = intent.getStringExtra("mark");
        title = intent.getStringExtra("title");
    }

    //设置标题
    private void setTitle() {
        //有3级列表标题是设置title  2级设置classifyName
        if (!flag) {
            if (title != null) {
                tvTooltarTitle.setText(title);
            }
        } else {
            tvTooltarTitle.setText(classifyName);
        }
    }

    //发送请求
    private void sendData() {
        //因为项目总数mark只有传null 才能查出来`所以做以下判断
        if (mark != null) {
            if (flag) {
                //2级页面的请求
                getDataFromService(classifyName2, mark);
            }else {
                //3级页面的请求
                getDataFromService(classifyName1, mark);
            }
        } else {
            getDataFromService(classifyName, "");
        }
    }
}
