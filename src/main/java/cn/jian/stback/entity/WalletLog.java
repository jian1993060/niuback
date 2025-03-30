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
@TableName("wallet_log")
public class WalletLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime createTime;

    private String type;

    private BigDecimal beforeAmount;

    private Integer userId;

    private BigDecimal afterAmount;

    private String code;

    private String transResult;

    private String walletType;
}
