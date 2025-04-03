package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Recharge implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private LocalDateTime createTime;

	private BigDecimal amount;

	/**
	 * bsc erc20 trx bitcoin
	 */
	private String chainType;

	private String address;

	private Integer userId;

	/**
	 * 审核状态
	 */
	private String status;

	private String img;

	private String name;

	private String info;
}
