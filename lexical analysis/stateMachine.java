import java.util.*;
public class stateMachine {

    /*  lower_letter_lis中存储小写英文字母   */
    String str1 = "abcdefghijklmnopqrstuvwxyz";
    char[] lower_letter_list = str1.toCharArray();


    /* upper_letter_list中存储大写英文字母   */
    String str2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    char[] upper_letter_list = str2.toCharArray();


    /* digit 中存储数字   */
    String str3 = "0123456789";
    char[] digit = str3.toCharArray();


    Vector<Relation> Relations = new Vector<Relation>();          //当前状态与触发字符
    HashMap<Relation,Integer> mp = new HashMap();          //map存储状态映射关系
    HashMap<Integer,Boolean> isAccept = new HashMap();     //isAccept状态结点是否为接受状态


    String ILLEGAL = "ILLEGAL_STRING";
    String IDENTIFIER_LETTER = "IDENTIFIER_LETTER";
    String DECIMAL = "DECIMAL";
    String SYMBOL = "SYMBOL";
    String KEYWORDS = "KEYWORDS";

    String[] symbols = {";", "{", "}"};
    String[] keywords = {"thread",  "features",  "flows",  "properties",  "end",
            "none",  "in",  "out",  "data",  "port",  "event",  "parameter",  "flow" ,  "source",
            "sink" ,  "path",  "constant" ,  "access"};


    stateMachine(){};


}
