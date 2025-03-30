package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("second_product")
public class SecondProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer times;

    private Double rate;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Double tradeRate;

    private Integer orderNum;
}
