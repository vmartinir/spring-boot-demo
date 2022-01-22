package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.repository.Address;
import org.example.repository.User;
import org.example.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("userCollectService")
public class UserCollectServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserCollectServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "userCollect", key = "targetClass + methodName + #userId")
    public List<Address> getAddressByUserId(Integer userId) {
        log.info("调用程序获取数据");
        User user = UserRepository.cache.get(userId);
        if (Optional.ofNullable(user).isPresent()) {
            return user.getAddresses().stream().filter(address -> address.isCollect()).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    @CacheEvict(value = "userCollect", allEntries = true)
    public boolean reload() {
        userRepository.reload();
        return true;
    }
}