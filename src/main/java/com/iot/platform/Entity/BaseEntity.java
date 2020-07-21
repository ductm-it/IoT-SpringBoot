package com.iot.platform.Entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Const.SystemConst;
import com.iot.platform.Interface.Entity.EntityInterface;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements EntityInterface {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @Getter(onMethod_ = { @JsonIgnore, @ApiModelProperty(hidden = true) })
    @Setter(onMethod_ = { @JsonIgnore, @ApiModelProperty(hidden = true) })
    protected Boolean isDelete;

    @ApiModelProperty(hidden = true)
    protected String createdBy;

    @ApiModelProperty(hidden = true)
    protected String changedBy;

    @ApiModelProperty(hidden = true, example = SystemConst.DefaultDate)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConst.DateFormat)
    protected Date changedDate;

    @ApiModelProperty(hidden = true, example = SystemConst.DefaultDate)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConst.DateFormat)
    protected Date createdDate;

    @ApiModelProperty(hidden = true)
    @Version
    @JsonIgnore
    private Long version;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private String query;

    protected void preSaveOrUpdate() {
        this.query = this.getQuery();
        if (this.query == null)
            this.query = "";
    }

    @PrePersist
    public void prePersist() {
        this.isDelete = false;
        this.createdDate = new Date();
        this.changedDate = new Date();
        this.preSaveOrUpdate();
    }

    @PreUpdate
    public void preUpdate() {
        this.changedDate = new Date();
        this.preSaveOrUpdate();
    }

}