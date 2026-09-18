package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.UserInvitationMapper;
import com.una.embyhub.model.entity.UserInvitation;
import com.una.embyhub.service.UserInvitationService;
import org.springframework.stereotype.Service;

@Service
public class UserInvitationServiceImpl extends ServiceImpl<UserInvitationMapper, UserInvitation> implements UserInvitationService {
}
