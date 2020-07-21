package com.iot.platform.Interface.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.platform.Core.Response.ResponseFilter;
import com.iot.platform.Util.StringUtil;

import io.swagger.annotations.ApiModelProperty;

public interface EntityInterface extends Serializable {

    @Transient
    public void setId(String id);

    public void setVersion(Long version);

    public void setIsDelete(Boolean isDelete);

    public void setCreatedBy(String createdBy);

    public void setChangedBy(String changedBy);

    public void setCreatedDate(Date createdDate);

    public void setChangedDate(Date changedDate);

    @Transient
    public String getId();

    public Long getVersion();

    public Boolean getIsDelete();

    public String getCreatedBy();

    public String getChangedBy();

    public Date getCreatedDate();

    public Date getChangedDate();

    @ApiModelProperty(hidden = true)
    public default String getDisplay() {
        return this.getId();
    }

    public default ResponseFilter toResponseFilter() {
        return new ResponseFilter(this.getId(), this.getDisplay());
    }

    public default String getQuery() {
        return StringUtil.toQueryString(this.getId() + " " + this.getDisplay());
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public default Boolean isNewItem() {
        return (this.getId() == null || this.getId().trim().equals("") ? true : false);
    }

}