package me.r2d2.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by Park Ji Hong, ggikko.
 */

/**
 * 임시로 만든 암호화 클래스
 * 추 후에 Spring security로 변환 예정
 */
public class Encryption2 {

    private static String defaultCharset = "utf-8";

    /**
     * 암호화 생성
     * 테스트 암호화
     * @param str
     * @return
     * @throws Exception
     */
    public static String encode(String str) throws Exception {
        String key = createKey();
        return str+key;
    }

    /**
     * DateUtil 로 키생성
     * @return
     * @throws Exception
     */
    public static String createKey() throws Exception{
        String today = DateUtil.getToday();
        String test = base64Encode(today);
        return test;
    }

    /**
     * base64 인코딩
     * @param str
     * @return
     */
    public static String base64Encode(String str){

        byte[] targetBytes = str.getBytes(StandardCharsets.UTF_8);

        /** Base64 인코딩 */
        Base64.Encoder encoder = Base64.getEncoder();

        /** Encoder#encodeToString(byte[] src) :: 문자열로 반환 */
        String encodedString = encoder.encodeToString(targetBytes);

        return encodedString;
    }

    /**
     * base64 디코딩
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public static String base64Decode(String str) throws UnsupportedEncodingException {

        //TODO : BASE64 인코딩문제 막아야함
        Base64.Decoder decoder = Base64.getDecoder();

        /** Decoder#decode(String src) */
        byte[] decodedBytes = decoder.decode(str);

        /** 디코딩한 문자열을 표시 */
        String decodedString = new String(decodedBytes, defaultCharset);

        return decodedString;
    }

}
