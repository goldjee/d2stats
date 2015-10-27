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
            cMatch match = hGlobals.mDS.getMatchStats(matchHistory.get(0));

            System.out.println(match.matchID);
            System.out.println();
            System.out.println("PlayerID\tSlotID\tHeroID\tK\tD\tA");
            for (cPlayer player : match.players) {
                System.out.println(String.valueOf(player.accountID) + "\t" +
                        String.valueOf(player.statsMatch.playerSlot) + "\t" +
                        String.valueOf(player.statsMatch.heroID) + "\t" +
                        String.valueOf(player.statsMatch.kills) + "\t" +
                        String.valueOf(player.statsMatch.deaths) + "\t" +
                        String.valueOf(player.statsMatch.assists));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
