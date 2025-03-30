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
@TableName("stock_data")
public class StockData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String type;

    private String status;
}
