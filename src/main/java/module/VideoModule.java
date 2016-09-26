package module;

/**
 * Created by tanrong.ltr on 16/9/25.
 */
public class VideoModule {
    private String videoName;
    private String title;
    private String HDUrl;
    private String videoDownloadName;
    private String videoUrl;
    private String audioUrl;
    private String videoOrder;

    public VideoModule() {
    }


    public String getVideoDownloadName() {
        return videoDownloadName;
    }

    public void setVideoDownloadName(String videoDownloadName) {
        this.videoDownloadName = videoDownloadName;
    }

    public String getVideoOrder() {
        return videoOrder;
    }

    public void setVideoOrder(String videoOrder) {
        this.videoOrder = videoOrder;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHDUrl() {
        return HDUrl;
    }

    public void setHDUrl(String HDUrl) {
        this.HDUrl = HDUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
