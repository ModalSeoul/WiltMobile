package fm.wilt.mobile.jwilt;

import java.io.IOException;

import org.json.simple.*;


public class Api {

    public String apiToken;
    private static Api instance = null;
    protected String format;
    protected Http http = new Http("https://wilt.fm/api/");

    protected Api() {}

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    /**
     * Returns the JSONOBject of a given member
     * @param nick
     * @return Member JSONObject
     */
    public JSONObject getMember(String nick) {
        return http.apiGet(String.format("members/%s/by_nick/", nick));
    }

    /**
     * Returns a JSONObject containing the leaderboard
     * @return Leaderboard JSONObject
     */
    public JSONObject getLeaderboard() {
        return http.apiGet("members/most_scrobbles/?leaderboard=true");
    }

    /**
     * Logs a user in and sets apiToken
     * to authorization header
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        String payload = String.format("username=%s&password=%s", username, password);
        try {
            JSONObject tokenResponse = http.post("api-token-auth/", payload, "");
            this.apiToken = "Token " + (String) tokenResponse.get("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Posts a scrobble to the backend with
     * the current token held stored in apiToken
     * @param artist
     * @param song
     * @param album
     */
    public JSONObject postScrobble(String artist, String song, String... album) {
        if (artist.length() > 0 && song.length() > 0) {
            String payload = String.format("song=%s&artist=%s", artist, song);
            if (album != null)
                payload.concat("&album=" + album);

            try {
                return http.post("scrobbles/", payload, apiToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns true or false depending on
     * whether or not the user has been authenticated
     * @return boolean
     */
    public boolean isAuthed() {
        if (apiToken.length() > 5)
            return true;
        else
            return false;
    }

}