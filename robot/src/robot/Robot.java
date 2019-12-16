package robot;

public interface Robot {
    /**
     *
     * @param seatInfo AI座位信息
     */
    void init(AISeatInfo seatInfo);


    boolean isReady();
    void deal(AICard[] cards);
    int isGetLord();
    int completeLord(int curScore);
    void lordInfo(AILordInfo lordInfo);

    /**
     *
     * @param status 1:lord 0:peasant
     */
    void lordResult(int status);

    /**
     *
     * @return null represents pass
     */
    AICard[] playCards();
    void cardsInfo(AICardsInfo cardsInfo);

    /**
     *
     * @param res 1:lose 0:win
     */
    void gameResult(int res);
}
