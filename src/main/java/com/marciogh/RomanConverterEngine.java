package com.marciogh;

import org.springframework.stereotype.Component;

@Component
public class RomanConverterEngine {

    public String fromInt(int i) {
        if (i == 1) {
            return "I";
        }
        if (i == 2) {
            return "II";
        }
        if (i == 3) {
            return "III";
        }
        return "";
    }

}
