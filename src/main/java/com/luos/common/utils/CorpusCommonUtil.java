package com.luos.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorpusCommonUtil {
    /**翻译接口*/
    private final static String transUri = "https://nmt.xmu.edu.cn/nmt";
    /**获取文本所属语种接口*/
//    private final static String langUri = "https://cloudtranslation.com/langid";
    private final static String langUri = "http://nmt.xmu.edu.cn/langid";

    public static void main(String[] args) {
//        System.out.println(translate("","bo","钣金铆焊厂"));
//        System.out.println(getTextLanguage("癌治疗学会"));
        List<String> newOriginal = getParticiple("ལྕགས་ལེབ་གཟེར་ཞྭ་ཚ་ལ་བཟོ་གྲྭ།");
        for (int i = 0; i < 100; i++){
            try{
                List<String> originalText=  getParticiple(translate("","bo","钣金铆焊厂"));
                int ss =  textCompare(originalText,newOriginal);
                System.err.println(i+"-----------"+ss+"% ----------------");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        System.out.println("原始字符："+translate("","en","你好,李焕英"));
//        System.out.println("新字符："+translate("","en","你好"));
//
//        List<String> originalText = getParticiple("མཚོ་སྔོན་ཞིང་ཆེན་མི་རིགས་ལས་དོན་ཨུ་ཡོན་ལྷན་ཁང་གི་མི་རིགས་གངས་ཉུང་སྐདགཞུང་ལས་ཁང་།");
//        List<String> newOriginal = getParticiple("མཚོ་སྔོན་ཞིང་ཆེན་མི་རིགས་ལས་དོན་ཨུ་ཡོན་ལྷན་ཁང་གི་མི་རིགས་གངས་ཉུང་སྐད་ཡིག་གཞུང་ལས་ཁང་།");
//        System.out.println(originalText);
//
//        System.out.println(textCompare(originalText,newOriginal)+"%");
    }

    /**
     * 获取文本所属语种
     * @param text
     * @return
     */
    public static String getTextLanguage(String text){
        Map<String, String> params = new HashMap<>();
        params.put("text", text);
        String jsonData = translateCommonUtil.httpclientPost(langUri, params, null);
        return jsonData;
    }

    /**
     *  翻译文本
     * @param originLang
     * @param targetLang
     * @param src
     * @return
     */
    public static String translate(String originLang,String targetLang, String src){
        Map<String, String> params = new HashMap<>();
        if (!CommonUtil.isNotNull(originLang)) {
            originLang = getTextLanguage(src);
        }
        params.put("lang", originLang+"_"+targetLang);
        params.put("src", src);
        String jsonData = translateCommonUtil.httpclientPost(transUri, params, null);
        return jsonData;
    }

    /**
     * 语料分字
     * @param str
     * @return
     */
    public static List<String> getParticiple (String str){
        if (StringUtils.isBlank(str)) {
            return new ArrayList<String>(0);
        }
//        String str = "བོད་ཡིག 张三，Hello 123 ";
//        System.out.println("原始字符: "+str);
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        List<String> wordList = new ArrayList<String>();
        for(char c:chars) {
            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) { // 英语区间
//                System.out.println(c);
                buffer.append(c);
            } else if(0x4E00 <= c && c <= 0x9FA5) {	// 中文区间
                wordList.add(String.valueOf(c));
            } else if(0x0F00 <= c && c <= 0x0FFF && '་' != c && '།' != c) { // 藏文区间和藏文分字符
                buffer.append(c);
            } else {
                if(buffer.length() == 0) {
                    continue;
                }
                wordList.add(buffer.toString());
                buffer.setLength(0);
            }
        }

        if(buffer.length() != 0){
            wordList.add(buffer.toString());
        }
        buffer = null;
        return wordList;
    }

    /**
     * 文本相似度比较
     * @param originalText
     * @param newOriginal
     * @return
     */
    public static Integer textCompare(List<String> originalText,List<String> newOriginal){
        if(originalText == null || originalText.isEmpty() || newOriginal == null || newOriginal.isEmpty()){
            return null;
        }

        // 重复数量
        int replaceNum = 0;
        for(String word:originalText){
            if(StringUtils.isBlank(word)){
                continue;
            }
            if (newOriginal.contains(word)) {
                replaceNum++;
            }
        }

        double a = (double) replaceNum/Math.max(originalText.size(), newOriginal.size());
        return (int)Math.floor(a * 100);
    }


}
