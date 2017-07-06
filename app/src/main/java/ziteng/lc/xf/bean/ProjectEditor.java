package ziteng.lc.xf.bean;

import java.util.List;

/**
 * Created by luochao on 2017/5/5.
 */

public class ProjectEditor {

    /**
     * totalpages : 42
     * projectList : [{"org_name":null,"project_status":"1","project_name":"22","project_descrip":"33","project_id":"402880085d1718ef015d172a9cda0002","createdate":"2017-07-06","updatedate":"2017-07-06"},{"org_name":null,"project_status":"1","project_name":"333","project_descrip":"33","project_id":"402880085d1718ef015d1727550d0000","createdate":"2017-07-06","updatedate":"2017-07-06"}]
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
         * org_name : null
         * project_status : 1
         * project_name : 22
         * project_descrip : 33
         * project_id : 402880085d1718ef015d172a9cda0002
         * createdate : 2017-07-06
         * updatedate : 2017-07-06
         */

        private Object org_name;
        private String project_status;
        private String project_name;
        private String project_descrip;
        private String project_id;
        private String createdate;
        private String updatedate;

        public Object getOrg_name() {
            return org_name;
        }

        public void setOrg_name(Object org_name) {
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

        public String getProject_descrip() {
            return project_descrip;
        }

        public void setProject_descrip(String project_descrip) {
            this.project_descrip = project_descrip;
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
