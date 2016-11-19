package teamwap.wap;

/**
 * Created by Tabi on 2016-11-14.
 */

public class webtoonIn {

    private int _id;

    String name;
    String url;

    public webtoonIn(int _id, String name, String url){
        this._id = _id;
        this.name = name;
        this.url = url;
    }

    public int get_id(){
        return _id;
    }

    public void set_id(){
        this._id = _id;
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
