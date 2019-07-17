package cn.exceptioncode.common.io;


import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileUtils extends org.apache.commons.io.FileUtils {

    public static void main(String[] args) throws IOException {
        File m3u8Path = new File("D:\\m3u8");
        Collection<File> m3u8FileList = listFiles(m3u8Path,new String[]{"m3u8"},false);

        for (File file : m3u8FileList) {
            String m3u8FilePath = file.getPath();
            List<File> contentFileList =  getContentFileCollectionBym3u8(new File(m3u8FilePath));
            byte[] bytes = null ;
            for (File contentFile : contentFileList) {
                if(!contentFile.exists()){
                    System.err.println("m3u8内容文件不存在["+contentFile.getPath()+"]");
                }else {
                    if(bytes==null){
                        bytes = readFileToByteArray(contentFile);
                    }
                    bytes =  ArrayUtils.addAll(bytes,readFileToByteArray(contentFile));
                }
            }
            File m3u8File = new File(m3u8FilePath+".ts");
            if(m3u8File.exists()){
                m3u8File.delete();
            }
            writeByteArrayToFile(m3u8File,bytes);
        }


    }


    private static List<File> getContentFileCollectionBym3u8(File file) throws IOException {
        File parentFile = file.getParentFile();
        List<File> contentfileList = new ArrayList();
        List<String> lines = readLines(file);
        for (String line : lines) {
            if (line.startsWith("file:///")) {
                String[] strArr = line.split("/");
                String contentPath = parentFile.getPath() + "\\" + strArr[strArr.length - 2] + "\\" + strArr[strArr.length - 1];
                System.out.println(contentPath);
                contentfileList.add(new File(contentPath));
            }
        }
        return contentfileList;
    }

}
