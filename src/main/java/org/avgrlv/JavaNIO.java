package org.avgrlv;

import java.net.URI;

public class JavaNIO {
    public static void main(String[] args) {
        observableFileRead();
    }

    private static void observableFileRead() {
        TryReadFileAsync readFileAsync = new TryReadFileAsync();
        readFileAsync.readFile();
    }
}


