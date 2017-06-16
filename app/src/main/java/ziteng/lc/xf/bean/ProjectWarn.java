package ziteng.lc.xf.bean;


import java.util.List;

/**
 * Created by luochao on 2017/5/5.\
 * 项目提醒
 */

public class ProjectWarn {
    /**
     * totalpages : 2
     * projectList : [{"org_name":"天津华林同创建材有限公司","project_name":"纳米自洁铝板项目","status":"超期","project_id":"531737285b6b9dc5015b6b9f9c5e0000","createdate":"2017-06-13","read":"2"},{"org_name":"河北亦乐纸塑包装科技有限公司","project_name":"河北亦乐纸塑包装项目","status":"超期","project_id":"402881ea5b6b5f36015b6b627cfd0000","createdate":"2017-06-13","read":"2"},{"org_name":"任丘市某某有限公司","project_name":"2017年5月16日测试数据n.vv","status":"超期","project_id":"4028804d5c0b94a3015c0ec1b9700004","createdate":"2017-06-13","read":"2"},{"org_name":"北汽","project_name":"汽车制造","status":"超期","project_id":"4028804d5baec631015baeddd26d0000","createdate":"2017-06-13","read":"2"},{"org_name":"河北鹏盛天冠智能仓储设备制造有限公司","project_name":"河北鹏盛天冠智能仓储设备制造项目","status":"超期","project_id":"402881ea5b6b5f36015b6b64346c0001","createdate":"2017-06-13","read":"2"},{"org_name":"北京市某某科技发展有限公司","project_name":"2017年6月13日测试数据1","status":"超期","project_id":"4028804d5ca07aaa015ca08ed64c0004","createdate":"2017-06-13","read":"2"},{"org_name":"测试单位","project_name":"2017测试数据","status":"超期","project_id":"4028804d5c1ab906015c1f5f65380015","createdate":"2017-06-13","read":"2"},{"org_name":"刘雯","project_name":"刘雯","status":"超期","project_id":"4028804d5c1ab906015c1f7047650018","createdate":"2017-06-13","read":"2"},{"org_name":"任丘市170612某某公司","project_name":"2017年6月12日招商项目测试数据","status":"超期","project_id":"4028804d5c99d4e9015c99f530c50009","createdate":"2017-06-13","read":"2"},{"org_name":"北京市某某科技发展有限公司","project_name":"2017年6月13日测试数据1","status":"超期","project_id":"4028804d5ca07aaa015ca08ed64c0004","createdate":"2017-06-13","read":"1"}]
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
         * createdate : 2017-06-13
         * read : 2
         */

        private String org_name;
        private String project_name;
        private String status;
        private String project_id;
        private String createdate;
        private String read;

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

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }
    }
}
