import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class mainclass {

    public static void main(String[] args) {

        String userID = "76561198009583879";

        List<cMatch> matchHistory = new ArrayList<cMatch>();
        try {
            matchHistory = hGlobals.mDS.getMatchHistory(userID);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(matchHistory.get(0).players.get(0).accountID);
    }
}
