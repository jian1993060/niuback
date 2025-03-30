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
 * @since 2025-03-30
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

    private String code;

    private String rateAmount;

    /**
     * 30 60 120
     */
    private Integer type;

    /**
     * 买入数量
     */
    private BigDecimal num;

    /**
     * 平仓价格
     */
    private BigDecimal finalPrice;
}
