package com.software.codetime.toolwindows.codetime;

import com.software.codetime.toolwindows.WebviewClosedConnection;
import com.software.codetime.toolwindows.WebviewOpenedConnection;
import com.software.codetime.toolwindows.WebviewResourceState;
import com.software.codetime.toolwindows.codetime.components.*;
import org.apache.commons.lang.StringUtils;
import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

import swdc.java.ops.manager.FileUtilManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.MalformedURLException;
import java.net.URL;

public class CodeTimeResourceHandler implements CefResourceHandler {

    private WebviewResourceState state = new WebviewClosedConnection();

    @Override
    public boolean processRequest(CefRequest cefRequest, CefCallback cefCallback) {
        String url = cefRequest.getURL();
        if (StringUtils.isNotBlank(url)) {

            String pathToResource = url.replace("http://myapp", "codetime");
            URL resourceUrl = getClass().getClassLoader().getResource(pathToResource);

            File f = new File(FileUtilManager.getCodeTimeViewHtmlFile());
            Writer writer = null;
            try {
                String html = buildHtml();

                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(f), StandardCharsets.UTF_8));
                writer.write(html);
            } catch (Exception e) {
                System.out.println("Dashboard write error: " + e.getMessage());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        System.out.println("Writer close error: " + e.getMessage());
                    }
                }
            }

            try {
                resourceUrl = f.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                state = new WebviewOpenedConnection(resourceUrl.openConnection());
            } catch (Exception e) {
                //
            }
            cefCallback.Continue();
            return true;
        }
        return false;
    }

    @Override
    public void getResponseHeaders(CefResponse cefResponse, IntRef responseLength, StringRef redirectUrl) {
        state.getResponseHeaders(cefResponse, responseLength, redirectUrl);
    }

    @Override
    public boolean readResponse(byte[] dataOut, int designedBytesToRead, IntRef bytesRead, CefCallback callback) {
        return state.readResponse(dataOut, designedBytesToRead, bytesRead, callback);
    }

    @Override
    public void cancel() {
        state.close();
        state = new WebviewClosedConnection();
    }

    private String buildHtml() {
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                MetadataHeader.getHtml() +
                "  <body>\n" +
                GettingStarted.getHtml() +
                FlowMode.getHtml() +
                Stats.getHtml() +
                Account.getHtml() +
                Teams.getHtml() +
                JsDependencies.getHtml() +
                "  </body>\n" +
                "</html>";
    }

}
