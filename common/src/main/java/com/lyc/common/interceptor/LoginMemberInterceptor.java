package com.lyc.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.lyc.common.context.LoginMemberContext;
import com.lyc.common.resp.MemberLoginResp;
import com.lyc.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lyc
 */
@Component
public class LoginMemberInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("获取token");
        final String token = request.getHeader("token");
        LOG.info("token：{}", token);
        if (StrUtil.isNotBlank(token)) {
            final JSONObject jsonObject = JwtUtil.getJSONObject(token);
            final MemberLoginResp memberLoginResp = JSONUtil.toBean(jsonObject, MemberLoginResp.class);
            LOG.info("token转为MemberLoginResp对象：{}", memberLoginResp);
            LoginMemberContext.setMember(memberLoginResp);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
