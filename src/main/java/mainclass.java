import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class mainclass {

    public static void main(String[] args) {

        long userID = 76561198009583879L;

        List<cMatch> matchHistory = new ArrayList<cMatch>();
        try {
            matchHistory = hGlobals.mDS.getMatchHistory(userID, 1);
            System.out.println(matchHistory.get(0).matchID);
            hGlobals.mDS.getMatchStats(matchHistory.get(0).matchID);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
