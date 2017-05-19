package ziteng.lc.xf.bean;

import java.util.List;

/**
 * Created by luochao on 2017/4/18.
 * 首页新闻列表
 */

public class NewsList {
    private int totalpages;
    private List<ProjectListBean> projectList;

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public List<ProjectListBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListBean> projectList) {
        this.projectList = projectList;
    }

    public static class ProjectListBean {

        private String imgUrl;
        private String news_name;
        private String news_id;
        private String news_content;
        private String news_status;
        private String news_time;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getNews_name() {
            return news_name;
        }

        public void setNews_name(String news_name) {
            this.news_name = news_name;
        }

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getNews_content() {
            return news_content;
        }

        public void setNews_content(String news_content) {
            this.news_content = news_content;
        }

        public String getNews_status() {
            return news_status;
        }

        public void setNews_status(String news_status) {
            this.news_status = news_status;
        }

        public String getNews_time() {
            return news_time;
        }

        public void setNews_time(String news_time) {
            this.news_time = news_time;
        }
    }
}
