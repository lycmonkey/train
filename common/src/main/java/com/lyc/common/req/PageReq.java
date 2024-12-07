package com.lyc.common.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lyc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReq {

    @NotNull(message = "页码不能为空")
    private Integer page;

    @NotNull(message = "每页条数不能为空")
    @Max(value = 100, message = "每页最大条数不能超过100条")
    private Integer size;
}
