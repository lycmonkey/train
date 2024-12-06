package com.lyc.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.lyc.common.exception.BusinessException;
import com.lyc.common.exception.BusinessExceptionEnum;
import com.lyc.common.resp.CommonResp;
import com.lyc.common.util.JwtUtil;
import com.lyc.common.util.SnowUtil;
import com.lyc.member.domain.Member;
import com.lyc.member.domain.MemberExample;
import com.lyc.member.mapper.MemberMapper;
import com.lyc.member.req.MemberLoginReq;
import com.lyc.member.req.MemberRegisterReq;
import com.lyc.member.resp.MemberLoginResp;
import com.lyc.member.service.MemberService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        final Member list = getMemberByMobile(mobile);
        if (ObjectUtil.isNull(list)) {
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
        final Member list = getMemberByMobile(mobile);
        if (ObjectUtil.isNull(list)) {
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

    private Member getMemberByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        final List<Member> members = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(members)) {
            return null;
        }
        return members.get(0);
    }

    @Override
    public CommonResp<MemberLoginResp> login(MemberLoginReq memberLoginReq) {

        final String mobile = memberLoginReq.getMobile();
        final String code = memberLoginReq.getCode();
        LOG.info("从数据库中查询用户");
        final Member member = getMemberByMobile(mobile);
        if (ObjectUtil.isNull(member)) {
            LOG.info("抛出“用户不存在”异常");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        LOG.info("用户存在，开始进行验证码校验");
        if (!"8888".equals(code)) {
            LOG.info("抛出“验证码错误”异常");
            throw new BusinessException(BusinessExceptionEnum.MEMBER_CODE_NOT_EXIST);
        }
        LOG.info("验证码校验通过");
        final MemberLoginResp memberLoginResp = BeanUtil.copyProperties(member, MemberLoginResp.class);
        final String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return new CommonResp<>(memberLoginResp);
    }

    @Override
    public CommonResp<Integer> count() {
        return new CommonResp<>(Math.toIntExact(memberMapper.countByExample(null)));
    }
}
