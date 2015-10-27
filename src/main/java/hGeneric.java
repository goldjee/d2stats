import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class hGeneric {
    public static String fastConcat(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }
}
