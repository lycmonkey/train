package com.lyc.member.service.impl;

import com.lyc.member.mapper.MemberMapper;
import com.lyc.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lyc
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public int count() {
        return memberMapper.count();
    }
}
