package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String fileContent = getFileContent(file);

        String[] lines = fileContent.split("\r\n");
        String name = lines[0].split(": ")[1];
        int age = Integer.parseInt(lines[1].split(": ")[1]);
        String email = lines[2].split(": ")[1];
        long phone =  Long.parseLong(lines[3].split(": ")[1]);

        return new Profile(name, age, email, phone);
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
