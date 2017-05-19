package ziteng.lc.xf.bean;

import java.util.List;

/**
 * Created by luochao on 2017/4/14.
 * 所有项目展示
 */

public class ProjectMessage {

    /**
     * totalpages : 1
     * projectList : [{"org_name":"天津华林同创建材有限公司","time":"2017-04-14","project_name":"纳米自洁铝板项目","project_id":"531737285b6b9dc5015b6b9f9c5e0000"},{"org_name":"河北鹏盛天冠智能仓储设备制造有限公司","time":"2017-04-13","project_name":"河北鹏盛天冠智能仓储设备制造项目","project_id":"402881ea5b6b5f36015b6b64346c0001"},{"org_name":"河北亦乐纸塑包装科技有限公司","time":"2017-04-11","project_name":"河北亦乐纸塑包装项目","project_id":"402881ea5b6b5f36015b6b627cfd0000"}]
     */

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
        /**
         * org_name : 天津华林同创建材有限公司
         * time : 2017-04-14
         * project_name : 纳米自洁铝板项目
         * project_id : 531737285b6b9dc5015b6b9f9c5e0000
         */

        private String org_name;
        private String time;
        private String project_name;
        private String project_id;

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }
    }
}
