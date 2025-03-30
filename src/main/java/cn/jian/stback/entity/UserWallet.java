package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName("user_wallet")
public class UserWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private BigDecimal amount;

    private String type;

    private Integer userId;

    @Version
    private Integer version;
}
