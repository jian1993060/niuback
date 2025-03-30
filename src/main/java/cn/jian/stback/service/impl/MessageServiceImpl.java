package cn.jian.stback.service.impl;

import cn.jian.stback.entity.Message;
import cn.jian.stback.mapper.MessageMapper;
import cn.jian.stback.service.MessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
