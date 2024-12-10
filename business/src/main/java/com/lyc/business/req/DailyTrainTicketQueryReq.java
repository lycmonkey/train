package com.lyc.business.req;

import com.lyc.common.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DailyTrainTicketQueryReq extends PageReq {

    private String trainCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String start;
    private String end;


}
