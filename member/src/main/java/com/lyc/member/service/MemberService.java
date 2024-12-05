package com.lyc.member.service;


import com.lyc.common.resp.CommonResp;
import com.lyc.member.req.MemberLoginReq;
import com.lyc.member.req.MemberRegisterReq;
import com.lyc.member.resp.MemberLoginResp;

/**
 * @author lyc
 */
public interface MemberService {


    CommonResp<Long> register(MemberRegisterReq memberRegisterReq);

    void sendCode(MemberRegisterReq memberRegisterReq);


    CommonResp<Integer> count();

    CommonResp<MemberLoginResp> login(MemberLoginReq memberLoginReq);
}
