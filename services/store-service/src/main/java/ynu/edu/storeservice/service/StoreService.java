package ynu.edu.storeservice.service;

import java.util.List;

import ynu.edu.storeservice.common.Result;
import ynu.edu.storeservice.dto.ProductQueryDTO;
import ynu.edu.storeservice.dto.StoreQueryDTO;
import ynu.edu.storeservice.entity.ProductCategory;
import ynu.edu.storeservice.vo.ProductVO;
import ynu.edu.storeservice.vo.StoreVO;

/**
 * 商家服务接口
 */
public interface StoreService {
    
    /**
     * 根据条件分页查询商家列表
     */
    Result<List<StoreVO>> getStoreList(StoreQueryDTO queryDTO);
    
    /**
     * 根据ID获取商家详情
     */
    Result<StoreVO> getStoreById(Long storeId);
    
    /**
     * 获取商家分类列表
     */
    Result<List<ProductCategory>> getStoreCategories();
    
    /**
     * 根据商家ID获取商品分类
     */
    Result<List<ProductCategory>> getProductCategoriesByStoreId(Long storeId);
    
    /**
     * 根据条件分页查询商品列表
     */
    Result<List<ProductVO>> getProductList(ProductQueryDTO queryDTO);
    
    /**
     * 根据ID获取商品详情
     */
    Result<ProductVO> getProductById(Long productId);
    
    /**
     * 搜索商品
     */
    Result<List<ProductVO>> searchProducts(String keyword, Integer pageNum, Integer pageSize);
}
