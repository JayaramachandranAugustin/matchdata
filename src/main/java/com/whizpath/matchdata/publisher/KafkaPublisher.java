package com.whizpath.matchdata.publisher;

import com.whizpath.matchdata.model.Match;
import com.whizpath.matchstream.generated.MatchScore;
import com.whizpath.matchstream.generated.MatchType;
import com.whizpath.matchstream.generated.Player;
import com.whizpath.matchstream.generated.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaPublisher {
    private final KafkaTemplate<String, GenericRecord> kafkaTemplate;

    public void produceKafkaMessage(String topicName, Match match){
        MatchScore matchScore=new MatchScore();
        matchScore.setMatchId(match.getMatchId());
        matchScore.setMatchType(MatchType.valueOf(match.getMatchType()));
        matchScore.setMatchAttendance(match.getMatchAttendance());
        matchScore.setGroundName(match.getGroundName());
        matchScore.setVenueCity(match.getVenueCity());
        matchScore.setVenueState(match.getVenueState());
        matchScore.setVenueCountry(match.getVenueCountry());
        matchScore.setMatchDateTime(match.getMatchDateTime());
        matchScore.setResult(match.getResult());
        matchScore.setFirstHalfExtraTime(match.getFirstHalfExtraTime());
        matchScore.setSecondHalfExtraTime(match.getSecondHalfExtraTime());
        matchScore.setTeams(mapTeams(match.getTeams()));
        matchScore.setPlayers(mapPlayers(match.getPlayers()));
        CompletableFuture<SendResult<String,GenericRecord>> futureResult= kafkaTemplate.send(topicName,match.getMatchId(), matchScore);

        futureResult.whenComplete((result,ex)->{
            if(ex == null){
                log.info("message send successfully to Kafka topic - {}, partition - {} offset- {}",
                        result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            }else{
                log.error("Error sending message to the topic -{}, e- {}",topicName,ex);
            }
        });
    }

    private List<Player> mapPlayers(List<com.whizpath.matchdata.model.Player> playerList){
        return playerList.stream().map((value)-> new Player(value.getPlayerName(), value.getPlayerId(),value.getGoalCount(),value.getAssistCount(),value.getKeypass(),value.getDribble(),value.getTackle(),
                value.getTeamName(),value.getInTime(),value.getOutTime(),value.getYellowCardTime(),value.getSecondYellowCardTime(),value.getRedCardTime())).collect(Collectors.toList());

    }
    private List<Team> mapTeams(List<com.whizpath.matchdata.model.Team> teamList){
        return teamList.stream().map((value)-> new Team(value.getTeamName(), value.getTeamId(),value.getTeamGoal(),value.isHome(),value.getYellowCardCount(),value.getRedCardCount())).collect(Collectors.toList());

    }
}
