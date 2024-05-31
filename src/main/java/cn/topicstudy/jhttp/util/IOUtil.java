package cn.topicstudy.jhttp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
    private IOUtil() {
    }

    public static String inputStreamToString(InputStream is, String charset) throws IOException {
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        return new String(bytes, charset);
    }

    public static byte[] inputStreamToBytes(InputStream is) throws IOException {
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        return bytes;
    }

    public static byte[] fileToBytes(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

}
