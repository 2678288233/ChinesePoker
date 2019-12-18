package LogicTest;


import entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import services.CardAuditService;
import services.Imp.CardAudit;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardAuditTest {
    @Autowired
    CardAuditService cardAuditService;

    @Test
    public void cardAudit(){
        String[] users=new String[]{"1","2","3"};
        CardAudit cardAudit=cardAuditService.getCardAudit();
        Map<String, List<Card>> mp=cardAudit.getUserCards();
        List<Card> cards=cardAudit.getBaseCard();
        int[] tmp=new int[54];

        Arrays.stream(tmp).forEach((t)->{
            System.out.print(t+" ");
        });
        System.out.println();

        mp.forEach((key, value) -> {
            System.out.println(value.size());value.forEach(
                (card) -> {
                    //System.out.print(card.getInter() + "  ");
                    tmp[card.getInter()]++;
                }

        );
        });
        System.out.println();
        cards.forEach((card -> {//System.out.print(card.getInter()+" ");
            tmp[card.getInter()]++;}));
        System.out.println();

        Arrays.stream(tmp).forEach((t)->{
            System.out.print(t+" ");
        });
    }
    @Test
    public  void TestPara(){
        getPara("ws://localhost/ChinesePoker_war/ws?user=10").forEach((key,val)-> System.out.println(key+" "+val));
    }
    private static Map<String,String> getPara(String uri){

        String para=uri.substring(uri.lastIndexOf('?')+1);
        String[] pari=para.split("&");
        Map<String,String>map=new HashMap<>();
        Arrays.stream(pari).forEach((s -> {
            int index=s.indexOf('=');
            map.put(s.substring(0,index),s.substring(index+1));
        }));
        return map;
    }

class T1{
        int a;
    }
    @Test
    public void test(){
        Map<String,T1> mp1=new HashMap<>();
        Map<String,T1>mp2=new HashMap<>();
        T1 t=new T1();
        t.a=10;
        mp1.put("12",t);
        mp2.put("12",t);
        t.a=20;
        System.out.println(mp1.get("12").a);
        System.out.println(mp2.get("12").a);
    }
}
