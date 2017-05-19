package ziteng.lc.xf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.ProjectEditextAdapter;
import ziteng.lc.xf.base.BaseActivity;

/**
 * Created by luochao on 2017/4/13.
 * 项目信息
 */

public class ProjectMessageActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.iv_projectinput)
    ImageView ivProjectinput;
    @BindView(R.id.iv_projecteditext)
    ImageView ivProjecteditext;

    @Override
    protected int getLayout() {
        return R.layout.activity_projectmsg;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview(){
        tvTooltarTitle.setText("项目信息");
    }

    @OnClick({R.id.iv_toolbar_back, R.id.iv_projectinput, R.id.iv_projecteditext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.iv_projectinput:
                Intent intent_input=new Intent(this,InformationInputActivity.class);
                startActivity(intent_input);
                break;
            case R.id.iv_projecteditext:
                Intent intent_editext=new Intent(this,ProjectEditorActivity.class);
                startActivity(intent_editext);
                break;
        }
    }
}
