package com.example.practice1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SystemInfoServlet", value = "/system-info")
public class SystemInfoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;

        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        int processors = runtime.availableProcessors();

        long totalMemory = runtime.totalMemory() / mb;
        long freeMemory = runtime.freeMemory() / mb;
        long maxMemory = runtime.maxMemory() / mb;
        long usedMemory = totalMemory - freeMemory;

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Характеристики сервера:</h2>");
        out.println("<ul>");
        out.println("<li><b>Операційна система:</b> " + osName + " (Версія: " + osVersion + ")</li>");
        out.println("<li><b>Кількість ядер CPU (доступних для JVM):</b> " + processors + "</li>");
        out.println("<li><b>Використана пам'ять:</b> " + usedMemory + " MB</li>");
        out.println("<li><b>Вільна пам'ять:</b> " + freeMemory + " MB</li>");
        out.println("<li><b>Загальна виділена пам'ять:</b> " + totalMemory + " MB</li>");
        out.println("<li><b>Максимально доступна пам'ять:</b> " + maxMemory + " MB</li>");
        out.println("</ul>");
        out.println("</body></html>");
    }
}