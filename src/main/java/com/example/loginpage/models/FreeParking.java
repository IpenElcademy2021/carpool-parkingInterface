package com.example.loginpage.models;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FreeParking {

    private @Getter @Setter String freeParkingID;
    private @Getter @Setter String visa;
    private @Getter @Setter String date;

    public FreeParking(String date) {
        this.date = date;
    }
}
