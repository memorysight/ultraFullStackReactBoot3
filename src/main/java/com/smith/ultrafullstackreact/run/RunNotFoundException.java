package com.smith.ultrafullstackreact.run;

import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus
public class RunNotFoundException extends RuntimeException {
    public RunNotFoundException() {
        super("Run not found");

    }
}


