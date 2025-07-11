package ynu.edu.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ynu.edu.gatewayservice.util.JwtUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证过滤器
 */
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    
    @Autowired
    private JwtUtils jwtUtils;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // 不需要认证的路径
    private static final List<String> EXCLUDE_PATHS = List.of(
            "/user/register",
            "/user/login",
            "/user/sms/send",
            "/store/list",
            "/store/search",
            "/store/category/list"
    );
    
    public AuthFilter() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            
            log.info("认证过滤器处理请求: {}", path);
            
            // 检查是否需要认证
            if (isExcludePath(path)) {
                log.info("路径无需认证: {}", path);
                return chain.filter(exchange);
            }
            
            // 获取Authorization头
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null) {
                log.warn("缺少Authorization头");
                return unauthorizedResponse(exchange, "缺少认证信息");
            }
            
            // 提取token
            String token = jwtUtils.extractTokenFromHeader(authHeader);
            if (token == null) {
                log.warn("Token格式错误");
                return unauthorizedResponse(exchange, "认证信息格式错误");
            }
            
            // 验证token
            if (!jwtUtils.validateToken(token)) {
                log.warn("Token验证失败");
                return unauthorizedResponse(exchange, "认证信息无效或已过期");
            }
            
            // 从token中获取用户信息
            Long userId = jwtUtils.getUserIdFromToken(token);
            String username = jwtUtils.getUsernameFromToken(token);
            
            if (userId == null) {
                log.warn("无法获取用户信息");
                return unauthorizedResponse(exchange, "认证信息无效");
            }
            
            log.info("用户认证成功: userId={}, username={}", userId, username);
            
            // 将用户信息添加到请求头中传递给下游服务
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("userId", userId.toString())
                    .header("username", username != null ? username : "")
                    .build();
            
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();
            
            return chain.filter(mutatedExchange);
        };
    }
    
    /**
     * 检查路径是否需要排除认证
     */
    private boolean isExcludePath(String path) {
        return EXCLUDE_PATHS.stream().anyMatch(excludePath -> 
                path.contains(excludePath) || path.endsWith(excludePath));
    }
    
    /**
     * 返回未认证响应
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", message);
        result.put("data", null);
        result.put("timestamp", System.currentTimeMillis());
        
        try {
            String body = objectMapper.writeValueAsString(result);
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("序列化响应失败", e);
            return response.setComplete();
        }
    }
    
    public static class Config {
        // 配置参数（如有需要可扩展）
    }
}
