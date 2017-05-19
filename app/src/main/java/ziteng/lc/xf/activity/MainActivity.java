package ziteng.lc.xf.activity;


import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import ziteng.lc.xf.R;
import ziteng.lc.xf.app.App;
import ziteng.lc.xf.base.BaseActivity;
import ziteng.lc.xf.fragment.HomePageFragment;
import ziteng.lc.xf.fragment.MineFragment;
import ziteng.lc.xf.fragment.ProjectFlowFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.tv_homepae)
    TextView tvHomepae;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    private HomePageFragment homePageFragment;
    private MineFragment mineFragment;
    private ProjectFlowFragment projectFlowFragment;
    private long backtime=0;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        defaultSelected();
    }

    //默认选中
    private void defaultSelected(){
        selected();
        tvHomepae.setSelected(true);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
            transaction.add(R.id.fragment_container, homePageFragment);
        } else {
            transaction.show(homePageFragment);
        }
        transaction.commit();
    }
    //重置所有文本的选中状态
    public void selected() {
        tvHomepae.setSelected(false);
        tvProject.setSelected(false);
        tvMine.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (projectFlowFragment != null) {
            transaction.hide(projectFlowFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @OnClick({R.id.tv_homepae, R.id.tv_project, R.id.tv_mine})
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (view.getId()) {
            case R.id.tv_homepae:
                selected();
                tvHomepae.setSelected(true);
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.fragment_container, homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                }
                break;
            case R.id.tv_project:
                selected();
                tvProject.setSelected(true);
                if (projectFlowFragment == null) {
                    projectFlowFragment = new ProjectFlowFragment();
                    transaction.add(R.id.fragment_container, projectFlowFragment);
                } else {
                    transaction.show(projectFlowFragment);
                }
                break;
            case R.id.tv_mine:
                selected();
                tvMine.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fragment_container, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    //是否退出客户端
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long t = System.currentTimeMillis();
            if (t - backtime > 3000) {
               ToastUtils.showShortToast("再按一次退出应用");
                backtime = t;
                return true;
            }
            App.getInstance().exitApp();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
