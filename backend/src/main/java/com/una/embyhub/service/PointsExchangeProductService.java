package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.distributionapplication.ExchangeRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductListRequest;
import com.una.embyhub.model.dto.request.distributionapplication.ProductSaveRequest;
import com.una.embyhub.model.entity.PointsExchangeProduct;
import java.util.List;

public interface PointsExchangeProductService extends IService<PointsExchangeProduct> {
   List<PointsExchangeProduct> listProducts(ProductListRequest request);

   void saveProduct(ProductSaveRequest request);

   void deleteProduct(Long id);

   void exchange(ExchangeRequest request);
}
