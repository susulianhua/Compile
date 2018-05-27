import java.util.*;

public class Identifier_stateMachine extends stateMachine{

    Identifier_stateMachine(){}

       /*  状态机声明                        */
    public void init(){

        isAccept.put(1,false);
        isAccept.put(2,false);
        isAccept.put(3,false);
        isAccept.put(4,true);

        /* 将字母表与当前状态存入relation中   */
        for(int i = 0; i < 26; i++){
            Relation relation1 = new Relation(1,lower_letter_list.charAt(i));
            Relation relation2 = new Relation(1,upper_letter_list.charAt(i));
            Relation relation3 = new Relation(2,lower_letter_list.charAt(i));
            Relation relation4 = new Relation(2,upper_letter_list.charAt(i));
            Relation relation5 = new Relation(3,lower_letter_list.charAt(i));
            Relation relation6 = new Relation(3,upper_letter_list.charAt(i));

            mp.put(relation1,2);
            mp.put(relation2,2);
            mp.put(relation3,2);
            mp.put(relation4,2);
            mp.put(relation5,2);
            mp.put(relation6,2);
        }

        /* 将digit与当前状态绑定存入relation  */
        for(int i = 0; i < 10; i++){
            Relation relation7 = new Relation(2,digit_list.charAt(i));
            Relation relation8 = new Relation(3,digit_list.charAt(i));

            mp.put(relation7,2);
            mp.put(relation8,2);
        }

        /* 将underlinerelation存入mp        */
           Relation relation9 = new Relation(2,'_');
           mp.put(relation9,3);
    }

        /* Identifier_recognize状态机       */
    public Result Identifier_recognize(String str){
        int state = 1;
        int i = 0;
        char chcur = str.charAt(i);

        while(true){
            Relation relation = new Relation(state,chcur);
            //mp中无此映射关系 上一个状态为可结束状态 如re::  feature# 截取re  feature
            if(!mp.containsKey(relation) && state == 2){
                break;
            }
            //mp中无此映射关系 上一个状态不为可结束状态 如re_::  截取re_
            if(!mp.containsKey(relation) && state != 2){
                state = 4;
                break;
            }
            try {
                state = mp.get(relation);
            }catch (Exception e){

            }
            //解析成功
            if(i == str.length() - 1 && state == 2){
                i++;
                break;
            }
            //如feature_
            if (i == str.length() - 1 && state != 2){
                state = 4;
                break;
            }
            chcur = str.charAt(++i);
        }
            //截取字符串，正确的style赋值IDDENTIFIER_LETTER 反之ILLEGAL
        String res = str.substring(0,i);
        Result output = new Result("lilijie",i,res);
        if( state == 2){
            output.setSTYLE(IDENTIFIER_LETTER);
        }
        else{
            output.setSTYLE(ILLEGAL);
        }
        return output;
    }
}
