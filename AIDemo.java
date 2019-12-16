

import robot.*;

public class AIDemo implements Robot {

    @Override
    public void init(AISeatInfo aiSeatInfo) {

        System.out.println("init");
    }

    @Override
    public boolean isReady() {
        System.out.println("isReady");
        return false;
    }

    @Override
    public void deal(AICard[] aiCards) {

        System.out.println("deal");
    }

    @Override
    public int isGetLord() {
        System.out.println("isGetLord");
        return 0;
    }

    @Override
    public int completeLord(int i) {
        System.out.println("completeLord");
        return 0;
    }

    @Override
    public void lordInfo(AILordInfo aiLordInfo) {
        System.out.println("lordInfo");
    }

    @Override
    public void lordResult(int i) {
        System.out.println("lordResult");
    }

    @Override
    public AICard[] playCards() {
        System.out.println("playcards");
        return new AICard[0];
    }

    @Override
    public void cardsInfo(AICardsInfo aiCardsInfo) {
        System.out.println("cardsInfo");

    }

    @Override
    public void gameResult(int i) {
        System.out.println("gameResult");
    }
}
