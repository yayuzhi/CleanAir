package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class BasePojo implements Serializable {
    private static final long serialVersionUID = -3121669440061630976L;

    @TableField(fill = FieldFill.INSERT)
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;

}
