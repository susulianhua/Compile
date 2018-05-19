import java.lang.reflect.Array;
import java.util.*;

 class Keywords_stateMachine extends stateMachine {

    Keywords_stateMachine(){}

    HashMap<String , Integer> kwd = new HashMap<>();



    void init(){

    }

     /**
      *  关键字解析
      * 经过Identifier状态机解析成功的单词若解析成功则该单词为关键字
      * @return
      */
    Result Keywords_stateMachine(String str){

        Result output = new Result(str,str.length(),str);
        List<String> Keywordslist = new ArrayList<String>();
        Keywordslist = Arrays.asList(keywords);

        //解析成功
        if(Keywordslist.contains(str) ){
            output.setSTYLE(KEYWORDS);
            }
            //未解析成功则该单词必然是Identifier_LETTER
            else
        {
            output.setSTYLE(IDENTIFIER_LETTER);
        }
        return output;
    }


}
