package com.lyc.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyc
 */
@Service
//@Slf4j
public class MemberServiceImpl implements MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

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



    public void sendCode(MemberRegisterReq memberRegisterReq) {
        LOG.info("手机号格式符合，开始执行逻辑");
        String mobile = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        final List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollectionUtil.isEmpty(list)) {
            LOG.info("手机号不存在，作为一个用户插入数据");
            final int res = memberMapper.insert(new Member(SnowUtil.getSnowflakeNextId(), mobile));
            if (res != 1) {
                throw new RuntimeException();
            }
        }
        LOG.info("插入成功或者手机号存在，生成验证码");
        final String code = RandomUtil.randomString(4);
        LOG.info("验证码：{}", code);

        LOG.info("保存信息到验证码表中");
        LOG.info("第三方接口发送验证码");

    }





    @Override
    public CommonResp<Integer> count() {
        return new CommonResp<>(Math.toIntExact(memberMapper.countByExample(null)));
    }
}
