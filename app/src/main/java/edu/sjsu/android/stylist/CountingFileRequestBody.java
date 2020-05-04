package edu.sjsu.android.stylist;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class CountingFileRequestBody extends RequestBody {
    private File file;
    private String contentType;
    private static final int SEGMENT_SIZE = 2048;

    public CountingFileRequestBody(File f, String c)
    {
        file = f;
        contentType = c;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        Source source = null;
        try
        {
            Log.d("log", "is file empty " + (file == null));
            source = Okio.source(file);
            Log.d("log", "is source null " + (source == null));
            long total = 0;
            long read = -1;

            while ((read = source.read(bufferedSink.getBuffer(), SEGMENT_SIZE)) != -1)
            {
                total += read;
                bufferedSink.flush();
                int percentage = (int)((total * 100) / contentLength());
            }

        } finally {
            Log.d("log", "is source null in finally " + (source == null));
            Util.closeQuietly(source);
        }

    }
}


