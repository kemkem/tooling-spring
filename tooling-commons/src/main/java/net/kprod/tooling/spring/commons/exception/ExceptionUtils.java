package net.kprod.tooling.spring.commons.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    /**
     * Transform exception stacktrace to a {@link String}
     * @param e exception with stacktrace
     * @return stacktrace as string
     */
    public static String getStacktraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
