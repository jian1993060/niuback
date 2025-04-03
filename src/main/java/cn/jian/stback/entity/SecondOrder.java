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
@TableName("second_order")
public class SecondOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime createTime;

    private BigDecimal price;

    private String name;

    private Double tradeRate;

    /**
     * 30 60 120
     */
    private Integer type;

    /**
     * 买入金额
     */
    private BigDecimal buyAmount;

    /**
     * 平仓价格
     */
    private BigDecimal finalPrice;

    private Integer userId;

    private String status;

    private String orderType;

    private Double rate;

    private String resAmount;
}
