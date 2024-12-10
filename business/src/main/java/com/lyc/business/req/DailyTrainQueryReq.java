package com.lyc.business.req;

import com.lyc.common.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DailyTrainQueryReq extends PageReq {

    private String code;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
