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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectEditextAdapter;
import ziteng.lc.xf.adapter.ProjectEditorListAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectEditor;
import ziteng.lc.xf.bean.ProjectMessage;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/5/3.
 * 项目编辑所在列表
 */

public class ProjectEditorActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_project)
    ListView lvProject;
    private ProjectEditorListAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_stopproject;
    }

    @Override
    protected void initData() {
        String personuuid= (String) SPUtils.get(ProjectEditorActivity.this,"personuuid","");
        tvTooltarTitle.setText("项目编辑");
        getDataFromService(1, 9, personuuid);
    }


    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    private void getDataFromService(final int page, int rows, String personuuid) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        map.put("personuuid",personuuid);
        GsonRequest<ProjectEditor> request = new GsonRequest<ProjectEditor>(Request.Method.POST, map, Url.ProjectEditor, ProjectEditor.class, new Response.Listener<ProjectEditor>() {
            @Override
            public void onResponse(final ProjectEditor response) {
                if (response != null) {
                    adapter = new ProjectEditorListAdapter(ProjectEditorActivity.this,response);
                }
                if (adapter != null) {
                    lvProject.setAdapter(adapter);
                    lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ProjectEditorActivity.this, InformationEditorActivity.class);
                            intent.putExtra("project_id", response.getProjectList().get(position).getProject_id());
                            startActivity(intent);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        App.getInstance().getHttpQueue().add(request);
    }
}
