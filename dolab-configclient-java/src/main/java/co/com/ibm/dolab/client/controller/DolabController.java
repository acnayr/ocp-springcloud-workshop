package co.com.ibm.dolab.client.controller;

import co.com.ibm.dolab.client.model.ClientConfiguration;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class DolabController {

    private final ClientConfiguration clientConfiguration;

    /**
     * @param clientConfiguration
     */
    public DolabController(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @GetMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getConfig() {
        return new ResponseEntity(clientConfiguration.toString(), HttpStatus.OK);
    }
}
