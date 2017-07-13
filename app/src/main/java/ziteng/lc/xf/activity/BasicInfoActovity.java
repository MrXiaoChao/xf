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
    @BindView(R.id.tv_link_man)
    TextView tvLinkMan;
    @BindView(R.id.rl_link_man)
    RelativeLayout rlLinkMan;
    @BindView(R.id.tv_office_phone)
    TextView tvOfficePhone;
    @BindView(R.id.rl_office_phone)
    RelativeLayout rlOfficePhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.rl_sfzh)
    RelativeLayout rlSfzh;
    @BindView(R.id.tv_sfz)
    TextView tvSfz;

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
        String link_man = (String) SPUtils.get(BasicInfoActovity.this, "link_man", "");
        String office_phone = (String) SPUtils.get(BasicInfoActovity.this, "office_phone", "");
        String email = (String) SPUtils.get(BasicInfoActovity.this, "email", "");
        String status = (String) SPUtils.get(BasicInfoActovity.this, "status", "");
        String org_code = (String) SPUtils.get(BasicInfoActovity.this, "org_code", "");
        if (StringUtils.equals("3", status) || StringUtils.equals("4", status) || StringUtils.equals("5", status)) {
            rlEmail.setVisibility(View.VISIBLE);
            rlLinkMan.setVisibility(View.VISIBLE);
            rlOfficePhone.setVisibility(View.VISIBLE);
            rlSfzh.setVisibility(View.GONE);
            rlPhone.setVisibility(View.GONE);
        }
        if (StringUtils.equals("2", status)) {
            tvSfz.setText("机构编码");
        }

        if (!StringUtils.isEmpty(phone)) {
            tvPhoneNum.setText(StringReplaceUtil.phoneNumberReplaceWithStar(phone));
        }
        //电话号码 机构编码
        if (StringUtils.equals("2", status)) {
            tvIdcard.setText(org_code);
        } else {
            if (!StringUtils.isEmpty(idcard)) {
                tvIdcard.setText(StringReplaceUtil.idCardReplaceWithStar(idcard));
            }
        }
        tvUsename.setText(usename);
        tvName.setText(name);
        tvLinkMan.setText(link_man);
        tvEmail.setText(email);
        tvOfficePhone.setText(office_phone);
    }


    @OnClick({R.id.iv_toolbar_back, R.id.rl_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.rl_phone:
                Intent intent = new Intent(BasicInfoActovity.this, ChangPhoneNumberActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1) {
            Bundle bundle = data.getExtras();
            String phone = bundle.getString("phonenuber");
            tvPhoneNum.setText(StringReplaceUtil.phoneNumberReplaceWithStar(phone));
        }
    }

}
