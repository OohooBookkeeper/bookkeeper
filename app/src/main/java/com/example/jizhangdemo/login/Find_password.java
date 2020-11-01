package com.example.jizhangdemo.login;
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


public class Find_password {
    static AccountHelper accountHelper;

    public static int Username_is_ok(Context context, String username) {  //判断用户名是否存在
        accountHelper = new AccountHelper(context, "account.db");
        int error_code = -1;
        if (accountHelper.query(username).getCount() == 0) {
            error_code = 1;//用户名不存在
        } else {
            error_code = 0;//用户名存在，跳转下一步
        }
        return error_code;
    }

    public static int Return_problem_num(Context context, String username) {  //返回预存密保问题题号
        accountHelper = new AccountHelper(context, "account.db");
        Cursor user = accountHelper.query(username);//读取数据库中用户数据
        user.moveToFirst();
        int qst = user.getInt(user.getColumnIndex("question"));
        return qst;
    }

    public static int Answer_is_correct(Context context, String username, String answer) {  //判断密保问题是否正确
        accountHelper = new AccountHelper(context, "account.db");
        int error_code = -1;
        Cursor user = accountHelper.query(username);//读取数据库中用户数据
        user.moveToFirst();
        String ans = user.getString(user.getColumnIndex("answer"));
        String Key = "0123456789ABCDEF";//设置固定密钥
        byte[] encryptResult = encrypt(answer, Key);
        String encryptResultStr = parseByte2HexStr(encryptResult);//将密码明文转为密文
        if (!encryptResultStr.equals(ans)) {
            error_code = 1;//  密保问题回答错误
        } else {
            error_code = 0;//  密保问题回答正确
        }
        return error_code;
    }

    public static int Change_password(Context context, String username, String password, String password_rp) {
        accountHelper = new AccountHelper(context, "account.db");
        int error_code = -1;
        int len = password.length();//获取密码长度
        boolean password_is_ok1 = Password_rule(password);//判断密码是否符合规范
        boolean password_is_ok2 = (len >= 8 && len <= 16);//判断密码长度是否在8-16之间
        if(!password.equals(password_rp)) {  //判断两次输入密码是否一致
            error_code = 1;//两次输入密码不一致
        }
        else if(!(password_is_ok1 && password_is_ok2)) {  //判断密码是否符合规范
            error_code = 2;//密码不符合规范
        }
        else{
            error_code = 0;//修改密码成功
            String Key = "0123456789ABCDEF";//设置固定密钥
            byte[] encryptResult = encrypt(password, Key);
            String encryptResultStr = parseByte2HexStr(encryptResult);//将密码明文转为密文
            accountHelper.update(username,encryptResultStr,0,null,-1,null,accountHelper.KEEP);
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
