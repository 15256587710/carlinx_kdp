package com.ecarxclub.app.utils.picker.model;

import java.util.List;

/**
 * 省份
 * <br/>
 * Author:matt : addapp.cn
 * DateTime:2016-10-15 19:06
 *
 */
public class Province {

    public String region_id;
    public String region_name;
    public List<City> child_list;
    public class City{
        public String region_id;
        public String region_name;
        public List<County> child_list;
        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public List<County> getChild_list() {
            return child_list;
        }

        public void setChild_list(List<County> child_list) {
            this.child_list = child_list;
        }
        public class County{
            public String region_id;
            public String region_name;
            public String getRegion_id() {
                return region_id;
            }

            public void setRegion_id(String region_id) {
                this.region_id = region_id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }
        }
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<City> getChild_list() {
        return child_list;
    }

    public void setChild_list(List<City> child_list) {
        this.child_list = child_list;
    }

}