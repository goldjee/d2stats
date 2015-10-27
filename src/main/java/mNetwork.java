import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import java.util.List;

/**
 * Created by Ins on 27.10.2015.
 */
public class mNetwork {

    public static String prepareParamString(List<String[]> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i)[0]);
            sb.append("=");
            sb.append(params.get(i)[1]);

            if (i != params.size()-1) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    public static String sendGET(String URL, List<String[]> params) throws Exception {
        String p = prepareParamString(params);
        String rURL = URL + p;
//        System.out.println(rURL);

        HttpResponse<String> res = Unirest.get(rURL)
                .asString();

        if(res.getStatus() != 200) {
            throw new Exception("Request failed. HTTP code " + res.getStatus());
        }
        else {
            return res.getBody();
        }
    }
}
