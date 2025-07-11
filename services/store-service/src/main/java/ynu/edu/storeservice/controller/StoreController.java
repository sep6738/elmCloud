package ynu.edu.storeservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ynu.edu.storeservice.common.Result;
import ynu.edu.storeservice.dto.ProductQueryDTO;
import ynu.edu.storeservice.dto.StoreQueryDTO;
import ynu.edu.storeservice.entity.ProductCategory;
import ynu.edu.storeservice.service.StoreService;
import ynu.edu.storeservice.vo.ProductVO;
import ynu.edu.storeservice.vo.StoreVO;

import java.util.List;

/**
 * 商家控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@Validated
public class StoreController {
    
    private final StoreService storeService;
    
    /**
     * 根据条件分页查询商家列表
     */
    @PostMapping("/list")
    public Result<List<StoreVO>> getStoreList(@RequestBody StoreQueryDTO queryDTO) {
        return storeService.getStoreList(queryDTO);
    }
    
    /**
     * 根据ID获取商家详情
     */
    @GetMapping("/{storeId}")
    public Result<StoreVO> getStoreById(@PathVariable Long storeId) {
        return storeService.getStoreById(storeId);
    }
    
    /**
     * 获取商家分类列表
     */
    @GetMapping("/categories")
    public Result<List<ProductCategory>> getStoreCategories() {
        return storeService.getStoreCategories();
    }
    
    /**
     * 根据商家ID获取商品分类
     */
    @GetMapping("/{storeId}/product-categories")
    public Result<List<ProductCategory>> getProductCategoriesByStoreId(@PathVariable Long storeId) {
        return storeService.getProductCategoriesByStoreId(storeId);
    }
    
    /**
     * 根据条件分页查询商品列表
     */
    @PostMapping("/products")
    public Result<List<ProductVO>> getProductList(@RequestBody ProductQueryDTO queryDTO) {
        return storeService.getProductList(queryDTO);
    }
    
    /**
     * 根据ID获取商品详情
     */
    @GetMapping("/product/{productId}")
    public Result<ProductVO> getProductById(@PathVariable Long productId) {
        return storeService.getProductById(productId);
    }
    
    /**
     * 搜索商品
     */
    @GetMapping("/products/search")
    public Result<List<ProductVO>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return storeService.searchProducts(keyword, pageNum, pageSize);
    }
}
