package cn.jian.stback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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
@TableName("user_real")
public class UserReal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String idNo;

    private String img;

    private String name;

    private String status;

    private Integer userId;

    private LocalDateTime createTime;

    private String refuseInfo;
}
