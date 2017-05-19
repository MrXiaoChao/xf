package ziteng.lc.xf.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.TestArrayAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.Register;
import ziteng.lc.xf.bean.Success;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;

/**
 * 注册页面
 * Created by luochao on 2017/4/10.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.sp_register)
    Spinner spRegister;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sfNumber)
    TextView tvSfNumber;
    @BindView(R.id.tv_phoneNuber)
    TextView tvPhoneNuber;
    @BindView(R.id.tv_yzm)
    TextView tvYzm;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sfz)
    EditText etSfz;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.btn_achieve)
    Button btnAchieve;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_zhanhao)
    EditText etZhanghao;
    @BindView(R.id.tv_zhanhao)
    TextView tvZhanghao;
    @BindView(R.id.et_comfirpassword)
    EditText etComfirpassword;
    private String[] mStringarray;
    private ArrayAdapter<String> adapter;
    private String selectText;
    private String phone;
    private int sp;

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
    private String name;
    private String sfz;
    private String yzm;
    private String textZhanghao;
    private String password;
    private String useName;
    private String comfirpassword;


    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview() {
        tvTooltarTitle.setText("注册");
        changText();
        changTextContent();
    }


    //根据注册类型的不同修改TextView的内容
    private void changTextContent() {
        spRegister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectText = getResources().getStringArray(R.array.register)[position];
                if (!selectText.equals("个人注册")) {
                    tvName.setText("团体名称");
                    tvSfNumber.setText("机构编码");
                    tvPhoneNuber.setText("联系方式");
                    tvZhanghao.setText("团体账号");
                    sp=2;
                } else {
                    tvZhanghao.setText("账号");
                    tvName.setText("姓名");
                    tvSfNumber.setText("身份证号");
                    tvPhoneNuber.setText("手机号码");
                    tvZhanghao.setText("账号");
                    sp=1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //修改SP的默认字体大小
    private void changText() {
        mStringarray = getResources().getStringArray(R.array.register);
        adapter = new TestArrayAdapter(RegisterActivity.this, mStringarray,true);
        spRegister.setAdapter(adapter);
    }

    @OnClick({R.id.iv_toolbar_back, R.id.btn_achieve, R.id.btn_register, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_achieve:
                useName=etZhanghao.getText().toString();
                textZhanghao = tvZhanghao.getText().toString().trim();
                name = etName.getText().toString().trim();
                sfz = etSfz.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                password=etPassword.getText().toString().trim();
                comfirpassword=etComfirpassword.getText().toString().trim();
                if (textZhanghao.equals("账号")) {
                    if (EmptyUtils.isEmpty(useName)) {
                        ToastUtils.showShortToast("请输入账号");
                        etZhanghao.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(name)) {
                        ToastUtils.showShortToast("请输入姓名");
                        etName.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(password)) {
                        ToastUtils.showShortToast("请输入密码");
                        etPassword.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(comfirpassword)) {
                        ToastUtils.showShortToast("请再次输入密码");
                        etComfirpassword.requestFocus();
                        return;
                    }

                    if (!RegexUtils.isIDCard18(sfz)) {
                        ToastUtils.showShortToast("请输入正确的身份证号码");
                        etSfz.requestFocus();
                        return;
                    }
                    if (!RegexUtils.isMobileExact(phone)) {
                        ToastUtils.showShortToast("请输入正确的手机号码");
                        etPhone.requestFocus();
                        return;
                    }
                    if (!password.equals(comfirpassword)){
                        ToastUtils.showShortToast("密码输入不一致");
                        etPassword.requestFocus();
                        return;
                    }
                }else {
                    if (EmptyUtils.isEmpty(name)) {
                        ToastUtils.showShortToast("请输入团体名称");
                        etName.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(sfz)) {
                        ToastUtils.showShortToast("请输入机构编码");
                        etSfz.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(name)) {
                        ToastUtils.showShortToast("请输入姓名");
                        etName.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(password)) {
                        ToastUtils.showShortToast("请输入密码");
                        etPassword.requestFocus();
                        return;
                    }
                    if (EmptyUtils.isEmpty(phone)) {
                        ToastUtils.showShortToast("请输入联系方式");
                        etPhone.requestFocus();
                        return;
                    }
                    if (!password.equals(comfirpassword)){
                        ToastUtils.showShortToast("密码输入不一致");
                        etPassword.requestFocus();
                        return;
                    }
                }
                timer.start();
                getYzm();
                break;
            case R.id.btn_register:
                yzm = etYzm.getText().toString().trim();
                if (EmptyUtils.isEmpty(yzm)){
                    ToastUtils.showShortToast("请填写验证码");
                    etYzm.requestFocus();
                    return;
                }
                getRegister();
                break;
            case R.id.btn_cancel:
                Intent intent_cancel = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent_cancel);
                break;
        }
    }

    //获取验证码
    private void getYzm() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        GsonRequest<Success> request = new GsonRequest<Success>(Request.Method.POST, map, Url.YZM, Success.class, new Response.Listener<Success>() {
            @Override
            public void onResponse(Success response) {
                if (response.isSuccess()) {
                    ToastUtils.showShortToast("正在发送中...");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    private void getRegister() {
        Map<String, String> map = new HashMap<>();
        map.put("username", useName);
        map.put("idcard", sfz);
        map.put("phone", phone);
        map.put("code", yzm);
        map.put("status", String.valueOf(sp));
        map.put("name",name);
        map.put("password",password);
        GsonRequest<Register> request = new GsonRequest<Register>(Request.Method.POST, map, Url.Register, Register.class, new Response.Listener<Register>() {
            @Override
            public void onResponse(Register response) {
                if (response.isSuccess()) {
                    ToastUtils.showShortToast("注册成功");
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
