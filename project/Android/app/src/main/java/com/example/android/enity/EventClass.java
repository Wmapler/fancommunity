package com.example.android.enity;

public class EventClass {
    public  String title;
    public  String content;
    public  String startTime;
    public  String endTime;
    public String needCount;
    public String applyCount;
    public String during;

    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNeedCount() {
        return needCount;
    }

    public void setNeedCount(String needCount) {
        this.needCount = needCount;
    }

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public  String getTitle() {
        return title;
    }

    public  void setTitle(String title) {
        this.title = title;
    }

    public  String getContent() {
        return content;
    }

    public  void setContent(String content) {
        this.content = content;
    }

    public  String getStartTime() {
        return startTime;
    }

    public  void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public  String getEndTime() {
        return endTime;
    }

    public  void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
