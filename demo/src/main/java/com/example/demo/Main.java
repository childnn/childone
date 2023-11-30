package com.example.demo;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;
import ucar.nc2.Variable;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String file_name = "202308160700_1.nc";
        System.out.println("args = " + Arrays.asList(args));
        dataSave(file_name);

    }

    public static Map<String, Object> dataSave(String filename) throws IOException {

        // String currentDirectory = System.getProperty("user.dir");
        // ClassPathResource classPathResource = new ClassPathResource(filename);
        // if (!classPathResource.exists()) {
        //     System.out.println("create " + filename);
        //     Files.createFile(classPathResource.getFile().toPath());
        // } else {
        //     System.out.println("class path file exists: " + filename);
        // }

        // 创建一个HashMap实例
        Map<String, Object> dataMap = new HashMap<>();

        // 创建NetcdfFile对象表示当前目录下的*.nc文件
        try (NetcdfFile ncFile = NetcdfFiles.open(filename/* currentDirectory + File.separator +
        filename
        */))  // "202308160700_1.nc"
        {
            List<Variable> variables = ncFile.getVariables();

            // 遍历变量并打印变量名
            for (Variable var : variables) {
                String variableName = var.getShortName();

                // 读取变量数据
                Variable variable = ncFile.findVariable(variableName);  // PRE_10m

                Array data = variable.read();
                int[] shape = data.getShape();

                if (shape.length == 1) {
                    // 创建一维矩阵
                    long num = ncFile.findVariable(variableName).getSize();
                    String[] matrix = new String[(int) num];
                    for (int i = 0; i < variable.getSize(); i++) {
                        Double temp = data.getDouble(i);
                        int index = (int) (i % num);
                        matrix[index] = temp.toString();
                    }
                    dataMap.put(variableName, matrix);
                } else {
                    Index index = data.getIndex();

                    // 创建三维矩阵
                    String[][][] matrix = new String[shape[0]][shape[1]][shape[2]];

                    for (int i = 0; i < shape[0]; i++) {
                        for (int j = 0; j < shape[1]; j++) {
                            String[] strings = new String[shape[2]];
                            for (int k = 0; k < shape[2]; k++) {
                                Float dval = data.getFloat(index.set(i, j, k));
                                strings[k] = dval.toString();
                            }
                            matrix[i][j] = strings;
                        }
                    }
                    dataMap.put(variableName, matrix);
                }


                System.out.println(variableName);

                System.out.println(dataMap);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataMap;
    }
}