import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class mDotaStatsman {

    public mDotaStatsman() {}

    public List<cMatch> getMatchHistory(long userID, int num) throws Exception {
        if (num <= 0) num = 1;

        String URL = hGlobals.apiBase + hGlobals.apiGroupMatch + hGlobals.apiGetMatchHistory + hGlobals.apiVersion;
        List<String[]> params = new ArrayList<String[]>(4);
        params.add(new String[]{"key", hGlobals.apiKey});
        params.add(new String[]{"format", "JSON"});
        params.add(new String[]{"account_id", String.valueOf(userID)});

        params.add(new String[]{"matches_requested", String.valueOf(num)});

        String history = mNetwork.sendGET(URL, params);
//        System.out.println(history);

        JSONObject historyJSON = new JSONObject(history);
        if (!historyJSON.has("result")) {
            throw new Exception("Inadequate server response");
        }

        JSONArray matchArrayJSON = historyJSON.getJSONObject("result").getJSONArray("matches");
        List<cMatch> matchArray = new ArrayList<cMatch>(matchArrayJSON.length());
        for (int i = 0; i < matchArrayJSON.length(); i++) {
            JSONObject matchJSON = matchArrayJSON.getJSONObject(i);
//            JSONArray playerArrayJSON = matchJSON.getJSONArray("players");
//            List<cPlayer> playerArray = new ArrayList<cPlayer>(playerArrayJSON.length());
//
//            for (int j = 0; j < playerArrayJSON.length(); j++) {
//                JSONObject playerJSON = playerArrayJSON.getJSONObject(j);
//                cPlayer player = new cPlayer(playerJSON.getLong("account_id"));
//                playerArray.add(player);
//            }

            cMatch match = new cMatch(matchJSON.getLong("match_id"));
            matchArray.add(match);
        }

        return matchArray;
    }

    public List<cMatch> getMatchHistory(long userID, int num, long offsetID) throws Exception {
        if (num <= 0) num = 1;
        if (offsetID <= 0) return getMatchHistory(userID, num);

        String URL = hGlobals.apiBase + hGlobals.apiGroupMatch + hGlobals.apiGetMatchHistory + hGlobals.apiVersion;
        List<String[]> params = new ArrayList<String[]>(4);
        params.add(new String[]{"key", hGlobals.apiKey});
        params.add(new String[]{"format", "JSON"});
        params.add(new String[]{"account_id", String.valueOf(userID)});

        params.add(new String[]{"matches_requested", String.valueOf(num)});
        params.add(new String[]{"start_at_match_id", String.valueOf(offsetID)});

        String history = mNetwork.sendGET(URL, params);
//        System.out.println(history);

        JSONObject historyJSON = new JSONObject(history);
        if (!historyJSON.has("result")) {
            throw new Exception("Inadequate server response");
        }

        JSONArray matchArrayJSON = historyJSON.getJSONObject("result").getJSONArray("matches");
        List<cMatch> matchArray = new ArrayList<cMatch>(matchArrayJSON.length());
        for (int i = 0; i < matchArrayJSON.length(); i++) {
            JSONObject matchJSON = matchArrayJSON.getJSONObject(i);
//            JSONArray playerArrayJSON = matchJSON.getJSONArray("players");
//            List<cPlayer> playerArray = new ArrayList<cPlayer>(playerArrayJSON.length());
//
//            for (int j = 0; j < playerArrayJSON.length(); j++) {
//                JSONObject playerJSON = playerArrayJSON.getJSONObject(j);
//                cPlayer player = new cPlayer(Long.valueOf(playerJSON.get("account_id").toString()));
//                playerArray.add(player);
//            }

            cMatch match = new cMatch(matchJSON.getLong("match_id"));
            matchArray.add(match);
        }

        return matchArray;
    }

    public List<cMatch> getMatchHistory(long userID) throws Exception {
        List<cMatch> matchArray = new ArrayList<cMatch>();
        matchArray.addAll(getMatchHistory(userID, 100));

        long offsetid = matchArray.get(matchArray.size()-1).matchID;
        while (true) {
            matchArray.addAll(getMatchHistory(userID, 100, offsetid));
            if (offsetid == matchArray.get(matchArray.size()-1).matchID)
                break;
            else
                offsetid = matchArray.get(matchArray.size()-1).matchID;
        }

        return matchArray;
    }

    public cMatch getMatchStats(cMatch match) throws Exception {
        String URL = hGlobals.apiBase + hGlobals.apiGroupMatch + hGlobals.apiGetMatchDetails + hGlobals.apiVersion;
        List<String[]> params = new ArrayList<String[]>(4);
        params.add(new String[] {"key", hGlobals.apiKey});
        params.add(new String[]{"format", "JSON"});
        params.add(new String[]{"match_id", String.valueOf(match.matchID)});

        String stats = mNetwork.sendGET(URL, params);
//        System.out.println(stats);

        JSONObject statsJSON = new JSONObject(stats);
        if (!statsJSON.has("result")) {
            throw new Exception("Inadequate server response");
        }

        statsJSON = statsJSON.getJSONObject("result");

        match.radiantWin = statsJSON.getBoolean("radiant_win");
        match.duration = statsJSON.getLong("duration");
        match.startTime = statsJSON.getLong("start_time");
        match.matchSeqNum = statsJSON.getLong("match_seq_num");
        match.towerStatusRadiant = statsJSON.getInt("tower_status_radiant");
        match.towerStatusDire = statsJSON.getInt("tower_status_dire");
        match.barracksStatusRadiant = statsJSON.getInt("barracks_status_radiant");
        match.barracksStatusDire = statsJSON.getInt("barracks_status_dire");
        match.cluster = statsJSON.getInt("cluster");
        match.firstBloodTime = statsJSON.getLong("first_blood_time");
        match.lobbyType = statsJSON.getInt("lobby_type");
        match.humanPlayers = statsJSON.getInt("human_players");
        match.leagueID = statsJSON.getLong("leagueid");
        match.positiveVotes = statsJSON.getInt("positive_votes");
        match.negativeVotes = statsJSON.getInt("negative_votes");
        match.gameMode = statsJSON.getInt("game_mode");
        match.engine = statsJSON.getInt("engine");

        JSONArray playersArrayJSON = statsJSON.getJSONArray("players");
        List<cPlayer> playersArray = new ArrayList<cPlayer>(playersArrayJSON.length());
        for (int i = 0; i < playersArrayJSON.length(); i++) {
            JSONObject playerJSON = playersArrayJSON.getJSONObject(i);
            cPlayer player = new cPlayer(playerJSON.getLong("account_id"));

            cStats playerStats = new cStats();
            playerStats.playerSlot = playerJSON.getInt("player_slot");
            playerStats.heroID = playerJSON.getInt("hero_id");
            playerStats.item0 = playerJSON.getInt("item_0");
            playerStats.item1 = playerJSON.getInt("item_1");
            playerStats.item2 = playerJSON.getInt("item_2");
            playerStats.item3 = playerJSON.getInt("item_3");
            playerStats.item4 = playerJSON.getInt("item_4");
            playerStats.item5 = playerJSON.getInt("item_5");
            playerStats.kills = playerJSON.getInt("kills");
            playerStats.deaths = playerJSON.getInt("deaths");
            playerStats.assists = playerJSON.getInt("assists");
            playerStats.leaverStatus = playerJSON.getInt("leaver_status");
            playerStats.gold = playerJSON.getInt("gold");
            playerStats.lastHits = playerJSON.getInt("last_hits");
            playerStats.denies = playerJSON.getInt("denies");
            playerStats.goldPerMin = playerJSON.getInt("gold_per_min");
            playerStats.xpPerMin = playerJSON.getInt("xp_per_min");
            playerStats.goldSpent = playerJSON.getInt("gold_spent");
            playerStats.heroDamage = playerJSON.getInt("hero_damage");
            playerStats.towerDamage = playerJSON.getInt("tower_damage");
            playerStats.heroHealing = playerJSON.getInt("hero_healing");
            playerStats.level = playerJSON.getInt("level");

            player.statsMatch = playerStats;

            playersArray.add(player);
        }
        match.players = playersArray;

        return match;
    }
}
