
import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.io.*;
import java.util.*;

public class Main {

    public static Identifier_stateMachine identifier_stateMachine = new Identifier_stateMachine();
    public static Decimal_stateMachine decimal_stateMachine = new Decimal_stateMachine();
    public static Keywords_stateMachine keywords_stateMachine = new Keywords_stateMachine();
    public static Symbols_stateMachine symbols_stateMachine = new Symbols_stateMachine();

    //状态机初始化
    public static void initiaization(){


        identifier_stateMachine.init();
        decimal_stateMachine.init();
        keywords_stateMachine.init();
        symbols_stateMachine.init();

    }

    public static void append(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String [] strs){

            initiaization();
            //存储输出文件地址
            String out1 = "D:\\out1.txt";
            String out2 = "D:\\out2.txt";
            String out3 = "D:\\out3.txt";
            String out4 = "D:\\tokenOut.txt";

           //存储测试文件地址
            String pathname1 = "D:\\test1.txt";
            String pathname2 = "D:\\test2.txt";
           String pathname3 = "D:\\test3.txt";


            File file1 = new File(pathname1);
            BufferedReader reader = null;
            //将行数与相应行的字符串绑定


            try {
            reader = new BufferedReader(new FileReader(file1));
            String tempString = null;
            int line = 1;
            append(out4,"misstake from txt1:\r\n");

            // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {

                    // 若此行为空行则跳过并记录行数
                    if(tempString.isEmpty()){
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
                                if(output.STYLE == "KEYWORDS"){
                                   append(out1,output.opt_str + "    " + output.STYLE + "("+output.opt_str+")" + "\r\n============");
                                }
                                else {
                                    System.out.println(output.opt_str + " " + output.STYLE);
                                    append(out1, output.opt_str + "    " + output.STYLE + "\r\n============");
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
                            output = symbols_stateMachine.Symbol_recognize(input_cur);
                            if (output.STYLE != "ILLEGAL_STRING") {
                                System.out.println(output.opt_str + " " + output.STYLE);
                                append(out1,output.opt_str + "    " + output.STYLE + "\r\n============");

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
                                System.out.println(output.opt_str + " " + output.STYLE);
                                append(out1,output.opt_str + "    " + output.STYLE + "\r\n============");
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
                                System.out.println("line:" + line + " " + output.opt_str + " " + output.STYLE);
                                append(out4,"line:" + line+"\r\n"+ output.opt_str + "    " + output.STYLE + "\r\n===========");

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


        File file2 = new File(pathname2);
       ;
        //将行数与相应行的字符串绑定
        append(out4,"misstake from text.2:\t\n");




        try {
            reader = new BufferedReader(new FileReader(file2));
            String tempString = null;
            int line = 1;


            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                // 若此行为空行则跳过并记录行数
                if(tempString.isEmpty()){
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
                            if(output.STYLE == "KEYWORDS"){
                                append(out2,output.opt_str + "    " + output.STYLE + "("+output.opt_str+")" + "\r\n============");
                            }
                            else {
                                System.out.println(output.opt_str + " " + output.STYLE);
                                append(out2, output.opt_str + "    " + output.STYLE + "\r\n============");
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
                        output = symbols_stateMachine.Symbol_recognize(input_cur);
                        if (output.STYLE != "ILLEGAL_STRING") {
                            System.out.println(output.opt_str + " " + output.STYLE);
                            append(out2,output.opt_str + "    " + output.STYLE + "\r\n============");

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
                            System.out.println(output.opt_str + " " + output.STYLE);
                            append(out2,output.opt_str + "    " + output.STYLE + "\r\n============");
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
                            System.out.println("line:" + line + " " + output.opt_str + " " + output.STYLE);

                            append(out4,"line:" + line+"\r\n"+ output.opt_str + "    " + output.STYLE + "\r\n===========");

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


        File file3 = new File(pathname3);

        //将行数与相应行的字符串绑定
        append(out4,"misstake from text.3:\t\n");




        try {
            reader = new BufferedReader(new FileReader(file3));
            String tempString = null;
            int line = 1;


            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                // 若此行为空行则跳过并记录行数
                if(tempString.isEmpty()){
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
                            if(output.STYLE == "KEYWORDS"){
                                append(out3,output.opt_str + "    " + output.STYLE + "("+output.opt_str+")" + "\r\n============");
                            }
                            else {
                                System.out.println(output.opt_str + " " + output.STYLE);
                                append(out3, output.opt_str + "    " + output.STYLE + "\r\n============");
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
                        output = symbols_stateMachine.Symbol_recognize(input_cur);
                        if (output.STYLE != "ILLEGAL_STRING") {
                            System.out.println(output.opt_str + " " + output.STYLE);
                            append(out3,output.opt_str + "    " + output.STYLE + "\r\n============");

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
                            System.out.println(output.opt_str + " " + output.STYLE);
                            append(out3,output.opt_str + "    " + output.STYLE + "\r\n============");
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
                            System.out.println("line:" + line + " " + output.opt_str + " " + output.STYLE);
                            append(out4,"line:" + line+"\r\n"+ output.opt_str + "    " + output.STYLE + "\r\n===========");

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
}



