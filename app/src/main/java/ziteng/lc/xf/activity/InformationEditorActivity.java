package ziteng.lc.xf.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.zaaach.citypicker.CityPickerActivity;

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
import ziteng.lc.xf.bean.ProjectInfoEditext;
import ziteng.lc.xf.bean.Sscy;
import ziteng.lc.xf.bean.Success;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;

/**
 * Created by luochao on 2017/4/12.
 * 信息编辑
 */

public class InformationEditorActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.project_name)
    EditText projectName;
    @BindView(R.id.org_name)
    EditText orgName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.project_descrip)
    EditText projectDescrip;
    @BindView(R.id.situations)
    EditText situations;
    @BindView(R.id.invest)
    EditText invest;
    @BindView(R.id.floor_area)
    EditText floorArea;
    @BindView(R.id.site_area)
    EditText siteArea;
    @BindView(R.id.intensity)
    EditText intensity;
    @BindView(R.id.cycle)
    EditText cycle;
    @BindView(R.id.year_output)
    EditText yearOutput;
    @BindView(R.id.tax_contribution)
    EditText taxContribution;
    @BindView(R.id.employment_pull)
    EditText employmentPull;
    @BindView(R.id.sp_hefs)
    Spinner spHefs;
    @BindView(R.id.sp_xmlx)
    Spinner spXmlx;
    @BindView(R.id.sp_cylb)
    Spinner spCylb;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.sp_xmbz)
    Spinner spXmbz;
    @BindView(R.id.sp_hzlb)
    Spinner sp_Hzlb;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private ArrayAdapter<String> mAdapterSphefd;
    private String[] mStringArraySphefd;
    private ArrayAdapter<String> mAdapterSpxmlx;
    private String[] mStringArraySpxmlx;
    private ArrayAdapter<String> mAdapterspXmbz;
    private String[] mStringArrayspXmbz;
    private ArrayAdapter<String> mAdapterspHzlx;
    private String[] mStringArrayspHzlx;

    private String hefs1;
    private String xmlx1;
    private String cylb1;
    private String city1;
    private String xmbz1;
    private String hzlb1;
    private String projectName1;
    private String orgName1;
    private String name1;
    private String phone1;
    private String email1;
    private String invest1;
    private String floorArea1;
    private String siteArea1;
    private String intensity1;
    private String date11;
    private String cycle1;
    private String yearOutput1;
    private String taxContribution1;
    private String employmentPull1;
    private String projectDescrip1;
    private String situations1;
    private String pronotes;

    private static final int REQUEST_CODE_PICK_CITY = 0;
    private String project_id;
    private String project_status;


    @Override
    protected int getLayout() {
        return R.layout.activity_inforeditor;
    }

    @Override
    protected void initData() {
        initview();
    }

    private void initview() {
        tvTooltarTitle.setText("信息编辑");
        getSscy();
        changSpinnerText();
        getSpinnerSelect();
        getStringforintent();
        getDataFromSerVice(project_id);
    }

    //获取上一个页面传过来的值
    private void getStringforintent() {
        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");
        project_status = intent.getStringExtra("project_status");

        //根据传过来的project_status设置权限 1:录入未签约 2:签约 3:完成  (项目签约后不能修改,以此字段来区分)
        //要求，项目状态只有在“未签约”时才能修改；其他状态下只能查看内容，不能进行修改，（要去掉 保存 按钮，内容为只读状态）
        if (!project_status.equals("1")) {
            setEditextToEnabled(projectName,false);
            setEditextToEnabled(orgName,false);
            setEditextToEnabled(name,false);
            setEditextToEnabled(phone,false);
            setEditextToEnabled(email,false);
            btnSave.setVisibility(View.GONE);
            setEditextToEnabled(projectDescrip,false);
            setEditextToEnabled(situations,false);
            setEditextToEnabled(invest,false);
            setEditextToEnabled(floorArea,false);
            setEditextToEnabled(siteArea,false);
            setEditextToEnabled(intensity,false);
            setEditextToEnabled(cycle,false);
            setEditextToEnabled(yearOutput,false);
            setEditextToEnabled(taxContribution,false);
            setEditextToEnabled(employmentPull,false);
            setSpinnerToEnabled(spHefs,false);
            setSpinnerToEnabled(spXmlx,false);
            setSpinnerToEnabled(spCylb,false);
            setSpinnerToEnabled(spXmbz,false);
            setSpinnerToEnabled(sp_Hzlb,false);
            tvDate.setFocusable(false);
            tvDate.setFocusableInTouchMode(false);
            tvDate.setEnabled(false);
            tvCity.setFocusable(false);
            tvCity.setFocusableInTouchMode(false);
            tvCity.setEnabled(false);
        }
    }
    //Editext设置不可以编辑
    public void setEditextToEnabled(EditText editext,boolean t){
        editext.setFocusable(t);
        editext.setFocusableInTouchMode(t);
        editext.setEnabled(t);
    }

    //Editext设置不可以编辑
    public void setSpinnerToEnabled(Spinner Spinner,boolean t){
        Spinner.setFocusable(t);
        Spinner.setFocusableInTouchMode(t);
        Spinner.setEnabled(t);
    }

    //修改Spinner默认字体的大小与颜色
    private void changSpinnerText() {
        mStringArraySphefd = getResources().getStringArray(R.array.hzfs);
        mAdapterSphefd = new TestArrayAdapter(InformationEditorActivity.this, mStringArraySphefd, true);
        spHefs.setAdapter(mAdapterSphefd);

        mStringArraySpxmlx = getResources().getStringArray(R.array.xmlx);
        mAdapterSpxmlx = new TestArrayAdapter(InformationEditorActivity.this, mStringArraySpxmlx, true);
        spXmlx.setAdapter(mAdapterSpxmlx);

        mStringArrayspXmbz = getResources().getStringArray(R.array.xmbz);
        mAdapterspXmbz = new TestArrayAdapter(InformationEditorActivity.this, mStringArrayspXmbz, true);
        spXmbz.setAdapter(mAdapterspXmbz);

        mStringArrayspHzlx = getResources().getStringArray(R.array.hzlb);
        mAdapterspHzlx = new TestArrayAdapter(InformationEditorActivity.this, mStringArrayspHzlx, true);
        sp_Hzlb.setAdapter(mAdapterspHzlx);
    }


    //项目信息回显
    private void getDataFromSerVice(String project_id) {
        Map<String, String> map = new HashMap<>();
        map.put("project_id", project_id);
        GsonRequest<ProjectInfoEditext> request = new GsonRequest<ProjectInfoEditext>(Request.Method.POST, map, Url.InformationEditext, ProjectInfoEditext.class, new Response.Listener<ProjectInfoEditext>() {
            @Override
            public void onResponse(ProjectInfoEditext response) {
                projectName.setText(response.getProject_name());
                orgName.setText(response.getOrg_name());
                name.setText(response.getName());
                phone.setText(response.getPhone());
                email.setText(response.getEmail());
                projectDescrip.setText(response.getProject_descrip());
                situations.setText(response.getSituations());
                invest.setText(response.getInvest());
                floorArea.setText(response.getFloor_area());
                siteArea.setText(response.getSite_area());
                intensity.setText(response.getIntensity());
                cycle.setText(response.getCycle());
                yearOutput.setText(response.getYear_output());
                taxContribution.setText(response.getTax_contribution());
                employmentPull.setText(response.getEmployment_pull());
                tvDate.setText(DateFormat.format("yyyy-MM-dd", response.getExpect_date()));
                //是否外资
                if (response.getCooperation().equals("1")) {
                    spHefs.setSelection(0);
                } else {
                    spHefs.setSelection(1);
                }
                //项目类型
                if (response.getProject_type().equals("1")) {
                    spXmlx.setSelection(0);
                } else if (response.getProject_type().equals("2")) {
                    spXmlx.setSelection(1);
                } else if (response.getProject_type().equals("3")) {
                    spXmlx.setSelection(2);
                } else if (response.getProject_type().equals("4")) {
                    spXmlx.setSelection(3);
                } else if (response.getProject_type().equals("5")) {
                    spXmlx.setSelection(4);
                }
                //所属产业
                if (sscyList != null && sscyList.size() > 0) {
                    for (Sscy sscy : sscyList) {
                        if (response.getIndustry().equals(sscy.getCategory_id())) {
                            spCylb.setSelection(sscy.getSqe() - 1);
                        }
                    }
                }
                //所属城市
                if (response.getColony().equals("1")) {
                    tvCity.setText("北京");
                } else if (response.getColony().equals("2")) {
                    tvCity.setText("天津");
                } else if (response.getColony().equals("3")) {
                    tvCity.setText("上海");
                } else if (response.getColony().equals("4")) {
                    tvCity.setText("河北");
                } else if (response.getColony().equals("5")) {
                    tvCity.setText(response.getPronotes());
                }

                //项目备注
                if (response.getNote().equals("1")) {
                    spXmbz.setSelection(0);
                } else if (response.getNote().equals("2")) {
                    spXmbz.setSelection(1);
                } else if (response.getNote().equals("3")) {
                    spXmbz.setSelection(2);
                } else if (response.getNote().equals("4")) {
                    spXmbz.setSelection(3);
                } else if (response.getNote().equals("5")) {
                    spXmbz.setSelection(4);
                }
                //合作类别
                if (response.getCategory().equals("1")) {
                    sp_Hzlb.setSelection(0);
                } else if (response.getCategory().equals("2")) {
                    sp_Hzlb.setSelection(1);
                } else if (response.getCategory().equals("3")) {
                    sp_Hzlb.setSelection(2);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }


    //获取该页面editext的值与spinner所选中的值
    private void getSpinnerSelect() {
        //获取城市的值

        spHefs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sphefs = getResources().getStringArray(R.array.hzfs)[position];
                if (sphefs.equals("是")) {
                    hefs1 = "1";
                } else {
                    hefs1 = "2";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spXmlx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spxmlx = getResources().getStringArray(R.array.xmlx)[position];
                if (spxmlx.equals("工业")) {
                    xmlx1 = "1";
                } else if (spxmlx.equals("农业")) {
                    xmlx1 = "2";
                } else if (spxmlx.equals("服务业")) {
                    xmlx1 = "3";
                } else if (spxmlx.equals("旅游业")) {
                    xmlx1 = "4";
                } else if (spxmlx.equals("其他")) {
                    xmlx1 = "5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCylb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (list != null && list.size() > 0) {
                    String spcylb = list.get(position);
                    for (Sscy sscy : sscyList) {
                        if (spcylb.equals(sscy.getCategory_name())) {
                            cylb1 = sscy.getCategory_id();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(InformationEditorActivity.this, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
            }

        });
        spXmbz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spxmbz = getResources().getStringArray(R.array.xmbz)[position];
                if (spxmbz.equals("全球500强")) {
                    xmbz1 = "1";
                } else if (spxmbz.equals("全国500强")) {
                    xmbz1 = "2";
                } else if (spxmbz.equals("央企")) {
                    xmbz1 = "3";
                } else if (spxmbz.equals("上市公司")) {
                    xmbz1 = "4";
                } else if (spxmbz.equals("其他")) {
                    xmbz1 = "5";
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
                    hzlb1 = "1";
                } else if (sphzlb.equals("允许类")) {
                    hzlb1 = "2";
                } else if (sphzlb.equals("限制类")) {
                    hzlb1 = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                tvDate.setText(DateFormat.format("yyyy-MM-dd", showDate));
            }
        }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick({R.id.iv_toolbar_back, R.id.tv_date, R.id.btn_save, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.tv_date:
                showDateDialog();
                break;
            case R.id.btn_save:
                //项目名称
                if (!projectName.getText().toString().trim().isEmpty()) {
                    projectName1 = projectName.getText().toString().trim();
                } else {
                    projectName1 = (String) projectName.getHint();
                }

                //企业名称
                if (!orgName.getText().toString().trim().isEmpty()) {
                    orgName1 = orgName.getText().toString().trim();
                } else {
                    orgName1 = (String) orgName.getHint();
                }

                //姓名
                if (!name.getText().toString().trim().isEmpty()) {
                    name1 = name.getText().toString().trim();
                } else {
                    name1 = (String) name.getHint();
                }

                //电话
                if (!phone.getText().toString().trim().isEmpty()) {
                    phone1 = phone.getText().toString().trim();
                } else {
                    phone1 = (String) phone.getHint();
                }

                //电子邮箱
                if (!email.getText().toString().trim().isEmpty()) {
                    email1 = email.getText().toString().trim();
                } else {
                    email1 = (String) email.getHint();
                }
                //总投资
                if (!invest.getText().toString().trim().isEmpty()) {
                    invest1 = invest.getText().toString().trim();
                } else {
                    invest1 = (String) invest.getHint();
                }
                //总占地面积
                if (!floorArea.getText().toString().trim().isEmpty()) {
                    floorArea1 = floorArea.getText().toString().trim();
                } else {
                    floorArea1 = (String) floorArea.getHint();
                }

                //一期用地面积
                if (!siteArea.getText().toString().trim().isEmpty()) {
                    siteArea1 = siteArea.getText().toString().trim();
                } else {
                    siteArea1 = (String) siteArea.getHint();
                }

                //投资强度
                if (!intensity.getText().toString().trim().isEmpty()) {
                    intensity1 = intensity.getText().toString().trim();
                } else {
                    intensity1 = (String) intensity.getHint();
                }

                //建设周期
                if (!cycle.getText().toString().trim().isEmpty()) {
                    cycle1 = cycle.getText().toString().trim();
                } else {
                    cycle1 = (String) cycle.getHint();
                }

                //达产后年产值
                if (!yearOutput.getText().toString().trim().isEmpty()) {
                    yearOutput1 = yearOutput.getText().toString().trim();
                } else {
                    yearOutput1 = (String) yearOutput.getHint();
                }

                //年税收贡献
                if (!taxContribution.getText().toString().trim().isEmpty()) {
                    taxContribution1 = taxContribution.getText().toString().trim();
                } else {
                    taxContribution1 = (String) taxContribution.getHint();
                }

                //就业拉动
                if (!employmentPull.getText().toString().trim().isEmpty()) {
                    employmentPull1 = employmentPull.getText().toString().trim();
                } else {
                    employmentPull1 = (String) employmentPull.getHint();
                }

                //项目概述
                if (!projectDescrip.getText().toString().trim().isEmpty()) {
                    projectDescrip1 = projectDescrip.getText().toString().trim();
                } else {
                    projectDescrip1 = (String) projectDescrip.getHint();
                }

                //其他情况
                if (!situations.getText().toString().trim().isEmpty()) {
                    situations1 = situations.getText().toString().trim();
                } else {
                    situations1 = (String) situations.getHint();
                }

                date11 = tvDate.getText().toString().trim();
                getCityText();
                sendInfoToService();
                break;
            case R.id.btn_cancel:
                onBackPressedSupport();
                break;
        }

    }

    //获取所属产业类别接口
    private List<String> list;
    private List<Sscy> sscyList;

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
                    TestArrayAdapter adapter = new TestArrayAdapter(InformationEditorActivity.this, list, false);
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

    //项目信息编辑之后提交至服务器
    private void sendInfoToService() {
        Map<String, String> map = new HashMap<>();
        map.put("project_id", project_id);
        map.put("project_name", projectName1);
        map.put("org_name", orgName1);
        map.put("cooperation", hefs1);
        map.put("project_type", xmlx1);
        map.put("industry", cylb1);
        map.put("colony", city1);
        map.put("note", xmbz1);
        map.put("name", name1);
        map.put("phone", phone1);
        map.put("email", email1);
        map.put("project_descrip", projectDescrip1);
        map.put("invest", invest1);
        map.put("floor_area", floorArea1);
        map.put("site_area", siteArea1);
        map.put("intensity", intensity1);
        map.put("expect_date", date11);
        map.put("cycle", cycle1);
        map.put("year_output", yearOutput1);
        map.put("tax_contribution", taxContribution1);
        map.put("employment_pull", employmentPull1);
        map.put("category", hzlb1);
        map.put("product", "");
        map.put("situations", situations1);
        map.put("pronotes", pronotes);

        GsonRequest<Success> request = new GsonRequest<Success>(Request.Method.POST, map, Url.InformationProjectEdit, Success.class, new Response.Listener<Success>() {
            @Override
            public void onResponse(Success response) {
                if (response.isSuccess()) {
                    ToastUtils.showShortToast("提交成功");
                    finish();
                } else {
                    ToastUtils.showShortToast("修改数据失败，请重试");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShortToast("修改数据失败，请重试");
            }
        });
        App.getInstance().getHttpQueue().add(request);
    }


    //信息编辑这里的城市编辑，如果客户没有编辑就传以下方法获取的值，如果客户编辑了就传onActivityResult中的city值。
    private void getCityText() {
        city1 = tvCity.getText().toString().trim();
        if (city1.equals("北京")) {
            city1 = "1";
        } else if (city1.equals("天津")) {
            city1 = "2";
        } else if (city1.equals("上海")) {
            city1 = "3";
        } else if (city1.equals("河北")) {
            city1 = "4";
        } else {
            city1 = "5";
            pronotes = tvCity.getText().toString().trim();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                tvCity.setText(city);
            }
        }
    }
}
