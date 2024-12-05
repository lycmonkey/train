package com.lyc.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * @author lyc
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MemberLoginReq {

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4}$", message = "验证码格式错误")
    private String code;

}
