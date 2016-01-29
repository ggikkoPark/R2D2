package me.r2d2.util;

/**
 * Created by Park Ji Hong, ggikko.
 */

import kr.cipher.seed.Seed128Cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Encryption {

    private static MessageDigest md;
    private static String defaultCharset = "utf-8";

    /**
     * 단방향 암호화
     * @param str 문자열 (defalt형식:SHA-512)
     * @return 암호화된 문자열
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String encryptOnly(String str) throws NoSuchAlgorithmException {
        return encryptOnly(str, "SHA-512");
    }

    /**
     * 단방향 암호화
     * @param str 문자열
     * @param str 방식 (MD2,MD5,SHA,SHA-1,SHA-256,SHA-384,SHA-512)
     * @return 암호화된 문자열
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String encryptOnly(String str, String sec) throws NoSuchAlgorithmException {

        /** 암호화 방식 선택 */
        md = MessageDigest.getInstance(sec);
        /** 암호화 모듈에 암호화할 문구 저장 */
        md.update(str.getBytes());
        /** 암호화 된 바이트형 데이타 리턴 */
        byte byteData[] = md.digest();
        /** byte를 String 으로 바꿈 */
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < byteData.length ; i++){
            sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 1).substring(1));
        }

        return base64Encode(sb.toString());
    }

    /**
     * base64 인코딩
     * @param str 문자열
     * @return
     */
    public static String base64Encode(String str){

        byte[] targetBytes = str.getBytes();

        /** Base64 인코딩 */
        Encoder encoder = Base64.getEncoder();

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

        Decoder decoder = Base64.getDecoder();

        /** Decoder#decode(String src) */
        byte[] decodedBytes = decoder.decode(str);

        /** 디코딩한 문자열을 표시 */
        String decodedString = new String(decodedBytes, defaultCharset);

        return decodedString;
    }

    /**
     * Seed 알고리즘으로 암호화
     * @param str
     * @return 암호화 된 값
     * @throws Exception
     */
    public static String seedEncode(String str) throws Exception {
        String key = createKey();
        return Seed128Cipher.encrypt(str, key.getBytes(), defaultCharset);
    }

    /**
     * Seed 알고리즘으로 암호화 한 데이터 복호화
     * @param str 된값
     * @return 복호화 된 값
     * @throws Exception
     */
    public static String seedDecode(String str) throws Exception {
        String key = createKey();
        return Seed128Cipher.decrypt(str, key.getBytes(), defaultCharset);
    }

    /**
     * 암호화 키 생성
     * @return 암호화 키
     * @throws Exception
     */
    public static String createKey() throws Exception{

        String today = DateUtil.getToday();
        String key = encryptOnly(today);
        key = base64Encode(key);

        return key;

    }

}
