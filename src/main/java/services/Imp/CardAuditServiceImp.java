package services.Imp;

import dao.GameReplayRecordDao;
import entity.Card;
import entity.GameReplayRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.CardAuditService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardAuditServiceImp implements CardAuditService {

    private GameReplayRecordDao gameReplayRecordDao;


    @Autowired
    public void setGameReplayRecordDao(GameReplayRecordDao gameReplayRecordDao) {
        this.gameReplayRecordDao = gameReplayRecordDao;
    }



    @Override
    public CardAudit getCardAudit() {
        return new CardAudit(gameReplayRecordDao);
    }
}
