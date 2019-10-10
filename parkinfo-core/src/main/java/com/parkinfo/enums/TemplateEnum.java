package com.parkinfo.enums;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Li
 * description:
 * date: 2019-10-10 17:05
 */
public enum TemplateEnum {

    BIG_EVENT{
        @Override
        public String getName() {
            return "园区大事记";
        }
    },CHECK_RECORD{
        @Override
        public String getName() {
            return "点检记录表";
        }
    },COMPETE{
        @Override
        public String getName() {
            return "竞争园区信息";
        }
    },INFO_EQUIPMENT{
        @Override
        public String getName() {
            return "信息化设备";
        }
    },POLICY{
        @Override
        public String getName() {
            return "政策统计";
        }
    },ROOM{
        @Override
        public String getName() {
            return "本园区房间统计";
        }
    },STORE_INFO{
        @Override
        public String getName() {
            return "招商信息";
        }
    },DEMAND_INFO{
        @Override
        public String getName() {
            return "需求信息";
        }
    },WORK_PLAN{
        @Override
        public String getName() {
            return "工作计划";
        }
    },ENTER_BASIC{
        @Override
        public String getName() {
            return "考试题目";
        }
    };
    public abstract String getName();

}
