import java.util.*;

public class Decimal_stateMachine extends stateMachine{

    Decimal_stateMachine(){}

    //小数状态机声明
    public void init(){

        isAccept.put(1,false);
        isAccept.put(2, false);
        isAccept.put(3,false);
        isAccept.put(4,false);
        isAccept.put(5,false);
        isAccept.put(6,true);

        //     当前状态与digit存入relation中并构建映射关系
        for(int i = 0; i < 10; i++){
            Relation relation1 = new Relation(1,digit_list.charAt(i));
            Relation relation2 = new Relation(2,digit_list.charAt(i));
            Relation relation3 = new Relation(3,digit_list.charAt(i));
            Relation relation4 = new Relation(4,digit_list.charAt(i));
            Relation relation5 = new Relation(5,digit_list.charAt(i));

            mp.put(relation1,3);
            mp.put(relation2,3);
            mp.put(relation3,3);
            mp.put(relation4,5);
            mp.put(relation5,5);
        }
        //    三个符号的映射关系存入哈希表中
        Relation relation1 = new Relation(1,'+');
        Relation relation2 = new Relation(1,'-');
        Relation relation3 = new Relation(3,'.');

        mp.put(relation1,2);
        mp.put(relation2,2);
        mp.put(relation3,4);
    }

    public Result Decimal_recognize(String str){
        int state = 1;
        int i = 0;
        char curCh = str.charAt(i);

        while(true){
            Relation relation = new Relation(state,curCh);

            // mp中无此映射关系 上一个状态为可结束状态 如+50.0;
            if(!mp.containsKey(relation) && state == 5){
                break;
            }
            // mp中无此映射关系 上一个状态为不可结束状态 如+50.;
            if(!mp.containsKey(relation) && state!= 5){
                state = 6;
                break;
            }
            //  解析成功  如+50.0
            if(i == str.length() -1 && mp.get(relation) == 5){
                i++;
                state = 5;
                break;
            }
            //  如 50.
            if(i == str.length() - 1 && mp.get(relation) != 5){
                state = 6;
                i++;
                break;
            }
            state = mp.get(relation);
            curCh = str.charAt(++i);

        }
        if(i == 0) {
            i = 1;
        }
        String res = str.substring(0,i);
        Result output = new Result("lilijie", i,res);

        if(state == 5){
            output.setSTYLE(DECIMAL);
        }
        else{
            output.setSTYLE(ILLEGAL);
        }
        return output;
    }
}