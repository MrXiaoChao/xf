package ziteng.lc.xf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luochao on 2017/5/4.
 * 项目统计
 */

public class ProjectStatement implements Serializable {

    /**
     * count : 16
     * classifyName : 总项目数量
     * subclassification : [{"count":"14","subclassification":[{"count":"1","mark":"402881e85b61681b015b616a64b10005","classifyName":"节能环保"},{"count":"3","mark":"402881e85b61681b015b616b9ad6000b","classifyName":"其他"},{"count":"6","mark":"402881e85b61681b015b6169b6920000","classifyName":"新材料"},{"count":"2","mark":"402881e85b61681b015b616a42b40004","classifyName":"电子信息"},{"count":"1","mark":"402881e85b61681b015b616a84ce0006","classifyName":"装备制造"},{"count":"1","mark":"402881e85b61681b015b6169fe380002","classifyName":"汽车及配件 "}],"classifyName":"所属产业"},{"count":"15","subclassification":[{"count":"9","mark":"1","classifyName":"鼓励类"},{"count":"1","mark":"3","classifyName":"限制类"},{"count":"5","mark":"2","classifyName":"允许类"}],"classifyName":"产业类别"},{"count":"15","subclassification":[{"count":"10","mark":"1","classifyName":"工业"},{"count":"3","mark":"3","classifyName":"服务业"},{"count":"2","mark":"2","classifyName":"农业"}],"classifyName":"项目类型"},{"count":"13","subclassification":[{"count":"4","mark":"1","classifyName":"50亩以下"},{"count":"1","mark":"2","classifyName":"50亩-100亩"},{"count":"2","mark":"3","classifyName":"100亩-200亩"},{"count":"6","mark":"4","classifyName":"200亩以上"}],"classifyName":"总占地面积"},{"count":"15","subclassification":[{"count":"1","mark":"1","classifyName":"北京"},{"count":"10","mark":"5","classifyName":"其他"},{"count":"4","mark":"4","classifyName":"上海"}],"classifyName":"项目属地"}]
     */

    private String count;
    private String classifyName;
    private List<SubclassificationBeanX> subclassification;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public List<SubclassificationBeanX> getSubclassification() {
        return subclassification;
    }

    public void setSubclassification(List<SubclassificationBeanX> subclassification) {
        this.subclassification = subclassification;
    }

    public static class SubclassificationBeanX implements Serializable {
        /**
         * count : 14
         * subclassification : [{"count":"1","mark":"402881e85b61681b015b616a64b10005","classifyName":"节能环保"},{"count":"3","mark":"402881e85b61681b015b616b9ad6000b","classifyName":"其他"},{"count":"6","mark":"402881e85b61681b015b6169b6920000","classifyName":"新材料"},{"count":"2","mark":"402881e85b61681b015b616a42b40004","classifyName":"电子信息"},{"count":"1","mark":"402881e85b61681b015b616a84ce0006","classifyName":"装备制造"},{"count":"1","mark":"402881e85b61681b015b6169fe380002","classifyName":"汽车及配件 "}]
         * classifyName : 所属产业
         */

        private String count;
        private String classifyName;
        private List<SubclassificationBean> subclassification;

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        private String mark;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public List<SubclassificationBean> getSubclassification() {
            return subclassification;
        }

        public void setSubclassification(List<SubclassificationBean> subclassification) {
            this.subclassification = (subclassification != null) ? subclassification : new ArrayList<SubclassificationBean>();

        }

        public static class SubclassificationBean implements Serializable {
            /**
             * count : 1
             * mark : 402881e85b61681b015b616a64b10005
             * classifyName : 节能环保
             */
            private String count;
            private String mark;
            private String classifyName;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }
        }
    }
}
