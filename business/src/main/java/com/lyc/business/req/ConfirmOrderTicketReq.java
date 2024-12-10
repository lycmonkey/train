package com.lyc.business.req;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmOrderTicketReq {

    @NotNull
    private Long passengerId;

    @NotNull
    private String passengerType;

    @NotNull
    private String passengerName;

    @NotNull
    private String passengerIdCard;

    @NotNull
    private String seatTypeCode;

    private String seat;


}
