package info.kgeorgiy.ja.potekhin.walk;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAFileVisitor implements FileVisitor<Path> {

    BufferedWriter writer;
    int buffSize = 8192;
    MessageDigest digest;


    SHAFileVisitor(BufferedWriter writer) throws NoSuchAlgorithmException {
        this.writer = writer;
        digest = MessageDigest.getInstance("SHA-1");
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
        try {
            InputStream fis = new FileInputStream(filePath.toFile());
            int n = 0;
            byte[] buffer = new byte[buffSize];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            StringBuilder sb = new StringBuilder();
            for(byte b: digest.digest())
                sb.append(String.format("%02x", b));
            writer.write(sb + " " + filePath + System.lineSeparator());
        } catch (Exception e) {
            writeNullHash(filePath);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path filePath, IOException exc) {
        RecursiveWalk.trackErrors("Can't visit file " + filePath);
        writeNullHash(filePath);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public <T> void writeNullHash(T filePath){
        try {
            writer.write(String.format("%040d", 0) + " " + filePath + System.lineSeparator());
        } catch (IOException e) {
            RecursiveWalk.trackErrors("Can't write in file");
        }
    }
}
