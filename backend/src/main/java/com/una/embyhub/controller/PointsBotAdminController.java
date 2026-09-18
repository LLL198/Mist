package com.una.embyhub.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotFoamBagConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotFoamBagRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotGameConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotHellVaultTopUpRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLedgerRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLevelConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryEntryRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotLotteryRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotPrizeConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedPacketRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigSave;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotRedeemConfigUpdate;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserAdjustRequest;
import com.una.embyhub.model.dto.request.pointsbot.PointsBotUserRequest;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotFoamBagResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotGameConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLedgerStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLevelConfigStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryEntryStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotLotteryStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotPrizeConfigStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketCancelResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketClaimResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedPacketResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotRedeemConfigStatsResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserAdjustResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserResponse;
import com.una.embyhub.model.dto.response.pointsbot.PointsBotUserStatsResponse;
import com.una.embyhub.model.entity.PointsBotRedPacket;
import com.una.embyhub.pointsbot.PointsBot;
import com.una.embyhub.pointsbot.config.PointsBotInitializer;
import com.una.embyhub.pointsbot.model.RedPacketExpireResult;
import com.una.embyhub.pointsbot.service.HellDiceGameService;
import com.una.embyhub.pointsbot.service.PointsBotFoamBagService;
import com.una.embyhub.pointsbot.service.PointsBotGameConfigService;
import com.una.embyhub.service.PointsBotLedgerManageService;
import com.una.embyhub.service.PointsBotLevelConfigService;
import com.una.embyhub.service.PointsBotLotteryEntryManageService;
import com.una.embyhub.service.PointsBotLotteryManageService;
import com.una.embyhub.service.PointsBotPrizeConfigService;
import com.una.embyhub.service.PointsBotRedPacketManageService;
import com.una.embyhub.service.PointsBotRedeemConfigService;
import com.una.embyhub.service.PointsBotUserManageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"pointsBot"})
@Validated
public class PointsBotAdminController {
   @Autowired
   private PointsBotUserManageService pointsBotUserManageService;
   @Autowired
   private PointsBotLedgerManageService pointsBotLedgerManageService;
   @Autowired
   private PointsBotLotteryManageService pointsBotLotteryManageService;
   @Autowired
   private PointsBotLotteryEntryManageService pointsBotLotteryEntryManageService;
   @Autowired
   private PointsBotRedeemConfigService pointsBotRedeemConfigService;
   @Autowired
   private PointsBotLevelConfigService pointsBotLevelConfigService;
   @Autowired
   private PointsBotPrizeConfigService pointsBotPrizeConfigService;
   @Autowired
   private PointsBotInitializer pointsBotInitializer;
   @Autowired
   private PointsBotGameConfigService pointsBotGameConfigService;
   @Autowired
   private PointsBotFoamBagService pointsBotFoamBagService;
   @Autowired
   private PointsBotRedPacketManageService pointsBotRedPacketManageService;
   @Autowired
   private PointsBot pointsBot;

