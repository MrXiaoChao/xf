package ziteng.lc.xf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.TestArrayAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.UserInfo;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.MyDialog;
import ziteng.lc.xf.widegt.SPUtils;


/**
 * Created by luochao on 2017/3/16.
 * 登录页面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.sp_enter)
    Spinner spEnter;
    @BindView(R.id.tv_forgetpassword)
    TextView tvForgetpassword;
    private ArrayAdapter<String> mAdapterEnter;
    private String[] mStringArrayEnter;
    private String spinner;
    private int sp = 1;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        changSpinnerText();
        initview();
    }

    private void initview() {
        //监听获取spinner所选中的值
        spEnter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = getResources().getStringArray(R.array.enter)[position];
                if (spinner.equals("个人登录")) {
                    sp = 1;
                } else if (spinner.equals("社会团体")) {
                    sp = 2;
                } else if (spinner.equals("政府部门")) {
                    sp = 3;
                }
            }

            //1：个人账号2：企业账号3：责任单位4：管理员5：领导
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //修改Spinner默认字体的大小与颜色
    private void changSpinnerText() {
        mStringArrayEnter = getResources().getStringArray(R.array.enter);
        mAdapterEnter = new TestArrayAdapter(LoginActivity.this, mStringArrayEnter, true);
        spEnter.setAdapter(mAdapterEnter);
    }

    @Override
    public void onBackPressedSupport() {
        showExitDialog();
    }

    private void showExitDialog() {
        final MyDialog dialog = new MyDialog(LoginActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("你确定退出应用吗?");
        dialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                App.getInstance().exitApp();
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

    //从服务器取数据
    private void getDataFromService(String username, String password, final int sp) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        final GsonRequest<UserInfo> request = new GsonRequest<UserInfo>(Request.Method.POST, map, Url.LoginUrl, UserInfo.class, new Response.Listener<UserInfo>() {
            @Override
            public void onResponse(UserInfo response) {
                if (response.isIssuccess()) {
                    String personuuid = response.getPersonuuid();
                    String phone = response.getPhone();
                    String idCard = response.getIdcard();
                    String name = response.getName();
                    String usename = response.getUsername();
                    String org_name = (String) response.getOrg_name();
                    String link_man=response.getLink_man();
                    String office_phone=response.getOffice_phone();
                    String email=response.getEmail();
                    String org_code=response.getOrg_code();
                    //status 1：个人账号2：企业账号3：责任单位4：市领导5：管理员
                    String status=response.getStatus();
                    if (sp == 3) {
                        if (response.getStatus().equals("3")|| response.getStatus().equals("4")|| response.getStatus().equals("5")) {
                            SPUtils.put(LoginActivity.this, "personuuid", personuuid);
                            SPUtils.put(LoginActivity.this, "phone", phone);
                            SPUtils.put(LoginActivity.this, "idcard", idCard);
                            SPUtils.put(LoginActivity.this, "name", name);
                            SPUtils.put(LoginActivity.this, "usename", usename);
                            SPUtils.put(LoginActivity.this, "org_name", org_name);
                            SPUtils.put(LoginActivity.this,"status",status);
                            SPUtils.put(LoginActivity.this,"link_man",link_man);
                            SPUtils.put(LoginActivity.this,"office_phone",office_phone);
                            SPUtils.put(LoginActivity.this,"email",email);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShortToast("登录方式出错了");
                        }
                    } else {
                        if (sp==Integer.valueOf(response.getStatus())) {
                            SPUtils.put(LoginActivity.this, "personuuid", personuuid);
                            SPUtils.put(LoginActivity.this, "phone", phone);
                            SPUtils.put(LoginActivity.this, "idcard", idCard);
                            SPUtils.put(LoginActivity.this, "name", name);
                            SPUtils.put(LoginActivity.this, "usename", usename);
                            SPUtils.put(LoginActivity.this, "org_name", org_name);
                            SPUtils.put(LoginActivity.this,"status",status);
                            SPUtils.put(LoginActivity.this,"org_code",org_code);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShortToast("登录方式出错了");
                        }
                    }

                } else {
                    ToastUtils.showShortToast("账号密码错误");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShortToast("账号密码错误");
            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    @OnClick({R.id.btn_enter, R.id.btn_register,R.id.tv_forgetpassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                String user = etUser.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (EmptyUtils.isEmpty(user)) {
                    ToastUtils.showShortToast("账号不能为空");
                    etUser.requestFocus();//把焦点转移到要填写信息之处
                    return;
                } else if (EmptyUtils.isEmpty(password)) {
                    ToastUtils.showShortToast("密码不能为空");
                    etPassword.requestFocus();
                    return;
                }
                getDataFromService(user, password, sp);
                break;
            case R.id.btn_register:
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                break;
            case R.id.tv_forgetpassword:
                if (spinner.equals("政府部门")){
                    ToastUtils.showShortToast("请联系管理员");
                }else {
                    Intent intent =new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
