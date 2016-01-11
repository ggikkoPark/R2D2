package ggikko.me.r2d2.util;

/**
 * Created by ggikko on 16. 1. 3..
 */
public class JoinValidators {

    /**
     * 이메일 유효성 검사
     * @param email
     * @return
     */
    public boolean checkEmail(String email){
        if(email.length() > 30)return false;
        if(email.length() < 6)return false;
        if(!email.contains("@"))return false;
        return true;
    }

    /**
     * 비밀번호 유효성 검사
     * @param password
     * @return
     */
    public boolean checkPassword(String password){
        if(password.length()<4)return false;
        if(password.length()>15)return false;
        return true;
    }

    /**
     * 비밀번호 확인 유효성 검사
     * @param password
     * @param passwordCheck
     * @return
     */
    public boolean checkPasswordCheck(String password, String passwordCheck){
        if(!password.equals(passwordCheck))return false;
        return true;
    }

    /**
     * 지하철 역 유효성 검사
     * @param subway
     * @return
     */
    public boolean checkSubway(String subway){
        if(subway.equals(""))return false;
        if(subway.isEmpty())return false;
        return true;
    }
}
