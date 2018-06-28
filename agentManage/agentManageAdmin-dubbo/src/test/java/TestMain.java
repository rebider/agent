
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
//        System.out.println(AimportServiceImpl.gs.indexOf("对私"));
//        System.out.println("JP00000066-JZ00000351-X00000089".substring(0,"JP00000066-JZ00000351-X00000089".lastIndexOf("-")));
        System.out.println(Pattern.matches("south.*", "south123"));
    }
}
