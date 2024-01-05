package org.example.codePlatform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class ExecutePythonCode {
    public static void main(String[] args) {
        // Python代码字符串
//        String pythonCode = "print('Hello from Python!')\n"
//                + "result = 3 + 5\n"
//                + "print(result)";


        String pythonCode = "import matplotlib.pyplot as plt\n" +
                "\n" +
                "# 创建一个简单的示例图表\n" +
                "plt.plot([1, 2, 3, 4])\n" +
                "plt.xlabel('X-axis')\n" +
                "plt.ylabel('Y-axis')\n" +
                "plt.title('Sample Plot')\n" +
                "\n" +
                "# 保存图像为文件\n" +
                "plt.savefig('path_to_generated_image.png')\n" +
                "print('Image saved')";


        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python");
            Process process = processBuilder.start();

            // 获取Python解释器的标准输入
            OutputStream stdin = process.getOutputStream();

            // 向Python标准输入发送Python代码
            stdin.write(pythonCode.getBytes());
            stdin.flush();
            stdin.close();

            // 获取Python解释器的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待Python进程完成
            if (process.waitFor(10, TimeUnit.SECONDS)) {
                int exitCode = process.exitValue();
                System.out.println("Python process exited with code " + exitCode);
            } else {
                // Python进程超时
                System.out.println("Python process did not complete within the specified time.");
                process.destroy();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

