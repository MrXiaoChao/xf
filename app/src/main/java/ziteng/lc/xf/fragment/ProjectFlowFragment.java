package ziteng.lc.xf.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ziteng.lc.xf.activity.EndManageProjectActivity;
import ziteng.lc.xf.activity.LandingProjectActivity;
import ziteng.lc.xf.activity.ManageProjectActivity;
import ziteng.lc.xf.activity.ProjectFlowActivity;
import ziteng.lc.xf.activity.ProjectLcActivity;
import ziteng.lc.xf.activity.SigningActivity;
import ziteng.lc.xf.activity.StopProjectActivity;
import ziteng.lc.xf.adapter.ProjectEditextAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseFragment;
import ziteng.lc.xf.bean.ProjectMessage;
import ziteng.lc.xf.widegt.RecyclerItemClickListener;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;

/**
 * Created by luochao on 2017/3/20.
 * 项目跟踪
 */

public class ProjectFlowFragment extends BaseFragment {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.ll_ybxm)
    LinearLayout llYbxm;
    @BindView(R.id.ll_qxxm)
    LinearLayout llQxxm;
    @BindView(R.id.ll_ldxm)
    LinearLayout llLdxm;
    @BindView(R.id.ll_blzxm)
    LinearLayout llBlzxm;
    @BindView(R.id.ll_yblxm)
    LinearLayout llYblxm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_projectflow;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("项目跟踪");
    }


    @OnClick({R.id.iv_toolbar_back, R.id.ll_ybxm, R.id.ll_qxxm, R.id.ll_ldxm, R.id.ll_blzxm, R.id.ll_yblxm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.ll_ybxm:
                Intent intent_ybxm =new Intent(getActivity(),StopProjectActivity.class);
                startActivity(intent_ybxm);
                break;
            case R.id.ll_qxxm:
                Intent intent_qxxm =new Intent(getActivity(),SigningActivity.class);
                startActivity(intent_qxxm);
                break;
            case R.id.ll_ldxm:
                Intent intent_ldxm =new Intent(getActivity(),LandingProjectActivity.class);
                startActivity(intent_ldxm);
                break;
            case R.id.ll_blzxm:
                Intent intent_blzxm =new Intent(getActivity(),ManageProjectActivity.class);
                startActivity(intent_blzxm);
                break;
            case R.id.ll_yblxm:
                Intent intent_yblxm =new Intent(getActivity(),EndManageProjectActivity.class);
                startActivity(intent_yblxm);
                break;
        }
    }
}
