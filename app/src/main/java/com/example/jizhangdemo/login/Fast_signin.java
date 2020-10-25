package com.example.jizhangdemo.login;
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


public class Fast_signin extends Activity {
    static AccountHelper accountHelper;

    public static int Username_is_ok(Context context, String username){
        accountHelper = new AccountHelper(context,"account.db");
        int error_code = -1;
        if(accountHelper.query(username).getCount()==0) {//判断用户名是否存在
            error_code = 1;//用户名不存在
            return error_code;
        }
        else{
            Cursor user = accountHelper.query(username);
            user.moveToFirst();
            int pte = user.getInt(user.getColumnIndex("patternenabled"));
            if(pte == 0){
                error_code = 2;
                return error_code;
            }
            else{
                error_code = 0;
                return error_code;
            }
        }

    }


    public static int Fast_signin(Context context, String username, String  pattern){
        accountHelper = new AccountHelper(context,"account.db");
        int error_code = -1;
        Cursor user = accountHelper.query(username);
        user.moveToFirst();
        String ptn = user.getString(user.getColumnIndex("pattern"));
        String Key = "0123456789ABCDEF";//设置固定密钥
        byte[] encryptResult = encrypt(pattern, Key);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        if(!encryptResultStr.equals(ptn)){
            error_code = 1;
        }
        else{
            error_code = 0;
        }
//        if(accountHelper.query(username).getCount()!=0) {
//            error_code = 1;
//            return error_code;
//        }
//        else{
//            Cursor user = accountHelper.query(username);
//            user.moveToFirst();
//            int pte = user.getInt(user.getColumnIndex("patternenabled"));
//            if(pte == 0){
//                error_code = 2;
//                return error_code;
//            }
//            else{
//                user.moveToFirst();
//                String ptn = user.getString(user.getColumnIndex("pattern"));
//                String Key = "0123456789ABCDEF";//设置固定密钥
//                byte[] encryptResult = encrypt(pattern, Key);
//                String encryptResultStr = parseByte2HexStr(encryptResult);
//                if(!encryptResultStr.equals(ptn)){
//                    error_code = 3;
//                }
//                else{
//                    error_code = 0;
//                }
//            }
//        }
        return error_code;
    }

    public static byte[] encrypt(String content, String password) {  //AES-128加密算法
        try {
            byte[] raw = password.getBytes("utf-8");
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
