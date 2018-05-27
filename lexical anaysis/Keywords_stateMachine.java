import java.util.*;

public class Keywords_stateMachine extends stateMachine {

    Keywords_stateMachine(){}

    public void init(){
        }

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
