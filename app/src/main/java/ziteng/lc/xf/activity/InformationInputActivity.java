package ziteng.lc.xf.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.adapter.TestArrayAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.bean.Sscy;
import ziteng.lc.xf.bean.Success;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;

/**
 * Created by luochao on 2017/3/23.
 * 信息录入
 */

public class InformationInputActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.sp_xmlx)
    Spinner spXmlx;
    @BindView(R.id.sp_cylb)
    Spinner spCylb;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.sp_xmbz)
    Spinner spXmbz;
    @BindView(R.id.sp_hzlb)
    Spinner sp_Hzlb;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_invest)
    EditText etInvest;
    @BindView(R.id.et_floor_area)
    EditText etFloorArea;
    @BindView(R.id.et_site_area)
    EditText etSiteArea;
    @BindView(R.id.et_intensity)
    EditText etIntensity;
    @BindView(R.id.et_date1)
    TextView etDate1;
    @BindView(R.id.et_cycle)
    EditText etCycle;
    @BindView(R.id.et_year_output)
    EditText etYearOutput;
    @BindView(R.id.et_tax_contribution)
    EditText etTaxContribution;
    @BindView(R.id.et_employment_pull)
    EditText etEmploymentPull;
    @BindView(R.id.et_project_descrip)
    EditText etProjectDescrip;
    @BindView(R.id.et_situations)
    EditText etSituations;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.et_tzzt)
    EditText etTzzt;
    @BindView(R.id.et_dqjz)
    EditText etDqjz;
    private ArrayAdapter<String> mAdapterSphefd;
    private String[] mStringArraySphefd;
    private ArrayAdapter<String> mAdapterSpxmlx;
    private String[] mStringArraySpxmlx;
    private ArrayAdapter<String> mAdaptercity;
    private String[] mStringArraycity;
    private ArrayAdapter<String> mAdapterspCylb;
    private String[] mStringArrayspCylb;
    private ArrayAdapter<String> mAdapterspXmbz;
    private String[] mStringArrayspXmbz;
    private ArrayAdapter<String> mAdapterspHzlx;
    private String[] mStringArrayspHzlx;
    private String xmlx;
    private String cylb;
    private String city;
    private String xmbz;
    private String hzlb;
    private String projectName;
    private String orgName;
    private String name;
    private String phone;
    private String email;
    private String invest;
    private String floorArea;
    private String siteArea;
    private String intensity;
    private String date1;
    private String cycle;
    private String yearOutput;
    private String taxContribution;
    private String employmentPull;
    private String projectDescrip;
    private String situations;
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private String personuuid;
    private List<String> list;
    private List<Sscy> sscyList;
    private String pronotes;
    private String dqjz;
    private String tzzt;


    @Override
    protected int getLayout() {
        return R.layout.activity_inforinput;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview() {
        tvTooltarTitle.setText("信息录入");
        personuuid = (String) SPUtils.get(InformationInputActivity.this, "personuuid", "");
        getSscy();
        changSpinnerText();
        getSpinnerSelect();
    }

    //修改Spinner默认字体的大小与颜色
    private void changSpinnerText() {

        mStringArraySpxmlx = getResources().getStringArray(R.array.xmlx);
        mAdapterSpxmlx = new TestArrayAdapter(InformationInputActivity.this, mStringArraySpxmlx, true);
        spXmlx.setAdapter(mAdapterSpxmlx);

        mStringArraycity = getResources().getStringArray(R.array.city);
        mAdaptercity = new TestArrayAdapter(InformationInputActivity.this, mStringArraycity, true);
        spCity.setAdapter(mAdaptercity);

//        mStringArrayspCylb = getResources().getStringArray(R.array.cylb);
//        mAdapterspCylb = new TestArrayAdapter(InformationInputActivity.this, mStringArrayspCylb);
//        spCylb.setAdapter(mAdapterspCylb);

        mStringArrayspXmbz = getResources().getStringArray(R.array.xmbz);
        mAdapterspXmbz = new TestArrayAdapter(InformationInputActivity.this, mStringArrayspXmbz, true);
        spXmbz.setAdapter(mAdapterspXmbz);

        mStringArrayspHzlx = getResources().getStringArray(R.array.hzlb);
        mAdapterspHzlx = new TestArrayAdapter(InformationInputActivity.this, mStringArrayspHzlx, true);
        sp_Hzlb.setAdapter(mAdapterspHzlx);
    }


    @OnClick({R.id.iv_toolbar_back, R.id.btn_save, R.id.btn_cancel, R.id.et_date1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_save:
                //当前进展
                dqjz = etDqjz.getText().toString().trim();
                //投资主体
                tzzt = etTzzt.getText().toString().trim();
                projectName = etProjectName.getText().toString().trim();
                name = etName.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                invest = etInvest.getText().toString().trim();
                floorArea = etFloorArea.getText().toString().trim();
                siteArea = etSiteArea.getText().toString().trim();
                intensity = etIntensity.getText().toString().trim();
                date1 = etDate1.getText().toString().trim();
                cycle = etCycle.getText().toString().trim();
                yearOutput = etYearOutput.getText().toString().trim();
                taxContribution = etTaxContribution.getText().toString().trim();
                employmentPull = etEmploymentPull.getText().toString().trim();
                projectDescrip = etProjectDescrip.getText().toString().trim();
                situations = etSituations.getText().toString().trim();

                //项目名称，企业名称，姓名，电话，电子邮箱，总投资（万元），总占地面积（亩），一期用地面积（亩）,项目概述
                if (EmptyUtils.isEmpty(projectName)) {
                    ToastUtils.showShortToast("请填写项目名称");
                    etProjectName.requestFocus();
                    return;
                }


                if (EmptyUtils.isEmpty(name)) {
                    ToastUtils.showShortToast("请填写姓名");
                    etName.requestFocus();
                    return;
                }

                if (!EmptyUtils.isEmpty(phone)) {
                    if (!RegexUtils.isMobileExact(phone)) {
                        ToastUtils.showShortToast("请填写正确的电话号码");
                        etPhone.requestFocus();
                        return;
                    }
                } else {
                    ToastUtils.showShortToast("请填写电话号码");
                    etPhone.requestFocus();
                    return;
                }

                if (!EmptyUtils.isEmpty(email)) {
                    if (!RegexUtils.isEmail(email)) {
                        ToastUtils.showShortToast("请填写正确的电子邮箱");
                        etEmail.requestFocus();
                        return;
                    }
                } else {
                    ToastUtils.showShortToast("请填写电子邮箱");
                    etEmail.requestFocus();
                    return;
                }

                if (EmptyUtils.isEmpty(invest)) {
                    ToastUtils.showShortToast("请填写总投资");
                    etInvest.requestFocus();
                    return;
                }

                if (EmptyUtils.isEmpty(floorArea)) {
                    ToastUtils.showShortToast("请填写总占地面积");
                    etFloorArea.requestFocus();
                    return;
                }

                if (EmptyUtils.isEmpty(siteArea)) {
                    ToastUtils.showShortToast("请填写一期用地面积");
                    etSiteArea.requestFocus();
                    return;
                }

                if (EmptyUtils.isEmpty(projectDescrip)) {
                    ToastUtils.showShortToast("请填写项目概述");
                    etProjectDescrip.requestFocus();
                    return;
                }
                sendInfoToService();

                break;
            case R.id.btn_cancel:
                onBackPressedSupport();
                break;
            case R.id.et_date1://时间选择器
                showDateDialog();
                break;
        }
    }

    //获取该页面editext的值与spinner所选中的值
    private void getSpinnerSelect() {

        spXmlx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spxmlx = getResources().getStringArray(R.array.xmlx)[position];
                if (spxmlx.equals("工业")) {
                    xmlx = "1";
                } else if (spxmlx.equals("农业")) {
                    xmlx = "2";
                } else if (spxmlx.equals("服务业")) {
                    xmlx = "3";
                } else if (spxmlx.equals("旅游业")) {
                    xmlx = "4";
                } else if (spxmlx.equals("其他")) {
                    xmlx = "5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCylb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spcylb = list.get(position);
                for (Sscy sscy : sscyList) {
                    if (spcylb.equals(sscy.getCategory_name())) {
                        cylb = sscy.getCategory_id();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //城市选择
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!StringUtils.isEmpty(city1)) {
                    city = StringArray(city1)[position];
                } else {
                    city = getResources().getStringArray(R.array.city)[position];
                }
                if (!city.isEmpty()) {
                    if (city.equals("北京")) {
                        city = "1";
                    } else if (city.equals("天津")) {
                        city = "2";
                    } else if (city.equals("上海")) {
                        city = "3";
                    } else if (city.equals("河北")) {
                        city = "4";
                    } else if (city.equals("其他")) {
                        Intent intent = new Intent(InformationInputActivity.this, SelectCityActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_PICK_CITY);
                    } else {
                        city = "5";
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spXmbz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spxmbz = getResources().getStringArray(R.array.xmbz)[position];
                if (spxmbz.equals("全球500强")) {
                    xmbz = "1";
                } else if (spxmbz.equals("全国500强")) {
                    xmbz = "2";
                } else if (spxmbz.equals("央企")) {
                    xmbz = "3";
                } else if (spxmbz.equals("上市公司")) {
                    xmbz = "4";
                } else if (spxmbz.equals("其他")) {
                    xmbz = "5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_Hzlb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sphzlb = getResources().getStringArray(R.array.hzlb)[position];
                if (sphzlb.equals("鼓励类")) {
                    hzlb = "1";
                } else if (sphzlb.equals("允许类")) {
                    hzlb = "2";
                } else if (sphzlb.equals("限制类")) {
                    hzlb = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //提交信息至后台
    private void sendInfoToService() {
        Map<String, String> map = new HashMap<>();
        map.put("project_name", projectName);
        map.put("investment_entity",dqjz);
        map.put("current_progress",tzzt);
        map.put("project_type", xmlx);
        map.put("industry", cylb);
        map.put("colony", city);
        map.put("note", xmbz);
        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("project_descrip", projectDescrip);
        map.put("invest", invest);
        map.put("floor_area", floorArea);
        map.put("site_area", siteArea);
        map.put("intensity", intensity);
        map.put("expect_date", date1);
        map.put("cycle", cycle);
        map.put("year_output", yearOutput);
        map.put("tax_contribution", taxContribution);
        map.put("employment_pull", employmentPull);
        map.put("category", hzlb);
        map.put("product", "");
        map.put("situations", situations);
        map.put("personuuid", personuuid);
        map.put("pronotes", (pronotes == null) ? "" : pronotes);
        GsonRequest<Success> request = new GsonRequest<Success>(Request.Method.POST, map, Url.InfomationInput, Success.class, new Response.Listener<Success>() {
            @Override
            public void onResponse(Success response) {
                if (response != null && response.isSuccess()) {
                    ToastUtils.showShortToast("提交成功");
                    finish();
                } else {
                    ToastUtils.showShortToast("录入数据失败，请重试");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("lc", error.toString());
            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //时间选择器
    private Calendar showDate;

    private void showDateDialog() {
        showDate = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDate.set(Calendar.YEAR, year);
                showDate.set(Calendar.MONTH, monthOfYear);
                showDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                etDate1.setText(DateFormat.format("yyyy-MM-dd", showDate));
            }
        }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String city1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                city1 = data.getStringExtra("cityname");
                if (city1 != null) {
                    city = "5";
                    StringArray(city1)[5] = city1;
                    mAdaptercity = new TestArrayAdapter(InformationInputActivity.this, StringArray(city1), true);
                    spCity.setAdapter(mAdaptercity);
                    spCity.setSelection(5);
                    pronotes = city1;
                }
            }

        }
    }

    //在数组中增加一个元素
    public String[] StringArray(String s) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < mStringArraycity.length; i++) {
            list.add(mStringArraycity[i]);
        }
        list.add(5, s);
        String[] newStr = list.toArray(new String[1]); //返回一个包含所有对象的指定类型的数组
        return newStr;
    }

    //获取所属产业类别接口
    private void getSscy() {
        TypeToken type = new TypeToken<List<Sscy>>() {
        };
        GsonRequest<List<Sscy>> request = new GsonRequest<List<Sscy>>(Url.SSCY, type, new Response.Listener<List<Sscy>>() {
            @Override
            public void onResponse(List<Sscy> listsscy) {
                sscyList = listsscy;
                list = new ArrayList<>();
                if (listsscy != null && listsscy.size() > 0) {
                    for (Sscy sscy : listsscy) {
                        list.add(sscy.getCategory_name());
                    }
                }
                if (list != null && list.size() > 0) {
                    TestArrayAdapter adapter = new TestArrayAdapter(InformationInputActivity.this, list, false);
                    spCylb.setAdapter(adapter);
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
