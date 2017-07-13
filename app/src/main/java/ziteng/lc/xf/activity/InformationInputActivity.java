package ziteng.lc.xf.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
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
import ziteng.lc.xf.util.ListUtils;
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
    @BindView(R.id.tv_xmlx)
    TextView tvXmlx;
    @BindView(R.id.tv_cylb)
    TextView tvCylb;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_xmbz)
    TextView tvXmbz;
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
        mStringArrayspHzlx = getResources().getStringArray(R.array.hzlb);
        mAdapterspHzlx = new TestArrayAdapter(InformationInputActivity.this, mStringArrayspHzlx, true);
        sp_Hzlb.setAdapter(mAdapterspHzlx);
    }


    @OnClick({R.id.iv_toolbar_back, R.id.btn_save, R.id.btn_cancel, R.id.et_date1, R.id.tv_xmlx, R.id.tv_xmbz, R.id.tv_cylb})
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
            case R.id.tv_xmlx:
                creatDialogXMLX();
                break;
            case R.id.tv_xmbz:
                creatDialogXMBZ();
                break;
            case R.id.tv_cylb:
                creatDialogCYLB();
                break;
        }
    }

    //获取该页面editext的值与spinner所选中的值
    private void getSpinnerSelect() {
        //城市选择
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatDialog();
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
        map.put("investment_entity", dqjz);
        map.put("current_progress", tzzt);
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
                for (int i = 0; i < stringList.size(); i++) {
                    if (stringList.get(i).equals("其他")) {
                        stringList.remove(i);
                        break;
                    }
                }
                city1 = data.getStringExtra("cityname");
                pronotes = city1;
                stringList.add(city1);
            }
        }
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //创建选择城市的dialog
    List<String> stringList = new LinkedList<>();
    List<String> stringList1 = new LinkedList<>();

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationInputActivity.this);
        builder.setTitle("项目属地");
        final String[] hobbies = {"北京", "天津", "河北", "上海", "其他"};

        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (which == 4 && isChecked) {
                    Intent intent = new Intent(InformationInputActivity.this, SelectCityActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PICK_CITY);
                }
                if (isChecked) {
                    stringList.add(hobbies[which]);
                }
                if (!isChecked) {
                    stringList.remove(hobbies[which]);
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvCity.setText(ListUtils.listToString(stringList));
                //把这种字符串 北京,天津 改为 1,2
                for (String s : stringList) {
                    if (StringUtils.equals(s, "北京")) {
                        stringList1.add("1");
                    } else if (StringUtils.equals(s, "天津")) {
                        stringList1.add("2");
                    } else if (StringUtils.equals(s, "河北")) {
                        stringList1.add("3");
                    } else if (StringUtils.equals(s, "上海")) {
                        stringList1.add("4");
                    } else {
                        stringList1.add("5");
                    }
                }
                city = ListUtils.listToString(stringList1);
                stringList.clear();
                stringList1.clear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //创建选择项目类型的dialog
    List<String> stringListXMLX = new LinkedList<>();
    List<String> stringListXMLX1 = new LinkedList<>();


    private void creatDialogXMLX() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationInputActivity.this);
        builder.setTitle("项目类型");
        final String[] hobbies = {"工业", "农业", "服务业", "旅游业", "其他"};
        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    stringListXMLX.add(hobbies[which]);
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvXmlx.setText(ListUtils.listToString(stringListXMLX));
                for (String s : stringListXMLX) {
                    if (StringUtils.equals(s, "工业")) {
                        stringListXMLX1.add("1");
                    } else if (StringUtils.equals(s, "农业")) {
                        stringListXMLX1.add("2");
                    } else if (StringUtils.equals(s, "服务业")) {
                        stringListXMLX1.add("3");
                    } else if (StringUtils.equals(s, "旅游业")) {
                        stringListXMLX1.add("4");
                    } else {
                        stringListXMLX1.add("5");
                    }
                }
                xmlx = ListUtils.listToString(stringListXMLX1);
                stringListXMLX.clear();
                stringListXMLX1.clear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //创建选择项目类型的dialog
    List<String> stringListXMBZ = new LinkedList<>();
    List<String> stringListXMBZ1 = new LinkedList<>();

    private void creatDialogXMBZ() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationInputActivity.this);
        builder.setTitle("项目备注");
        final String[] hobbies = {"全球500强", "全国500强", "央企", "上市公司", "其他"};
        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    stringListXMBZ.add(hobbies[which]);
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvXmbz.setText(ListUtils.listToString(stringListXMBZ));
                for (String s : stringListXMBZ) {
                    if (StringUtils.equals(s,"全球500强")) {
                        stringListXMBZ1.add("1");
                    } else if (StringUtils.equals(s,"全国500强")) {
                        stringListXMBZ1.add("2");
                    } else if (StringUtils.equals(s,"央企")) {
                        stringListXMBZ1.add("3");
                    } else if (StringUtils.equals(s,"上市公司")) {
                        stringListXMBZ1.add("4");
                    } else if (StringUtils.equals(s,"其他")){
                        stringListXMBZ1.add("5");
                    }
                }
                xmbz = ListUtils.listToString(stringListXMBZ1);
                stringListXMBZ.clear();
                stringListXMBZ1.clear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //创建所属类别的dialog
    List<String> stringListSSLB = new LinkedList<>();
    List<String> stringListSSLB1 = new LinkedList<>();

    private void creatDialogCYLB() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationInputActivity.this);
        builder.setTitle("产业类别");
        final String[] hobbies = ListUtils.listToStrings(list);

        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    stringListSSLB.add(hobbies[which]);
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvCylb.setText(ListUtils.listToString(stringListSSLB));
                for (int i = 0; i < stringListSSLB.size(); i++) {
                    for (Sscy sscy : sscyList) {
                        if (sscy.getCategory_name().equals(stringListSSLB.get(i))) {
                            stringListSSLB1.add(sscy.getCategory_id());
                        }
                    }
                }
                cylb = ListUtils.listToString(stringListSSLB1);
                Log.i("lc",cylb);
                stringListSSLB.clear();
                stringListSSLB1.clear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
