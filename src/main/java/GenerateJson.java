import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * Created by tanrong.ltr on 16/9/25.
 * 生成视频信息JSON,下载链接
 */
public class GenerateJson {
    private static String PUBLIC_URL="http://115.153.176.229";
    private static String JSON_PATH="JSON/";
    public static void main(String[] args) {
        File file=new File(JSON_PATH);
        if (!file.exists()){
            if (!file.mkdir()){
                System.out.println("创建保存JSON目录失败");
            }
        }

        Map<String, String> map2016 = VideoXmlInfoMap.getMap2016();
        parseMap(map2016,JSON_PATH+"2016.json",JSON_PATH+"downloadUrl");

    }
    private static void parseMap(Map<String,String> map,String jsonInfoPath,String downloadUrlPath){
        StringBuilder sb=new StringBuilder();
        Set<Map.Entry<String, String>> set2015 = map.entrySet();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> set : set2015) {
            JSONObject object = new JSONObject();
            String lessonName = set.getKey();
            String xmlUrl = set.getValue();
            object.put("lessonName", lessonName);
            try {
                List<VideoModule> videoList = DownloadParseXml.getSchoolNews(xmlUrl);
                object.put("videoModule", videoList);
                for (VideoModule videoModule:videoList){
                    sb.append(PUBLIC_URL).append(videoModule.getHDUrl()).append("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            jsonArray.add(object);
        }

        //将生产的json及下载链接写入文件
        writeStrToFile(sb.toString(),downloadUrlPath);
        writeStrToFile(JSON.toJSONString(jsonArray),jsonInfoPath);
    }

    /**
     * 将字符串写入文件
     * @param xml
     * @param path
     */
    private static void writeStrToFile(String xml,String path) {
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
