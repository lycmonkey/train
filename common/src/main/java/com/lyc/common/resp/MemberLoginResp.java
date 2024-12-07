package com.lyc.common.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 * @author lyc
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MemberLoginResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String mobile;
    private String token;
}
