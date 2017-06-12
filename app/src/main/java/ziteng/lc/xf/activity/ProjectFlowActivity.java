package ziteng.lc.xf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/4/28.
 * 项目跟踪页面
 */

public class ProjectFlowActivity extends BaseActivity {
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
    @BindView(R.id.ll_ywbl)
    LinearLayout llYwbl;
    @BindView(R.id.ll_ld)
    LinearLayout llLd;

    @Override
    protected int getLayout() {
        return R.layout.activity_projectflow;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("项目跟踪");
        getStatus();
    }

    //获取权限并且根据权限显示模块
    private void getStatus() {
        //status 1：个人账号2：企业账号3：责任单位4：管理员5：领导
        int status = (int) SPUtils.get(ProjectFlowActivity.this, "status",1);
        if (!EmptyUtils.isEmpty(status)) {
            if (status == 1 || status == 2) {
                llYwbl.setVisibility(View.GONE);
                llLd.setVisibility(View.GONE);
            }
        }
    }


    @OnClick({R.id.iv_toolbar_back, R.id.ll_ybxm, R.id.ll_qxxm, R.id.ll_ldxm, R.id.ll_blzxm, R.id.ll_yblxm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.ll_ybxm:
                Intent intent_ybxm = new Intent(ProjectFlowActivity.this, StopProjectActivity.class);
                startActivity(intent_ybxm);
                break;
            case R.id.ll_qxxm:
                Intent intent_qxxm = new Intent(ProjectFlowActivity.this, SigningActivity.class);
                startActivity(intent_qxxm);
                break;
            case R.id.ll_ldxm:
                Intent intent_ldxm = new Intent(ProjectFlowActivity.this, LandingProjectActivity.class);
                startActivity(intent_ldxm);
                break;
            case R.id.ll_blzxm:
                Intent intent_blzxm = new Intent(ProjectFlowActivity.this, ManageProjectActivity.class);
                startActivity(intent_blzxm);
                break;
            case R.id.ll_yblxm:
                Intent intent_yblxm = new Intent(ProjectFlowActivity.this, EndManageProjectActivity.class);
                startActivity(intent_yblxm);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
