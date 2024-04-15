package com.example.duan1_nhom7.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="duan1";

    public static final int DB_VERSION=2;
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng TheLoai
        String createTableTheLoai = "CREATE TABLE THELOAI(id_Loai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT, imgLoaiSP TEXT);";
        db.execSQL(createTableTheLoai);
        db.execSQL("INSERT INTO THELOAI VALUES(1, 'Apple', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/logo-apple-1.webp?alt=media&token=366883f4-a81b-49c0-a27e-bb82a99d6c89'), " +
                "(2, 'Xiaomi', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/xiaomi.jpg?alt=media&token=25c8c84f-ef12-4998-86f2-b768c593e92f'), " +
                "(3, 'SamSung', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/samsung.jpg?alt=media&token=bdab3eef-6c2e-4fe0-bed3-f949d2512f26'), " +
                "(4, 'Lenovo', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/Lenovo_Global_Corporate_Logo.png?alt=media&token=759c0222-6f8b-4862-a60e-5bee6694d9c4'), " +
                "(5, 'Huawei', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/hoawei.png?alt=media&token=15d0a466-26d9-4553-87f6-1d9ebb574fd7');");

        // SanPham
        String createTableSanPham = ("CREATE TABLE SanPham(\n" +
                "id_sanPham INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "anhSP TEXT,\n" +
                "tenSP TEXT,\n" +
                "giaTienSP Integer,\n" +
                "id_Loai INTEGER REFERENCES THELOAI(id_Loai),\n" +
                "moTaSP TEXT,\n" +"soLuongSP INTEGER,\n" +" ngaySP TEXT\n"+

                ");");
        db.execSQL(createTableSanPham);
        db.execSQL("INSERT INTO SanPham VALUES(1, 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-black-1.jpg','iphone 14 256gb',20000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50,'03/03/30224'),"+
                "(2, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/iphone-15-pro-max_3.webp?alt=media&token=8f5f8602-b73e-4baa-af73-f14382a262be','iphone 15 promax 128gb',30000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50,'21/03/2024')," +
                "(3, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/xiaomi-14-pre-xanh-la_1.webp?alt=media&token=6cb62713-b8b4-4b13-a93b-128b3c5a2031','Xiaomi 14 256gb',18000000,2,'Xiaomi 14 256GB chiếc flagship hứa hẹn mang đến những trải nghiệm đột phá cho người dùng. Với thiết kế sang trọng, màn hình hiển thị đỉnh cao, hiệu năng mạnh mẽ, camera chụp ảnh ấn tượng và thời lượng pin dài. Máy là một lựa chọn hoàn hảo cho những ai đang tìm kiếm một chiếc điện thoại cao cấp trong phân khúc giá tầm trung.',50,'27/03/2024')," +
                "(4, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/800x800-640x640-5_3.webp?alt=media&token=0d5de253-5f51-4f0d-a94e-b3a8bbb24fab','Xiaomi Redmi Note 11',22000000,2,'Điện thoại Xiaomi Redmi Note 11 được trang bị màn hình 6.43 inches trên tấm nền AMOLED mang lại khả năng hiển thị hình ảnh sống động. Điện thoại sẽ chạy trên con chip Snapdragon 680 8 nhân cùng bộ nhớ RAM 4 GB. Máy cũng đáp ứng tootsa nhu cầu nhiếp ảnh với camera 50 MP.',30,'27/03/2024'),"+
                "(5, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/samsung-s23-ulatra_2__4.webp?alt=media&token=9f2744a9-cdbe-4d22-9be7-fed9a8461f69','SamSung Galaxy s23',25000000,3,'SamSung Galaxy s23 là phiên bản cao cấp nhất của dòng SamSung Galaxy với bộ nhớ trong lên đến 1TB. Máy sở hữu màn hình Super AMOLED lớn, hiển thị sắc nét, độ phản chiếu thấp và hỗ trợ tần số quét 120Hz giúp trải nghiệm giải trí mượt mà. Bên trong, SamSung Galaxy s23 trang bị con chip Snapdragon 8 Gen 2, cùng với RAM lên đến 12GB và bộ nhớ trong 1TB, cho hiệu năng mạnh mẽ và khả năng đa nhiệm tốt. Ngoài ra, sản phẩm còn hỗ trợ mạng 5G, pin dung lượng lớn và hệ thống camera đa chức năng, đem lại những trải nghiệm tuyệt vời cho người dùng.',20,'27/03/2024')," +
                "(6, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/sm-s908_galaxys22ultra_front_burgundy_211119_1.webp?alt=media&token=086970ee-e3fe-4369-b268-b161acafe6c7','Samsung Galaxy S22 Ultra',25000000,3,'Samsung Galaxy S22 Ultra là phiên bản cao cấp nhất trong dòng S22 của Samsung. Với thiết kế mạnh mẽ và sang trọng, màn hình Dynamic AMOLED 2X với tần số quét 120Hz cho trải nghiệm mượt mà và đẹp mắt. Bên trong, S22 Ultra được trang bị chip Exynos 2200 hoặc Snapdragon 8 Gen 2 tùy khu vực, RAM lên đến 16GB và bộ nhớ trong lên đến 1TB. Ngoài ra, máy còn có hệ thống camera đa chức năng với cảm biến chính 108MP cho khả năng chụp ảnh ấn tượng và zoom quang học 10x. S22 Ultra cũng hỗ trợ bút S Pen và chuẩn chống nước IP68.',20,'27/03/2024')," +
                "(7, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/nothing-phone-2.webp?alt=media&token=bf224c14-c250-4576-96e1-72548666d341','Lenovo Legion Phone 2 Pro',20000000,4,'Lenovo Legion Phone 2 Pro là chiếc smartphone chuyên dụng cho game thủ với cấu hình mạnh mẽ và thiết kế độc đáo. Máy được trang bị màn hình AMOLED 6.92 inch với tần số quét 144Hz, chip Snapdragon 8 Gen 1, RAM lên đến 18GB và bộ nhớ trong lên đến 512GB. Lenovo Legion Phone 2 Pro còn có hệ thống làm mát kép và bốn cổng cấp nguồn giúp trải nghiệm chơi game mượt mà và không gián đoạn. Ngoài ra, máy còn có hệ thống camera kép 64MP và 44MP, pin 5500mAh với sạc nhanh 90W.',30,'27/03/2024')," +
                "(8, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/blacbert.webp?alt=media&token=8231f687-0592-4d7b-b694-5cd687afbf93','Lenovo Z5',7000000,4,'Ra mắt vào 2018, cấu hình Lenovo Z5 gồm dung lượng pin 3300mAh, màn hình 6.2, độ phân giải 1080 x 2246pixels, camera selfie 8MP và camera chính 16 + 8MP. Giá Lenovo Z5 hiện nay là 2,788,000.00VND tại Shopee',50,'27/03/2024')," +
                "(9, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/mate30__0004_layer_6_2_1_1.webp?alt=media&token=a6475dc3-b65c-4ddd-ba01-853742b76e69','Huawei Mate 30 Pro',11000000,5,'Huawei Mate 30 Pro là smartphone lấy cảm hứng từ tương lai với loạt công nghệ mới được tích hợp, hứa hẹn vượt qua mọi mong đợi về trải nghiệm sử dụng của người dùng. Tiếp nối sự thành công của người tiền nhiệm Huawei Mate 20 Pro, thế hệ tiếp theo của dòng Mate đã không làm mọi người thất vọng và mang lại tiếng vang lớn trên thị trường.',40,'27/03/2024')," +
                "(10, 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/23_2_46.webp?alt=media&token=2108fe9c-eab1-4e51-a660-74afa5aca8a8','Huawei Nova 9',15000000,5,'Huawei Nova 9 là chiếc smartphone tầm trung của Huawei với thiết kế sang trọng và màn hình AMOLED 6.57 inch. Máy được trang bị chip Snapdragon 778G, RAM 8GB và bộ nhớ trong 256GB. Nova 9 có hệ thống camera chính 50MP và camera selfie kép 32MP + 12MP. Ngoài ra, máy còn có pin 4300mAh và hỗ trợ sạc nhanh 66W.',30,'27/03/2024')");

        //Gio hang
        String tableGioHang = "CREATE Table GioHang (\n" +
                "id_gioHang INTEGER,\n" +
                "id_sanPham INTEGER REFERENCES SanPham(id_sanPham),\n" +
                "SoLuong INTEGER,\n" +
                "Mau TEXT,\n"+
                "DonGia DOUBLE\n" +
                ");";
        db.execSQL(tableGioHang);
        //admin
        String createTableAdmin = "create table Admin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "pass TEXT NOT NULL," +
                "quyen INTEGER )";
        db.execSQL(createTableAdmin);
        db.execSQL("INSERT INTO Admin VALUES (1,'admin','admin',1)");

        // User
        String createTableUser = "create table User (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten_user TEXT NOT NULL, " +
                "password TEXT NOT NULL," +
                "sodienthoai TEXT NOT NULL," +
                "diaChi TEXT NOT NULL," +
                "fullname TEXT NOT NULL)";
        db.execSQL(createTableUser);

        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_HoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER REFERENCES User(id_user), " +
                "ngayMua TEXT, " +
                "tongTien INTEGER, " +
                "pttt TEXT, " +
                "status TEXT, " +
                "nameUser TEXT, " +
                "location TEXT, " +
                "phone TEXT" +
                ");";
        db.execSQL(createTableHoaDon);



        String createTableDonHangChiTiet = "CREATE TABLE DonHangChiTiet(" +
                "id_donHangChiTiet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_HoaDon INTEGER REFERENCES HoaDon(id_HoaDon), " +
                "id_sanPham INTEGER REFERENCES SanPham(id_sanPham), " +
                "soLuong INTEGER, " +
                "giaBan INTEGER, " +
                "mau TEXT);";
        db.execSQL(createTableDonHangChiTiet);


    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropLoaiSP = "drop table if exists THELOAI";
        db.execSQL(dropLoaiSP);
        String dropSanPham = "drop table if exists SanPham";
        db.execSQL(dropSanPham);
        String dropGioHang = "drop table if exists GioHang";
        db.execSQL(dropGioHang);
        onCreate(db);
    }
}