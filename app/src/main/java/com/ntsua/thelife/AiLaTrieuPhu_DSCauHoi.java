package com.ntsua.thelife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AiLaTrieuPhu_DSCauHoi extends SQLiteOpenHelper {
    public static final String DATA_NAME="cauhoialtp.sqlite";
    public static final int DATA_VERSION=1;

    String createTableCauHoi = "CREATE TABLE "+"Cauhoi"
            +"("
            +"id "+"INTEGER "+"PRIMARY KEY" +" autoincrement, "
            +"capdo "+" INTEGER,"
            +"noidung "+" TEXT,"
            +"dapan "+" TEXT,"
            +"dapansai "+" TEXT"
            +")";

    public AiLaTrieuPhu_DSCauHoi(Context context) {
        super(context, DATA_NAME, null, DATA_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableCauHoi);
        fakedata(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private void fakedata(SQLiteDatabase d){
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,1,\""+"Nhào lộn, tung hứng hay ảo thuật thuộc bộ môn nghệ thuật nào?"+"\",\""+"Xiếc"+"\",\""+"Chèo&Tuồng&Ca Trù"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,2,\""+"Đâu là tên một loại bệnh trên cây lúa?"+"\",\""+"Đạo ôn"+"\",\""+"Đạo tráng&Đạo lí&Đạo hạnh"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,3,\""+"Đâu là tên một ngôi chùa nổi tiếng ở Thái Bình?"+"\",\""+"Chùa Keo"+"\",\""+"Chùa Dâu&Chùa Đồng&Chùa Hang"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,4,\""+"Sóng âm không thể truyền được trong môi trường nào?"+"\",\""+"Chân không"+"\",\""+"Gỗ&Không khí&Sắt"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,5,\""+" 'Sống trong đời sống cần có một tấm lòng' là lời ca trong nhạc phẩm nào của Trịnh Công Sơn?"+"\",\""+"Để gió cuốn đi"+"\",\""+"Cát bụi&Ta thấy gì đêm nay&Bến đời hiu quạnh"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,6,\""+"Nhà Hậu Lê được thành lập sau thắng lợi của cuộc khởi nghĩa nào?"+"\",\""+"Lam Sơn"+"\",\""+"Hàm Tử&Bắc Sơn&Đông Sơn"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,7,\""+"Ngà voi là do bộ phận nào biến đổi thành?"+"\",\""+"Răng cửa"+"\",\""+"Mũi&Xương trán&Cằm"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,8,\""+"Tế bào nào là loại tế bào dài nhất trong cơ thể người?"+"\",\""+"Tế bào thần kinh"+"\",\""+"Tế bào xương&Tế bào que&Tế bào nón"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,9,\""+"Hợp kim của thuỷ ngân và các kim loại khác được gọi là gì?"+"\",\""+"Hỗn hốn"+"\",\""+"Cường toan&Cường thủy&Ngân hống"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,10,\""+"Trong “Bình Ngô đại cáo”, Nguyễn Trãi viết: “Dơ bẩn thay, nước ... khôn rửa sạch mùi”. Điền từ còn thiếu vào chỗ trống?"+"\",\""+"Đông Hải"+"\",\""+"Hoàng Hà&Trường Giang&Cửu Long"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,11,\""+"Sông Mê Kông chảy qua mấy quốc gia?"+"\",\""+"6"+"\",\""+"5&7&8"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,12,\""+"Nghệ thuật sân khấu Dù kê là loại hình nghệ thuật đặc trưng của dân tộc nào?"+"\",\""+"Khmer"+"\",\""+"Ê Đê&Ba Na&Gia Rai"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,13,\""+"Bác Hồ đã từng dạy học ở ngôi trường nào?"+"\",\""+"Dục Thanh"+"\",\""+"Nghệ Thanh&Kim Thanh&Hoạt Thanh"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,14,\""+"Đâu là con tem bưu chính đầu tiên trên thế giới?"+"\",\""+"Penny Black"+"\",\""+"Perot Provisional&Pence Black&Inverted Jenny"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,15,\""+"Ngày nào dưới đây là ngày Tự do Báo chí thế giới?"+"\",\""+"3/5"+"\",\""+"2/5&4/5&6/5"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,1,\""+"Đâu là tên một loại hoa?"+"\",\""+"Xẻng"+"\",\""+"Mai&Cuốc&Cọc"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,2,\""+"Ca trù còn được gọi là hát gì?\n"+"\",\""+"Ả đào"+"\",\""+"Ả mận&Ả hồng&Ả mơ"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,3,\""+"Người ta còn nói: Hổ chết còn để lại ... gì?"+"\",\""+"Da"+"\",\""+"Bụng&Mũi&Mắt"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,4,\""+"Người ta thường dùng thứ gì sau đây để trượt tuyết?"+"\",\""+"Ván"+"\",\""+"Cánh cửa&Chiếu&Ván"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,5,\""+"Trong các loại trái cây sau, loại nào kị nhất với người bị tiểu đường?\n"+"\",\""+"Sầu riêng"+"\",\""+"Dưa chuột&Chanh&Dưa gang"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,6,\""+"Tỉnh nào sau đây không có đường bờ biển?"+"\",\""+"Hải Dương"+"\",\""+"Quảng Ninh&Hải Phòng&Khánh Hòa"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,7,\""+"Viên là thủ đô của nước nào?"+"\",\""+"Áo"+"\",\""+"Pháp&Ý&Nga"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,8,\""+"Theo thần thoại, loài vật nào có khả năng hồi sinh từ đống tro tàn?"+"\",\""+"Phượng hoàng"+"\",\""+"Ba ba&Sư tử&Thuồng luồng"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,9,\""+"Đội tuyển bóng đá nam Mexico đã xuất sắc vượt qua đội bóng nào để giành ngôi vô địch Olympic 2012?"+"\",\""+"Brazil"+"\",\""+"Hàn Quốc&Nhật Bản&Anh"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,10,\""+"Trong 10 năm liên tiếp bất kì, có thể có tối đa bao nhiêu ngày?"+"\",\""+"3653"+"\",\""+"3650&3651&3652"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,11,\""+"Tàu điện ngầm xuất hiện lần đầu tiên ở nước nào?"+"\",\""+"Anh"+"\",\""+"Nga&Pháp&Đức"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,12,\""+"Theo bản dịch của Tương Như thì trong bài thơ 'Xa ngắm thác núi Lư', Lý Bạch đã tả làn khói có màu gì?"+"\",\""+"Tía"+"\",\""+"Trắng&Lam&Xám"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,13,\""+"Tướng Nguyễn Xí là vị danh tướng của triều đại nào?"+"\",\""+"Hậu Lê"+"\",\""+"Tiền Lê&Trần&Mạc"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,14,\""+"Đâu là tên một bãi biển ở Quảng Bình?"+"\",\""+"Đá Nhảy"+"\",\""+"Đá Lăn&Đá Chạy&Đá Bò"+"\");");
        d.execSQL("INSERT INTO Cauhoi (id,capdo , noidung,dapan,dapansai) VALUES ( null,15,\""+"Haiku là thể thơ truyền thống của nước nào?"+"\",\""+"Nhật Bản"+"\",\""+"Mông Cổ&Hàn Quốc&Trung Quốc"+"\");");
    }
}
