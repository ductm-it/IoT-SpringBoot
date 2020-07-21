package com.iot.platform.Interface.Controller;

import java.util.List;

import javax.validation.Valid;

import com.iot.platform.Core.Request.RequestFilter;
import com.iot.platform.Core.Request.RequestPage;
import com.iot.platform.Core.Response.ResponseData;
import com.iot.platform.Core.Response.ResponseFilter;
import com.iot.platform.Core.Response.ResponsePage;
import com.iot.platform.Core.Response.UiConfig;
import com.iot.platform.Interface.Entity.EntityInterface;
import com.iot.platform.Interface.Repository.CmsRepositoryInterface;
import com.iot.platform.Validator.NotNull;
import com.iot.platform.Validator.SqlId;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiModelProperty;

public interface CmsControllerInterface<T extends EntityInterface> extends ControllerInterface {

    public <U extends CmsRepositoryInterface<T>> U getRepository();

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @ApiModelProperty(value = "Get filter data")
    public default ResponseEntity<ResponseData<List<ResponseFilter>>> filter(
            @Valid @RequestBody RequestFilter requestFilter) {
        return ResponseDataEntity(this.getRepository().filter(requestFilter));
    }

    @RequestMapping(value = "names", method = RequestMethod.POST)
    @ApiModelProperty(value = "Get names")
    public default ResponseEntity<ResponseData<List<ResponseFilter>>> getNames(
            @Valid @RequestBody @NotNull List<@SqlId @NotNull String> ids) {
        return ResponseDataEntity(this.getRepository().getNames(ids));
    }

    @RequestMapping(value = "name/{id}", method = RequestMethod.GET)
    @ApiModelProperty(value = "Get name")
    public default ResponseEntity<ResponseData<ResponseFilter>> getName(@Valid @PathVariable @SqlId String id) {
        return ResponseDataEntity(this.getRepository().getName(id));
    }

    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ApiModelProperty(value = "Get page data")
    public default ResponseEntity<ResponseData<ResponsePage<T>>> page(@Valid @RequestBody RequestPage requestPage) {
        return ResponseDataEntity(this.getRepository().page(requestPage));
    }

    @RequestMapping(value = "ui-config", method = RequestMethod.GET)
    @ApiModelProperty(value = "Get UI Config")
    public default ResponseEntity<ResponseData<List<UiConfig>>> getUiConfig() {
        return ResponseDataEntity(this.getRepository().uiConfig());
    }

}