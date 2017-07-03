package ziteng.lc.xf.fragment;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import ziteng.lc.xf.R;
import ziteng.lc.xf.activity.ProjectFlowActivity;
import ziteng.lc.xf.activity.ProjectMessageActivity;
import ziteng.lc.xf.activity.ProjectWarnActivity;
import ziteng.lc.xf.activity.StatementActivity;
import ziteng.lc.xf.activity.WebViewActivity;
import ziteng.lc.xf.adapter.NewsListAdapter;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseFragment;
import ziteng.lc.xf.bean.NewsList;
import ziteng.lc.xf.bean.UpView;
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;
import ziteng.lc.xf.widegt.UPMarqueeView;


/**
 * Created by luochao on 2017/3/20.
 * 首页
 */

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.lv_refresh)
    PullToRefreshListView lvRefresh;
    Unbinder unbinder;


    private View headerView;
    private View headerUpView;
    private ArrayList<NewsList.ProjectListBean> list;
    private ListView listView;
    private int page = 1;
    private NewsListAdapter adapter;
    private int totalpage;
    private String personuuid;
    private String count;
    private QBadgeView badgeView;
    private LinearLayout warn;
    private LinearLayout statement;
    private View view_warn;
    private View view_stament;
    private View view_flow;
    private UPMarqueeView upMarqueeView;
    private String status;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initData() {
        initview();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EmptyUtils.isEmpty(personuuid)) {
            getReadCount(personuuid);
        }
    }

    private void initview() {
        //status 1：个人账号2：企业账号3：政府机关 4：市领导5：超级管理员 6：部门领导 7：商务局管理员
        status = (String) SPUtils.get(getActivity(), "status", "");
        ivToolbarBack.setVisibility(View.INVISIBLE);
        tvTooltarTitle.setText("招商信息录入平台");
        personuuid = (String) SPUtils.get(getActivity(), "personuuid", "");
        if (!EmptyUtils.isEmpty(personuuid)) {
            getReadCount(personuuid);
        }
        initRefresh();
        initHeaderView();
        getDataFromService(page, 3, "", "");
        clickItem();
    }

    //初始化上拉刷新下拉加载
    private void initRefresh() {
        listView = lvRefresh.getRefreshableView();
        lvRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        lvRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isMore = false;
                page = 1;
                getDataFromService(page, 3, "", "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                isMore = true;
                if (page < totalpage) {
                    page++;
                    getDataFromService(page, 3, "", "");
                } else {
                    //延迟1秒刷新
                    lvRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lvRefresh.onRefreshComplete();
                        }
                    }, 1000);
                    ToastUtils.showShortToast("已经是最后一页了");
                }
            }
        });
    }

    //item的点击事件
    private void clickItem() {
        final String URL = Url.NewsXQ;
        lvRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (StringUtils.equals(status, "4")) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("URL", URL + "news_id=" + list.get(position - 3).getNews_id());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("URL", URL + "news_id=" + list.get(position - 2).getNews_id());
                    startActivity(intent);
                }
            }
        });
    }

    //是否是上拉加载更多
    private boolean isMore = false;

    //从服务器获取数据
    private void getDataFromService(final int page, int rows, String name, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        final GsonRequest<NewsList> request = new GsonRequest<NewsList>(Request.Method.POST, map, Url.NewsList, NewsList.class, new Response.Listener<NewsList>() {
            @Override
            public void onResponse(final NewsList response) {
                if (response != null) {
                    if (isMore) {
                        list.addAll(response.getProjectList());
                        adapter = new NewsListAdapter(getActivity(), list);
                        if (getActivity() != null && adapter != null) {
                            lvRefresh.setAdapter(adapter);
                            lvRefresh.onRefreshComplete();
                            if (page <= 2) {
                                listView.setSelection(1);
                            } else {
                                listView.setSelection(list.size() - 1);
                            }
                        }
                    } else {
                        totalpage = response.getTotalpages();
                        list = (ArrayList<NewsList.ProjectListBean>) response.getProjectList();
                        adapter = new NewsListAdapter(getActivity(), list);
                        if (getActivity() != null && adapter != null) {
                            lvRefresh.setAdapter(adapter);
                        }
                    }
                    lvRefresh.onRefreshComplete();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //初始化头布局
    private void initHeaderView() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.headview, null);
        headerUpView = LayoutInflater.from(getActivity()).inflate(R.layout.headview_caoqi, null);
        upMarqueeView = (UPMarqueeView) headerUpView.findViewById(R.id.upview);
        //添加头布局
        listView.addHeaderView(headerView);
        //市领导 在首页 要显示所有项目超期的垂直滚动的Textview
        if (StringUtils.equals(status, "4")) {
            //市领导获取超期项目数据
            initUpViewData();
            listView.addHeaderView(headerUpView);
        }
        ImageView ivBanner = (ImageView) headerView.findViewById(R.id.iv_banner);
        Glide.with(getActivity()).load(R.mipmap.banner).into(ivBanner);
        LinearLayout news = (LinearLayout) headerView.findViewById(R.id.ll_news);
        LinearLayout flows = (LinearLayout) headerView.findViewById(R.id.ll_following);
        warn = (LinearLayout) headerView.findViewById(R.id.ll_warn);
        statement = (LinearLayout) headerView.findViewById(R.id.ll_statement);
        view_warn = headerView.findViewById(R.id.view_warn);
        view_stament = headerView.findViewById(R.id.view_stament);
        view_flow = headerView.findViewById(R.id.view_flow);
        LinearLayout llHeardview = (LinearLayout) headerView.findViewById(R.id.ll_heardview);
        llHeardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_news = new Intent(getActivity(), ProjectMessageActivity.class);
                startActivity(intent_news);
            }
        });
        flows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_following = new Intent(getActivity(), ProjectFlowActivity.class);
                startActivity(intent_following);
            }
        });
        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_warn = new Intent(getActivity(), ProjectWarnActivity.class);
                startActivity(intent_warn);
            }
        });
        statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_statement = new Intent(getActivity(), StatementActivity.class);
                startActivity(intent_statement);
            }
        });

        //消息数目显示
        badgeView = new QBadgeView(getActivity());
        badgeView.bindTarget(warn);
        badgeView.setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-6, false).setBadgeTextSize(24, false);
        getStatus();
    }

    //获取权限并且根据权限显示模块
    private void getStatus() {
        if (!EmptyUtils.isEmpty(status)) {
            if (status.equals("1") || status.equals("2")) {
                warn.setVisibility(View.GONE);
                statement.setVisibility(View.GONE);
                view_warn.setVisibility(View.GONE);
                view_stament.setVisibility(View.GONE);
                view_flow.setVisibility(View.GONE);
            }
        }
    }

    //获取未读数据的条目
    private void getReadCount(final String personuuid) {

        StringRequest request = new StringRequest(Request.Method.POST, Url.READ_COUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    count = object.getString("count");
                    if (!EmptyUtils.isEmpty(count)) {
                        badgeView.setBadgeNumber(Integer.parseInt(count));
                    }
                    badgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                        @Override
                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String a = error.toString();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("personuuid", personuuid);
                return map;
            }
        };
        App.getInstance().getHttpQueue().add(request);
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     */

    List<UpView> upViewsData = new ArrayList<>();
    List<View> views = new ArrayList<>();

    private void setView() {
        final String url = "http://211.151.183.170:8097/rqzsj/news/reminddetails.jsp?";
        if (upViewsData != null && upViewsData.size() > 0) {
            for (int i = 0; i < upViewsData.size(); i++) {
                final int position = i;
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_textview, null);
                //初始化布局的控件
                TextView tv1 = (TextView) moreView.findViewById(R.id.tv);
                /**
                 * 设置监听
                 */
                moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("URL", url + "project_id=" + upViewsData.get(position).getProject_id() + "&status=超期"+"&role="+status+"&personuuid="+personuuid);
                        intent.putExtra("flag","超期项目详情");
                        startActivity(intent);
                    }
                });
                //进行对控件赋值
                tv1.setText(upViewsData.get(i).getContent());
                //添加到循环滚动数组里面去
                views.add(moreView);
            }
        }
        upMarqueeView.setViews(views);
    }

    /**
     * 获取超期项目的数据
     */
    private void initUpViewData() {
        TypeToken type = new TypeToken<List<UpView>>() {
        };
        GsonRequest<List<UpView>> request = new GsonRequest<List<UpView>>(Url.SLD, type, new Response.Listener<List<UpView>>() {
            @Override
            public void onResponse(List<UpView> upViews) {
                upViewsData = upViews;
                setView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

}
