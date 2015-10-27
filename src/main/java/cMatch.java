import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class cMatch {
    public String matchID;         //the numeric match ID
    public String matchSeqNum;    //the match's sequence number - the order in which matches are recorded
    public String startTime;       //date in UTC seconds since Jan 1, 1970 (unix time format)
    public String lobbyType;       //the type of lobby (https://github.com/kronusme/dota2-api/blob/master/data/lobbies.json)
    public String radiantTeamID;
    public String direTeamID;
    public List<cPlayer> players;   //an array of players

    public cMatch(String match_id, String match_seq_num, String start_time, String lobby_type,
                  String radiant_team_id, String dire_team_id, List<cPlayer> players_) {
        matchID = match_id;
        matchSeqNum = match_seq_num;
        startTime = start_time;
        lobbyType = lobby_type;
        radiantTeamID = radiant_team_id;
        direTeamID = dire_team_id;
        players = players_;
    }
}
