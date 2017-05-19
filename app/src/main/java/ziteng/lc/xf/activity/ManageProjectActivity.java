package ziteng.lc.xf.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectEditextAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.ProjectMessage;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/5/3.
 * 办理中项目
 */

public class ManageProjectActivity extends BaseActivity {

    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_project)
    ListView lvProject;
    private ProjectEditextAdapter adapter;
    private String personuuid;

    @Nullable
    @Override
    protected int getLayout() {
        return R.layout.activity_stopproject;
    }

    @Override
    protected void initData() {
        personuuid = (String) SPUtils.get(ManageProjectActivity.this, "personuuid", "");
        tvTooltarTitle.setText("办理中项目");
        sendDate();
    }
    private void sendDate(){

        if (!EmptyUtils.isEmpty(personuuid)) {
            getDataFromService(1, 6, personuuid);
        } else {
            ToastUtils.showShortToast("登录用户出错");
        }
    }


    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    private void getDataFromService(final int page, int rows, String personuuid) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        map.put("personuuid", personuuid);

        GsonRequest<ProjectMessage> request = new GsonRequest<ProjectMessage>(Request.Method.POST, map, Url.BLZXM, ProjectMessage.class, new Response.Listener<ProjectMessage>() {
            @Override
            public void onResponse(final ProjectMessage response) {
                if (response != null && response.getProjectList().size()>0) {
                    adapter = new ProjectEditextAdapter(ManageProjectActivity.this, response);
                }else {
                    ToastUtils.showShortToast("该用户数据为空");
                }
                if (adapter != null) {
                    lvProject.setAdapter(adapter);
                    lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ManageProjectActivity.this, WebViewProjectActivity.class);
                            intent.putExtra("URL",Url.XMGZXQ+"project_id="+response.getProjectList().get(position).getProject_id());
                            startActivity(intent);
                        }
                    });

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
