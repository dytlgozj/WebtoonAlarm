package teamwap.wap;

/**
 * Created by Tabi on 2016-11-14.
 */

// 웹툰 정보를 저장하기 위한 코드
public class webtoonIn {

    String name;
    String url;

    // 웹툰의 이름과 url 정보를 받도록 설계
    public webtoonIn(String name, String url){
        this.name = name;
        this.url = url;
    }

    // 웹툰 이름을 반환하는 함수
    public String get_name(){
        return name;
    }

    // 웹툰 이름을 수정하는 함수 (필요가 없었다)
    public void set_name(String new_name){
        this.name = new_name;
    }

    // 웹툰 url을 반환하는 함수
    public String get_url(){
        return url;
    }

    // 웹툰 url을 반환하는 함수 (필요가 없었다)
    public void set_url(String new_url) {
        this.url = new_url;
    }
}
