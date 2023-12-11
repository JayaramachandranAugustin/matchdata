package com.whizpath.matchdata.model;

import lombok.Data;

import java.util.List;
@Data
public class Match {
    private List<Team> teams;
    private List<Player> players;
    private String matchId;
    private String matchType;
    private long matchAttendance;
    private String groundName;
    private String venueCity;
    private String venueState;
    private String venueCountry;
    private String matchDateTime;
    private String result;
    private int firstHalfExtraTime;
    private int secondHalfExtraTime;
}
