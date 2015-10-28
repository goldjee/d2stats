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
            matchHistory = hGlobals.mDS.getMatchHistory(userID, 10);
            System.out.println(matchHistory.size());
            for (int i = 0; i < matchHistory.size(); i++) {
                cMatch match = matchHistory.get(i);
                match = hGlobals.mDS.getMatchStats(match);
                matchHistory.set(i, match);
            }
            hGlobals.mE.exportMatchToHTML(matchHistory);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
