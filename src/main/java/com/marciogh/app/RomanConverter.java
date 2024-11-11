package com.marciogh.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RomanConverter {

    @Autowired
    RomanConverterEngine romanConverterEngine;

    public String fromInt(int i) {
        return romanConverterEngine.fromInt(i);
    }
}
