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
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectWarnAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectWarn;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/4/12.
 * 项目提醒
 */

public class ProjectWarnActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_project)
    ListView lvProjectt;
    private ProjectWarnAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_stopproject;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("项目提醒");
        String personuuid = (String) SPUtils.get(ProjectWarnActivity.this, "personuuid", "");
        if (!EmptyUtils.isEmpty(personuuid)) {
            getDataFromService(personuuid,1,20);
        }
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    private void getDataFromService(String personuuid,int page,int rows) {
        //现在数据不多``以后要做成分页加载
        Map<String, String> map = new HashMap<>();
        map.put("personuuid", personuuid);
        map.put("page",page+"");
        map.put("rows",rows+"");

        GsonRequest<ProjectWarn> request = new GsonRequest<ProjectWarn>(Request.Method.POST, map, Url.ProjectWarn, ProjectWarn.class, new Response.Listener<ProjectWarn>() {
            @Override
            public void onResponse(final ProjectWarn response) {
                if (response != null && response.getProjectList().size() > 0) {
                    adapter = new ProjectWarnAdapter(ProjectWarnActivity.this, response);
                    lvProjectt.setAdapter(adapter);
                    lvProjectt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String URL="http://211.151.183.170:8097/rqzsj/news/reminddetails.jsp?project_id="+response.getProjectList().get(position).getProject_id()+"&status="+response.getProjectList().get(position).getStatus();
                            Intent intent =new Intent(ProjectWarnActivity.this,WebViewProjectActivity.class);
                            intent.putExtra("URL",URL);
                            startActivity(intent);
                        }
                    });
                }else {
                    ToastUtils.showShortToast("当前用户数据为空");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }
}
