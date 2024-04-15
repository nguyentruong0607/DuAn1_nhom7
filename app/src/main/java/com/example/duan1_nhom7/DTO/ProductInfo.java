package com.example.duan1_nhom7.DTO;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private String tenSP;
    private String anhSP;

    public ProductInfo(String tenSP, String anhSP) {
        this.tenSP = tenSP;
        this.anhSP = anhSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }
}
