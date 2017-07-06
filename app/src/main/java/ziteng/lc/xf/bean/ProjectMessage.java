package ziteng.lc.xf.bean;

import java.util.List;

/**
 * Created by luochao on 2017/4/14.
 * 所有项目展示
 */

public class ProjectMessage {


    /**
     * totalpages : 10
     * projectList : [{"org_name":"任丘市某某公司","time":"2017-06-27","project_name":"2017627测试12","project_descrip":"嘎哈呢明明白白加工费吃噶","project_id":"4028804d5ce78a01015ce845601b000e"}]
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
         * org_name : 任丘市某某公司
         * time : 2017-06-27
         * project_name : 2017627测试12
         * project_descrip : 嘎哈呢明明白白加工费吃噶
         * project_id : 4028804d5ce78a01015ce845601b000e
         */

        private String org_name;
        private String time;
        private String project_name;
        private String project_descrip;
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
    }
}
