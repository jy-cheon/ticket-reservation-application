package io.jeeyeon.app.ticketReserve.infra.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisCommonTemplate {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private ZSetOperations<String, Object> zSet;
    private HashOperations<String, String, String> zHash;
    private SetOperations<String, Object> set;
    private ValueOperations<String, Object> valueOps;

    @PostConstruct
    public void init() {
        zSet = redisTemplate.opsForZSet();
        zHash = redisTemplate.opsForHash();
        set = redisTemplate.opsForSet();
        valueOps = redisTemplate.opsForValue();
    }

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean deleteValue(String key) {
        return redisTemplate.delete(key);
    }

    // Hash 자료구조 관련 메소드
    public void putHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void putAllHashValues(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public <T> Map<String, T> getAllHashValues(String key, Class<T> type) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Map<String, T> result = new HashMap<>();

        entries.forEach((hashKey, value) -> {
            try {
                T obj = objectMapper.convertValue(value, type);
                result.put((String) hashKey, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result;
    }


    public boolean deleteHashValue(String key, String hashKey) {
        Long result = redisTemplate.opsForHash().delete(key, hashKey);
        return result != null && result > 0;
    }

    public long getZSetSize(String zsetKey) {
        return zSet.size(zsetKey);
    }

    public boolean deleteMemberInZset(String key, Long member) {
        Long result = zSet.remove(key, member);
        return result != null && result > 0;
    }

    public void deleteMembersInZset(String key, Set<Long> members) {
        zSet.remove(key, members.toArray());
    }

    public void deleteValueInSet(String key, Long member) {
        set.remove(key, member);
    }

    public Set<Object> getValuesInSet(String key) {
        return set.members(key);
    }

    public void addValueWithTTl(String key, Object value, long ttl) {
        valueOps.set(key, value, ttl, TimeUnit.MINUTES);
    }

    public void addToZSet(String key, Object value, double score) {
        zSet.add(key, value, score);
    }

    public void addToSet(String key, Set<Long> members) {
        set.add(key, members.toArray());
    }

    public boolean isMemberInZSet(String key, Long member) {
        return zSet.score(key, member) != null;
    }

    public boolean isMemberInValue(String key) {
        return valueOps.get(key) != null;
    }

    public boolean isMemberInSet(String key, Long member) {
        Boolean isMember = set.isMember(key, member);
        return Boolean.TRUE.equals(isMember);
    }

    public Set<Object> getOldestMembersFromZset(String key, long start, long end) {
        return zSet.range(key, start, end);
    }

    public Long rankFromZSet(String key, Object value) {
        return zSet.rank(key, value);
    }

    public void redisHashDataSet(String redisKey, String redisHashKey, Object object) throws JsonProcessingException {
        if (!StringUtils.isEmpty(redisKey) && !StringUtils.isEmpty(redisHashKey) && object != null) {
            JsonMapper mapper = new JsonMapper();
            mapper.rebuild().configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
            zHash.put(redisKey, redisHashKey, mapper.writeValueAsString(object));
        }
    }
    public void deleteHashData(String redisKey, String redisHashKey) {
        zHash.delete(redisKey, redisHashKey);
    }
}
