package entity;

public class Card {
    final int value;
    public enum color{
        //红桃、黑桃、方块、梅花
        HEART(0),SPADE(1),DIAMOND(2),PLUM(3);
        final int val;

        color(int val) {
            this.val = val;
        }
    }
    transient private final color[] colors={color.valueOf("HEART"),color.valueOf("SPADE"),color.valueOf("DIAMOND"),color.valueOf("PLUM")};

    public color  getColor(){
        return colors[value%4];
    }
    public int getValue(){
        return value/4;
    }
    public int getInter(){return value;}
    public Card(int value){
        this.value=value;
    }

}
