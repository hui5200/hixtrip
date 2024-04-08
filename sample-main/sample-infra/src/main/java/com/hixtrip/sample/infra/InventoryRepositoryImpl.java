package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    private final static String SKU_PRE = "inventory:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final RedisScript<Boolean> lockScript;

    public InventoryRepositoryImpl() {
        // 定义 Lua 脚本，用于加锁
        String lockScriptStr =
                """
                   local key = KEYS[1]
                   local value = ARGV[1]
                   local ttl = ARGV[2]
                   if redis.call('SETNX', key, value) == 1 then
                      redis.call('EXPIRE', key, ttl)
                      return true
                   else
                   return false
                   end""";
        lockScript = new DefaultRedisScript<>(lockScriptStr, Boolean.class);
    }

    @Override
    public Integer getInventory(String skuId) {
        // 从Redis缓存中获取SKU当前库存
        Object inventory = redisTemplate.opsForValue().get(SKU_PRE + skuId);
        return inventory != null ? (Integer) inventory : 0;
    }

    /**
     * 这里基于redis+lua方式实现分布式锁，控制并发超卖情况（实际生产可以直接使用Redisson工具就行）
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        String key = SKU_PRE + skuId;
        String value = "1";
        int ttl = 10;
        // 加锁
        Boolean lockAcquired = redisTemplate.execute(lockScript, List.of(key), value, ttl);
        if (Objects.isNull(lockAcquired) || !lockAcquired) {
            throw new RuntimeException("订单正在处理中，请稍后");
        }
        try {
            // 修改SKU的库存信息
            int currentInventory = getInventory(skuId);
            int newInventory = currentInventory + sellableQuantity.intValue() - withholdingQuantity.intValue() - occupiedQuantity.intValue();
            if (newInventory < 0) {
                throw new RuntimeException("库存不足");
            }
            redisTemplate.opsForValue().set(key, newInventory);
            return true;
        } finally {
            redisTemplate.delete(key);
        }
    }
}
