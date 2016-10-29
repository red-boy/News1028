package com.example.administrator.news1028.activities.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator.
 * @version .序列化处理
      1外部类，内部类，都要序列化，集合使用ArrayList/ LinkedList
 * @time .
 */

public class NewsTypes implements Serializable{
    public ArrayList<SubName> tList;//tList对应解析出的集合名称

    public NewsTypes(ArrayList<SubName> tList) {
        this.tList = tList;
    }

    public static class SubName implements Serializable {
        public String tid, tname;

        public SubName(String tid, String tname) {
            this.tid = tid;
            this.tname = tname;
        }
    }
}
