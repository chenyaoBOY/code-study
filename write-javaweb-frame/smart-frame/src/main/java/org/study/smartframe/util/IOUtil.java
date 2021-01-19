package org.study.smartframe.util;

import org.apache.commons.lang3.CharEncoding;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author chenyao
 * @date 2021/1/19 14:26
 * @description
 */
public class IOUtil {
    public static String getInputStreamString(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int b;
        while ((b = stream.read(bytes, 0, bytes.length)) > 0) {
            sb.append(new String(bytes, 0, b));
        }
        return sb.toString();
    }
    public static String getInputStreamString(InputStream stream,Charset charsets) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int b;
        while ((b = stream.read(bytes, 0, bytes.length)) > 0) {
            sb.append(new String(bytes, 0, b, charsets));
        }
        return sb.toString();
    }
}
