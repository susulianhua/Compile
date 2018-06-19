import java.io.*;
import java.util.*;
public class Lexical {

    Lexical(){};

    public Vector<String> lexicalres(){
        //四个词法分析状态机声明
        Identifier_stateMachine identifier_stateMachine = new Identifier_stateMachine();
        Decimal_stateMachine decimal_stateMachine = new Decimal_stateMachine();
        Keywords_stateMachine keywords_stateMachine = new Keywords_stateMachine();
        Symbol_stateMachine symbol_stateMachine = new Symbol_stateMachine();

        //四个词法分析状态机启动
        identifier_stateMachine.init();
        decimal_stateMachine.init();
        keywords_stateMachine.init();
        symbol_stateMachine.init();

        //Vector数组用来存储词法分析结果，result 用来存储每次解析出来的单词相关信息
        Vector<String> last = new Vector<>();

        //存储测试文件地址
        String pathname1 = "D:\\test.txt";
        String pathname2 = "D:\\test2.txt";
        String pathname3 = "D:\\test3.txt";
        String[] pathname = {pathname1, pathname2, pathname3};
        BufferedReader reader = null;

        //词法分析
        for(int x = 0; x < 3; x++) {
            try {
                File file = new File(pathname[x]);
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;

                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {

                    // 若此行为空行则跳过并记录行数
                    if (tempString.isEmpty()) {
                        line++;
                        continue;
                    }
                    int tempString_index = 0;

                    //清除每行头部空格以及换行符
                    while (tempString.charAt(tempString_index) == ' ' || tempString.charAt(tempString_index) == '\t') {
                        tempString_index++;
                    }
                    tempString = tempString.substring(tempString_index);
                    String[] ssplit = tempString.split(" ");

                    /**对每行的分解为数组单词
                     * 对每行的每个单词进行解析
                     */
                    for (int i = 0; i < ssplit.length; i++) {

                        String input_cur = ssplit[i];
                        while (true) {
                            Result output = new Result();
                            output = identifier_stateMachine.Identifier_recognize(input_cur);
                            if (output.STYLE != "ILLEGAL_STRING") {

                                //该单词合法，则不是keywords就是identifier_letter
                                //单词有可能为截取后的单词，进行判断类型
                                String word = output.opt_str;
                                output = keywords_stateMachine.Keywords_stateMachine(word);
                                if (output.STYLE == "KEYWORDS") {
                                    last.add(output.opt_str);
                                    last.addElement(output.STYLE);
                                    last.addElement(line + "");
                                } else {
                                    last.add(output.opt_str);
                                    last.addElement(output.STYLE);
                                    last.addElement(line + "");
                                }


                                //如果解析成功后此字符串没有完全解析，将已经解析的部分截掉，取后面部分
                                if (output.str_len < input_cur.length()) {
                                    input_cur = input_cur.substring(output.str_len);
                                    continue;
                                }

                                //此字符串已经完全解析了
                                else {
                                    break;
                                }
                            }

                            //符号解析
                            output = symbol_stateMachine.Symbol_recognize(input_cur);
                            if (output.STYLE != "ILLEGAL_STRING") {
                                last.add(output.opt_str);
                                last.addElement(output.STYLE);
                                last.addElement(line + "");

                                //如果解析成功后此字符串没有完全解析，将已经解析的部分截掉，取后面部分
                                if (output.str_len < input_cur.length()) {
                                    input_cur = input_cur.substring(output.str_len);
                                    continue;
                                } else {
                                    // 此字符串已经完全解析了
                                    break;
                                }
                            }
                            //浮点数解析
                            output = decimal_stateMachine.Decimal_recognize(input_cur);
                            if (output.STYLE != "ILLEGAL_STRING") {
                                last.add(output.opt_str);
                                last.addElement(output.STYLE);
                                last.addElement(line + "");
                                // 解析成功后，如果此字符串没有完全解析，将已经解析出来的字符串截掉，取后面部分
                                if (output.str_len < input_cur.length()) {
                                    input_cur = input_cur.substring(output.str_len);
                                    continue;
                                } else {
                                    // 此字符串已经完全解析了
                                    break;
                                }
                            } else {
                                //所有状态机都无法解析该词
                                last.add(output.opt_str);
                                last.addElement(output.STYLE);
                                last.addElement(line + "");

                                //继续判断单词手完全解析
                                if (output.str_len < input_cur.length()) {
                                    input_cur = input_cur.substring(output.str_len);
                                    continue;
                                }
                                //完全解析则结束对spplit[i]的解析
                                else {
                                    break;
                                }
                            }
                        }

                    }
                    line++;
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return last;
    }
}
