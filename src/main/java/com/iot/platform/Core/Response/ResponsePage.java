package com.iot.platform.Core.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponsePage<T> {

    Integer pageSize;
    Integer pageIndex;
    Long totalRecord;

    List<T> records;

    public Integer getTotalPage() {
        return (int) Math.ceil(this.totalRecord * 1.0 / this.pageSize);
    }

}