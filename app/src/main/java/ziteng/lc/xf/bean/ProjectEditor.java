package ziteng.lc.xf.bean;

import java.util.List;

/**
 * Created by luochao on 2017/5/5.
 */

public class ProjectEditor {

    /**
     * totalpages : 9
     * projectList : [{"org_name":"刘雯","project_status":"1","project_name":"刘雯","project_id":"4028804d5c1ab906015c1f7047650018","createdate":"2017-05-19","updatedate":"2017-05-19"},{"org_name":"任丘某某工","project_status":"2","project_name":"测试数据20170519防寒服呵呵","project_id":"4028804d5c1ab906015c1f66491c0016","createdate":"2017-05-19","updatedate":"2017-05-19"}]
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
         * org_name : 刘雯
         * project_status : 1
         * project_name : 刘雯
         * project_id : 4028804d5c1ab906015c1f7047650018
         * createdate : 2017-05-19
         * updatedate : 2017-05-19
         */

        private String org_name;
        private String project_status;
        private String project_name;
        private String project_id;
        private String createdate;
        private String updatedate;

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getProject_status() {
            return project_status;
        }

        public void setProject_status(String project_status) {
            this.project_status = project_status;
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

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(String updatedate) {
            this.updatedate = updatedate;
        }
    }
}
