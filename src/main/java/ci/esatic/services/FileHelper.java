package ci.esatic.services;

import java.util.UUID;

public class FileHelper {

    public static String generateFileName(String fileName)
    {
        int lastIndex = fileName.lastIndexOf(".");
        String ext = fileName.substring(lastIndex);
        return UUID.randomUUID().toString().replace("-", "") + ext;
    }
}
