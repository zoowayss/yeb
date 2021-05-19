package com.awei.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author shizuwei
 * @since 2021-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mail_log")
@ApiModel(value="MailLog对象", description="")
public class MailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "接收员工id")
    private Integer eid;

    @ApiModelProperty(value = "状态（0:消息投递中 1:投递成功 2:投递失败）")
    private Integer status;

    @ApiModelProperty(value = "路由键")
    private String routeKey;

    @ApiModelProperty(value = "交换机")
    private String exchange;

    @ApiModelProperty(value = "重试次数")
    private Integer count;

    @ApiModelProperty(value = "重试时间")
    private LocalDateTime tryTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
