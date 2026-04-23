package com.scms.model;

import java.time.LocalDateTime;

public class Notice {
    private int noticeId;
    private String title;
    private String content;
    private int postedBy;
    private String postedByName;  // for display
    private int departmentId;
    private String departmentName;// for display
    private LocalDateTime createdAt;

    public Notice() {}

    public int getNoticeId()                            { return noticeId; }
    public void setNoticeId(int noticeId)               { this.noticeId = noticeId; }

    public String getTitle()                            { return title; }
    public void setTitle(String title)                  { this.title = title; }

    public String getContent()                          { return content; }
    public void setContent(String content)              { this.content = content; }

    public int getPostedBy()                            { return postedBy; }
    public void setPostedBy(int postedBy)               { this.postedBy = postedBy; }

    public String getPostedByName()                     { return postedByName; }
    public void setPostedByName(String postedByName)    { this.postedByName = postedByName; }

    public int getDepartmentId()                        { return departmentId; }
    public void setDepartmentId(int departmentId)       { this.departmentId = departmentId; }

    public String getDepartmentName()                   { return departmentName; }
    public void setDepartmentName(String name)          { this.departmentName = name; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)   { this.createdAt = createdAt; }
}