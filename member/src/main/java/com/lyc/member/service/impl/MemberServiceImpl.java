package com.lyc.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lyc.member.domain.Member;
import com.lyc.member.domain.MemberExample;
import com.lyc.member.mapper.MemberMapper;
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

    public long register(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        final List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollectionUtil.isNotEmpty(list)) {
            return list.get(0).getId();
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        int count = memberMapper.insert(member);
        if (count != 1) {
            return 0;
        }
        return member.getId();
    }




    @Override
    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }
}
