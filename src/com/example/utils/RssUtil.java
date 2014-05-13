package com.example.utils;

import android.text.Html;
import android.util.Log;
import android.util.Xml;

import com.example.beans.SyndCategory;
import com.example.beans.SyndContent;
import com.example.beans.SyndEnclosure;
import com.example.beans.SyndEntry;
import com.example.beans.SyndFeed;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 解析RSS工具类
 * @author fengzihua
 *
 */
public class RssUtil {
    
    public static DefaultHttpClient sHttpClient;

	/**
	 * 根据url接口解析rss
	 * @param url
	 * @return SyndFeed
	 */
	/*public static SyndFeed getRssBYURL(String url){
	    
	    XmlPullParser parser = Xml.newPullParser();
	    parser.setInput(inputStream, "utf-8");
		SyndFeedInput input = new SyndFeedInput();
		try {
			URL rss = new URL(url);
			XmlReader read=new XmlReader(rss);
			SyndFeed feed = input.build(read);
			return feed;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/**
	 * 根据文件路径解析rss
	 * @param path
	 * @return SyndFeed
	 */
	/*public static SyndFeed getRssBYPath(String path){
		SyndFeedInput input = new SyndFeedInput();
		try {
			File file = new File(path);
			InputStream in = new FileInputStream(file);
			XmlReader read=new XmlReader(in);
			SyndFeed feed = input.build(read);
			return feed;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}*/
    
	public static StringBuffer fetchRssList() {
	    StringBuffer sb = new StringBuffer();
		//String path = "file:///android_asset//rss.xml";
		//SyndFeed feed=getRssBYPath(path);
	    SyndFeed feed =getRssItems("http://www.toodaylab.com/feed");
	    sb.append("标题："+feed.getTitle());
	    sb.append("\n");
	    sb.append("发布时间："+feed.getPublishedDate());//Fri, 22 Feb 2008 15:49:18 GMT
        sb.append("\n");
	    sb.append("语言："+feed.getLanguage());
        sb.append("\n");
        sb.append("feed.getUri():"+feed.getUri());
        sb.append("\n");
		sb.append("feed.getEncoding():"+feed.getEncoding());
        sb.append("\n");
		sb.append("feed.getLink():"+feed.getLink());
        sb.append("\n");
		List<SyndEntry> list = feed.getEntries();
		if (list != null && !list.isEmpty()) {
		    for (SyndEntry entry : list) {
		    	// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
		        sb.append("--------------------------------------------------------------------");
		        sb.append("\n");
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
                sb.append("--------------------------------------------------------------------");
                sb.append("\n");
		    }  
		}
		return sb;
	}
	
    /**
     *  得到 apache http HttpClient对象
     */
    public static DefaultHttpClient getHttpClient() {

        /** 创建 HttpParams 以用来设置 HTTP 参数 **/

        HttpParams httpParams = new BasicHttpParams();

        /** 设置连接超时和 Socket 超时，以及 Socket 缓存大小 **/

        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

        HttpClientParams.setRedirecting(httpParams, true);

        /**
         * 创建一个 HttpClient 实例 //增加自动选择网络，自适应cmwap、CMNET、wifi或3G
         */
        //MyHttpCookies li = new MyHttpCookies(context);
        //String proxyStr = li.getHttpProxyStr();
        //if (proxyStr != null && proxyStr.trim().length() > 0) {
            //HttpHost proxy = new HttpHost(proxyStr, 80);
            //httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
       // }
        /** 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient **/

        sHttpClient = new DefaultHttpClient(httpParams);
        //sHttpClient.setHttpRequestRetryHandler(requestRetryHandler);

        return sHttpClient;

    }
	
	// 初始化网络连接
    public static final void initNetworkClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

//        SchemeRegistry schReg = new SchemeRegistry();
//        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
//        schReg.register(new Scheme("https", Connectivity.createInvalidCertificationAcceptingSSLSocketFactory(), 443));
//        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
//        sHttpClient = new DefaultHttpClient(conMgr, params);
        
        sHttpClient = new DefaultHttpClient(params);
    }
	
	
	public static SyndFeed getRssItems(String url) {
	    
	    initNetworkClient();
	    
	    SyndFeed syndFeed = new SyndFeed();
	    
        List<SyndEntry> list = null;

        SyndEntry item = null;

        try {
            
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = sHttpClient.execute(request);
            
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(response.getEntity().getContent(), "utf-8");
            int type = parser.getEventType();

            while (type != XmlPullParser.END_DOCUMENT) {

                switch (type) {

                case XmlPullParser.START_DOCUMENT:
                    list = new ArrayList<SyndEntry>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("item".equals(parser.getName())) {
                        item = new SyndEntry();
                    }

                    if (item != null) {
                        if ("title".equals(parser.getName())) {
                            item.setTitle(parser.nextText());
                        } else if ("link".equals(parser.getName())) {
                            item.setLink(parser.nextText());
                        } else if ("description".equals(parser.getName())) {
                            item.setDescription(parser.nextText());
                        } else if ("pubDate".equals(parser.getName())) {
                            item.setPublishedDate(parser.nextText());
                        } else if("creator".equals(parser.getName())) {
                            item.setAuthor(parser.nextText());
                            Log.d("fengzihua", item.getAuthor());
                        }
                    }

                    break;

                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        list.add(item);
                        item = null;
                    }

                    break;

                }

                type = parser.next();
            }
            
            syndFeed.setEntries(list);
            
            return syndFeed;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
