package cn.jian.stback.service.impl;

import cn.jian.stback.entity.User;
import cn.jian.stback.mapper.UserMapper;
import cn.jian.stback.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian
 * @since 2025-02-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
