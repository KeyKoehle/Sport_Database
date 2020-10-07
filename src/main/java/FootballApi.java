import Models.Spiel;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class FootballApi {

    private Gson getSpiel(int team_id, int league_id) throws UnirestException {
        Gson gson = new Gson();
        //https://api-football-v1.p.rapidapi.com/v2/fixtures/team/%7Bteam_id%7D/%7Bleague_id%7D
        HttpResponse<JsonNode> response = Unirest.post("https://api-football-v1.p.rapidapi.com/v2/fixtures/team/%7B"+ team_id +
                "%7D/%7B"+league_id + "%7D")
                .header("X-RapidAPI-Key","apikey")
                .asJson();
        gson.fromJson(String.valueOf(response), Spiel.class);
        System.out.println(gson.toString());
        return gson;
    }


}
