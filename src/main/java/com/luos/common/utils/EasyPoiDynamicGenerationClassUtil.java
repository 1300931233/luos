package com.luos.common.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 类描述: [动态生成EasyPoi解析实体类]</br>
 * 初始作者:  AirOrangeWorkSpace </br>
 * 创建日期: 2020/5/15 16:28<br/>
 * 开始版本: 1.0.0<br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 */
public class EasyPoiDynamicGenerationClassUtil {
    public static final String CLASS_NAME_PREFIX = "com.ev.common.vo.EasyPoiExcelVO@";
    public static final String ANNOTATION_PACKAGE_NAME = "cn.afterturn.easypoi.excel.annotation.Excel";
    public static final String STRING_PACKAGE_NAME = "java.lang.String";
    /**
     * 方法描述: [获取动态生成EasyPoi实体VO]</br>
     * 初始作者:  AirOrangeWorkSpace </br>
     * 创建日期: 2020/5/15 16:38<br/>
     * 开始版本: 1.0.0<br/>
     * =================================================<br/>
     * 修改记录：<br/>
     * 修改作者 日期 修改内容<br/>
     * ================================================<br/>
     * @param  list:获取用户自定义表头
     * @Return : 动态生成的EasyPoi实体类
     */
    public static Class<?> generatePrototypeClass(List<CustomizedColumnVO> list) throws NotFoundException, CannotCompileException, IOException {

        String className = CLASS_NAME_PREFIX + UUID.randomUUID().toString();

        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.makeClass(className);
        ClassFile ccFile = clazz.getClassFile();
        ConstPool constpool = ccFile.getConstPool();
        //添加fields
        addExpressField(pool, clazz, constpool,list);

        return clazz.toClass();
    }

    private static void addExpressField(ClassPool pool, CtClass clazz, ConstPool constpool,List<CustomizedColumnVO> list) throws CannotCompileException, NotFoundException {
        // 将数据库查出动态附上property 属性
        for (CustomizedColumnVO customizedColumnVO : list) {
            addFieldAndAnnotation(pool, clazz, constpool, customizedColumnVO.getName(), customizedColumnVO.getProperty());
        }
    }


    private static void addFieldAndAnnotation(ClassPool pool, CtClass clazz, ConstPool constpool, String titleName, String fieldName) throws NotFoundException, CannotCompileException {

        //生成field
        CtField field = new CtField(pool.get(STRING_PACKAGE_NAME), fieldName, clazz);
        field.setModifiers(Modifier.PUBLIC);

        //添加easypoi的注解
        AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(ANNOTATION_PACKAGE_NAME, constpool);
        annotation.addMemberValue("name", new StringMemberValue(titleName, constpool));
        fieldAttr.addAnnotation(annotation);
        field.getFieldInfo().addAttribute(fieldAttr);

        //生成get,set方法
        clazz.addMethod(CtNewMethod.getter("get" + upperFirstLatter(fieldName), field));
        clazz.addMethod(CtNewMethod.setter("set" + upperFirstLatter(fieldName), field));

        clazz.addField(field);
    }

    private static String upperFirstLatter(String letter) {
        return letter.substring(0, 1).toUpperCase() + letter.substring(1);
    }



    /**
     *
     * @param customizedColumn  封装的表头数据
     * @param file excel文件
     * @return
     * @throws IOException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    public static List<?> exportExcel(List<CustomizedColumnVO> customizedColumn, File file) throws IOException, CannotCompileException, NotFoundException {
        if (!file.exists()) {
            return null;
        }

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        params.setStartRows(0);

        // 检验表的是否合法
        String[] importFields = customizedColumn
                .stream()
                .map(CustomizedColumnVO::getName).toArray(String[]::new);
        params.setImportFields(importFields);

        // 生成动态的实体类（核心代码）
        Class<?> salaryArchivesClass = EasyPoiDynamicGenerationClassUtil.generatePrototypeClass(customizedColumn);

        List<?> clientDataList;
        FileInputStream fileInputStream=null;
        try {
             fileInputStream = new FileInputStream(file);
            clientDataList = ExcelImportUtil.importExcel(fileInputStream, salaryArchivesClass, params);
            //ExcelImportUtil.importExcelMore(fileInputStream, salaryArchivesClass, params);
            return clientDataList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if(null !=fileInputStream){
                fileInputStream.close();
            }
        }
        // TODO 根据数据处理逻辑
        return null;
    }

    //转换file

    public static File multipartFileToFile(MultipartFile file){

        File toFile = null;
        InputStream ins = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                //System.out.println("文件路径》》》》》》》》》》》》："+ ResourceUtils.getURL("classpath:").getPath());
                inputStreamToFile(ins, toFile);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(null != ins)
                        ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os=new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null !=os)
                    os.close();
                if(null !=ins)
                    ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }


    /**
     * 获取excel 封面对应列(默认在最后一列)
     * @param filePath  excel地址
     * @param dir excel 后缀 .xls .xlsx
     * @return
     */
    public static String getExcelCell(String filePath,String dir){
        FileInputStream fis=null;
        try {
            fis = new FileInputStream(filePath);
            Workbook workbook = null;

            // excel类型
            if (dir.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else if (dir.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            }

            Sheet sheet = workbook.getSheetAt(0);
            int firstRowNum = sheet.getFirstRowNum();
            //int lastRowNum = sheet.getLastRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            //int firstCellNum = firstRow.getFirstCellNum();
            int lastCellNum = firstRow.getLastCellNum();

            //新增的四句话，设置CELL格式为文本格式
            CellStyle cellStyle2 = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            cellStyle2.setDataFormat(format.getFormat("@"));
            //最后一列
            Cell cell = sheet.getRow(0).getCell(lastCellNum - 1);
            cell.setCellStyle(cellStyle2);
            cell.setCellType(CellType.STRING);
            //最后一列内容
            String stringCellValue=cell.getStringCellValue();
            if(!"封面地址".equals(stringCellValue)){
                return null;
            }
            return excelColIndexToStr(lastCellNum);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(null != fis){
                    fis.close();
                }
            }catch (Exception e){

            }
        }
    }


    /**
     * Excel列索引从1开始
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

}
