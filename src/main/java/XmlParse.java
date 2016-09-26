import module.VideoModule;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanrong.ltr on 16/9/25.
 */
public class XmlParse {

    public static List<VideoModule> getSchoolNews(String xmlUrl)
            throws MalformedURLException, IOException, XmlPullParserException {
        String path =xmlUrl;
        HttpURLConnection con = (HttpURLConnection) new URL(path)
                .openConnection();
        con.setConnectTimeout(15000);
        con.setRequestMethod("GET");
        int i=con.getResponseCode();
        if(i==200){
            InputStream in = con.getInputStream();
            return parseXML(InputStreamTOString(in));
        }
        return null;

    }
    public static String InputStreamTOString(InputStream in){

        try {

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[256];
            int count = -1;
            while((count = in.read(data,0,256)) != -1)
                outStream.write(data, 0, count);

            data = null;
            return new String(outStream.toByteArray(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * pull方法解析xml
     */
    private static List<VideoModule> parseXML(String in)
            throws XmlPullParserException, IOException {

        List<VideoModule> videoModuleList=new ArrayList<VideoModule>();
        try {
            Document document = DocumentHelper.parseText(in);
            Element root = document.getRootElement();
            List<Element> childElements = root.elements();
            for (Element child : childElements) {
                if (child.getName().equals("courseware")){
                    VideoModule module=new VideoModule();

                    //未知子元素名情况下
                   List<Element> elementList = child.elements();
                   for (Element ele : elementList) {
                       String name=ele.getName();
                       if (name.equals("audiourl")){
                           module.setAudioUrl(ele.getText());
                       }else if (name.equals("videourl")){
                           module.setVideoUrl(ele.getText());

                       }else if (name.equals("videoHDurl")){
                           String namee=ele.getText();
                           module.setHDUrl(namee);
                           module.setVideoDownloadName(namee.substring(5));


                       }else if (name.equals("title")){
                           module.setTitle(ele.getText());

                       }else if (name.equals("videoname")){
                           module.setVideoName(ele.getText());

                       }else if (name.equals("videoOrder")){
                           module.setVideoOrder(ele.getText());
                       }
                   }
                    videoModuleList.add(module);
                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return videoModuleList;

    }
    public static Element getRootElement(Document doc){
        return doc.getRootElement();
    }
}
