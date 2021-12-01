package com.example.loginpage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class PoolingPropose {

    private @Getter @Setter Date date;
    private @Getter @Setter String region;
    private @Getter @Setter String pickUpPoint;
    private @Getter @Setter String pickUpTime;
    private @Getter @Setter String departureTime;
    private @Getter @Setter int seat;
    private @Getter @Setter User user;
}
