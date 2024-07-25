package io.jeeyeon.app.ticketReserve.interfaces.presentation.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CachedBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cacheInputStream(request);
    }

    private void cacheInputStream(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(request.getInputStream().readAllBytes());
        this.cachedBody = byteArrayOutputStream.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
    }

    private static class CachedBodyServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream byteArrayInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

        @Override
        public boolean isFinished() {
            return byteArrayInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }
    }
}
