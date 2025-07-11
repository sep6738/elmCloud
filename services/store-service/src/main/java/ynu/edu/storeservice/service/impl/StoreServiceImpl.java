package ynu.edu.storeservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ynu.edu.storeservice.common.Result;
import ynu.edu.storeservice.dto.ProductQueryDTO;
import ynu.edu.storeservice.dto.StoreQueryDTO;
import ynu.edu.storeservice.entity.Product;
import ynu.edu.storeservice.entity.ProductCategory;
import ynu.edu.storeservice.entity.Store;
import ynu.edu.storeservice.mapper.ProductCategoryMapper;
import ynu.edu.storeservice.mapper.ProductMapper;
import ynu.edu.storeservice.mapper.StoreMapper;
import ynu.edu.storeservice.service.StoreService;
import ynu.edu.storeservice.vo.ProductVO;
import ynu.edu.storeservice.vo.StoreVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商家服务实现类
 */
@Service
public class StoreServiceImpl implements StoreService {
    
    private static final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);
    
    private final StoreMapper storeMapper;
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;
    
    public StoreServiceImpl(StoreMapper storeMapper, ProductMapper productMapper, ProductCategoryMapper productCategoryMapper) {
        this.storeMapper = storeMapper;
        this.productMapper = productMapper;
        this.productCategoryMapper = productCategoryMapper;
    }
    
    @Override
    public Result<List<StoreVO>> getStoreList(StoreQueryDTO queryDTO) {
        try {
            // 简化实现：直接查询所有商家
            List<Store> stores = storeMapper.selectAll();
            
            List<StoreVO> storeVOList = stores.stream()
                .map(this::convertToStoreVO)
                .collect(Collectors.toList());
            
            return Result.success(storeVOList);
        } catch (Exception e) {
            log.error("获取商家列表失败", e);
            return Result.error("获取商家列表失败");
        }
    }
    
    @Override
    public Result<StoreVO> getStoreById(Long storeId) {
        try {
            Store store = storeMapper.selectById(storeId);
            if (store == null) {
                return Result.error("商家不存在");
            }
            
            StoreVO storeVO = convertToStoreVO(store);
            return Result.success(storeVO);
        } catch (Exception e) {
            log.error("获取商家详情失败", e);
            return Result.error("获取商家详情失败");
        }
    }
    
    @Override
    public Result<List<ProductCategory>> getStoreCategories() {
        try {
            List<ProductCategory> categories = productCategoryMapper.selectAll();
            return Result.success(categories);
        } catch (Exception e) {
            log.error("获取商家分类失败", e);
            return Result.error("获取商家分类失败");
        }
    }
    
    @Override
    public Result<List<ProductCategory>> getProductCategoriesByStoreId(Long storeId) {
        try {
            List<ProductCategory> categories = productCategoryMapper.selectByStoreId(storeId);
            return Result.success(categories);
        } catch (Exception e) {
            log.error("获取商品分类失败", e);
            return Result.error("获取商品分类失败");
        }
    }
    
    @Override
    public Result<List<ProductVO>> getProductList(ProductQueryDTO queryDTO) {
        try {
            // 简化实现：直接查询所有商品
            List<Product> products = productMapper.selectAll();
            
            List<ProductVO> productVOList = products.stream()
                .map(this::convertToProductVO)
                .collect(Collectors.toList());
            
            return Result.success(productVOList);
        } catch (Exception e) {
            log.error("获取商品列表失败", e);
            return Result.error("获取商品列表失败");
        }
    }
    
    @Override
    public Result<ProductVO> getProductById(Long productId) {
        try {
            Product product = productMapper.selectById(productId);
            if (product == null) {
                return Result.error("商品不存在");
            }
            
            ProductVO productVO = convertToProductVO(product);
            return Result.success(productVO);
        } catch (Exception e) {
            log.error("获取商品详情失败", e);
            return Result.error("获取商品详情失败");
        }
    }
    
    @Override
    public Result<List<ProductVO>> searchProducts(String keyword, Integer pageNum, Integer pageSize) {
        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            List<Product> products = productMapper.searchProducts(keyword, null, null, offset, pageSize);
            
            List<ProductVO> productVOList = products.stream()
                .map(this::convertToProductVO)
                .collect(Collectors.toList());
            
            return Result.success(productVOList);
        } catch (Exception e) {
            log.error("搜索商品失败", e);
            return Result.error("搜索商品失败");
        }
    }
    
    /**
     * 转换Store为StoreVO
     */
    private StoreVO convertToStoreVO(Store store) {
        StoreVO storeVO = new StoreVO();
        storeVO.setId(store.getId());
        storeVO.setName(store.getName());
        storeVO.setDescription(store.getDescription());
        storeVO.setStatus(store.getStatus());
        return storeVO;
    }
    
    /**
     * 转换Product为ProductVO
     */
    private ProductVO convertToProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        productVO.setId(product.getId());
        productVO.setName(product.getName());
        productVO.setDescription(product.getDescription());
        productVO.setPrice(product.getPrice());
        return productVO;
    }
}
