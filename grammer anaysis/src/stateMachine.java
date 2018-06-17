import java.util.*;
public class stateMachine {

    /*     lower_letter_lis中存储小写英文字母     */
    String lower_letter_list = "abcdefghijklmnopqrstuvwxyz";

    /*     upper_letter_list中存储大写英文字母    */
    String upper_letter_list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*    digit 中存储数字   */
    String digit_list = "0123456789";

    /*    relation存储当前state和当前字符
    *     map存储状态映射关系
    *     isAccept存储状态是否为接受状态           */
    Vector<Relation> Relations = new Vector<Relation>();
    HashMap<Relation,Integer> mp = new HashMap();
    HashMap<Integer,Boolean> isAccept = new HashMap();

    /*    解析结果类型                            */
    String ILLEGAL = "ILLEGAL_STRING";
    String IDENTIFIER_LETTER = "IDENTIFIER_LETTER";
    String DECIMAL = "DECIMAL";
    String SYMBOL = "SYMBOL";
    String KEYWORDS = "KEYWORDS";

    String[] symbols = {";",":", "{", "}"};
    String[] keywords = {"thread",  "features",  "flows",  "properties",  "end",
            "none",  "in",  "out",  "data",  "port",  "event",  "parameter",  "flow" ,  "source",
            "sink" ,  "path",  "constant" ,  "access"};


    stateMachine(){};


}