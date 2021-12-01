package com.example.loginpage.models;

import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FreeParking {

    private @Getter @Setter String freeParkingID;
    private @Getter @Setter String date;
    private @Getter @Setter String visa;

}
