package com.sdyijia.modules.sys.service;

import com.sdyijia.modules.sys.bean.SysUser;
import com.sdyijia.modules.sys.repository.UserRepository;
import com.sdyijia.utils.ECS.EncryptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SysUserService {

    @Resource
    private UserRepository userRepository;

    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 该save方法会对pwd进行加密处理
     *
     * @return
     */
    public SysUser save(SysUser sysUser)   {
        String pwd = sysUser.getPassword();
        System.out.println(pwd);
        String salt = sysUser.getSalt();
        System.out.println(salt);
        if (Objects.nonNull(sysUser) && Objects.nonNull(sysUser.getSalt()) && !"".equals(sysUser.getSalt().trim())) {
            sysUser.setPassword(EncryptionUtils.getSha512Hash(sysUser.getPassword(), sysUser.getSalt()));
        } else {
            sysUser.setPassword(EncryptionUtils.getSha512Hash(sysUser.getPassword()));
        }
        return userRepository.save(sysUser);
    }


}
