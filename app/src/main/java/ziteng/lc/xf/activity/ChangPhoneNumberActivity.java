package ziteng.lc.xf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.isSuccess;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/5/12.
 * 修改手机号码
 */

public class ChangPhoneNumberActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.et_phonenumber)
    EditText etPhonenumber;
    @BindView(R.id.btn_save)
    Button btnSave;
    private String personuuid;

    @Override
    protected int getLayout() {
        return R.layout.activity_changphonenum;
    }

    @Override
    protected void initData() {
        personuuid = (String) SPUtils.get(ChangPhoneNumberActivity.this,"personuuid","");
        tvTooltarTitle.setText("修改手机号码");
    }


    @OnClick({R.id.iv_toolbar_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_save:
                String phone =etPhonenumber.getText().toString().trim();
                if (StringUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("请填写手机号码");
                    etPhonenumber.requestFocus();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    ToastUtils.showShortToast("请填写正确的手机号码");
                    return;
                }
                sendNumToService(personuuid,phone);
                break;
        }
    }
    private void sendNumToService(String personuuid, final String phone){
        Map<String,String> map =new HashMap<>();
        map.put("personuuid",personuuid);
        map.put("phone",phone);
        GsonRequest<isSuccess> request =new GsonRequest<isSuccess>(Request.Method.POST, map, Url.CHANG_PHONENUM, isSuccess.class, new Response.Listener<isSuccess>() {
            @Override
            public void onResponse(isSuccess response) {
                if (response.isIssuccess()){
                    ToastUtils.showShortToast(response.getMessage());
                    Intent intent =new Intent();
                    intent.putExtra("phonenuber",phone);
                    setResult(1,intent);
                    onBackPressedSupport();
                }else {
                    ToastUtils.showShortToast(response.getMessage());
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