   @PostMapping({"redPacket/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotRedPacketResponse> selectRedPacket(@RequestBody @Valid MybatisPlusPage<PointsBotRedPacketRequest> page) {
      return this.pointsBotRedPacketManageService.select(page);
   }

   @GetMapping({"redPacket/{redPacketId}/claims"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotRedPacketClaimResponse> listRedPacketClaims(
      @PathVariable @Positive(message = "红包ID不合法") Long redPacketId,
      @RequestParam(defaultValue = "1") @Positive(message = "页码不合法") long current,
      @RequestParam(defaultValue = "10") @Positive(message = "每页条数不合法") long size
   ) {
      return this.pointsBotRedPacketManageService.listClaims(redPacketId, current, size);
   }

   @PostMapping({"redPacket/{redPacketId}/cancel"})
   @SaCheckPermission({"admin"})
   public PointsBotRedPacketCancelResponse cancelRedPacket(@PathVariable @Positive(message = "红包ID不合法") Long redPacketId) {
      RedPacketExpireResult result = this.pointsBot.cancelRedPacketByAdmin(redPacketId);
      PointsBotRedPacket redPacket = result.redPacket();
      int totalCount = redPacket.getTotalCount() == null ? 0 : Math.max(0, redPacket.getTotalCount());
      int remainingCount = redPacket.getRemainingCount() == null ? 0 : Math.max(0, redPacket.getRemainingCount());
      return new PointsBotRedPacketCancelResponse(
         redPacket.getId(),
         redPacket.getStatus(),
         redPacket.getRemainingPoints(),
         redPacket.getRemainingCount(),
         Math.max(0, totalCount - remainingCount),
         redPacket.getRefundedPoints(),
         redPacket.getRefundedAt(),
         redPacket.getFinishedAt()
      );
   }

   @PostMapping({"foamBag/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotFoamBagResponse> selectFoamBag(@RequestBody @Valid MybatisPlusPage<PointsBotFoamBagRequest> page) {
      return this.pointsBotFoamBagService.selectAdmin(page);
   }

   @GetMapping({"foamBag/config"})
   @SaCheckPermission({"admin"})
   public PointsBotFoamBagConfigResponse getFoamBagConfig() {
      return this.pointsBotFoamBagService.getConfig();
   }

   @PutMapping({"foamBag/config"})
   @SaCheckPermission({"admin"})
   public PointsBotFoamBagConfigResponse updateFoamBagConfig(@RequestBody @Valid PointsBotFoamBagConfigUpdate request) {
      return this.pointsBotFoamBagService.updateConfig(request);
   }

   @GetMapping({"game/list"})
   @SaCheckPermission({"admin"})
   public List<PointsBotGameConfigResponse> listGames() {
      return this.pointsBotGameConfigService.list();
   }

   @PostMapping({"game/update"})
   @SaCheckPermission({"admin"})
   public PointsBotGameConfigResponse updateGame(@RequestBody @Valid PointsBotGameConfigUpdate request) {
      PointsBotGameConfigResponse response = this.pointsBotGameConfigService.update(request);
      this.pointsBotInitializer.restart();
      return response;
   }

   @PostMapping({"game/hell-vault/top-up"})
   @SaCheckPermission({"admin"})
   public HellDiceGameService.VaultView topUpHellDiceVault(@RequestBody @Valid PointsBotHellVaultTopUpRequest request) {
      return this.pointsBotGameConfigService.topUpHellDiceVault(request.getAmount());
   }

   @PostMapping({"reload"})
   @SaCheckPermission({"admin"})
   public void reload() {
      this.pointsBotInitializer.restart();
   }

   @PostMapping({"user/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotUserResponse> selectUser(@RequestBody @Valid MybatisPlusPage<PointsBotUserRequest> page) {
      return this.pointsBotUserManageService.select(page);
   }

   @PostMapping({"user/adjust"})
   @SaCheckPermission({"admin"})
   public PointsBotUserAdjustResponse adjustUserPoints(@RequestBody @Valid PointsBotUserAdjustRequest request) {
      return this.pointsBotUserManageService.adjustPoints(request);
   }

   @PostMapping({"ledger/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotLedgerResponse> selectLedger(@RequestBody @Valid MybatisPlusPage<PointsBotLedgerRequest> page) {
      return this.pointsBotLedgerManageService.select(page);
   }

   @PostMapping({"lottery/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotLotteryResponse> selectLottery(@RequestBody @Valid MybatisPlusPage<PointsBotLotteryRequest> page) {
      return this.pointsBotLotteryManageService.select(page);
   }

   @PostMapping({"lotteryEntry/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotLotteryEntryResponse> selectLotteryEntry(@RequestBody @Valid MybatisPlusPage<PointsBotLotteryEntryRequest> page) {
      return this.pointsBotLotteryEntryManageService.select(page);
   }

   @PostMapping({"redeemConfig/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotRedeemConfigResponse> selectRedeemConfig(@RequestBody @Valid MybatisPlusPage<PointsBotRedeemConfigRequest> page) {
      return this.pointsBotRedeemConfigService.select(page);
   }

   @PostMapping({"redeemConfig/insert"})
   @SaCheckPermission({"admin"})
   public void insertRedeemConfig(@RequestBody @Valid PointsBotRedeemConfigSave save) {
      this.pointsBotRedeemConfigService.insertConfig(save);
   }

   @PostMapping({"redeemConfig/update"})
   @SaCheckPermission({"admin"})
   public void updateRedeemConfig(@RequestBody @Valid PointsBotRedeemConfigUpdate update) {
      this.pointsBotRedeemConfigService.updateConfig(update);
   }

   @PostMapping({"redeemConfig/delete"})
   @SaCheckPermission({"admin"})
   public void deleteRedeemConfig(@RequestParam @Positive(message = "配置ID不合法") Long configId) {
      this.pointsBotRedeemConfigService.deleteByConfigId(configId);
   }

   @PostMapping({"levelConfig/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotLevelConfigResponse> selectLevelConfig(@RequestBody @Valid MybatisPlusPage<PointsBotLevelConfigRequest> page) {
      return this.pointsBotLevelConfigService.select(page);
   }

   @PostMapping({"levelConfig/insert"})
   @SaCheckPermission({"admin"})
   public void insertLevelConfig(@RequestBody @Valid PointsBotLevelConfigSave save) {
      this.pointsBotLevelConfigService.insertConfig(save);
   }

   @PostMapping({"levelConfig/update"})
   @SaCheckPermission({"admin"})
   public void updateLevelConfig(@RequestBody @Valid PointsBotLevelConfigUpdate update) {
      this.pointsBotLevelConfigService.updateConfig(update);
   }

   @PostMapping({"levelConfig/delete"})
   @SaCheckPermission({"admin"})
   public void deleteLevelConfig(@RequestParam Long configId) {
      this.pointsBotLevelConfigService.deleteByConfigId(configId);
   }

   @PostMapping({"prizeConfig/select"})
   @SaCheckPermission({"admin"})
   public Page<PointsBotPrizeConfigResponse> selectPrizeConfig(@RequestBody @Valid MybatisPlusPage<PointsBotPrizeConfigRequest> page) {
      return this.pointsBotPrizeConfigService.select(page);
   }

   @PostMapping({"prizeConfig/insert"})
   @SaCheckPermission({"admin"})
   public void insertPrizeConfig(@RequestBody @Valid PointsBotPrizeConfigSave save) {
      this.pointsBotPrizeConfigService.insertConfig(save);
   }

   @PostMapping({"prizeConfig/update"})
   @SaCheckPermission({"admin"})
   public void updatePrizeConfig(@RequestBody @Valid PointsBotPrizeConfigUpdate update) {
      this.pointsBotPrizeConfigService.updateConfig(update);
   }

   @PostMapping({"prizeConfig/delete"})
   @SaCheckPermission({"admin"})
   public void deletePrizeConfig(@RequestParam Long configId) {
      this.pointsBotPrizeConfigService.deleteByConfigId(configId);
   }

   @GetMapping({"user/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotUserStatsResponse getUserStats() {
      return this.pointsBotUserManageService.getStats();
   }

   @GetMapping({"ledger/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotLedgerStatsResponse getLedgerStats() {
      return this.pointsBotLedgerManageService.getStats();
   }

   @GetMapping({"ledger/translateReason"})
   @SaCheckPermission({"admin"})
   public String translateReason(@RequestParam String reason) {
      return this.pointsBotLedgerManageService.translateReason(reason);
   }

   @GetMapping({"ledger/reasonMappings"})
   @SaCheckPermission({"admin"})
   public Map<String, String> getReasonMappings() {
      return this.pointsBotLedgerManageService.getAllReasonMappings();
   }

   @GetMapping({"lottery/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotLotteryStatsResponse getLotteryStats() {
      return this.pointsBotLotteryManageService.getStats();
   }

   @GetMapping({"lotteryEntry/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotLotteryEntryStatsResponse getLotteryEntryStats() {
      return this.pointsBotLotteryEntryManageService.getStats();
   }

   @GetMapping({"redeemConfig/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotRedeemConfigStatsResponse getRedeemConfigStats() {
      return this.pointsBotRedeemConfigService.getStats();
   }

   @GetMapping({"levelConfig/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotLevelConfigStatsResponse getLevelConfigStats() {
      return this.pointsBotLevelConfigService.getStats();
   }

   @GetMapping({"prizeConfig/stats"})
   @SaCheckPermission({"admin"})
   public PointsBotPrizeConfigStatsResponse getPrizeConfigStats() {
      return this.pointsBotPrizeConfigService.getStats();
   }
}
