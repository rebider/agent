import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Conver10ToConver33Utils;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.ryx.credit.common.util.Conver10ToConver33Utils.getBetweenValues;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
        try {

            JSONObject par = new JSONObject();
            par.put("identification","POS");
            String pars = par.toJSONString();

            System.out.println("请求明文:"+pars);
            String agent_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnb/YbYzvf+aVfhQHu1u0"+
                    "nSAy1RT2slmUiErvCMiLe21sJroLIZ/X1sdFeOSuTj21ejGYknhu+JbqcozmAG0s"+
                    "JSvgFNh86HXmcaPM46BqjWEgFGE85/RoyGNoK/vanwjZst4ouf7U3jLIupcJtxEU"+
                    "AkxAs0gnut4uP7Nd8VV6fSkxk+rlsAWkWp9Iu6q/ubnOW+RntHwJyt9+Qdfgz0O0"+
                    "DbN0yZqlvMRRMe059X09hSmV5aaWqxeMEx65jV0GVV1OhgQMtNUvTMCFLNWvGnPZ"+
                    "7w4hhy6g0XqY2YDRpMXEKn6EUINwQiu4OTqQO4G7feeeQKbAYnLKZIAwZOqa7twU"+
                    "QQIDAQAB";
            String rdb_prim_key = 
                    "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2cP05SisTRwiO\n" +
                    "EHXslZnmQ8G5mSLQQRoNwhncq+frFqXEulcQEvMF8QpO0xw0l5ZE/9J/uiHJR47/\n" +
                    "TiGnAOdBtMQcw4uDP3UBM8TGmK4XcokWh1eCqJ4jbpslJqoQw7hYfY0ApSXgiqSQ\n" +
                    "AF/VsUuh71TArrMwT4NJZIveaM3BWi0qccxvYGQf6Z+pwJVC7gqcOXbq7vJiYVpF\n" +
                    "J5VveO9/BqIAV3/nAE5+cmxv3Ag40YZ0m4+TRgUr4yo2g4voXbZKvvhQehycF8PB\n" +
                    "HyKCU+4Yk2anD0eej7Q3nH2Pqyz7ONZsUe1tntIOnBIOE5aZpGtq9Glozq7CQDwO\n" +
                    "PA4M8LWHAgMBAAECggEAPTF83iBwflDPH8eJpkcvjbqgWf5xXOTZNlAqmIfYjFPW\n" +
                    "WjweQ7f3Z8DHslTBGyrVLmOYWVs3ReoInr24L+nvp2xYc0VroCRCvJp+oBZKHnc+\n" +
                    "YB4ZTiv8junkg/uKA4GCbbv+9X5vS/d05xAZFPYY5kH+bXqrg8LHyHA/MeR5Tya8\n" +
                    "4zugI8Op7iCHBmZvZ4sb6kWi1l16wL9J5XML/XjZ0Sw9cEmBYcVgAM53h7doT44c\n" +
                    "TUW0q1tx2epvAaKX3IaiFbM2vCwNITrHiurt+XgYyOp1KAYNGyAo6uyrT81s5Qmf\n" +
                    "rVzZSvpeXOrPnU0aidOAzJWEaSWJhkBGm/bcFILlgQKBgQDjKxYEgvCGd01BVgzC\n" +
                    "VPGYKx6GqMhaIGzRfXAU9poSQrwaRgM3KMpLGHAgAp04K5IMyCFQDHiW8OCfXQsx\n" +
                    "57jbP5ve7e0jpawZPQQRU3uzavZK3J/Z5UHPRQeGzV1/S/dD+pEUTg7cjfFFcxqX\n" +
                    "5m7V9fxiBaNl+lUSU3V7ll9g4QKBgQDNmK6vCCIZH//JpQsrn//OrzrJ64xX8kYG\n" +
                    "VmoiEIN5u5R/bgmjSV7epFUS2OmQgCQzyEO5BwW8bbCFcetlQ0CxoHgKCvbjbRHp\n" +
                    "8W6OynrBnnWmxGlz59+DmT1KdtRX6zn4eWMLY5QK8Ho01bTGcIZCX8UIFBIfEVwk\n" +
                    "NOaTE3kbZwKBgQDaFxVOQ38zOPn8TeZ18yL2Wf82fHfMvvB5tpmSJqYH/fWkq66O\n" +
                    "mCSOL+oFcT0LHhlbGA+TUEW9c4fSt3r18GcyZkD4pydRX853dHKhR6qst3W4rVLx\n" +
                    "1/10RetR6XtnqMC8jKQYAFCYQdVuPXE8f8fMthoU6SDT2J8vttxFSDpNQQKBgQCd\n" +
                    "5MNLAukE+LE9kDXq8/7GIYb5qZ9nr1Wd6mGp9ZqzM58Q2lYmg+OLAxOe3N1LL04e\n" +
                    "Qo/YGo+KLYdihKGubB7UC5QEynTfJlWan+MzYEc0SVi7kZ4W+J2MeHQ8qtPbDXjA\n" +
                    "3aB2D6yTVinUR8eEmshRUzGDSb69A10LJWFq1IquwwKBgAkITiw+NxXjpMUz3/tv\n" +
                    "YQRu9qPt+ethZo2gOEWzhqKF/+MMPFYDKrASTZu3/5d80YU9T8b1P1Y9VeFjnMuH\n" +
                    "EN7vrlkj3g7jMv+XCa/VKVJcuHqRY/FiO0JDNwnHPE88SPXsPeR3Zkl0RK4ki475\n" +
                    "NEPotI6upccEvY6gPe1K69gW";


            PublicKey publicKey = RSAUtil.getRSAPublicKey(agent_public_key, "pem", "RSA");
            PrivateKey privateKey = RSAUtil.getRSAPrivateKey(rdb_prim_key, "pem", null, "RSA");
            String charset = "UTF-8";
            String plainXML = pars;
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(Base64.encodeBase64(RSAUtil.encrypt(keyBytes, publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("signData", signData);
            map.put("identification", "POS");
            String s = JSONObject.toJSONString(map);

            System.out.println("param值:"+map);

            Map<String, String> reqmap = new HashMap<>();
            reqmap.put("param",s);
            String s1 = HttpClientUtil.doPost("http://12.3.10.205:8089/center/authCode", reqmap);
            JSONObject jsonObject = JSONObject.parseObject(s1);
            String resEncryptData = jsonObject.getString("encryptData");
            String resEncryptKey = jsonObject.getString("encryptKey");
            byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
            byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
            byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
            String respXML = new String(merchantXmlDataBytes, charset);
            System.out.println("返回明文:"+respXML);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static Map getDigit(String firstSn, String lastSn) {
        char[] chars = firstSn.toCharArray();
        char[] charss = lastSn.toCharArray();
        int num = 0;
        for (int i = 1; i <= chars.length; i++) {
            if (chars[i] != charss[i]) {
                num = i;
                break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("num", num);
        map.put("length", chars.length);
        map.put("firstSn", firstSn);
        map.put("lastSn", lastSn);
        return map;
    }
}
