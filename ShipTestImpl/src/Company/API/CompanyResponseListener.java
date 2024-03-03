package Company.API;

import Company.BusinessLogic.CompanyController;

import java.io.BufferedReader;
import java.io.IOException;

public class CompanyResponseListener extends Thread {
    private final BufferedReader reader;

    private final CompanyController controller;

    private final CompanyAPI api;

    public CompanyResponseListener(BufferedReader reader, CompanyController controller, CompanyAPI api) {
        this.reader = reader;
        this.controller = controller;
        this.api = api;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var json = reader.readLine();

                var handler = new CompanyResponseHandler(controller, api, json);
                handler.handle();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

