package com.whizpath.matchdata.model;

import lombok.Data;

@Data
public class Player {
    private String playerName;
    private String playerId;
    private int goalCount;
    private int assistCount;
    private int keypass;
    private int dribble;
    private int tackle;
    private String teamName;
    private String inTime;
    private String outTime;
    private String yellowCardTime;
    private String secondYellowCardTime;
    private String redCardTime;
}
