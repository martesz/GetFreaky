package org.martin.getfreaky;

import android.app.Application;


/**
 * Created by martin on 2016. 10. 10..
 */

public class GlobalVariables extends Application {
    public String currentToken;

    public String serverSocket;

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public String getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(String serverSocket) {
        this.serverSocket = serverSocket;
    }
}
