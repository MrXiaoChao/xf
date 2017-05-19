package ziteng.lc.xf.http;

/**
 * Created by luochao on 2017/4/14.
 * 接口地址
 */

public class Url {
    public static String BaseUrl="http://211.151.183.170:8097";
//    public static String BaseUrl="http://211.151.183.168:8085";
    //登入
    public static String LoginUrl="http://211.151.183.170:8097/rqzsj/person/loginphone?";
    //项目跟踪
    public static String HomePageUrl="http://211.151.183.170:8097/rqzsj/project/getAllProjectList?";
    //项目编辑
    public static String ProjectEditor="http://211.151.183.170:8097/rqzsj/project/projectEditList?";
    //项目信息录入
    public static String InfomationInput="http://211.151.183.170:8097/rqzsj/project/saveprojectphone?";
    //项目信息编辑之后提交至服务器
    public static String InformationProjectEdit="http://211.151.183.170:8097/rqzsj/project/saveProjectEdit?";
    //项目的信息编辑
    public static String InformationEditext="http://211.151.183.170:8097/rqzsj/project/getProjectDetailsById?";
    //首页新闻列表
    public static String NewsList="http://211.151.183.170:8097/rqzsj/news/getNewsList?";
    //首页新闻详情
    public static String NewsXQ="http://211.151.183.170:8097/rqzsj/news/newsdetails.jsp?";
    //注册接口
    public static String Register="http://211.151.183.170:8097/rqzsj/person/registerPersongPhone?";
    //获取验证码
    public static String YZM="http://211.151.183.170:8097/rqzsj/person/setcode?";
    //获取所属产业所有字段
    public static String SSCY="http://211.151.183.170:8097/rqzsj/cat/getAllCategoryList";
    //已经报项目列表
    public static String YBXM="http://211.151.183.170:8097/rqzsj/project/projectFollow2Report?";
    //项目跟踪详情
    public static String XMGZXQ=" http://211.151.183.170:8097/rqzsj/news/fllowdetails.jsp?";
    //签约项目列表
    public static String QYXM="http://211.151.183.170:8097/rqzsj/project/projectFollow2Sign?";
    //落地项目列表
    public static String LDXM="http://211.151.183.170:8097/rqzsj/project/projectFollow2Accomplish?";
    //办理中项目
    public static String BLZXM="http://211.151.183.170:8097/rqzsj/project/projectFollow2Transact?";
    //已办理项目
    public static String YBLXM="http://211.151.183.170:8097/rqzsj/project/projectFollow2TransactEnd?";
    //项目统计
    public static String ProjectStatement="http://211.151.183.170:8097/rqzsj/project/projectStatistics";
    //项目提醒
    public static String ProjectWarn="http://211.151.183.170:8097/rqzsj/project/projectRemind?";
    //获取未读项目的数目
    public static String READ_COUNT="http://211.151.183.170:8097/rqzsj/project/remindCount?";
    //修改密码
    public static String CHANG_PASSWORD="http://211.151.183.170:8097/rqzsj/person/updatePassword?";
    //修改手机号码
    public static String CHANG_PHONENUM="http://211.151.183.170:8097/rqzsj/person/updatePhone?";
    //获取项目统计里面的列表
    public static String STATEMENT_LIST="http://211.151.183.170:8097/rqzsj/project/StatisticsList?";
    //项目同意详情
    public static String STATEMENT_XQ="http://211.151.183.170:8097/rqzsj/news/projectdetails.jsp?";
    //扣分统计列表
    public static String KFTJ="http://211.151.183.170:8097/rqzsj/news/kfContent.jsp?";
    //检验手机号码是否被注册
    public static String CHECK_PHONENUB="http://211.151.183.170:8097/rqzsj/person/getPersonByPhone?";
    //校验验证码
    public static String JIAOYAN_YZM="http://211.151.183.170:8097/rqzsj/person/validateCode?";
    //重置密码
    public static String CHONGZHI_MIMA="http://211.151.183.170:8097/rqzsj/person/resettingPassword?";
}
