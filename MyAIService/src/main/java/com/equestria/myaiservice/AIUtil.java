package com.equestria.myaiservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AIUtil {

    public static String recognize(String imagePath) {
        StringBuilder result = new StringBuilder(); // 用于存储Python脚本的输出
        try {
            // 提供Python解释器路径以及predict.py的绝对路径
            ProcessBuilder pb = new ProcessBuilder("python", "predict.py", imagePath);            pb.redirectErrorStream(true); // 将错误流合并到标准输出流中

            Process process = pb.start();

            // 获取Python脚本的输出，使用UTF-8编码处理
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n"); // 将每行拼接到result中
                System.out.println(line);
            }

            // 等待脚本执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Python脚本执行时出错，退出代码：" + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // 返回Python脚本的输出结果
        return result.toString().trim();
    }
}