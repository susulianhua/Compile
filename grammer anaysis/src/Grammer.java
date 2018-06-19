
import com.sun.xml.internal.ws.wsdl.writer.document.PortType;
import java.io.*;

import java.util.*;

public class Grammer {

    //将词法分析结果转化为队列
    public LinkedList<Result> queue = new LinkedList<>();
    Vector<String> errorlist = new Vector<>();
    //packageName 最后多余的identifer个数，即最后一个::后面identifer的个数
    // 作为全局变量，使reference和package均能访问到此变量的值
    public int package_identifier_cnt = 0;

    Grammer(){};    //构造函数

    String out1 = "D:\\SyntaxOut.txt";

    public void init(Vector<Result> v) {
        //创建队列存储词法分析结果

        for (int i = 0; i < v.size(); i++) {

            Result tmp = v.get(i);
            if (tmp != null) {
                queue.offer(tmp);
            }
        }
           /*
           for(int i = 0; i < queue.size(); i++){
               System.out.println(queue.get(i).opt_str + " " + queue.get(i).STYLE);
           }
           */

        //System.out.println(queue.getFirst().opt_str + " " + queue.getFirst().STYLE);
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

    public treeNode ThreadSpec_grammer(){
        treeNode thread = new treeNode();
        thread.data = "thread";

        //match  thread
        if(!queue.getFirst().opt_str.equals("thread")){
            errorlist.addElement("error: the first word is not thread");
            while (!queue.getFirst().opt_str.equals("thread") ){
                queue.pop();
            }
            return null;
        }
        else queue.pop();

        //match  identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            errorlist.addElement("error:ThreadSpec the second is not IDENTIFIER");
            while (!queue.getFirst().opt_str.equals(";")){
                queue.pop();
            }
            queue.pop();
            return null;
        }
        else {
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str;
            if(thread != null){
                thread.child.offer(identifier);
                queue.pop();
            }
        }

        //match  features
        if(queue.getFirst().opt_str.equals("features")){
            treeNode features = new treeNode();
            features.data = "features";
            if(thread != null){
                thread.child.offer(features);
                queue.pop();
            }
            treeNode featureschild = new treeNode();
            featureschild = FeatureSpec_grammer();
            //System.out.println(FeatureSpec_grammer().data);
            //System.out.println(FeatureSpec_grammer().data);
            //判断featureSpec是否为空，为空则将queue删除至引号后重新开始检测
            if(featureschild != null){

                features.child.offer(featureschild);
            }
            else {
                //System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh");
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                return null;
            }
        }

        //match  flows
        if(queue.getFirst().opt_str.equals("flows")){
            treeNode flows = new treeNode();
            flows.data = "flows";
            if(thread != null){
                thread.child.offer(flows);
                queue.pop();
            }
            treeNode flowschlid = new treeNode();
            flowschlid = FlowSpec_grammer();
            if(flowschlid != null){
                flows.child.offer(flowschlid);
            }
            else {
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                return null;
            }
        }

        //match  properties
        if(queue.getFirst().opt_str.equals("properties")){
            treeNode properties = new treeNode();
            properties.data = "properties";
            if(thread != null){
                thread.child.offer(properties);
                queue.pop();
            }
            treeNode propertieschild = new treeNode();
            propertieschild = Association_grammer();
            if(propertieschild != null){
                properties.child.offer(propertieschild);
            }
            else {
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                errorlist.add("Association is null");
                return null;
            }
            if(!queue.getFirst().opt_str.equals(";")){
               errorlist.addElement("error: threadspec properties the last is not fenhao");
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                return null;
            }
            else{
                treeNode fenhao1 = new treeNode();
                fenhao1.data = ";";
                properties.child.offer(fenhao1);
                queue.pop();
            }
        }

        //match  end
        if(!queue.getFirst().opt_str.equals("end")){
            errorlist.addElement("error: threadSpec  end");
            while (!queue.getFirst().opt_str.equals(";")){
                queue.pop();
            }
            queue.pop();
            return null;
        }
        else {
            treeNode end = new treeNode();
            end.data = "end";
            thread.child.offer(end);
            queue.pop();

            //match  INEDTIFIER
            if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
                errorlist.addElement("error:ThreadSpec the last IDENTIFIER");
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                return null;
            }
            else {
                treeNode identifier = new treeNode();
                identifier.data = queue.getFirst().opt_str;
                if(end != null) {
                    end.child.offer(identifier);
                    queue.pop();
                }
            }

            //match  ;
            if(!queue.getFirst().opt_str.equals(";")){
                errorlist.addElement("error: threadSpec the last ;");
                while (!queue.getFirst().opt_str.equals(";")){
                    queue.pop();
                }
                queue.pop();
                return null;
            }
            else {
                treeNode fenhao = new treeNode();
                fenhao.data = ";";
                end.child.offer(fenhao);
                queue.pop();
            }
            return thread;
        }
    }

    /*
        FeatureSpec 语法
     */
    public treeNode FeatureSpec_grammer() {
        treeNode featureSpec = new treeNode();

        //match portSpec_grammer()
        treeNode portspec = new treeNode();
        portspec = PortSpec_grammer();

        if (portspec != null) {
            featureSpec = portspec;
            return featureSpec;
        }

        //match ParameterSpec_grammer()
        else {
            treeNode parameter = new treeNode();
            parameter = ParameterSpec_grammer();
            if (parameter != null) {
                featureSpec = parameter;
                return featureSpec;
            }
            else {
            //match  none
             if (!queue.getFirst().opt_str.equals("none")) {
                errorlist.addElement(" FeatureSpec is null");

                return null;
            }
            else {
                    featureSpec.data = "none";
                    queue.pop();

                    //mathc ;
                    if (!queue.getFirst().opt_str.equals(";")) {
                        errorlist.addElement("error: FeatureSpec none next is not ;");
                        return null;
                    } else {
                        treeNode fenhao = new treeNode();
                        fenhao.data = ";";
                        featureSpec.child.offer(fenhao);
                        queue.pop();
                        return featureSpec;
                    }
                }
            }
        }
    }

    //match FlowSpec_grammer()
    public treeNode FlowSpec_grammer(){
        treeNode flowSpec = new treeNode();
        treeNode flowsource = new treeNode();
        flowsource = FlowSourseSpec_grammer();
        //match  FlowSourseSpec_grammer()
        if(flowsource != null){
            flowSpec = flowsource;
            return flowSpec;
        }

        //match  FlowSinkSpec_grammer();
        treeNode flowsink = new treeNode();
        flowsink = FlowSinkSpec_grammer();
         if(flowsink != null){
            flowSpec = flowsink;
            return flowSpec;
        }

        //match FlowPathSpec_grammer()
         treeNode flowpath = new treeNode();
         flowpath = FlowPathSpec_grammer();
         if(flowpath != null){
            flowSpec = flowpath;
            return flowSpec;
        }
        //match  none
        else if(!queue.getFirst().opt_str.equals("none")){
            errorlist.addElement(" FlowSpec is null");
            return null;
        }
        else {
            queue.pop();

            //mathc ;
            if(!queue.getFirst().opt_str.equals(";")){
                errorlist.addElement("error:FeatureSpec none next is not ;");
                return null;
            }
            else{
                flowSpec.data = "none ;";
                queue.pop();
                return flowSpec;
            }
        }
    }

    //match  Association
    public treeNode Association_grammer(){
        treeNode assoction = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match  [identifier::]
        if(!queue.getFirst().opt_str.equals("none")) {
            if (!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")) {
                errorlist.addElement("erroer: Association first is not IDENTIFIER");
                return null;
            }
            if ( queue.get(1).opt_str.equals("::")) {
                assoction.data = queue.getFirst().opt_str;
                queue.pop();
                treeNode shuangfen = new treeNode();
                shuangfen.data = "::";
                assoction.child.offer(shuangfen);
                queue.pop();

                //match  identifier split
                if (!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")) {
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    errorlist.addElement("error:Association no IDENTIFIERLETTER Split");
                    return null;
                } else {
                    treeNode identifier = new treeNode();
                    identifier.data = queue.getFirst().opt_str;
                    assoction.child.offer(identifier);
                    queue.pop();
                    treeNode splitter = new treeNode();
                    splitter = Splitter_grammer();
                    if (splitter != null) {
                        identifier.child.offer(splitter);
                    } else {
                        queue.clear();
                        for(int i = 0; i < cha.size();i++){
                            queue.offer(cha.get(i));
                        }
                        return null;
                    }
                }
            }

            //match Identifier split
            else {
                assoction.data = queue.getFirst().opt_str;
                queue.pop();
                treeNode splitter = new treeNode();
                splitter = Splitter_grammer();
                if (splitter != null) {
                    assoction.child.offer(splitter);
                } else {
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    return null;
                }
            }

            //match contant
            if (queue.getFirst().opt_str.equals("constant")) {
                treeNode constant = new treeNode();
                constant.data = "constant";
                assoction.child.offer(constant);
                queue.pop();
            }

            //match  access
            if (!queue.getFirst().opt_str.equals("access")) {
                queue.clear();
                for(int i = 0; i < cha.size();i++){
                    queue.offer(cha.get(i));
                }
                errorlist.addElement("error:Association have no access");
                return null;
            } else {
                treeNode access = new treeNode();
                access.data = "access";
                assoction.child.offer(access);
                queue.pop();
                //match decimal
                if (!queue.getFirst().STYLE.equals("DECIMAL") ) {
                    errorlist.addElement("error: Association have no decimal");
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    return null;
                } else {
                    treeNode decimal = new treeNode();
                    decimal.data = queue.getFirst().STYLE;
                    access.child.offer(decimal);
                    queue.pop();
                }
            }
        }
        else {
            assoction.data = queue.getFirst().opt_str;
            queue.pop();
            return assoction;
        }
        return assoction;
    }

    //match  PortSpec_grammer()
    public treeNode PortSpec_grammer(){
        LinkedList<Result> cha = new LinkedList();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }
        treeNode portspec = new treeNode();
        //match identifier:
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            return null;
        }
        else {
            if(queue.get(1).opt_str.equals(":")){
                portspec.data = queue.getFirst().opt_str + ":";
                queue.pop();
                queue.pop();

                //match IOtype_grammer()
                treeNode iotype = new treeNode();
                iotype = IOtype_grammer();
                if(iotype != null){
                    portspec.child.offer(iotype);
                }
                else {
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    return null;
                }

                //match PortType
                treeNode porttype = new treeNode();
                porttype = PortType_grammer();
                if(porttype != null){
                    portspec.child.offer(porttype);
                }
                else {
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    return null;
                }

                // match [{{association}}]
                if(queue.getFirst().opt_str.equals("{")){
                    treeNode lkuo = new treeNode();
                    lkuo.data = "{";
                    portspec.child.offer(lkuo);
                    queue.pop();
                    treeNode association = new treeNode();
                    association = Association_grammer();
                    while (association != null){
                            portspec.child.offer(association);
                            association = Association_grammer();
                        }
                    if(!queue.getFirst().opt_str.equals("}")){
                        queue.clear();
                        for(int i = 0; i < cha.size();i++){
                            queue.offer(cha.get(i));
                        }
                        errorlist.addElement("error: PortSpec have no }");
                        return null;
                    }
                    else {
                        treeNode rkuo = new treeNode();
                        rkuo.data = "}";
                        portspec.child.offer(rkuo);
                        queue.pop();
                    }
                }

                if(queue.getFirst().opt_str.equals(";")){
                    treeNode end = new treeNode();
                    end.data = ";";
                    portspec.child.offer(end);
                    queue.pop();
                    return portspec;
                }
                else {
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    errorlist.addElement("error: PortSpec last is not ;");
                    return null;
                }


            }
            else {
                return null;
            }
        }


    }

    //match ParameterSpec_grammer()
    public treeNode ParameterSpec_grammer(){
        treeNode parameterspec = new treeNode();
        LinkedList<Result> cha = new LinkedList();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER") || !queue.get(1).opt_str.equals(":")){
            return null;
        }
        else {
            parameterspec.data = queue.getFirst().opt_str + ":";
            queue.pop();
            queue.pop();
            treeNode iotype = new treeNode();
            iotype = IOtype_grammer();
            if(iotype != null){
                parameterspec.child.offer(iotype);
                if(!queue.getFirst().opt_str.equals("parameter")){
                    queue.clear();
                    for(int i = 0; i < cha.size();i++){
                        queue.offer(cha.get(i));
                    }
                    return null;
                }
                else {
                    treeNode parameter = new treeNode();
                    parameter.data = "parameter";
                    parameterspec.child.offer(parameter);
                    queue.pop();

                    //match reference
                    treeNode reference = new treeNode();
                    reference = Reference_grammer();
                    if(reference != null){
                        parameterspec.child.offer(reference);
                        queue.pop();
                    }

                    //match [{{association}}]
                    if(queue.getFirst().opt_str.equals("{")){
                        treeNode lkuo = new treeNode();
                        lkuo.data = "{";
                        parameterspec.child.offer(lkuo);
                        queue.pop();
                        while (queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
                            treeNode association = new treeNode();
                            association = Association_grammer();
                            if(association == null){
                                queue.clear();
                                for(int i = 0; i < cha.size();i++){
                                    queue.offer(cha.get(i));
                                }
                                return null;
                            }
                            else {
                                parameterspec.child.offer(association);
                            }
                        }
                        //match }
                        if(!queue.getFirst().opt_str.equals("}")){
                            queue.clear();
                            for(int i = 0; i < cha.size();i++){
                                queue.offer(cha.get(i));
                            }
                            errorlist.addElement("error: ParameterSpec have no }");
                            return null;
                        }
                        else {
                            treeNode rkuo = new treeNode();
                            rkuo.data = "}";
                            queue.pop();
                            parameterspec.child.offer(rkuo);
                            return parameterspec;
                        }
                    }
                    else {
                        queue.clear();
                        for(int i = 0; i < cha.size();i++){
                            queue.offer(cha.get(i));
                        }
                        errorlist.addElement("eror: ParameterSpec the last is not ;");
                        return null;
                    }

                }

            }
            else {
                queue.clear();
                for(int i = 0; i < cha.size();i++){
                    queue.offer(cha.get(i));
                }
                return null;
            }
        }
    }

    //match  IOtype_grammer
    public treeNode IOtype_grammer(){
        treeNode iotype = new treeNode();

        //match in
        if(queue.getFirst().opt_str.equals("in")){
            queue.pop();
            //match in out
            if(queue.getFirst().opt_str.equals("out")){
                iotype.data = "in out";
                queue.pop();
                return iotype;
            }
            else {
                iotype.data = "in";
                return iotype;
            }
        }

        //match out
        else if(queue.getFirst().opt_str.equals("out")){
            iotype.data = queue.getFirst().opt_str;
            queue.pop();
            return iotype;
        }
        else {
            errorlist.addElement("error: IOtype is null");
            return null;
        }

    }

    //match PortType_grammer()
    public treeNode PortType_grammer(){
        treeNode porttype = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match data port
        if(queue.getFirst().opt_str.equals("data") && queue.get(1).opt_str.equals("port")){
            porttype.data = "data port";
            queue.pop();
            queue.pop();
            treeNode reference = new treeNode();
            reference = Reference_grammer();
            if(reference != null){
                porttype.child.offer(reference);
            }
                return porttype;
        }

        //match event port
        else if(queue.getFirst().opt_str.equals("event") && queue.get(1).opt_str.equals("port")){
            porttype.data = "event port";
            queue.pop();
            queue.pop();
            return porttype;
        }

        //match event data port [reference]
        else if(queue.getFirst().opt_str.equals("event") && queue.get(1).opt_str.equals("data") && queue.get(2).opt_str.equals("port")){
            porttype.data = "event data port";
            queue.pop();
            queue.pop();
            queue.pop();
            treeNode reference = new treeNode();
            reference = Reference_grammer();
            if(reference != null){
                porttype.child.offer(reference);
            }else {
                queue.clear();
                for(int i = 0; i < cha.size();i++){
                    queue.offer(cha.get(i));
                }
                return null;
            }
            return porttype;
        }
        else {
            if(queue.getFirst().opt_str.equals("data" )|| queue.getFirst().opt_str.equals("event")){
                errorlist.add("error:PortType is wrong");
            }
            return null;
        }
    }

    //match  FlowSourceSpec grammer
    public treeNode FlowSourseSpec_grammer(){
        treeNode flowsourcespec = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            return null;
        }
        else {
            flowsourcespec.data = queue.getFirst().opt_str;
            queue.pop();
        }

        //match : flow source
        if(queue.getFirst().opt_str.equals(":") && queue.get(1).opt_str.equals("flow") && queue.get(2).opt_str.equals("source")){
            treeNode fenhao = new treeNode();
            fenhao.data = ": flow source";
            queue.pop();
            queue.pop();
            queue.pop();
            flowsourcespec.child.offer(fenhao);
        }
        else {
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }

        //match  identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str;
            flowsourcespec.child.offer(identifier);
            queue.pop();
        }

        //match {
        if(queue.getFirst().opt_str.equals("{")){
            treeNode lkuo = new treeNode();
            lkuo.data = "{";
            flowsourcespec.child.offer(lkuo);
            queue.pop();
            //match association
            treeNode association = new treeNode();
            association = Association_grammer();
            while (association != null){
                flowsourcespec.child.offer(association);
                association = Association_grammer();
            }

            //match }
            if(queue.getFirst().opt_str.equals("}")){
                treeNode rkuo = new treeNode();
                rkuo.data = "}";
                flowsourcespec.child.offer(rkuo);
                queue.pop();
            }
            else {
                queue.clear();
                for(int i = 0; i < cha.size();i++){
                    queue.offer(cha.get(i));
                }
                return null;
            }
        }

        //match ;
        if(!queue.getFirst().opt_str.equals(";")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode fenhao = new treeNode();
            fenhao.data = ";";
            queue.pop();
            flowsourcespec.child.offer(fenhao);
        }

        return flowsourcespec;

    }

    //match FlowSinkSpec_grammer()
    public treeNode FlowSinkSpec_grammer(){
        treeNode flowsinkspec = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            return null;
        }
        else {
            flowsinkspec.data = queue.getFirst().opt_str;
            queue.pop();
        }

        //match : flow source
        if(queue.getFirst().opt_str.equals(":") && queue.get(1).opt_str.equals("flow") && queue.get(2).opt_str.equals("sink")){
            treeNode fenhao = new treeNode();
            fenhao.data = ": flow source";
            queue.pop();
            queue.pop();
            queue.pop();
            flowsinkspec.child.offer(fenhao);
        }
        else {
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }

        //match  identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str;
            flowsinkspec.child.offer(identifier);
            queue.pop();
        }

        //match {
        if(queue.getFirst().opt_str.equals("{")){
            treeNode lkuo = new treeNode();
            lkuo.data = "{";
            flowsinkspec.child.offer(lkuo);
            queue.pop();
            //match association
            treeNode association = new treeNode();
            association = Association_grammer();
            while (association != null){
                flowsinkspec.child.offer(association);
                association = Association_grammer();
            }

            //match }
            if(queue.getFirst().opt_str.equals("}")){
                treeNode rkuo = new treeNode();
                rkuo.data = "}";
                queue.pop();
            }
            else {
                queue.clear();
                for(int i = 0; i < cha.size();i++){
                    queue.offer(cha.get(i));
                }
                return null;
            }
        }

        //match ;
        if(!queue.getFirst().opt_str.equals(";")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode fenhao = new treeNode();
            fenhao.data = ";";
            queue.pop();
            flowsinkspec.child.offer(fenhao);
        }

        return flowsinkspec;

    }

    //match  FlowPathSpec_grammer()
    public treeNode FlowPathSpec_grammer(){
        treeNode flowpathspec = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match identifier :
        if(queue.getFirst().STYLE.equals("IDENTIFIER_LETTER") && queue.get(1).opt_str.equals(":")){
            flowpathspec.data = queue.getFirst().opt_str + " :" ;
            queue.pop();
            queue.pop();
        }
        else return null;

        //match  flow path
        if(queue.getFirst().opt_str.equals("flow") && queue.get(1).opt_str.equals("path")){
            treeNode flow = new treeNode();
            flow.data = "flow path";
            queue.pop();
            queue.pop();
            flowpathspec.child.offer(flow);
        }
        else {
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }

        //match  identifier
        if(!queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str;
            queue.pop();
            flowpathspec.child.offer(identifier);
        }

        //match ->
        if(!queue.getFirst().opt_str.equals("->")){
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        else {
            treeNode fuhao = new treeNode();
            fuhao.data = "->";
            queue.pop();
            flowpathspec.child.offer(fuhao);
        }

        //match  identifier;
        if(queue.getFirst().STYLE.equals("IDENTIFIER_LETTER") && queue.get(1).opt_str.equals(";")){
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str + ";";
            queue.pop();
            queue.pop();
            flowpathspec.child.offer(identifier);
        }
        else{
            queue.clear();
            for(int i = 0; i < cha.size();i++){
                queue.offer(cha.get(i));
            }
            return null;
        }
        return flowpathspec;
    }

    //match splitter_grammer()
    public treeNode Splitter_grammer(){
        treeNode splitter = new treeNode();

        //match =>
        if(queue.getFirst().opt_str.equals("=>")){
            splitter.data = "=>";
            queue.pop();
            return splitter;
        }
        //match +=>
        else if(queue.getFirst().opt_str.equals("+=>")){
            splitter.data = "+=>";
            queue.pop();
            return splitter;
        }
        else return null;

    }

    //match  Reference_grammer()
    public treeNode Reference_grammer(){
        treeNode reference = new treeNode();
        LinkedList<Result> cha = new LinkedList<>();
        for(int i = 0; i < queue.size(); i++){
            cha.offer(queue.get(i));
        }

        //match identifier  ::
        if(queue.getFirst().STYLE.equals("IDENTIFIER_LETTER") && queue.get(1).opt_str.equals("::")){
            reference.data = queue.getFirst().opt_str + "::";
            queue.pop();
            queue.pop();
            while (queue.getFirst().STYLE.equals("IDENTIFIER_LETTER") && queue.get(1).opt_str.equals("::")){
                treeNode identifier = new treeNode();
                identifier.data = queue.getFirst().opt_str + "::";
                queue.pop();
                queue.pop();
                reference.child.offer(identifier);
            }
        }

        //match identifier
         if (queue.getFirst().STYLE.equals("IDENTIFIER_LETTER")){
            treeNode identifier = new treeNode();
            identifier.data = queue.getFirst().opt_str;
            reference.child.offer(identifier);
            queue.pop();
        }
        else {
             queue.clear();
             for(int i = 0; i < cha.size();i++){
                 queue.offer(cha.get(i));
             }
            return null;
        }
        return reference;
    }



    //输出
    public treeNode grammer_solve(){
        treeNode root = new treeNode();
        root.data = " ";
        //root = ThreadSpec_grammer();

        while (!queue.isEmpty()){
            treeNode thread1= new treeNode();
            thread1 = ThreadSpec_grammer();
            if(thread1 != null) {
                root.child.offer(thread1);
            }
        }

        return root;


    }

    String kongge = "   ";
    String lastkongge = "  ";
    //打印
    public void grammer_print(treeNode root){
        if(root != null) {
            if(root.data.equals("thread")){
                append(out1,"=======================");
                append(out1,root.data );
            }
            else {
                append(out1,lastkongge + root.data);
                if(root.child.isEmpty()){
                    lastkongge = lastkongge.substring(lastkongge.length() - 2);
                }
                else {
                    lastkongge += kongge;
                }
            }
        }
        while (!root.child.isEmpty()){
            int cnt = root.child.size();
            for(int i = 0; i < cnt; i++){
                grammer_print(root.child.get(i));
            }
            break;
        }
    }
    public void grammer_error_print(){
        append(out1,"============errorlist====================");
        for(int i = 0; i < errorlist.size(); i++){
            append(out1,errorlist.get(i));
        }
    }


}



