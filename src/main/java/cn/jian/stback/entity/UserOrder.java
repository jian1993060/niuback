package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * <p>
 * 
 * </p>
 *
 * @author jian
 * @since 2025-04-03
 */
@Getter
@Setter
@ToString
@TableName("user_order")
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private BigDecimal price;

    /**
     * 价格
     */
    private BigDecimal amount;

    /**
     * 数量
     */
    private BigDecimal num;

    private LocalDateTime createTime;

    /**
     * buy 和sale
     */
    private String type;

    private String name;

    /**
     * 1 已经成交  2挂单中 3已经撤销
     */
    private String status;

    /**
     * 1 限价单  2市价单
     */
    private String orderType;
}
