
package com.example.beans;

import android.text.Html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SyndEntry {
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    private String mTitle = "";
    private String mLink = "";
    private Date mDate;
    private SyndContent mSyndContent;
    
    private String mAuthor;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public List getCategories() {
        return null;
    }

    public List getEnclosures() {
        // TODO Auto-generated method stub
        return null;
    }

    public List getLinks() {
        // TODO Auto-generated method stub
        return null;
    }

    public List getContents() {
        return null;
    }

    public String getPublishedDate() {
        return sdf.format(mDate);
    }

    public void setPublishedDate(String mdateStr) {
        mDate = new Date(mdateStr);
    }

    public Date getUpdatedDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public SyndContent getDescription() {
        return mSyndContent;
    }
    
    public void setDescription(String description) {
        if(mSyndContent == null)
            mSyndContent = new SyndContent();
        mSyndContent.setValue(description);
    }

    public SyndContent getTitleEx() {
        return null;
    }

    public String getAuthor() {
        return mAuthor;
    }
    
    public void setAuthor(String author) {
        mAuthor = author;
    }
    
    public StringBuffer formatStr() {
        SyndEntry entry = this;
        StringBuffer sb = new StringBuffer();
        sb.append("标题：" + entry.getTitle());
        sb.append("\n");
        sb.append("连接地址：" + entry.getLink());
        sb.append("\n");
        SyndContent description = entry.getDescription();
        String date =entry.getPublishedDate();
        Date update =entry.getUpdatedDate();
        sb.append("发布时间：" + String.valueOf(date));
        sb.append("\n");
        sb.append("更新时间：" + String.valueOf(update));
        sb.append("\n");
        
        SyndContent titleEx = entry.getTitleEx();
        String title=titleEx==null?"":titleEx.getValue();
        sb.append("标题EX：" +title );
        sb.append("\n");
        // 以下是Rss源可先的几个部分
        sb.append("标题的作者：" + entry.getAuthor());
        sb.append("\n");
        sb.append("链接：" + entry.getLink());
        sb.append("\n");
        
        String value=description==null?"":description.getValue();
        sb.append("标题简介：" + Html.fromHtml(value));
        sb.append("\n");
    
        // 得到内容
        List contentsList=entry.getContents();
        if (contentsList != null) {
         for (int m = 0; m < contentsList.size(); m++) {
          String contents = (String) contentsList.get(m);
          sb.append("得到内容：" + contents);
          sb.append("\n");
         }
        }
        
        // 得到Links
        List linkList=entry.getLinks();
        if (linkList != null) {
         for (int m = 0; m < linkList.size(); m++) {
          String link = (String) linkList.get(m);
          sb.append("连接地址：" + m + link);
          sb.append("\n");
         }
        }
        
        // 此标题所属的范畴
        List categoryList = entry.getCategories();
        if (categoryList != null) {
         for (int m = 0; m < categoryList.size(); m++) {
          SyndCategory category = (SyndCategory) categoryList.get(m);
          sb.append("此标题所属的范畴：" + category.getName());
          sb.append("\n");
         }
        }
        
        // 得到流媒体播放文件的信息列表
        List enclosureList = entry.getEnclosures();
        if (enclosureList != null) {
         for (int n = 0; n < enclosureList.size(); n++) {
          SyndEnclosure enclosure = (SyndEnclosure) enclosureList.get(n);
          sb.append("流媒体播放文件：" + entry.getEnclosures());
          sb.append("\n");
         }
        }
        return sb;
    }

}
