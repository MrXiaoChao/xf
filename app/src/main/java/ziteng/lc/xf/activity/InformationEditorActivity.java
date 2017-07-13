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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
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
import ziteng.lc.xf.bean.ProjectInfoEditext;
import ziteng.lc.xf.bean.Sscy;
import ziteng.lc.xf.bean.Success;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.util.ListUtils;

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
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_tzzt)
    EditText etTzzt;
    @BindView(R.id.et_dqjz)
    EditText etDqjz;
    private ArrayAdapter<String> mAdapterspHzlx;
    private String[] mStringArrayspHzlx;

    private String xmlx1;
    private String cylb1;
    private String city1;
    private String xmbz1;
    private String hzlb1;
    private String projectName1;
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
    private String city;

    private static final int REQUEST_CODE_PICK_CITY = 0;
    private String project_id;
    private String project_status;
    private String current_progress;
    private String investment_entity;


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
            setEditextToEnabled(projectName, false);
            setEditextToEnabled(name, false);
            setEditextToEnabled(phone, false);
            setEditextToEnabled(email, false);
            btnSave.setVisibility(View.GONE);
            setEditextToEnabled(projectDescrip, false);
            setEditextToEnabled(situations, false);
            setEditextToEnabled(invest, false);
            setEditextToEnabled(floorArea, false);
            setEditextToEnabled(siteArea, false);
            setEditextToEnabled(intensity, false);
            setEditextToEnabled(cycle, false);
            setEditextToEnabled(yearOutput, false);
            setEditextToEnabled(taxContribution, false);
            setEditextToEnabled(employmentPull, false);
            setTextviewToEnabled(tvCylb, false);
            setSpinnerToEnabled(sp_Hzlb, false);
            setTextviewToEnabled(tvXmbz, false);
            setTextviewToEnabled(tvDate, false);
            setTextviewToEnabled(tvCity, false);
            setTextviewToEnabled(tvXmlx, false);
            setEditextToEnabled(etDqjz, false);
            setEditextToEnabled(etTzzt, false);
        }
    }

    //Editext设置不可以编辑
    public void setEditextToEnabled(EditText editext, boolean t) {
        editext.setFocusable(t);
        editext.setFocusableInTouchMode(t);
        editext.setEnabled(t);
    }

    //Spinner设置不可以编辑
    public void setSpinnerToEnabled(Spinner Spinner, boolean t) {
        Spinner.setFocusable(t);
        Spinner.setFocusableInTouchMode(t);
        Spinner.setEnabled(t);
    }

    //Textview设置不可以编辑
    public void setTextviewToEnabled(TextView textView, boolean t) {
        textView.setFocusable(t);
        textView.setFocusableInTouchMode(t);
        textView.setEnabled(t);
    }

    //修改Spinner默认字体的大小与颜色
    private void changSpinnerText() {
        mStringArrayspHzlx = getResources().getStringArray(R.array.hzlb);
        mAdapterspHzlx = new TestArrayAdapter(InformationEditorActivity.this, mStringArrayspHzlx, true);
        sp_Hzlb.setAdapter(mAdapterspHzlx);
    }


    //项目信息回显

    private List<String> listString = new LinkedList<>();
    private List<String> listXMLX = new LinkedList<>();
    private List<String> listXMBZ = new LinkedList<>();
    private List<String> listCYLB = new LinkedList<>();

    private void getDataFromSerVice(String project_id) {
        Map<String, String> map = new HashMap<>();
        map.put("project_id", project_id);
        GsonRequest<ProjectInfoEditext> request = new GsonRequest<ProjectInfoEditext>(Request.Method.POST, map, Url.InformationEditext, ProjectInfoEditext.class, new Response.Listener<ProjectInfoEditext>() {
            @Override
            public void onResponse(ProjectInfoEditext response) {
                etDqjz.setText(response.getCurrent_progress());
                etTzzt.setText(response.getInvestment_entity());
                projectName.setText(response.getProject_name());
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
                //DateFormat.format("yyyy-MM-dd", response.getExpect_date())
                tvDate.setText((CharSequence) response.getExpect_date());

                //项目类型   {"工业", "农业", "服务业", "旅游业", "其他"};
                List<String> listxmlx = ListUtils.stringTolist(response.getProject_type());
                for (String s : listxmlx) {
                    if (s.equals("1")) {
                        listXMLX.add("工业");
                    } else if (s.equals("2")) {
                        listXMLX.add("农业");
                    } else if (s.equals("3")) {
                        listXMLX.add("服务业");
                    } else if (s.equals("4")) {
                        listXMLX.add("旅游业");
                    } else if (s.equals("5")) {
                        listXMLX.add("其他");
                    }
                }
                tvXmlx.setText(ListUtils.listToString(listXMLX));
                //所属产业也是产业类别
                List<String> listcylb = ListUtils.stringTolist(response.getIndustry());
                if (sscyList != null && sscyList.size() > 0) {
                    for (int i = 0; i < listcylb.size(); i++) {
                        for (Sscy sscy : sscyList) {
                            if (sscy.getCategory_id().equals(listcylb.get(i))){
                                listCYLB.add(sscy.getCategory_name());
                            }
                        }
                    }
                }
                tvCylb.setText(ListUtils.listToString(listCYLB));
                //所属城市
                List<String> listString1 = ListUtils.stringTolist(response.getColony());
                for (String s : listString1) {
                    if (s.equals("1")) {
                        listString.add("北京");
                    } else if (s.equals("2")) {
                        listString.add("天津");
                    }
                    if (s.equals("3")) {
                        listString.add("河北");
                    }
                    if (s.equals("4")) {
                        listString.add("上海");
                    } else if (s.equals("5")) {
                        listString.add(response.getPronotes());
                    }
                }
                tvCity.setText(ListUtils.listToString(listString));

                //项目备注 "全球500强", "全国500强", "央企", "上市公司", "其他"
                List<String> listxmbz = ListUtils.stringTolist(response.getNote());
                for (String s : listxmbz) {
                    if (s.equals("1")) {
                        listXMBZ.add("全球500强");
                    } else if (s.equals("2")) {
                        listXMBZ.add("全国500强");
                    } else if (s.equals("3")) {
                        listXMBZ.add("央企");
                    } else if (s.equals("4")) {
                        listXMBZ.add("上市公司");
                    } else if (s.equals("5")) {
                        listXMBZ.add("其他");
                    }
                }
                tvXmbz.setText(ListUtils.listToString(listXMBZ));
                Log.i("lc",listXMBZ.toString());
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
                Log.i("lc", error.toString());
            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取该页面editext的值与spinner所选中的值
    private void getSpinnerSelect() {
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

    @OnClick({R.id.iv_toolbar_back, R.id.tv_date, R.id.btn_save, R.id.btn_cancel, R.id.tv_city, R.id.tv_xmlx,R.id.tv_xmbz,R.id.tv_cylb})
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
                //获取当前进展
                current_progress = etDqjz.getText().toString().trim();
                //投资主体
                investment_entity = etTzzt.getText().toString().trim();
                date11 = tvDate.getText().toString().trim();
                sendInfoToService();
                break;
            case R.id.btn_cancel:
                onBackPressedSupport();
                break;
            case R.id.tv_city:
                creatDialog();
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
        map.put("investment_entity", investment_entity);
        map.put("current_progress", current_progress);
        map.put("project_type", xmlx1);
        map.put("industry", cylb1);
        map.put("colony", city);
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
        map.put("pronotes", (pronotes == null) ? "" : pronotes);
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

    //创建选择城市的dialog
    List<String> stringList = new LinkedList<>();
    List<String> stringList1 = new LinkedList<>();

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationEditorActivity.this);
        builder.setTitle("项目属地选择");
        final String[] hobbies = {"北京", "天津", "河北", "上海", "其他"};

        builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (which == 4 && isChecked) {
                    Intent intent = new Intent(InformationEditorActivity.this, SelectCityActivity.class);
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

    //创建选择项目类型的dialog
    List<String> stringListXMLX = new LinkedList<>();
    List<String> stringListXMLX1 = new LinkedList<>();
    private void creatDialogXMLX() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationEditorActivity.this);
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
                xmlx1 = ListUtils.listToString(stringListXMLX1);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationEditorActivity.this);
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
                    if (StringUtils.equals(s, "全球500强")) {
                        stringListXMBZ1.add("1");
                    } else if (StringUtils.equals(s, "全国500强")) {
                        stringListXMBZ1.add("2");
                    } else if (StringUtils.equals(s, "央企")) {
                        stringListXMBZ1.add("3");
                    } else if (StringUtils.equals(s, "上市公司")) {
                        stringListXMBZ1.add("4");
                    }else if (StringUtils.equals(s,"其他")){
                        stringListXMBZ1.add("5");
                    }
                }
                xmbz1 = ListUtils.listToString(stringListXMBZ1);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(InformationEditorActivity.this);
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
                cylb1 = ListUtils.listToString(stringListSSLB1);
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
