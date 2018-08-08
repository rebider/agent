
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
//        System.out.println(AimportServiceImpl.gs.indexOf("对私"));
//        System.out.println("JP00000066-JZ00000351-X00000089".substring(0,"JP00000066-JZ00000351-X00000089".lastIndexOf("-")));
        System.out.println( String.format("%02d",(300%100))+String.format("%04d",(100%10000))+String.format("%02d",86));
    }
}
