package com.example.duan1_nhom7.DTO;

public class Top10BanChay {
   private SanPham sanPham;
    private Integer soluong;

    public Top10BanChay(SanPham sanPham, Integer soluong) {
        this.sanPham = sanPham;
        this.soluong = soluong;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }
}


