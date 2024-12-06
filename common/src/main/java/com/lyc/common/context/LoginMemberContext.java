package com.lyc.common.context;

import com.lyc.common.resp.MemberLoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyc
 */
public class LoginMemberContext {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberContext.class);

    private static final ThreadLocal<MemberLoginResp> member = new ThreadLocal<>();
    public static MemberLoginResp getMember() {
        LOG.info("member:{}", member);
        return member.get();
    }
    public static void setMember(MemberLoginResp memberLoginResp) {
        member.set(memberLoginResp);
    }

    public static Long getMemberId() {
        try {
            return member.get().getId();
        } catch (Exception e) {
            LOG.error("获取登录会员信息异常",e);
            throw e;
        }
    }
}
