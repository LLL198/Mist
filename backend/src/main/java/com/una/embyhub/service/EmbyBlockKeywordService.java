package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.embyblockkeyword.EmbyBlockKeywordRequest;
import com.una.embyhub.model.dto.response.embyblockkeyword.EmbyBlockKeywordResponse;
import com.una.embyhub.model.entity.EmbyBlockKeyword;
import java.util.List;

public interface EmbyBlockKeywordService extends IService<EmbyBlockKeyword> {
   Page<EmbyBlockKeywordResponse> select(MybatisPlusPage<EmbyBlockKeywordRequest> page);

   void add(EmbyBlockKeywordRequest request);

   void update(EmbyBlockKeywordRequest request);

   void delete(Long id);

   List<String> getEnabledKeywords();

   List<String> getDefaultClientFilterPatterns();

   List<String> getEffectiveClientFilterPatterns();

   boolean isUsingDefaultClientFilterPatterns();

   boolean isClientFilterEnabled();

   void updateClientFilterEnabled(boolean enabled);

   boolean isClientFilterBlockUserEnabled();

   void updateClientFilterBlockUserEnabled(boolean enabled);

   List<String> getAllKeywords();

   void addIfNotExists(String keyword, String description);
}
