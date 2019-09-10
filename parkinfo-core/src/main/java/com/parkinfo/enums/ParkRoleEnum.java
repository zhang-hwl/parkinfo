package com.parkinfo.enums;

public enum ParkRoleEnum {

    ADMIN{
        @Override
        String getName() {
            return "超级管理员";
        }
    },PRESIDENT{
        @Override
        String getName() {
            return "总裁";
        }
    },GENERAL_MANAGER{
        @Override
        String getName() {
            return "总经办";
        }
    },PARK_MANAGER{
        @Override
        String getName() {
            return "园区管理员";
        }
    },PARK_USER{
        @Override
        String getName() {
            return "园区员工";
        }
    },OFFICER{
        @Override
        String getName() {
            return "政府官员";
        }
    },HR_USER{
        @Override
        String getName() {
            return "HR机构";
        }
    };
    abstract String getName();
}
