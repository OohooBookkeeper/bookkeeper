package com.example.jizhangdemo.setting;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import com.example.jizhangdemo.AccountHelper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class User_setting extends Activity {
    static AccountHelper accountHelper;
    public static int Password_is_ok(Context context,String Username,String Password){
        accountHelper = new AccountHelper(context, "account.db");
        int error_code = -1;
        Cursor user = accountHelper.query(Username);//读取数据库中用户数据
        user.moveToFirst();
        String pwd = user.getString(user.getColumnIndex("password"));//读取用户密码
        String Key = "0123456789ABCDEF";//设置固定密钥
        byte[] encryptResult = encrypt(Password, Key);
        String encryptResultStr = parseByte2HexStr(encryptResult);//将用户输入密码转换为密文
        if(!encryptResultStr.equals(pwd)){//检查密码是否正确
            error_code = 1;//密码错误
        }
        else{
            error_code = 0;//密码验证成功
        }
        return error_code;
    }

    public static int Change_password(Context context, String username, String new_password, String new_password_rp) {
        accountHelper = new AccountHelper(context, "account.db");
        int error_code = -1;
        int len = new_password.length();//获取密码长度
        boolean password_is_ok1 = Password_rule(new_password);//判断密码是否符合规范
        boolean password_is_ok2 = (len >= 8 && len <= 16);//判断密码长度是否在8-16之间
        if(!new_password.equals(new_password_rp)) {  //判断两次输入密码是否一致
            error_code = 1;//两次输入密码不一致
        }
        else if(!(password_is_ok1 && password_is_ok2)) {  //判断密码是否符合规范
            error_code = 2;//密码不符合规范
        }
        else{
            error_code = 0;//修改密码成功
            String Key = "0123456789ABCDEF";//设置固定密钥
            byte[] encryptResult = encrypt(new_password, Key);
            String encryptResultStr = parseByte2HexStr(encryptResult);//将密码明文转为密文
            accountHelper.update(username,encryptResultStr,AccountHelper.KEEP,null,-1,null,accountHelper.KEEP);
        }
        return error_code;
    }

    public static boolean Password_rule(String password){
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for(int i=0 ; i<password.length() ; i++){ //循环遍历字符串
            if(Character.isDigit(password.charAt(i))){     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if(Character.isLetter(password.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if(password.contains(" ")){  //用char包装类中的方法来判断密码中是否含有空格
            return false;
        }
        if(isDigit && isLetter) return true;
        return false;
    }

    public static int Setting_pattern_password(Context context,String username, String pattern, String pattern_rp){
        int error_code = -1;
        accountHelper = new AccountHelper(context, "account.db");
        String Key = "0123456789ABCDEF";//设置固定密钥
        if(!pattern.equals(pattern_rp)){
            error_code = 1;
            return error_code;
        }
        byte[] encryptResult = encrypt(pattern, Key);
        String encryptResultStr = parseByte2HexStr(encryptResult);//将密码明文转为密文
        accountHelper.update(username,null,accountHelper.KEEP,null,1,encryptResultStr,accountHelper.KEEP);
        error_code = 0;
        return error_code;
    }

    public static int Changing_question(Context context,String username, int new_question,String new_answer){
        int error_code = -1;
        accountHelper = new AccountHelper(context, "account.db");
        String Key = "0123456789ABCDEF";//设置固定密钥
        byte[] encryptResult = encrypt(new_answer, Key);
        String encryptResultStr = parseByte2HexStr(encryptResult);//将密码明文转为密文
        accountHelper.update(username,null,new_question,encryptResultStr,1,null,accountHelper.KEEP);
        error_code = 0;
        return error_code;
    }


    public static byte[] encrypt(String content, String key) {  //AES-128加密算法
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return result;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }  //AES-128加密算法结束
}
