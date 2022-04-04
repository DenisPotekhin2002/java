package info.kgeorgiy.ja.potekhin.walk;

import java.io.*;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;

public class RecursiveWalk {

    private static final boolean showExceptions = true;

    public static void trackErrors(String error) {
        if (showExceptions) {
            System.err.println(error);
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            if (showExceptions) {
                System.err.println("Wrong input: expected two arguments");
            }
            return;
        }
        Path in;
        try {
            in = Paths.get(args[0]);
            Path out;
            try {
                out = Paths.get(args[1]);
                try {
                    walkRecur(in, out);
                } catch (IOException e) {
                    trackErrors(e.getMessage());
                } catch (NoSuchAlgorithmException e){
                    trackErrors("No such hashing algorithm");
                }
            } catch (SecurityException | InvalidPathException | FileSystemNotFoundException e) {
                trackErrors("Problems with writing to output file: " + e.getMessage());
            }
        } catch (SecurityException | InvalidPathException | FileSystemNotFoundException e) {
            trackErrors("Problems with reading from input file: " + e.getMessage());
        }
    }

    public static void walkRecur(Path in, Path out) throws IOException, NoSuchAlgorithmException {
        try (
        BufferedReader reader = Files.newBufferedReader(in)
        ) {
            try (
                    BufferedWriter writer = Files.newBufferedWriter(out)
            ) {
                if (out.getParent() != null) {
                    Files.createDirectories(out.getParent());
                }
                String cur;
                SHAFileVisitor visitor = new SHAFileVisitor(writer);
                while ((cur = reader.readLine()) != null) {
                    try {
                        Path curPath = Paths.get(cur);
                        Files.walkFileTree(curPath, visitor);
                    } catch (InvalidPathException e) {
                        visitor.writeNullHash(cur);
                        trackErrors(e.getMessage());
                    }
                }
            } catch (IOException e){
                throw new IOException("Problems with output file " + e.getMessage());
            }
        } catch (IOException e){
            throw new IOException("Problems with input file " + e.getMessage());
        }
    }
}
