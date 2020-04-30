package com.kazirahiv.httpserver.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    //Creates directories

    public static boolean  writeFile(String value,String file)
    {
        String currentDirectory = System.getProperty("user.dir");
        File targetFile = new File( currentDirectory.concat("/www/").concat(file).replace("/", File.separator));
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        try{
            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //Read File
    public static boolean deleteFile(String path) throws IOException {
        if(!path.isEmpty())
        {
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory.concat("/www").concat(path).replace("/", File.separator);
            if(directory.contains(".html"))
            {
                File file = new File(directory);
                if(file.exists())
                {
                    return  file.delete();

                }
            }
            return false;
        }
        return false;
    }


    //Read File
    public static String readFile(String path) throws IOException {
        if(!path.isEmpty())
        {
            String currentDirectory = System.getProperty("user.dir");
            String directory = currentDirectory.concat("/www").concat(path).replace("/", File.separator);
            if(directory.contains(".html"))
            {
                File file = new File(directory);
                if(file.exists())
                {
                    byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
                    return new String(encoded, StandardCharsets.UTF_8); //String in UTF8 Encoding
                }
            }
            return "";
        }
        return "";
    }
}
