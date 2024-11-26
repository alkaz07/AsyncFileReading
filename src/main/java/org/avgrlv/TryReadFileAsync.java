package org.avgrlv;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TryReadFileAsync implements CompletionHandler<Integer, AsynchronousFileChannel> {
    private int readBytes = 0;
    private AsynchronousFileChannel channel = null;

    private ByteBuffer byteBuffer = null;
    private int executed = 0;

    public void readFile() {
        Path file = Paths.get("src/main/resources/fileTest.txt");
        AsynchronousFileChannel channel = null;
        try {
            channel = AsynchronousFileChannel.open(file);
        } catch (IOException e) {
            System.err.println("Не удалось открыть файл: " + file.toString());
            System.exit(1);
        }
        byteBuffer = ByteBuffer.allocate(1000);

        channel.read(byteBuffer, readBytes, channel, this);
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }

    @Override
    public void completed(Integer result, AsynchronousFileChannel attachment) {
        if (result != -1) {
            readBytes += result;
            String fileInfo = new String(byteBuffer.array());
            System.out.println("Прочитана строка (executed = " + executed + "): " + fileInfo);
            executed++;
            byteBuffer.clear();
        }
        attachment.read(byteBuffer, readBytes, attachment, this);
    }

    @Override
    public void failed(Throwable exc, AsynchronousFileChannel attachment) {
        System.err.println("Ошибка чтения");
    }
}
