package com.parkinfo.enums;

public enum ParkRoleEnum {

    ADMIN{
        @Override
        public String getName() {
            return "超级管理员";
        }
    },PRESIDENT{
        @Override
        public String getName() {
            return "总裁";
        }
    },GENERAL_MANAGER{
        @Override
        public String getName() {
            return "总经办";
        }
    },PARK_MANAGER{
        @Override
        public String getName() {
            return "园区管理员";
        }
    },PARK_USER{
        @Override
        public String getName() {
            return "园区员工";
        }
    },OFFICER{
        @Override
        public String getName() {
            return "政府官员";
        }
    },HR_USER{
        @Override
        public String getName() {
            return "HR机构";
        }
    };
    public abstract String getName();
}
