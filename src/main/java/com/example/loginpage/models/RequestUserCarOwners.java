package com.example.loginpage.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestUserCarOwners {

    private @Getter @Setter String requestId;
    private @Getter @Setter String date;
    private @Getter @Setter String driverVisa;
    private @Getter @Setter String status;
    private @Getter @Setter String visa;

    public RequestUserCarOwners(String date, String parkingSlot, String status, String driverVisa) {
    }
}
