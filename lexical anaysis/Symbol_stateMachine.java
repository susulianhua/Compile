import java.util.*;

public class Symbol_stateMachine extends stateMachine{

    Symbol_stateMachine(){};
    //   =>
    HashMap<Relation,Integer> sym1 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept1 = new HashMap<>();
    //   +=>
    HashMap<Relation,Integer> sym2 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept2 = new HashMap<>();
    //    ;
    HashMap<Relation,Integer> sym3 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept3 = new HashMap<>();
    //    :
    HashMap<Relation,Integer> sym4 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept4 = new HashMap<>();
    //    ::
    HashMap<Relation,Integer> sym5 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept5 = new HashMap<>();
    //    {
    HashMap<Relation,Integer> sym6 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept6 = new HashMap<>();
    //     }
    HashMap<Relation,Integer> sym7 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept7 = new HashMap<>();
    //->
    HashMap<Relation,Integer> sym8 = new HashMap<>();
    HashMap<Integer,Boolean> isAccept8 = new HashMap<>();

    /*  符号状态机     */
    public void init(){
        //   =>状态机初始化
        isAccept1.put(1,false);
        isAccept1.put(2,false);
        isAccept1.put(3,false);
        isAccept1.put(4,true);
        Relation relation11 = new Relation(1,'=');
        Relation relation12 = new Relation(2,'>');
        sym1.put(relation11,2);
        sym1.put(relation12,3);

        //    +=>状态机初始化
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

        //    ;状态机初始化
        isAccept3.put(1,false);
        isAccept3.put(2,false);
        isAccept3.put(3,true);
        Relation relation31 = new Relation(1,';');
        sym3.put(relation31,2);

        //     ：状态机初始化
        isAccept4.put(1,false);
        isAccept4.put(2,false);
        isAccept4.put(3,true);
        Relation relation41 = new Relation(1,':');
        sym4.put(relation41,2);

        //     ::状态机初始化
        isAccept5.put(1,false);
        isAccept5.put(2,false);
        isAccept5.put(3,false);
        isAccept5.put(4,true);
        Relation relation51 = new Relation(1,':');
        Relation relation52 = new Relation(2,':');
        sym5.put(relation51,2);
        sym5.put(relation52,3);

        //     { 状态机初始化
        isAccept6.put(1,false);
        isAccept6.put(2,false);
        isAccept6.put(3,true);
        Relation relation61 = new Relation(1,'{');
        sym6.put(relation61,2);

        //      } 状态机初始化
        isAccept7.put(1,false);
        isAccept7.put(2,false);
        isAccept7.put(3,true);
        Relation relation71 = new Relation(1,'}');
        sym7.put(relation71,2);

        //      -> 状态机初始化
        isAccept8.put(1,false);
        isAccept8.put(2,false);
        isAccept8.put(3,false);
        isAccept8.put(4,true);
        Relation relation81 = new Relation(1,'-');
        Relation relation82 = new Relation(2,'>');
        sym8.put(relation81,2);
        sym8.put(relation82,3);

    }

        //   非单符号状态机公用解析函数
    public Result public_recognize(String str, HashMap<Relation,Integer> sym,HashMap<Integer,Boolean> isAccept){
        int state = 1;
        int i = 0;
        char curCh = str.charAt(i);

        while (true){
           Relation relation = new Relation(state,curCh);
           //  sym中无此映射关系 上一个状态为可结束状态 如+=>::
           if(!sym.containsKey(relation) && state == sym.size() + 1){
               break;
           }
           //  sym中无此映射关系  上一个状态为不可结束状态  如 +=：：  +50.0
           if(!sym.containsKey(relation) && state != sym.size() + 1){
               i = 0;
               state = sym.size() + 2;
               break;
           }
           //   单词成功解析为符号
           if(i == str.length() - 1 && sym.get(relation) == sym.size() + 1){
               i++;
               state = sym.get(relation);
               break;
           }
           //  单词未解析成功如  +=  +5
           if(i == str.length() -1 && sym.get(relation) != sym.size() + 1){
               i = 0;
               state = sym.size() + 2;
               break;
           }
           state = sym.get(relation);
           curCh = str.charAt(++i);
        }

        String res = str.substring(0,i);
        Result output = new Result("lilijie",i,res);

        if(state == sym.size() + 1){
            output.setSTYLE(SYMBOL);
        }
        else{
            output.setSTYLE(ILLEGAL);
        }
        return output;
    }

    public Result oneSymbol_recognize(String str){

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

    public Result Symbol_recognize(String str){

        Vector<Result> res = new Vector<>();
        //   +=>  状态机执行
        Result res1 = public_recognize(str,sym2,isAccept2);
        res.add(res1);
        //   =>   状态机执行
        Result res2 = public_recognize(str,sym1,isAccept1);
        res.add(res2);
        //    ::  状态机执行
        Result res3 = public_recognize(str,sym5,isAccept5);
        res.add(res3);
        //     -> 状态机执行
        Result res4 = public_recognize(str,sym8,isAccept8);
        res.add(res4);
        //    ;
        Result res5 = public_recognize(str,sym3,isAccept3);
        res.add(res5);
        //    :
        Result res6 = public_recognize(str,sym4,isAccept4);
        res.add(res6);
        //    {
        Result res7 = public_recognize(str,sym6,isAccept6);
        res.add(res7);
        //     }
        Result res8 = public_recognize(str,sym7,isAccept7);
        res.add(res8);
        //     单符号状态及执行
        Result res9 = oneSymbol_recognize(str);
        res.add(res9);

        boolean flag = false;
        for(int i = 0; i < 9; i++){
            if(res.get(i).STYLE != "ILLEGAL_STRING"){
                flag = true;
                return res.get(i);
            }
        }
        if(!flag){
            return res.get(0);
        }
        return res.get(0);
    }
}
