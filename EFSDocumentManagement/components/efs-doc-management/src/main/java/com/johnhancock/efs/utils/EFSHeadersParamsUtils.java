package com.johnhancock.efs.utils;

import com.google.gson.Gson;
import com.johnhancock.efs.constant.EFSHeaderEnum;
import com.johnhancock.efs.model.HeaderParamsModel;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.johnhancock.efs.constant.EFSHeaderEnum.*;

public class EFSHeadersParamsUtils {
    public static HeaderParamsModel getHeaderParams(Map<EFSHeaderEnum, String> rp){
        HeaderParamsModel hpm = new HeaderParamsModel();

        if(rp.get(EFSHeaderEnum.APP_SESSIONID).equals("")){
            hpm.setEFSAuth(String.join(" ",rp.get(APP_USERNAME),rp.get(APP_PASSWORD)));
        }else{
            hpm.setEFSAuth(rp.get(EFSHeaderEnum.APP_SESSIONID));
        }

        hpm.setEFSDate(prepareEFSDate());
        hpm.setEFSKey(rp.get(HMAC_KEY));
        hpm.setEFSSign(prepareEFSSign(hpm, rp.get(SECRET_KEY)));

        return hpm;
    }

    private static String prepareEFSSign(HeaderParamsModel hpm,String secretKey){
        String sign = "";
        try {
            byte[] bSign = (String.join("", hpm.getEFSAuth(), hpm.getEFSDate())).getBytes();
            SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(sks);
            byte[] bRc = mac.doFinal(bSign);
            sign = new String (new BASE64Encoder().encode(bRc).getBytes(), "UTF-8");
        }catch (Exception e){
            return sign;
        }

        return sign;
    }

    private static String prepareEFSDate(){
        SimpleDateFormat sdf = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss z", Locale.US );
        sdf.setTimeZone( TimeZone.getTimeZone( "GMT" ) );
        return sdf.format( Calendar.getInstance().getTime());
    }


    public static String convertToJson(Object efsException) {
        return new Gson().toJson(efsException);
    }

}

