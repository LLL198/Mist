package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.dto.response.embynotifydatadetails.EmbyNotifyDataDetailsResponseData;
import com.una.embyhub.model.entity.EmbyNotifyDataDetails;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface EmbyNotifyDataDetailsMapper extends BaseMapper<EmbyNotifyDataDetails> {
   String EPISODE_NUMBER_ORDER = "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(endd.episode_details, '集', 1), '第', -1) AS UNSIGNED)";

   @Select({"SELECT     endd.emby_notify_data_id as embyNotifyDataId,     endd.emby_info_id as embyInfoId,     SUBSTRING_INDEX(GROUP_CONCAT(endd.id ORDER BY CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(endd.episode_details, '集', 1), '第', -1) AS UNSIGNED) ASC, endd.id ASC SEPARATOR ','), ',', 30) AS idList,     SUBSTRING_INDEX(GROUP_CONCAT(endd.episode_details ORDER BY CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(endd.episode_details, '集', 1), '第', -1) AS UNSIGNED) ASC, endd.id ASC SEPARATOR '\\n'), '\\n', 30) AS episodeList,     SUM(endd.size) AS totalSize,     count(endd.id) count FROM     `emby_notify_data_details` endd WHERE     endd.del_flag = 0     AND endd.status = 2 GROUP BY     endd.emby_notify_data_id, endd.emby_info_id LIMIT 5"})
   List<EmbyNotifyDataDetailsResponseData> getEmbyNotifyDataDetails();
}
