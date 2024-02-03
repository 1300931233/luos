package com.asurplus.common.readjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.util.Map;

/**
 * 读取json文件，将一行的数据进行整合到一行，然后输出到对应的txt中，txt的文件名和对应的json文件名一致。
 */
public class ReadJsonUtil {

    public static int maxTolerateValue = 20;  //容忍值

    /**
     * 这个方法就是用来找到json文件
     * @param jsonFilePath  json文件所在的目录,必须是目录类型
     * @param outputPath   输出的目录位置
     * @param outputType   输出的类型，支持txt类型,doc类型等等
     */
    public static Object startBusinessForDirectory(String jsonFilePath,String outputPath,String outputType) throws IOException {
        //1.首先找到所有的json文件
        File file = new File(jsonFilePath);
        if (file.isDirectory()){
            String[] fileList = file.list();
            for (String filename : fileList){
                int lastIndex = filename.lastIndexOf('.');
                String str = filename.substring(lastIndex);
                if (".json".equals(str)){
                    //2.找到json文件后,需要读取json文件的内容进行整合，然后输出到对应的路径
                    File jsonFile = new File(jsonFilePath+File.separator+filename);
                    readJsonFileAndOutput(filename,jsonFile,outputPath,outputType);
                }
            }
        } else {

        }
        return null;
    }

    /**
     * 读取json文件，将内容读取出来
     * @param jsonFile  json文件
     * @param outputPath 输出的路径
     * @param outputType  输出的类型，只支持txt文件
     */
    private static void readJsonFileAndOutput(String filename,File jsonFile, String outputPath, String outputType) throws IOException {
        String jsonStr  ="";
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "UTF-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        jsonStr = sb.toString();
        //将读取出来的JSON文件内容从String转为Map

        Map jsonMap = JSON.parseObject(jsonStr,Map.class);
        outputFile(filename,jsonMap,outputPath,outputType);

    }

    /**
     * 将读取出来的json数据整合，输出到对应的文件中
     * @param jsonMap
     * @param outputPath
     * @param outputType
     */
    private static void outputFile(String filename,Map jsonMap, String outputPath, String outputType) throws IOException {
        JSONArray array = (JSONArray) jsonMap.get("ret");
        //Map[] maps = (Map[]) jsonMap.get("ret");

        String name = filename.substring(0,filename.lastIndexOf("."));
        String path = outputPath+File.separator+name+"."+outputType;
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));
        StringBuffer stringBuffer = new StringBuffer();
        Boolean newLine = true;
        for (int i = 0 ; i < array.size();i++){
            //如果是第1条数据，没有比较，直接存入stringbuffer
            if (i == 0){
                writer.write(array.getJSONObject(i).get("word").toString());
                continue;
            }
            //判断y轴高度和上一个数据的高度的差的绝对值，如果这个值比较小，就可以认为是同一行.
            int axisy1 = Integer.parseInt(String.valueOf(array.getJSONObject(i).getInteger("y")));
            int height1 = Integer.parseInt(String.valueOf(array.getJSONObject(i).getInteger("h")));
            int axisy2 = Integer.parseInt(String.valueOf(array.getJSONObject(i-1).getInteger("y")));
            int height2 = Integer.parseInt(String.valueOf(array.getJSONObject(i-1).getInteger("h")));
            int offset = Math.abs((axisy1+height1)-(axisy2+height2));
            //暂时以差距20以内认为是一行,最小高度应该是所有json文件中，所有数据的h的最小值略小一点。太小会导致本来是一行却识别出不在一行，
            //一般文本差距很小，有可能有上标，下标等，导致差距较大，差距最大的在30左右。
            //太大会导致很多数据识别出来在同一行。
            if (offset < 30){
                writer.write(array.getJSONObject(i).get("word").toString());
            } else {
                //如果不是一行，就把收集到的stringBuffer的数据线写入，然后将newLine置为true,然后strBuffer清空
                writer.newLine();
                writer.write(array.getJSONObject(i).get("word").toString());
            }
        }
        writer.close();

    }

    /**
     * 获取文件的编码格式
     * @param filename 文件名称
     * @return 返回编码类型
     */
    public static String getFileCode(String filename) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(filename));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }

}
