package com.lyc.business.req;

import com.lyc.common.req.PageReq;
import lombok.Data;

@Data
public class TrainSeatQueryReq extends PageReq {

    private String trainCode;

}
