package com.una.embyhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.mapper.SimultaneousPlaybackRecordDetailMapper;
import com.una.embyhub.model.entity.SimultaneousPlaybackRecordDetail;
import com.una.embyhub.service.SimultaneousPlaybackRecordDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class SimultaneousPlaybackRecordDetailServiceImpl
   extends ServiceImpl<SimultaneousPlaybackRecordDetailMapper, SimultaneousPlaybackRecordDetail>
   implements SimultaneousPlaybackRecordDetailService {
}
