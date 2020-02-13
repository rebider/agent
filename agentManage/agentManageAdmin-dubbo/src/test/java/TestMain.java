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
            par.put("identification","RDBPOS");
            String pars = par.toJSONString();

            System.out.println("请求明文:"+pars);
            String agent_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnb/YbYzvf+aVfhQHu1u0"+
                    "nSAy1RT2slmUiErvCMiLe21sJroLIZ/X1sdFeOSuTj21ejGYknhu+JbqcozmAG0s"+
                    "JSvgFNh86HXmcaPM46BqjWEgFGE85/RoyGNoK/vanwjZst4ouf7U3jLIupcJtxEU"+
                    "AkxAs0gnut4uP7Nd8VV6fSkxk+rlsAWkWp9Iu6q/ubnOW+RntHwJyt9+Qdfgz0O0"+
                    "DbN0yZqlvMRRMe059X09hSmV5aaWqxeMEx65jV0GVV1OhgQMtNUvTMCFLNWvGnPZ"+
                    "7w4hhy6g0XqY2YDRpMXEKn6EUINwQiu4OTqQO4G7feeeQKbAYnLKZIAwZOqa7twU"+
                    "QQIDAQAB";
            String rdb_prim_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDdGkO0506OOLQP"+
                    "lDDKJUoskFbrntcL8TEmcdx1lKjnT23QI0reL4BL4HRgvf3daL/MpuC4uGpuCKOb"+
                    "4jB75SJOpZzbBinlIwoVx6+jX/VRYDRZndOvh+KoWfIRUicptoghQwJ5nkaEknxd"+
                    "Z8AXjD73KPrHV5UFhn8GjTQ3YaK7UPYpNxQOvgCaRiWJkWmvOzTlieAUayw5Mu5Y"+
                    "k2623lnvYc1+5rMKhLIKITCvPXDmw0WM+qIApAwV7dwbaybFzRs9Yoa6ZKrKb/f8"+
                    "Mrys1SOnQ1Zb/SHyfCMzy9S5ZJdS8eBIWunngoZ2ZzoWouu19klHhq6XUyfRKDFE"+
                    "i7S28jJFAgMBAAECggEANvDY1DIJ/iMu8xQSz5hWhx5SyvfMXgZP/UqbyofnNJsz"+
                    "IV4zs2RAUBp1MLjlRUhLKLJq8tu4bqEs7oZVH+Q/EyXqBS7R6EM9tU3KYiFtFwx9"+
                    "1ar3OA3fD5BSK2Wrn5zg1MsO6WOgCD9SMe+e9vr9hnMjZbdoq1fRZrJdwEk9o6ez"+
                    "Q3Rki9ad498WynDARaImZZZWY80+h145MMfMUqotYaLZZtCSW1lN1y+MGuT+Tm/m"+
                    "GL2BVGtpU3vYOtsWDKBJqYCv/yehqjJxIQER6x8WO3qqB1fKWuVuP9xp8lQNhJbD"+
                    "+Vilgj/NeeQe7HU8BYjbHrHvLIeUIUm6dFCbGa5HAQKBgQD2iTiLfV9LqtGC/yGH"+
                    "KkYQdLs6etSBMLtx8pxKNaxVhSEcOUHCoF5uMD52g5R8ZOjlNNbzW00txzZVTWfR"+
                    "Kf1yUlQtQMMotB+9YvfeKsJdo96lxMwM+2yYIR4c8Pcqj6Mp+gGbqWfjfyWDC/bP"+
                    "8BvKuJ1MA/cDPAqDighIf7tUwQKBgQDllxonSfeaiKm1/mkdMi9qdiwCHCIRWEfw"+
                    "nblwpR+XuuB8TQx/7HUxLwzqREqp2KPNyuLF9bQ3uXPzazlBZbKa0hZT0YLvhEXl"+
                    "T4QMA7NE3VpCbMAnfPeJWtq2Ju+exhnmXDxN9hX8346q0XL1c3+XUwSFEt5fZJ87"+
                    "KR5GFxeqhQKBgQCsAuX+/USmKNLxkU01X5tvE+MmVFk8omHGiT3e0UAikzac2J43"+
                    "S2lXfDW4vum1OTr94TYwqX1Z+WrBbbaHy8JYJ3Uv9cW0+qhCnXDQBAmFCDbqdjrg"+
                    "u/Py2PiHvODXQ0zWlubUUwh3RFlJ6+kTADaD5Pdq+EE6coPrbz2ESArOgQKBgDOw"+
                    "oFfGJHPrXuI1pC7JlLlQX7od4WGsUY4PxKdi1ckQbugtZKoMb5oiMLMxqumwd7im"+
                    "fAX7upZtL3jXZC2gGuUWdaZrXiJU0nO9Qkp+LsdLWr3nxB9f2E9ZYDJwc7R4f25I"+
                    "x975j5u+LV2yCELQvSIxs6vOQriRa/Qi3skC+/YNAoGAFdev2l0gOjoW6pOBaNLt"+
                    "j0fEmMDZteUWJHh3XCCj3SqSRxhF2EKj+/ah2SVnzF7aynsvXp161fER+9A8x3NV"+
                    "TEeGu+t69dOkBs1BtFpexGGyHE4DijuhHb6O7VfGKNu4ZDe9jyqz1YHpihhdxBm/"+
                    "ktN299BPA3Rhg+1Ko1eT+go=";


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
            map.put("identification", "RDBPOS");
            String s = JSONObject.toJSONString(map);

            System.out.println("param值:"+map);

            Map<String, String> reqmap = new HashMap<>();
            reqmap.put("param",s);
            String s1 = HttpClientUtil.doPost("http://localhost:8080/center/authCode", reqmap);

            System.out.println(s1);

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
