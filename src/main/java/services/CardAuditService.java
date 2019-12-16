package services;

import entity.Card;
import entity.GameReplayRecord;
import services.Imp.CardAudit;

import java.util.List;

public interface CardAuditService {
    //void play(String gameID,String userID, Card[] cards);
    //List<GameReplayRecord> getRelayRecords(String gameID);
    CardAudit getCardAudit();
}
