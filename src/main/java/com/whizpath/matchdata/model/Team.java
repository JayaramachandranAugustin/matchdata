package com.whizpath.matchdata.model;

import lombok.Data;

@Data
public class Team {
    private String teamName;
    private String teamId;
    private int teamGoal;
    private boolean isHome;
    private int yellowCardCount;
    private int redCardCount;
}
