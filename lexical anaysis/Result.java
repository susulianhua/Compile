import java.net.PortUnreachableException;

public class Result {

    public String STYLE;
    int str_len;   //解析出来的单词长度
    String opt_str;

    Result(){}

    public Result(String str1, int len,String str2){
        this.STYLE = str1;
        this.str_len = len;
        this.opt_str = str2;
    }

    public String getSTYLE(Result result){
        return result.STYLE;
    }

    public void setSTYLE(String str){
        this.STYLE = str;
    }

    public void setStr_len(int x){
        this.str_len = x;
    }

    public void setOpt_str(String str){
        this.opt_str = str;
    }

}
