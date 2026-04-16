package com.example.miniTrackingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableScheduling
public class MiniTrackingProjectApplication {

//	1. Nhóm Luồng Thuận (Happy Path)
//	Đây là luồng từ khi khách đặt hàng đến khi nhận hàng thành công.
//
//	PENDING: Đơn mới tạo, chờ hệ thống hoặc Admin xác nhận.
//
//			CONFIRMED: Admin đã kiểm tra kho và xác nhận đơn hàng hợp lệ.
//
//	PACKED: Kho đã đóng gói xong, đang chờ đơn vị vận chuyển đến lấy.
//
//	SHIPPED: Đơn vị vận chuyển đã lấy hàng từ kho của bạn.
//
//			IN_TRANSIT: Hàng đang được luân chuyển giữa các kho của đơn vị vận chuyển hoặc đang trên đường giao.
//
//	DELIVERED: Shipper đã giao hàng thành công và khách đã nhận.
//
//			2. Nhóm Luồng Lỗi (Exceptions)
//	CANCELLED: Khách chủ động hủy hoặc hệ thống hủy (do hết hàng, quá hạn thanh toán).
//
//	FAILED: Giao hàng không thành công (khách không nghe máy, sai địa chỉ) sau nhiều lần thử.
//
//			3. Nhóm Luồng Ngược (Return & Refund)
//	Đây là phần bạn vừa bổ sung, xử lý khi khách trả hàng.
//
//	RETURNED: Khách hàng yêu cầu trả hàng và Shipper bắt đầu đi lấy hàng trả.
//
//	WAREHOUSE_RECEIVED: Kho của bạn đã nhận được gói hàng trả về.
//
//	RESTOCKED: Bạn đã kiểm tra hàng trả về còn dùng được và nhập lại vào kho bán tiếp (Tồn kho tăng lại ở bước này).
//
//	REFUNDED: Tiền đã được hoàn lại cho khách. Kết thúc quy trình trả hàng.

	public static void main(String[] args) {
		SpringApplication.run(MiniTrackingProjectApplication.class, args);
	}

}
