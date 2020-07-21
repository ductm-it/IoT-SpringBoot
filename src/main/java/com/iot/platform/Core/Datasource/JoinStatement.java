package com.iot.platform.Core.Datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinStatement {

    private String joinTable = "";
    private String joinAs = "";
    private String joinOn = "";

    @Override
    public String toString() {
        return this.joinTable + " " + this.joinAs + " ON " + this.joinOn;
    }

}