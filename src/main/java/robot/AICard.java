package robot;

public class AICard {
    private final int value;
    public AICard(int value){
        this.value=value;
    }
    public int getColor(){return value%4;}
    public int getRank(){return value/4;}
}
