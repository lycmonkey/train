package com.lyc.member.service;


import com.lyc.common.resp.CommonResp;
import com.lyc.member.req.MemberRegisterReq;

/**
 * @author lyc
 */
public interface MemberService {


    CommonResp<Long> register(MemberRegisterReq memberRegisterReq);

    void sendCode(MemberRegisterReq memberRegisterReq);


    CommonResp<Integer> count();

}
