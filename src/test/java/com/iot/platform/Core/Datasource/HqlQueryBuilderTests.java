package com.iot.platform.Core.Datasource;

import java.util.ArrayList;

public class HqlQueryBuilderTests {

    public static void main(String[] args) {
        HqlQueryBuilder hqlQueryBuilder = new HqlQueryBuilder();
        hqlQueryBuilder.setSelector("c");
        hqlQueryBuilder.setPrimaryKey("userCode");
        hqlQueryBuilder.setTable("User_Master");

        hqlQueryBuilder.getJoinStatements().add(new JoinStatement("Company", "d", "d.companyCode=c.companyCode"));
        hqlQueryBuilder.getWhereStatements().add(WhereStatement.in("companyCode", new String[] { "A", "B" }));
        hqlQueryBuilder.getWhereOrStatements().add(new WhereOrStatement() {
            {
                this.getListWhereStatements().add(new ArrayList<WhereStatement>() {
                    private static final long serialVersionUID = 1L;

                    {
                        this.add(WhereStatement.equal("c.dob", "02/02/2010"));
                        this.add(WhereStatement.equal("c.stateCode", "HCMC"));
                    }
                });
                this.getListWhereStatements().add(new ArrayList<WhereStatement>() {
                    private static final long serialVersionUID = 1L;

                    {
                        this.add(WhereStatement.equal("c.dob", "02/02/2010"));
                        this.add(WhereStatement.equal("c.stateCode", "HCMC"));
                    }
                });
            }
        });

        HqlQueryData hqlQueryData = hqlQueryBuilder.getHqlQueryData();
        System.out.println(hqlQueryData.getQuery());
    }

}