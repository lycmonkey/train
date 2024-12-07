package com.lyc.member.req;

import com.lyc.common.req.PageReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerQueryReq extends PageReq {
    private Long memberId;
}
