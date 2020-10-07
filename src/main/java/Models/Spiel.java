
package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Spiel {

    @SerializedName("api")
    @Expose
    private Api api;

    /**
     * No args constructor for use in serialization
     *
     */
    public Spiel() {
    }

    /**
     *
     * @param api
     */
    public Spiel(Api api) {
        super();
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public static class Api {

        @SerializedName("results")
        @Expose
        private Integer results;
        @SerializedName("fixtures")
        @Expose
        private List<Fixture> fixtures = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Api() {
        }

        /**
         *
         * @param results
         * @param fixtures
         */
        public Api(Integer results, List<Fixture> fixtures) {
            super();
            this.results = results;
            this.fixtures = fixtures;
        }

        public Integer getResults() {
            return results;
        }

        public void setResults(Integer results) {
            this.results = results;
        }

        public List<Fixture> getFixtures() {
            return fixtures;
        }

        public void setFixtures(List<Fixture> fixtures) {
            this.fixtures = fixtures;
        }

    }

    public static class AwayTeam {

        @SerializedName("team_id")
        @Expose
        private Integer teamId;
        @SerializedName("team_name")
        @Expose
        private String teamName;
        @SerializedName("logo")
        @Expose
        private String logo;

        /**
         * No args constructor for use in serialization
         *
         */
        public AwayTeam() {
        }

        /**
         *
         * @param teamName
         * @param teamId
         * @param logo
         */
        public AwayTeam(Integer teamId, String teamName, String logo) {
            super();
            this.teamId = teamId;
            this.teamName = teamName;
            this.logo = logo;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

    }

    public static class Fixture {

        @SerializedName("fixture_id")
        @Expose
        private Integer fixtureId;
        @SerializedName("league_id")
        @Expose
        private Integer leagueId;
        @SerializedName("league")
        @Expose
        private League league;
        @SerializedName("event_date")
        @Expose
        private String eventDate;
        @SerializedName("event_timestamp")
        @Expose
        private Integer eventTimestamp;
        @SerializedName("firstHalfStart")
        @Expose
        private Integer firstHalfStart;
        @SerializedName("secondHalfStart")
        @Expose
        private Integer secondHalfStart;
        @SerializedName("round")
        @Expose
        private String round;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("statusShort")
        @Expose
        private String statusShort;
        @SerializedName("elapsed")
        @Expose
        private Integer elapsed;
        @SerializedName("venue")
        @Expose
        private String venue;
        @SerializedName("referee")
        @Expose
        private Object referee;
        @SerializedName("homeTeam")
        @Expose
        private HomeTeam homeTeam;
        @SerializedName("awayTeam")
        @Expose
        private AwayTeam awayTeam;
        @SerializedName("goalsHomeTeam")
        @Expose
        private Integer goalsHomeTeam;
        @SerializedName("goalsAwayTeam")
        @Expose
        private Integer goalsAwayTeam;
        @SerializedName("score")
        @Expose
        private Score score;

        /**
         * No args constructor for use in serialization
         *
         */
        public Fixture() {
        }

        /**
         *
         * @param venue
         * @param goalsHomeTeam
         * @param goalsAwayTeam
         * @param awayTeam
         * @param fixtureId
         * @param league
         * @param referee
         * @param elapsed
         * @param score
         * @param round
         * @param leagueId
         * @param statusShort
         * @param homeTeam
         * @param secondHalfStart
         * @param firstHalfStart
         * @param eventDate
         * @param eventTimestamp
         * @param status
         */
        public Fixture(Integer fixtureId, Integer leagueId, League league, String eventDate, Integer eventTimestamp, Integer firstHalfStart, Integer secondHalfStart, String round, String status, String statusShort, Integer elapsed, String venue, Object referee, HomeTeam homeTeam, AwayTeam awayTeam, Integer goalsHomeTeam, Integer goalsAwayTeam, Score score) {
            super();
            this.fixtureId = fixtureId;
            this.leagueId = leagueId;
            this.league = league;
            this.eventDate = eventDate;
            this.eventTimestamp = eventTimestamp;
            this.firstHalfStart = firstHalfStart;
            this.secondHalfStart = secondHalfStart;
            this.round = round;
            this.status = status;
            this.statusShort = statusShort;
            this.elapsed = elapsed;
            this.venue = venue;
            this.referee = referee;
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.goalsHomeTeam = goalsHomeTeam;
            this.goalsAwayTeam = goalsAwayTeam;
            this.score = score;
        }

        public Integer getFixtureId() {
            return fixtureId;
        }

        public void setFixtureId(Integer fixtureId) {
            this.fixtureId = fixtureId;
        }

        public Integer getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(Integer leagueId) {
            this.leagueId = leagueId;
        }

        public League getLeague() {
            return league;
        }

        public void setLeague(League league) {
            this.league = league;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public Integer getEventTimestamp() {
            return eventTimestamp;
        }

        public void setEventTimestamp(Integer eventTimestamp) {
            this.eventTimestamp = eventTimestamp;
        }

        public Integer getFirstHalfStart() {
            return firstHalfStart;
        }

        public void setFirstHalfStart(Integer firstHalfStart) {
            this.firstHalfStart = firstHalfStart;
        }

        public Integer getSecondHalfStart() {
            return secondHalfStart;
        }

        public void setSecondHalfStart(Integer secondHalfStart) {
            this.secondHalfStart = secondHalfStart;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusShort() {
            return statusShort;
        }

        public void setStatusShort(String statusShort) {
            this.statusShort = statusShort;
        }

        public Integer getElapsed() {
            return elapsed;
        }

        public void setElapsed(Integer elapsed) {
            this.elapsed = elapsed;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public Object getReferee() {
            return referee;
        }

        public void setReferee(Object referee) {
            this.referee = referee;
        }

        public HomeTeam getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(HomeTeam homeTeam) {
            this.homeTeam = homeTeam;
        }

        public AwayTeam getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(AwayTeam awayTeam) {
            this.awayTeam = awayTeam;
        }

        public Integer getGoalsHomeTeam() {
            return goalsHomeTeam;
        }

        public void setGoalsHomeTeam(Integer goalsHomeTeam) {
            this.goalsHomeTeam = goalsHomeTeam;
        }

        public Integer getGoalsAwayTeam() {
            return goalsAwayTeam;
        }

        public void setGoalsAwayTeam(Integer goalsAwayTeam) {
            this.goalsAwayTeam = goalsAwayTeam;
        }

        public Score getScore() {
            return score;
        }

        public void setScore(Score score) {
            this.score = score;
        }

    }

    public static class HomeTeam {

        @SerializedName("team_id")
        @Expose
        private Integer teamId;
        @SerializedName("team_name")
        @Expose
        private String teamName;
        @SerializedName("logo")
        @Expose
        private String logo;

        /**
         * No args constructor for use in serialization
         *
         */
        public HomeTeam() {
        }

        /**
         *
         * @param teamName
         * @param teamId
         * @param logo
         */
        public HomeTeam(Integer teamId, String teamName, String logo) {
            super();
            this.teamId = teamId;
            this.teamName = teamName;
            this.logo = logo;
        }

        public Integer getTeamId() {
            return teamId;
        }

        public void setTeamId(Integer teamId) {
            this.teamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

    }

    public static class League {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("flag")
        @Expose
        private String flag;

        /**
         * No args constructor for use in serialization
         *
         */
        public League() {
        }

        /**
         *
         * @param country
         * @param flag
         * @param name
         * @param logo
         */
        public League(String name, String country, String logo, String flag) {
            super();
            this.name = name;
            this.country = country;
            this.logo = logo;
            this.flag = flag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

    }

    public static class Score {

        @SerializedName("halftime")
        @Expose
        private String halftime;
        @SerializedName("fulltime")
        @Expose
        private String fulltime;
        @SerializedName("extratime")
        @Expose
        private Object extratime;
        @SerializedName("penalty")
        @Expose
        private Object penalty;

        /**
         * No args constructor for use in serialization
         *
         */
        public Score() {
        }

        /**
         *
         * @param halftime
         * @param penalty
         * @param fulltime
         * @param extratime
         */
        public Score(String halftime, String fulltime, Object extratime, Object penalty) {
            super();
            this.halftime = halftime;
            this.fulltime = fulltime;
            this.extratime = extratime;
            this.penalty = penalty;
        }

        public String getHalftime() {
            return halftime;
        }

        public void setHalftime(String halftime) {
            this.halftime = halftime;
        }

        public String getFulltime() {
            return fulltime;
        }

        public void setFulltime(String fulltime) {
            this.fulltime = fulltime;
        }

        public Object getExtratime() {
            return extratime;
        }

        public void setExtratime(Object extratime) {
            this.extratime = extratime;
        }

        public Object getPenalty() {
            return penalty;
        }

        public void setPenalty(Object penalty) {
            this.penalty = penalty;
        }

    }
}
