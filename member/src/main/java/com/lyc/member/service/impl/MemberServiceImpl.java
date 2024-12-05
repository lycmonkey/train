package com.lyc.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.lyc.common.exception.BusinessException;
import com.lyc.common.exception.BusinessExceptionEnum;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.util.SnowUtil;
import com.lyc.member.domain.Member;
import com.lyc.member.domain.MemberExample;
import com.lyc.member.mapper.MemberMapper;
import com.lyc.member.req.MemberRegisterReq;
import com.lyc.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyc
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    public CommonResp<Long> register(MemberRegisterReq memberRegisterReq) {
        String mobile = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        final List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);

        int count = memberMapper.insert(member);
        if (count != 1) {
            throw new RuntimeException();
        }
        return new CommonResp<>(member.getId());
    }




    @Override
    public CommonResp<Integer> count() {
        return new CommonResp<>(Math.toIntExact(memberMapper.countByExample(null)));
    }
}
