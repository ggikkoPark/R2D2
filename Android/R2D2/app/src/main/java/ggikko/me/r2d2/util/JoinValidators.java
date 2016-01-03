package ggikko.me.r2d2.util;

/**
 * Created by ggikko on 16. 1. 3..
 */
public class JoinValidators {

    public boolean checkEmail(String email){
        if(email.length() > 30)return false;
        if(email.length() < 6)return false;
        if(!email.contains("@"))return false;
        return true;
    }

    public boolean checkPassword(String password){
        if(password.length()<4)return false;
        if(password.length()>15)return false;
        return true;
    }

    public boolean checkPasswordCheck(String password, String passwordCheck){
        if(!password.equals(passwordCheck))return false;
        return true;
    }

    public boolean subwaycheck(String subway){
        if(subway.trim().equals(""))return false;
        return true;
    }
}
