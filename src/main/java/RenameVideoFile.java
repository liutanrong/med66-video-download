import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.List;

/**
 * Created by tanrong.ltr on 16/9/26.
 */
public class RenameVideoFile {
    private static String TARGET_PATH_DIR ="video/";
    private static String SOURCE_PATH_DIR="G:\\下载\\视频\\sp\\";

    public static void main(String[] args){
        String jsonFilePath="JSON/2016_10_41.json";

        JSONArray jsonArray= JSON.parseArray(readFile(jsonFilePath));
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object=jsonArray.getJSONObject(i);
            String lessonName=object.getString("lessonName");
            JSONArray videoArray=object.getJSONArray("videoModule");
            List<VideoModule> videoModuleList=JSON.parseArray(JSON.toJSONString(videoArray),VideoModule.class);
            File file=new File(TARGET_PATH_DIR +lessonName);
            if (!file.exists()){
                if (!file.mkdir()){
                    System.out.println("创建目录["+lessonName+"]失败");
                }
            }
            for(VideoModule videoModule:videoModuleList){
                String order=videoModule.getVideoOrder();
                String title=videoModule.getTitle();
                String videoName=videoModule.getVideoName();
                String downloadName=videoModule.getHDUrl();
                downloadName=downloadName.substring(5);
                renameFile(SOURCE_PATH_DIR+downloadName, TARGET_PATH_DIR +lessonName+"/"+order+"_"+title+"_"+videoName+".mp4");
            }
        }
    }

    /**
     * 文件重命名
     * @param oldpath
     * @param newPath
     */
    public static void renameFile(String oldpath,String newPath){
        if(!oldpath.equals(newPath)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(oldpath);
            File newfile=new File(newPath);
            boolean flag=oldfile.exists();
            if(!flag){
                System.out.println("文件不存在");
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newPath+"已经存在！");
            else{
                boolean fla=oldfile.renameTo(newfile);
                if (!fla){
                    System.out.println(oldfile+"修改失败");
                }
            }
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }
    private static String readFile(String path){
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            StringBuilder stringBuilder=new StringBuilder();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                stringBuilder.append(tempString);
                line++;
            }
            reader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }
}
