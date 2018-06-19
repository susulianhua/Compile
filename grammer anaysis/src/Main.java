import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        //词法分析声明
        Lexical lexical = new Lexical();


        //last存储词法分析结果
        Vector<String> last = new Vector<>();
        last = lexical.lexicalres();
        Vector<Result> wordAnaysis = new Vector<>();

        //将分析结果存储到wordAnysis中
        for (int i = 0; i < last.size()/3; i++) {
            Result result1 = new Result();
            result1.setOpt_str(last.elementAt(3 * i));
            result1.setSTYLE(last.elementAt(3 * i + 1));
            result1.setStr_len(Integer.parseInt(last.elementAt(3 * i +2)));
            wordAnaysis.addElement(result1);
        }

        /*
        for(int i = 0; i < wordAnaysis.size()/3; i++){
            System.out.println(wordAnaysis.elementAt(i * 3).opt_str + wordAnaysis.elementAt(i*3).STYLE);
            System.out.println(wordAnaysis.elementAt(i * 3+1).opt_str + wordAnaysis.elementAt(i*3+1).STYLE);
        }
        */

        //初始化语法分析器
        Grammer grammer = new Grammer();
        grammer.init(wordAnaysis);


        treeNode root = grammer.grammer_solve();
        grammer.grammer_print(root);
        grammer.grammer_error_print();



    }
}
