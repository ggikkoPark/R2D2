package ggikko.me.r2d2.data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ggikko on 16. 1. 1..
 */
public class SubwayInformation {

    /** 기존에는 지역으로 하려고 생각했으나 프로토타입으로 역으로 바꾸기위해 잠시 보류  */

    public static List<String> location = Arrays.asList
            ("1호선","2호선","3호선","4호선","5호선","6호선","7호선","8호선","9호선","분당선","신분당선");

    public static List<String> one = Arrays.asList
            ("1호선 종각");

    public static List<String> two = Arrays.asList
            ("2호선 강남역","2호선 역삼역","2호선 선릉역","2호선 삼성역","2호선 교대역","2호선 서초역"
                    ,"2호선 방배역","2호선 사당역","2호선 구로디지털역");

    public static List<String> three = Arrays.asList("3호선 종로3가");
    public static List<String> four = Arrays.asList("4호선 명동역");
    public static List<String> five = Arrays.asList("5호선 강동역");
    public static List<String> six = Arrays.asList("6호선 고려대역");
    public static List<String> seven = Arrays.asList("7호선 건대입구역");
    public static List<String> eight = Arrays.asList("8호선 천호역");
    public static List<String> nine = Arrays.asList("9호선 신논현역");
    public static List<String> bundang = Arrays.asList("분당선 정자역");
    public static List<String> sinbundang = Arrays.asList("신분당선 판교역");



    public static List<List<String>> getAllLocation() {
        List<List<String>> list = Arrays.asList(one, two, three, four, five, six, seven, eight, nine, bundang, sinbundang);
        return list;
    }

}
