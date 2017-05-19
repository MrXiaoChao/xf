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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
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
import ziteng.lc.xf.http.GsonRequest;
import ziteng.lc.xf.http.Url;
import ziteng.lc.xf.widegt.SPUtils;


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
    private View headerView;
    private ArrayList<NewsList.ProjectListBean> list;
    private ListView listView;
    private int page = 1;
    private NewsListAdapter adapter;
    private int totalpage;
    private String personuuid;
    private String count;
    private QBadgeView badgeView;

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
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("URL", URL + "news_id=" + list.get(position - 2).getNews_id());
                startActivity(intent);
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
        //添加头布局
        listView.addHeaderView(headerView);
        ImageView ivBanner = (ImageView) headerView.findViewById(R.id.iv_banner);
        Glide.with(getActivity()).load(R.mipmap.banner).into(ivBanner);
        LinearLayout news = (LinearLayout) headerView.findViewById(R.id.ll_news);
        LinearLayout flows = (LinearLayout) headerView.findViewById(R.id.ll_following);
        LinearLayout warn = (LinearLayout) headerView.findViewById(R.id.ll_warn);
        LinearLayout statement = (LinearLayout) headerView.findViewById(R.id.ll_statement);
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
}
