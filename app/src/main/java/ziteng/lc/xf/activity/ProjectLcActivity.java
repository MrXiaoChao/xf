package ziteng.lc.xf.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.base.BaseActivity;

/**
 * Created by luochao on 2017/4/13.
 * 项目流程
 */

public class ProjectLcActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_projectlc;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void  initview(){
        tvTooltarTitle.setText("项目流程图");
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }
}
