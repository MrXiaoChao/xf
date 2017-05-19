package ziteng.lc.xf.bean;


import java.util.List;

/**
 * Created by luochao on 2017/5/5.\
 * 项目提醒
 */

public class ProjectWarn {

    /**
     * totalpages : 3
     * projectList : [{"org_name":"天津华林同创建材有限公司","project_name":"纳米自洁铝板项目","status":"超期","project_id":"531737285b6b9dc5015b6b9f9c5e0000","createdate":"2017-04-14 "}]
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
         * project_name : 纳米自洁铝板项目
         * status : 超期
         * project_id : 531737285b6b9dc5015b6b9f9c5e0000
         * createdate : 2017-04-14
         */

        private String org_name;
        private String project_name;
        private String status;
        private String project_id;
        private String createdate;

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
