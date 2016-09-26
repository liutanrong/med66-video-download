import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import module.VideoModule;
import org.omg.CORBA.portable.ValueInputStream;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * Created by tanrong.ltr on 16/9/25.
 */
public class Main {
    private static String PUBLIC_URL="http://115.153.176.229";
    public static void main(String[] args) {

        Map<String, String> map2016 = VideoMap.getMap2016();
        parseMap(map2016,"JSON/2016.json");
    }
    private static void parseMap(Map<String,String> map,String filePath){
        StringBuilder stringBuilder=new StringBuilder("<html>\n");
        StringBuilder sb=new StringBuilder();
        stringBuilder.append("<body>\n");
        Set<Map.Entry<String, String>> set2015 = map.entrySet();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> set : set2015) {
            JSONObject object = new JSONObject();
            String lessonName = set.getKey();
            String xmlUrl = set.getValue();
            object.put("lessonName", lessonName);
            try {
                List<VideoModule> videoList = XmlParse.getSchoolNews(xmlUrl);
                object.put("videoModule", videoList);
                for (VideoModule videoModule:videoList){
                    sb.append(PUBLIC_URL+ videoModule.getHDUrl());
                    sb.append("\n");

                    stringBuilder.append("<a href="+PUBLIC_URL+videoModule.getHDUrl()+">"+videoModule.getTitle()+"-"+videoModule.getVideoOrder()+"-"+videoModule.getVideoName());

                    stringBuilder.append("</a>\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            jsonArray.add(object);
        }

        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>\n");
        writeStrToFile(stringBuilder.toString(),"JSON/html.html");
        writeStrToFile(sb.toString(),"JSON/downloadlink.txt");
        writeStrToFile(JSON.toJSONString(jsonArray),filePath);
    }

    public static void writeStrToFile(String xml,String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            Writer os = new OutputStreamWriter(fos, "UTF-8");
            os.write(xml);
            os.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
