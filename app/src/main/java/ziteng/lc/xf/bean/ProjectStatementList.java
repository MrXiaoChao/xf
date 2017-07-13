package ziteng.lc.xf.bean;

/**
 * Created by luochao on 2017/5/16.
 * 项目统计列表
 */

public class ProjectStatementList {

    private String org_name;
    private String project_name;
    private String project_id;
    private String createdate;
    private String project_descrip;

    public String getProject_descrip() {
        return project_descrip;
    }

    public void setProject_descrip(String project_descrip) {
        this.project_descrip = project_descrip;
    }

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
