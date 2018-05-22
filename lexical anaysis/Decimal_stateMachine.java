import java.util.*;

public class Decimal_stateMachine extends stateMachine {

    Decimal_stateMachine(){};

    public void init(){

        /*c  isAccept数组下个状态是否为接受状态  */
        isAccept.put(1,false);
        isAccept.put(2,false);
        isAccept.put(3,false);
        isAccept.put(4,false);
        isAccept.put(5,false);
        isAccept.put(6,true);

        /*c  将数字存入relation中并同时将触发数字与起始状态存入哈希表中  */
        for(int i = 0; i < 10; i++){
            Relation relation1 = new Relation(1,digit[i]);
            Relation relation2 = new Relation(2,digit[i]);
            Relation relation3 = new Relation(3,digit[i]);
            Relation relation4 = new Relation(4,digit[i]);
            Relation relation5 = new Relation(5,digit[i]);

            //System.out.println(relation1.hashCode());
            //System.out.println(relation2.hashCode());
            //System.out.println(relation3.hashCode());
            //System.out.println(relation4.hashCode());
            //System.out.println(relation5.hashCode());

            Relations.add(relation1);
            Relations.add(relation2);
            Relations.add(relation3);
            Relations.add(relation4);
            Relations.add(relation5);

            mp.put(relation1,3);
            mp.put(relation2,3);
            mp.put(relation3,3);
            mp.put(relation4,5);
            mp.put(relation5,5);
        }

        Relation relation1 = new Relation(1,'+');
        Relation relation2 = new Relation(1,'-');
        Relation relation3 = new Relation(3,'.');


        Relations.add(relation1);
        Relations.add(relation2);
        Relations.add(relation3);

        mp.put(relation1,2);
        mp.put(relation2,2);
        mp.put(relation3,4);

        }

        Result Decimal_recognize(String str){

            int state = 1;          //初始状态为1
            int i = 0;
            String res = "failed!";
            char[] curChlist = str.toCharArray();
            char curCh = curChlist[i];        //curCh 存入当前读取字符

            while (!isAccept.get(state)   && i < str.length())   //状态条件判定
            {

                Relation relation = new Relation(state,curCh);

                try{
                    state = mp.get(relation);
                    //System.out.println(state);
                }catch (Exception e){
                    // System.out.println("55");
                }

                if (!mp.containsKey(relation))    //判定中没有relation该键则state取0，进入终止状态
                {
                    state = 6;
                    break;
                }
                if(i == str.length()-1) {
                    i++;
                    break;
                }

                curCh = curChlist[++i];
            }
            res = str.substring(0,i);             //截取单词
            Result output = new Result();    //输出单词类型，长度，以及单词本身


            if(i == 0 ){
                //截掉一个错误字符
                output.setSTYLE(ILLEGAL);
                output.setStr_len(1);
                output.setOpt_str(str.substring(0,1));

            }
            else{

                    output.setOpt_str(res);
                    output.setSTYLE(DECIMAL);
                    output.setStr_len(i);

            }
            return output;


        }
}
