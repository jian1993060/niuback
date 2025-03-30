package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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
@TableName("recharge_address")
public class RechargeAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String address;

    private String coinType;

    private String icon;

    private String chainType;

    private String name;
}
