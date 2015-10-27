import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class cMatch {
    public long matchID;            //the numeric match ID
    public long matchSeqNum;        //the match's sequence number - the order in which matches are recorded
    public long startTime;          //date in UTC seconds since Jan 1, 1970 (unix time format)
    public int lobbyType;           //the type of lobby (https://github.com/kronusme/dota2-api/blob/master/data/lobbies.json)
    public long radiantTeamID;
    public long direTeamID;
    public List<cPlayer> players;   //an array of players

    public boolean radiantWin;
    public long duration;
    public int towerStatusRadiant;
    public int towerStatusDire;
    public int barracksStatusRadiant;
    public int barracksStatusDire;
    public int cluster;
    public long firstBloodTime;
    public int humanPlayers;
    public long leagueID;
    public int positiveVotes;
    public int negativeVotes;
    public int gameMode;
    public int engine;


    public cMatch(long match_id) {
        matchID = match_id;
//        matchSeqNum = match_seq_num;
//        startTime = start_time;
//        lobbyType = lobby_type;
//        radiantTeamID = radiant_team_id;
//        direTeamID = dire_team_id;
//        players = players_;
    }
}
