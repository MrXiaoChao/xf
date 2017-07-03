package ziteng.lc.xf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.base.BaseActivity;

/**
 * Created by luochao on 2017/6/29.
 * 城市选择输入
 */

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.et_cityname)
    EditText etCityname;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected int getLayout() {
        return R.layout.activity_selectcity;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("输入城市");
    }



    @OnClick({R.id.iv_toolbar_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_save:
                String cityName=etCityname.getText().toString().trim();
                if (StringUtils.isEmpty(cityName)){
                    ToastUtils.showShortToast("请填写城市");
                    return;
                }
                Intent i = new Intent();
                i.putExtra("cityname",cityName);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }
}
