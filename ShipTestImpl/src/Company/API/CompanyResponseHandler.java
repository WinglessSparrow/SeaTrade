package Company.API;


import Company.BusinessLogic.CompanyController;
import Company.DTO.CompanyResponseDTO;

public class CompanyResponseHandler {
    private final CompanyController controller;
    private final CompanyAPI api;
    private final String json;

    public CompanyResponseHandler(CompanyController controller, CompanyAPI api, String json) {
        this.json = json;
        this.controller = controller;
        this.api = api;
    }

    private void handleResponse(CompanyResponseDTO response) {
        if (response.success()) {
            if (response.ship() != null) {
                controller.handleResponse(response.ship());
            } else {
                controller.handleHarbours(response.harbours());
            }
        } else {
            System.err.println(response.error());
        }
    }

    public void handle() {
        var response = api.parseResponse(json);

        handleResponse(response);
    }

}
