package ziteng.lc.xf.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.Success;
import ziteng.lc.xf.bean.isSuccess;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/5/19.
 * 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.btn_achieve)
    Button btnAchieve;
    @BindView(R.id.et_newpassword)
    EditText etNewpassword;
    @BindView(R.id.et_comfirpassword)
    EditText etComfirpassword;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnAchieve.setEnabled(false);
            btnAchieve.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            btnAchieve.setEnabled(true);
            btnAchieve.setText("获取");
        }
    };
    private String phone;
    private String password;
    private String password1;

    @Override
    protected int getLayout() {
        return R.layout.activity_forgetpassword;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("密码重置");
    }


    @OnClick({R.id.iv_toolbar_back, R.id.btn_achieve, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_achieve:
                phone = etPhone.getText().toString().trim();
                if (EmptyUtils.isEmpty(phone)) {
                    ToastUtils.showShortToast("请输入手机号码");
                    etPhone.requestFocus();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showShortToast("请填写正确的手机号码");
                    etPhone.requestFocus();
                    return;
                }
                checkPhoneNumber(phone);
                break;
            case R.id.btn_confirm:
                String code = etYzm.getText().toString().trim();
                password = etNewpassword.getText().toString().trim();
                password1 = etComfirpassword.getText().toString().trim();
                if (EmptyUtils.isEmpty(code)) {
                    ToastUtils.showShortToast("请填写验证码");
                    etYzm.requestFocus();
                    return;
                }
                jyanYZM(phone, code);
                break;
        }
    }

    //校验手机号码是否被注册
    private void checkPhoneNumber(String phonenumber) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phonenumber);
        GsonRequest<isSuccess> request = new GsonRequest<isSuccess>(Request.Method.POST, map, Url.CHECK_PHONENUB, isSuccess.class, new Response.Listener<isSuccess>() {
            @Override
            public void onResponse(isSuccess response) {
                if (response.isIssuccess()) {
                    getYZM(phone);
                } else {
                    ToastUtils.showShortToast("此账号未注册");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取验证码
    private void getYZM(String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        GsonRequest<Success> request = new GsonRequest<Success>(Request.Method.POST, map, Url.YZM, Success.class, new Response.Listener<Success>() {
            @Override
            public void onResponse(Success response) {
                if (response.isSuccess()) {
                    ToastUtils.showShortToast("正在发送中...");
                    timer.start();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    /*
    * 校验验证码
    * */
    private void jyanYZM(final String phone, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        GsonRequest<isSuccess> request = new GsonRequest<isSuccess>(Request.Method.POST, map, Url.JIAOYAN_YZM, isSuccess.class, new Response.Listener<isSuccess>() {
            @Override
            public void onResponse(isSuccess response) {
                if (response.isIssuccess()) {
                    if (EmptyUtils.isEmpty(password)){
                        ToastUtils.showShortToast("密码不能为空");
                        etNewpassword.requestFocus();
                        return;
                    }

                    if (EmptyUtils.isEmpty(password1)){
                        ToastUtils.showShortToast("确认密码不能为空");
                        etComfirpassword.requestFocus();
                        return;
                    }
                    if (!password.equals(password1)){
                        ToastUtils.showShortToast("两次输入密码不一致");
                        etNewpassword.requestFocus();
                        return;
                    }
                    changePassword(phone,password);
                } else {
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

    //重置密码
    private void changePassword(String phone, String password) {
        Map<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);
        GsonRequest<isSuccess> request = new GsonRequest<isSuccess>(Request.Method.POST, map, Url.CHONGZHI_MIMA, isSuccess.class, new Response.Listener<isSuccess>() {
            @Override
            public void onResponse(isSuccess response) {
                if (response.isIssuccess()){
                    ToastUtils.showShortToast(response.getMessage());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
