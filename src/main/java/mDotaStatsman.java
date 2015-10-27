import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class mDotaStatsman {

    public mDotaStatsman() {}

    public List<cMatch> getMatchHistory(String userID, int num) throws Exception {
        if (num <= 0) num = 1;

        String URL = hGlobals.apiBase + hGlobals.apiGroupMatch + hGlobals.apiGetMatchHistory + hGlobals.apiVersion;
        List<String[]> params = new ArrayList<String[]>(4);
        params.add(new String[] {"key", hGlobals.apiKey});
        params.add(new String[]{"format", "JSON"});
        params.add(new String[]{"account_id", userID});

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
            JSONArray playerArrayJSON = matchJSON.getJSONArray("players");
            List<cPlayer> playerArray = new ArrayList<cPlayer>(playerArrayJSON.length());

            for (int j = 0; j < playerArrayJSON.length(); j++) {
                JSONObject playerJSON = playerArrayJSON.getJSONObject(j);
                cPlayer player = new cPlayer(playerJSON.get("account_id").toString(),
                        playerJSON.get("player_slot").toString(),
                        playerJSON.get("hero_id").toString()
                );
                playerArray.add(player);
            }

            cMatch match = new cMatch(matchJSON.get("match_id").toString(),
                    matchJSON.get("match_seq_num").toString(),
                    matchJSON.get("start_time").toString(),
                    matchJSON.get("lobby_type").toString(),
                    matchJSON.get("radiant_team_id").toString(),
                    matchJSON.get("dire_team_id").toString(),
                    playerArray
            );
            matchArray.add(match);
        }

        return matchArray;
    }

    public List<cMatch> getMatchHistory(String userID, int num, int offsetID) throws Exception {
        if (num <= 0) num = 1;
        if (offsetID <= 0) return getMatchHistory(userID, num);

        String URL = hGlobals.apiBase + hGlobals.apiGroupMatch + hGlobals.apiGetMatchHistory + hGlobals.apiVersion;
        List<String[]> params = new ArrayList<String[]>(4);
        params.add(new String[] {"key", hGlobals.apiKey});
        params.add(new String[]{"format", "JSON"});
        params.add(new String[]{"account_id", userID});

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
            JSONArray playerArrayJSON = matchJSON.getJSONArray("players");
            List<cPlayer> playerArray = new ArrayList<cPlayer>(playerArrayJSON.length());

            for (int j = 0; j < playerArrayJSON.length(); j++) {
                JSONObject playerJSON = playerArrayJSON.getJSONObject(j);
                cPlayer player = new cPlayer(playerJSON.get("account_id").toString(),
                        playerJSON.get("player_slot").toString(),
                        playerJSON.get("hero_id").toString()
                        );
                playerArray.add(player);
            }

            cMatch match = new cMatch(matchJSON.get("match_id").toString(),
                    matchJSON.get("match_seq_num").toString(),
                    matchJSON.get("start_time").toString(),
                    matchJSON.get("lobby_type").toString(),
                    matchJSON.get("radiant_team_id").toString(),
                    matchJSON.get("dire_team_id").toString(),
                    playerArray
                    );
            matchArray.add(match);
        }

        return matchArray;
    }

    public List<cMatch> getMatchHistory(String userID) throws Exception {
        List<cMatch> matchArray = new ArrayList<cMatch>();
        matchArray.addAll(getMatchHistory(userID, 100));

        int offsetid = Integer.valueOf(matchArray.get(matchArray.size()-1).matchID);
        while (true) {
            matchArray.addAll(getMatchHistory(userID, 100, offsetid));
            if (offsetid == Integer.valueOf(matchArray.get(matchArray.size()-1).matchID))
                break;
            else
                offsetid = Integer.valueOf(matchArray.get(matchArray.size()-1).matchID);
        }

        return matchArray;
    }
}
