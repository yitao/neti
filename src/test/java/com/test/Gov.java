package com.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yitao on 2017/3/15.
 */
public class Gov {


    @Test
    public void test1() throws IOException {
        String site = "http://www.gsxt.gov.cn/%7BuYW_KjrxvwjVZ8-uiVpDsMDwfHjcVNx_vSV2wX0c-5WMg2Yio_GUlMjiJLigWeUOpwNQFM8viw6IOlKnyZKsErc4uizzGIci3DFZYXVmdUNuX9sOmdo9LzUsgG_5J7bZurbYumAg8bBV6KU8kvo99Q-1489541908459%7D";
        Map<String,String> data = new HashMap<String, String>();
        data.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        data.put("Accept-Encoding","gzip, deflate");
        data.put("Accept-Language","zh-CN,zh;q=0.8");
        data.put("Cache-Control","no-cache");
        data.put("Connection","keep-alive");
        data.put("Content-Length","235");
        data.put("Content-Type","application/x-www-form-urlencoded");
        data.put("Cookie","jsluid=27495ee7a991d476f1e9e1ad84909b83; UM_distinctid=15acbb0f6523-08d9edde5-3967470d-1fa400-15acbb0f6532db; tlb_cookie=42query_8080; CNZZDATA1261033118=341062022-1489472296-%7C1489542499; Hm_lvt_cdb4bc83287f8c1282df45ed61c4eac9=1489476057,1489543835; Hm_lpvt_cdb4bc83287f8c1282df45ed61c4eac9=1489544784; JSESSIONID=759E44FA12D96D79C2881F1018F972C5-n2:5");
        data.put("Host","www.gsxt.gov.cn");
        data.put("Origin","http://www.gsxt.gov.cn");
        data.put("Pragma","no-cache");
        data.put("Referer","http://www.gsxt.gov.cn/corp-query-homepage.html");
        data.put("Upgrade-Insecure-Requests","1");
        data.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        Connection conn = Jsoup.connect(site);
        for(String key:data.keySet()){
            conn.header(key,data.get(key));
        }
        Document document = conn.get();
        System.out.println(document.html());
    }
}
