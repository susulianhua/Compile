
import java.util.*;


public class Symbols_stateMachine extends stateMachine {

     public Symbols_stateMachine(){}


     /*
                          =>
     */
    HashMap<Relation,Integer> sym1 = new HashMap();
    HashMap<Integer,Boolean> isAccept1 = new HashMap();

    /*
                          +=>
     */
    HashMap<Relation,Integer> sym2 = new HashMap();
    HashMap<Integer,Boolean> isAccept2 = new HashMap();

    /*
                         ;
     */
    HashMap<Relation,Integer> sym3 = new HashMap();
    HashMap<Integer,Boolean> isAccept3 = new HashMap();

    /*
                        :
     */
    HashMap<Relation,Integer> sym4 = new HashMap();
    HashMap<Integer,Boolean> isAccept4 = new HashMap();

    /*
                        ::
    */
    HashMap<Relation,Integer> sym5 = new HashMap();
    HashMap<Integer,Boolean> isAccept5 = new HashMap();

    /*
                        {
     */
    HashMap<Relation,Integer> sym6 = new HashMap();
    HashMap<Integer,Boolean> isAccept6 = new HashMap();

    /*
                         }
     */
    HashMap<Relation,Integer> sym7 = new HashMap();
    HashMap<Integer,Boolean> isAccept7 = new HashMap();

    /*
                         ->
    */
    HashMap<Relation,Integer> sym8 = new HashMap();
    HashMap<Integer,Boolean> isAccept8 = new HashMap();

    /**
       初始化状态机函数的声明
     */

    void init(){
        /* *
          =>状态机初始化
        */

        isAccept1.put(1,false);
        isAccept1.put(2,false);
        isAccept1.put(3,false);
        isAccept1.put(4,true);

        Relation relation11 = new Relation(1,'=');
        Relation relation12 = new Relation(2,'>');

        sym1.put(relation11,2);
        sym1.put(relation12,3);

        /* *
            +=>状态机初始化
        */

        isAccept2.put(1,false);
        isAccept2.put(2,false);
        isAccept2.put(3,false);
        isAccept2.put(4,false);
        isAccept2.put(5,true);

        Relation relation21 = new Relation(1,'+');
        Relation relation22 = new Relation(2,'=');
        Relation relation23 = new Relation(3,'>');

        sym2.put(relation21,2);
        sym2.put(relation22,3);
        sym2.put(relation23,4);

        /* *
            ;状态机初始化
        */
        isAccept3.put(1,false);
        isAccept3.put(2,false);
        isAccept3.put(3,true);

        Relation relation31 = new Relation(1,';');

        sym3.put(relation31,2);

        /**
          :状态机初始化
         */

        isAccept4.put(1,false);
        isAccept4.put(2,false);
        isAccept4.put(3,true);

        Relation relation41 = new Relation(1,':');

        sym4.put(relation41,2);

        /* *
           ::状态机初始化
        */
        isAccept5.put(1,false);
        isAccept5.put(2,false);
        isAccept5.put(3,false);
        isAccept5.put(4,true);

        Relation relation51 = new Relation(1,':');
        Relation relation52 = new Relation(2,':');

        sym5.put(relation51,2);
        sym5.put(relation52,3);

        /* *
           { 状态机初始化
        */
        isAccept6.put(1,false);
        isAccept6.put(2,false);
        isAccept6.put(3,true);

        Relation relation61 = new Relation(1,'{');

        sym6.put(relation61,2);

        /* *
           }  状态机初始化
        */

        isAccept7.put(1,false);
        isAccept7.put(2,false);
        isAccept7.put(3,true);

        Relation relation71 = new Relation(1,'}');

        sym7.put(relation71,2);

        /* *
           ->状态机初始化
         */

        isAccept8.put(1,false);
        isAccept8.put(2,false);
        isAccept8.put(3,false);
        isAccept8.put(4,true);

        Relation relation81 = new Relation(1,'-');
        Relation relation82 = new Relation(2,'>');

        sym8.put(relation81,2);
        sym8.put(relation82,3);

    }

   /* *
         所有状态机公用解析函数
   */
    Result public_recognize(String str,HashMap<Integer,Boolean> isAccept,HashMap<Relation,Integer> sym){
        int state = 1;
        int i = 0;
        String res = "failed!";
        char[] curChlist = str.toCharArray();
        char curCh = curChlist[i];

        while( !isAccept.get(state) && i < str.length()){

            Relation relation = new Relation(state,curCh);
            try {
                state = sym.get(relation);
            }catch(Exception e){

            }

            if(!sym.containsKey(relation) ){
                state = sym.size()+ 2;
                break;

            }
            if(i == str.length()-1){
                i++;
                break;
            }
            curCh = curChlist[++i];

        }

        res = str.substring(0, i);
        Result output = new Result();

        if(i == 0){
            output.setSTYLE(ILLEGAL);
            output.setStr_len(1);
            output.setOpt_str(str.substring(0,1));

        }
        else{
            output.setOpt_str(res);
            output.setStr_len(i);
            output.setSTYLE(SYMBOL);
        }
        return output;
    }

    /**
     * 符号集合映射判断
     */
    Result Symbol_recognize_mapping(String str){


        Result output = new Result(ILLEGAL, 1, str);
        List<String> symbollist = new ArrayList<String>();
        symbollist = Arrays.asList(symbols);

        if(symbollist.contains(str) ){
            output.setSTYLE(SYMBOL);

        }
        else{
            output.setOpt_str(str.substring(0,1));
        }
        return output;

    }

    /**
     符号总状态机 = 集合判断 + 多字符符号状态机判断
     */
    Result Symbol_recognize(String str){
        Vector<Result> results = new Vector<Result>();

        /**
         * 多字符状态机执行
         */
        //+=>状态机
        Result res2 = public_recognize(str, isAccept2, sym2);
        results.add(res2);

        //=>状态机
        Result res1 = public_recognize(str, isAccept1, sym1);
        results.add(res1);


        //;状态机
        Result res3 = public_recognize(str, isAccept3, sym3);
        results.add(res3);

        //::状态机
        Result res5 = public_recognize(str, isAccept5, sym5);
        results.add(res5);

        //:状态机
        Result res4 = public_recognize(str, isAccept4, sym4);
        results.add(res4);


        //{状态机
        Result res6 = public_recognize(str, isAccept6, sym6);
        results.add(res6);

        //}状态机
        Result res7 = public_recognize(str, isAccept7, sym7);
        results.add(res7);

        //->状态机
        Result res8 = public_recognize(str, isAccept8, sym8);
        results.add(res8);

        /**
         * 符号集合判断
         */
        Result result_mapping = Symbol_recognize_mapping(str);
        results.add(result_mapping);

        /**
         * 符号状态机的最终解析结果
         */
        boolean flag = false;   // 默认状态机都不能解析
        for(int i = 0; i < results.size(); i++){
            if(results.get(i).getSTYLE(results.get(i)) != ILLEGAL){
                flag = true;
                return results.get(i);
            }
        }

        //均未能解析
        if(!flag ){

            return results.get(0);
        }
        return results.get(0);

    }

}
