package ziteng.lc.xf.activity;


import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
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
 * Created by luochao on 2017/3/30.
 */

public class ChangPassWordActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.et_currentpassword)
    EditText etCurrentpassword;
    @BindView(R.id.iv_currentpassword)
    ImageView ivCurrentpassword;
    @BindView(R.id.et_newpassword)
    EditText etNewpassword;
    @BindView(R.id.et_verifypassword)
    EditText etVerifypassword;
    @BindView(R.id.iv_newpassword)
    ImageView ivNewpassword;
    @BindView(R.id.iv_et_verifypassword)
    ImageView ivEtVerifypassword;
    @BindView(R.id.btn_save)
    Button btnSave;
    private String personuuid;

    @Override
    protected int getLayout() {
        return R.layout.activity_changpassword;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview() {
        personuuid = (String) SPUtils.get(ChangPassWordActivity.this, "personuuid", "");
        tvTooltarTitle.setText("修改密码");
        etCurrentpassword.setHorizontallyScrolling(true);    //设置EditText不换行
    }

    private boolean flagcurrent = false;
    private boolean flagnew = false;
    private boolean flagverify = false;

    @OnClick({R.id.iv_toolbar_back, R.id.iv_currentpassword, R.id.iv_newpassword, R.id.iv_et_verifypassword, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.iv_currentpassword:
                if (flagcurrent) {
                    //密码不可见
                    etCurrentpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flagcurrent = false;
                } else {
                    //密码可见
                    etCurrentpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flagcurrent = true;
                }
                break;
            case R.id.iv_newpassword:
                if (flagnew) {
                    //密码不可见
                    etNewpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flagnew = false;
                } else {
                    //密码可见
                    etNewpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flagnew = true;
                }
                break;
            case R.id.iv_et_verifypassword:
                if (flagverify) {
                    //密码不可见
                    etVerifypassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flagverify = false;
                } else {
                    //密码可见
                    etVerifypassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flagverify = true;
                }
                break;
            case R.id.btn_save:
                String currentPassword = etCurrentpassword.getText().toString().trim();
                String newPassword = etNewpassword.getText().toString().trim();
                String verifypassword = etVerifypassword.getText().toString().trim();
                if (EmptyUtils.isEmpty(currentPassword)) {
                    ToastUtils.showShortToast("当前密码不能为空");
                    etCurrentpassword.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(newPassword)) {
                    ToastUtils.showShortToast("新密码不能为空");
                    etNewpassword.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(verifypassword)) {
                    ToastUtils.showShortToast("确认密码不能为空");
                    etVerifypassword.requestFocus();
                    return;
                }
                if (!StringUtils.equals(newPassword, verifypassword)) {
                    ToastUtils.showShortToast("新密码输入不一致");
                    return;
                }
                ChangPassword(personuuid, currentPassword, newPassword);
                break;
        }
    }

    //修改密码
    public void ChangPassword(String personuuid, String oldpwd, String newpwd) {
        Map<String, String> map = new HashMap<>();
        map.put("personuuid", personuuid);
        map.put("oldpwd", oldpwd);
        map.put("newpwd", newpwd);
        GsonRequest<isSuccess> request = new GsonRequest<isSuccess>(Request.Method.POST, map, Url.CHANG_PASSWORD, isSuccess.class, new Response.Listener<isSuccess>() {
            @Override
            public void onResponse(isSuccess response) {
                if (response.isIssuccess()) {
                    ToastUtils.showShortToast(response.getMessage());
                    onBackPressedSupport();
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
}
