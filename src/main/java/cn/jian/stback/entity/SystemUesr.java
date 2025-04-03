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
 * @since 2025-04-03
 */
@Getter
@Setter
@ToString
@TableName("system_uesr")
public class SystemUesr implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String password;

    private String userType;

    private String langType;
}
