package ziteng.lc.xf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luochao on 2017/7/13.
 */

public class ListUtils {

    /*
    * List 转换成带,号的String
    */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    //在数组中增加一个元素
    public String[] StringArray(String s, String[] strings) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        list.add(5, s);
        String[] newStr = list.toArray(new String[1]); //返回一个包含所有对象的指定类型的数组
        return newStr;
    }

    /**
     * 带,号的字符串转换成list
     */
    public static List<String> stringTolist(String s) {
        if (s != null) {
            List<String> list = Arrays.asList(s.split("\\s*,\\s*"));
            return list;
        }
        return null;
    }

    //list转数组
    public static String[] listToStrings(List<String> list) {
        if (list != null && list.size() > 0) {
            int size = list.size();
            String[] array = (String[]) list.toArray(new String[size]);
            return array;
        }
        return null;
    }
}
