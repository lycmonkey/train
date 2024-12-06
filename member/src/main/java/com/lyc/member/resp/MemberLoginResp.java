package com.lyc.member.resp;

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
    private Long id;
    private String mobile;
    private String token;
}
