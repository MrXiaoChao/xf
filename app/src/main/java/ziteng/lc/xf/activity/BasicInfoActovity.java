package ziteng.lc.xf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.util.StringReplaceUtil;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/5/12.
 * 基本信息
 */

public class BasicInfoActovity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.tv_phoneNum)
    TextView tvPhoneNum;
    @BindView(R.id.tv_usename)
    TextView tvUsename;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;

    @Override
    protected int getLayout() {
        return R.layout.activity_basicinfo;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("基本信息");
        String phone = (String) SPUtils.get(BasicInfoActovity.this, "phone", "");
        String idcard = (String) SPUtils.get(BasicInfoActovity.this, "idcard", "");
        String name = (String) SPUtils.get(BasicInfoActovity.this, "name", "");
        String usename = (String) SPUtils.get(BasicInfoActovity.this, "usename", "");
        if (!StringUtils.isEmpty(phone)) {
            tvPhoneNum.setText(StringReplaceUtil.phoneNumberReplaceWithStar(phone));
        }
        if (!StringUtils.isEmpty(idcard)) {
            tvIdcard.setText(StringReplaceUtil.idCardReplaceWithStar(idcard));
        }
        tvUsename.setText(usename);
        tvName.setText(name);
    }


    @OnClick({R.id.iv_toolbar_back, R.id.rl_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.rl_phone:
                Intent intent = new Intent(BasicInfoActovity.this,ChangPhoneNumberActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0 && resultCode ==1){
            Bundle bundle =data.getExtras();
            String phone=bundle.getString("phonenuber");
            tvPhoneNum.setText(StringReplaceUtil.phoneNumberReplaceWithStar(phone));
        }
    }
}
