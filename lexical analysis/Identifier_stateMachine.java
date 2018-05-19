import java.util.*;

public class Identifier_stateMachine extends stateMachine{

    Identifier_stateMachine(){}

    /*c  状态机声明  */
    public  void init(){
        /*c  isAccept数组下个状态是否为接受状态  */
        isAccept.put(1,false);
        isAccept.put(2,false);
        isAccept.put(3,false);
        isAccept.put(4,true);

        /*  将字母表存入relation中，并map存储映射关系  */
        for(int i = 0; i < 26; i++){
            Relation relation1 = new Relation(1,lower_letter_list[i]);
            Relation relation2 = new Relation(1,upper_letter_list[i]);
            Relation relation3 = new Relation(2,lower_letter_list[i]);
            Relation relation4 = new Relation(2,upper_letter_list[i]);
            Relation relation5 = new Relation(3,lower_letter_list[i]);
            Relation relation6 = new Relation(3,upper_letter_list[i]);

            //System.out.println("out: " + relation1.hashCode());
            //System.out.println("out: " + relation2.hashCode());
            //System.out.println("out: " + relation3.hashCode());
            //System.out.println("out: " + upper_letter_list[i]);

            Relations.add(relation1);
            Relations.add(relation2);
            Relations.add(relation3);
            Relations.add(relation4);
            Relations.add(relation5);
            Relations.add(relation6);

            mp.put(relation1,2);
            mp.put(relation2,2);
            mp.put(relation3,2);
            mp.put(relation4,2);
            mp.put(relation5,2);
            mp.put(relation6,2);
        }

        /*  将数字存入relation中，并map存储映射关系  */
        for(int i = 0; i < 10; i++){
            Relation relation7 = new Relation(2,digit[i]);
            Relation relation8 = new Relation(3,digit[i]);

            //System.out.println("out: " + relation7.hashCode());

            Relations.add(relation7);
            Relations.add(relation8);

            mp.put(relation7,2);
            mp.put(relation8,2);
        }
        /*   将udline也存入map中迎神关系   */
        Relation relation1 = new Relation(2,'_');
        Relations.add(relation1);
        mp.put(relation1,3);

      // Relation rel = new Relation(2,'3');
       // System.out.println("out: " + rel.hashCode());



    }

     Result Identifier_recognize(String str){
        int state = 1;          //初始状态为1
        int i = 0;
        String res = "failed!";
        char[] curChlist = str.toCharArray();
        char curCh;        //curCh 存入当前读取字符
         curCh = curChlist[0];

         while (!isAccept.get(state)  && i < str.length())   //状态条件判定
         {

             Relation relation = new Relation(state,curCh);


                try{
                    state = mp.get(relation);
                    //System.out.println(state);
                }catch (Exception e){
                   // System.out.println("55");
                }



             //System.out.println("map size:" + map.size());

             if ( !mp.containsKey(relation))    //判定后的状态不为2或3则进入终止状态
             {
                 state = 4;
                 break;
             }

             if(i == str.length()-1) {
                  i++;
                 break;
             }

             curCh = curChlist[++i];

         }

         res = str.substring(0,i);             //截取单词
         Result output = new Result();

             if( i == 0 ){
                 output.setSTYLE(ILLEGAL);
                 output.setStr_len(1);
                 output.setOpt_str(str.substring(0,1));

                 }
                 else{
                 output.setSTYLE(IDENTIFIER_LETTER);
                 output.setStr_len(i);
                 output.setOpt_str(res);

             }

         return output;


         }

}
