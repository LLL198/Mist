package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.EmbyNotifyDataDetailsMapper;
import com.una.embyhub.model.entity.EmbyNotifyDataDetails;
import com.una.embyhub.service.EmbyNotifyDataDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class EmbyNotifyDataDetailsServiceImpl extends ServiceImpl<EmbyNotifyDataDetailsMapper, EmbyNotifyDataDetails> implements EmbyNotifyDataDetailsService {
}
