package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String fileContent = getFileContent(file);

        String[] lines = fileContent.split("(\r\n|\n)");
        Profile p = new Profile();

        for(String line : lines) {
            String[] keyValue = line.split(": ");
            switch (keyValue[0]) {
                case "Name":
                    p.setName(keyValue[1]);
                    break;
                case "Age":
                    p.setAge(Integer.parseInt(keyValue[1]));
                    break;
                case "Email":
                    p.setEmail(keyValue[1]);
                    break;
                case "Phone":
                    p.setPhone(Long.parseLong(keyValue[1]));
                    break;
                default:
                    break;
            }

        }

        return p;
    }

    private String getFileContent(File file) {
        StringBuilder content = new StringBuilder();;

        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel();) {

            long fileSize = inChannel.size();

            //Create buffer of the file size
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            for (int i = 0; i < fileSize; i++) {
                content.append((char)buffer.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
