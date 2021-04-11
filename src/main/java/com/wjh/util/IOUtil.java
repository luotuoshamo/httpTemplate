package com.wjh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOUtil {
    private IOUtil() {
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] fileToBytes(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

}
