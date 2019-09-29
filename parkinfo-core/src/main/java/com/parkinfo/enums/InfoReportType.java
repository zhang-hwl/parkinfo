package com.parkinfo.enums;

public enum InfoReportType {

    ENTER_TOP{
        @Override
        public String getName() {
            return "入驻百强企业";
        }
    },HIGH_TAX{
        @Override
        public String getName() {
            return "高产值税收企业";
        }
    },ADD_USER{
        @Override
        public String getName() {
            return "新增客户数量";
        }
    },ADD_ENTER{
        @Override
        public String getName() {
            return "新增入园企业";
        }
    },EXIT_COMPANY{
        @Override
        public String getName() {
            return "退出企业";
        }
    },ENTER_VALUE{
        @Override
        public String getName() {
            return "入园企业产值";
        }
    },ENTER_TAX{
        @Override
        public String getName() {
            return "入园企业税收";
        }
    },PARK_ROOM{
        @Override
        public String getName() {
            return "房间";
        }
    },ENTER_PRODUCT{
        @Override
        public String getName() {
            return "入驻企业产品类型";
        }
    },ENTER_BASIC{
        @Override
        public String getName() {
            return "入驻企业基础服务类";
        }
    },ENTER_HIGH{
        @Override
        public String getName() {
            return "入驻企业中高端服务类";
        }
    },ENTER_COMPOSITE{
        @Override
        public String getName() {
            return "入驻企业中综合服务类";
        }
    },ACTIVITY_TOTAL{
        @Override
        public String getName() {
            return "活动类统计";
        }
    },GET_GRADE{
        @Override
        public String getName() {
            return "获得的荣誉";
        }
    };
    public abstract String getName();
}
