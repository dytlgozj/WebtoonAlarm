package teamwap.wap;

/**
 * Created by Tabi on 2016-11-14.
 */

public class webtoonInfor {
    String name;
    String url;

    public webtoonInfor(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String get_name(){
        return name;
    }

    public void set_name(String new_name){
        this.name = new_name;
    }

    public String get_url(){
        return url;
    }

    public void set_url(String new_url) {
        this.url = new_url;
    }
}
