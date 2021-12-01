package com.example.loginpage.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FreeParkingUserCarOwners {

    private @Getter @Setter String freeParkingID;
    private @Getter @Setter String date;
    private @Getter @Setter String visa;
    private @Getter @Setter String password;
    private @Getter @Setter String name;
    private @Getter @Setter String address;
    private @Getter @Setter String phoneNumber;
    private @Getter @Setter String carPlate;
    private @Getter @Setter String parkingSlot;
}
