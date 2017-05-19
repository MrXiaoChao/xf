package ziteng.lc.xf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ziteng.lc.xf.R;
import ziteng.lc.xf.activity.BasicInfoActovity;
import ziteng.lc.xf.activity.ChangPassWordActivity;
import ziteng.lc.xf.activity.LoginActivity;
import ziteng.lc.xf.base.BaseFragment;
import ziteng.lc.xf.widegt.MyDialog;

/**
 * Created by luochao on 2017/3/20.
 * 我的
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.rl_password)
    RelativeLayout rlPassword;
    @BindView(R.id.rl_loginout)
    RelativeLayout rlLoginout;
    @BindView(R.id.rl_mine)
    RelativeLayout rlMine;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview() {
        tvTooltarTitle.setText("我  的");
        ivToolbarBack.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_password, R.id.rl_loginout,R.id.rl_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mine:
                Intent intent1=new Intent(getActivity(), BasicInfoActovity.class);
                startActivity(intent1);
                break;
            case R.id.rl_password:
                final Intent intent = new Intent(getActivity(), ChangPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_loginout:
                final MyDialog dialog = new MyDialog(getActivity());
                dialog.setTitle("提示");
                dialog.setMessage("是否确认退出当前用户?");
                dialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialog.dismiss(); //关闭dialog
                        Intent intent_loginout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent_loginout);
                    }
                });
                dialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
        }
    }

}
