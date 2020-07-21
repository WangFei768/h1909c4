package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Major implements Serializable {
    @Id
    private Integer mid;

    private String mname;

    private static final long serialVersionUID = 1L;

    /**
     * @return mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * @return mname
     */
    public String getMname() {
        return mname;
    }

    /**
     * @param mname
     */
    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mid=").append(mid);
        sb.append(", mname=").append(mname);
        sb.append("]");
        return sb.toString();
    }
}