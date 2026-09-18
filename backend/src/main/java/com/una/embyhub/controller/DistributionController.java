package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeRequest;
import com.una.embyhub.model.dto.request.distribution.DistributionCustomExchangeReviewRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationDeleteRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationReviewRequest;
import com.una.embyhub.model.dto.request.distributionapplication.DistributionApplicationSave;
import com.una.embyhub.model.dto.request.distributionapplication.ExchangeRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductListRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductSaveRequest;
import com.una.embyhub.model.dto.request.pointsrecord.PointsRecordRequest;
import com.una.embyhub.model.dto.response.distribution.DistributionCustomExchangeResponse;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationResponse;
import com.una.embyhub.model.dto.response.distributionapplication.DistributionApplicationStatisticsResponse;
import com.una.embyhub.model.dto.response.pointsrecord.PointsRecordResponse;
import com.una.embyhub.model.entity.PointsExchangeProduct;
import com.una.embyhub.model.entity.UserPoints;
import com.una.embyhub.service.DistributionApplicationService;
import com.una.embyhub.service.DistributionCustomExchangeService;
import com.una.embyhub.service.EmbyUserService;
import com.una.embyhub.service.PointsExchangeProductService;
import com.una.embyhub.service.PointsRecordService;
import com.una.embyhub.service.UserPointsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"distribution"})
public class DistributionController {
   @Autowired
   private PointsExchangeProductService pointsExchangeProductService;
   @Autowired
   private UserPointsService userPointsService;
   @Autowired
   private DistributionApplicationService distributionApplicationService;
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private PointsRecordService pointsRecordService;
   @Autowired
   private DistributionCustomExchangeService distributionCustomExchangeService;

   @PostMapping({"product/list"})
   public List<PointsExchangeProduct> listProducts(@RequestBody(required = false) ProductListRequest request) {
      return this.pointsExchangeProductService.listProducts(request);
   }

   @PostMapping({"product/save"})
   @SaCheckPermission({"admin"})
   public void saveProduct(@RequestBody ProductSaveRequest request) {
      this.pointsExchangeProductService.saveProduct(request);
   }

   @PostMapping({"product/delete"})
   @SaCheckPermission({"admin"})
   public void deleteProduct(@RequestParam Long id) {
      this.pointsExchangeProductService.deleteProduct(id);
   }

   @PostMapping({"points/records"})
   public Page<PointsRecordResponse> queryPointsRecords(@RequestBody MybatisPlusPage<PointsRecordRequest> page) {
      return this.pointsRecordService.queryPageWithUserInfo(page);
   }

   @PostMapping({"myPoints"})
   public UserPoints getMyPoints() {
      return this.userPointsService.getMyPoints();
   }

   @PostMapping({"exchange"})
   public void exchange(@RequestBody ExchangeRequest request) {
      this.pointsExchangeProductService.exchange(request);
   }

   @PostMapping({"application/submit"})
   public void submitApplication(@RequestBody DistributionApplicationSave saveDto) {
      this.distributionApplicationService.submitApplication(saveDto);
   }

   @PostMapping({"application/myList"})
   public Page<DistributionApplicationResponse> myApplications(@RequestBody MybatisPlusPage<DistributionApplicationRequest> page) {
      return this.distributionApplicationService.selectMyList(page);
   }

   @PostMapping({"application/statistics"})
   public DistributionApplicationStatisticsResponse statistics(@RequestBody(required = false) DistributionApplicationRequest request) {
      return this.distributionApplicationService.getStatistics(request);
   }

   @PostMapping({"application/delete"})
   public void deleteApplication(@RequestBody DistributionApplicationDeleteRequest request) {
      this.distributionApplicationService.deleteApplication(request);
   }

   @PostMapping({"application/review"})
   @SaCheckPermission({"admin"})
   public void reviewApplication(@RequestBody DistributionApplicationReviewRequest request) {
      this.distributionApplicationService.reviewApplication(request);
   }

   @PostMapping({"setDistributor"})
   @SaCheckPermission({"admin"})
   public void setDistributor(@RequestParam Long userId, @RequestParam Integer isDistributor) {
      this.embyUserService.setDistributor(userId, isDistributor);
   }

   @PostMapping({"dashboard"})
   public Map<String, Object> getDashboard() {
      return this.distributionApplicationService.getDashboard();
   }

   @PostMapping({"customExchange/page"})
   public Page<DistributionCustomExchangeResponse> queryCustomExchange(@RequestBody MybatisPlusPage<DistributionCustomExchangeRequest> page) {
      return this.distributionCustomExchangeService.queryCustomExchange(page);
   }

   @PostMapping({"customExchange/review"})
   @SaCheckPermission({"admin"})
   public void reviewCustomExchange(@RequestBody DistributionCustomExchangeReviewRequest request) {
      this.distributionCustomExchangeService.reviewCustomExchange(request);
   }
}
