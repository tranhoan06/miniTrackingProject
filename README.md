1. Vấn đề @Transaction
+ OK, Nhìn chung các Service methods đã biết thêm @Transactional đầy đủ, phân biệt rõ readOnly = true cho các tác vụ thuần đọc dữ liệu tốt. Đã clear và sửa gần hết từ jakarta.transaction.Transactional sang annotation của Spring.
+ Thiếu rollbackFor, @Transactional trong toàn bộ Service vẫn đang dùng mặc định. mặc định Spring chỉ rollback với RuntimeException. xem có cần sửa thành rollbackFor = Exception.class cho anh k nhé. => Đa số các transaction đều đang throw JavaBuilderException impl RuntimeException nên k cần rollbackfor
+ Bug logic nặng ở Vòng lặp: OrderServiceImpl.java e đang gọi reserveInventories(request.getItems()) ngay bên trong vòng lặp for. Cái này dính lỗi over-reservation inventory N lần. Tách hàm này ra ngoài loop. => Done
+ Sót Import: Thằng NotificationRetryJob.java:13 vẫn đang dùng jakarta.transaction.Transactional thay vì Spring, sửa lại cho đồng bộ với codebase. => Done
+ Voucher không save: OrderServiceImpl.java:254-256 gọi voucher.setUsedCount(voucher.getUsedCount() + 1) nhưng lại quên không lưu xuống DB. Check lại logic chỗ này e nhé. => Done
2. SOLID ok
3. Clean Code & Bugs linh tinh
+ Thiếu Validation: Một số DTO quan trọng như AddressRequest, PreviewOrderRequest vẫn đang thiếu @NotNull/@NotEmpty cho các field. Check và bổ sung nhé. => Done
+ Hardcode message: CategoryRequest.java:13 đang hardcode tiếng Việt "Không được để trống" trực tiếp trong code. Nên chuyển sang dùng properties/message source để sau này còn dễ quản lý hoặc làm đa ngôn ngữ.
4. Race Condition & Caching
+ OK phần Lock & Job: Đã biết dùng PESSIMISTIC_WRITE cho InventoryRepository và chuyển từ findTop100 sang Pageable + do-while trong NotificationRetryJob để tránh lock quá nhiều records, tốt.
+ Thiếu Optimistic Locking: Hiện tại 0/16 entity có @Version. Khuyến nghị thêm vào toàn bộ các Entity (đặc biệt là Inventory, Discount, Order) để né lost update. 
Đã dùng pessimistic lock rồi thì nên test thêm cả về Optimistic locking nữa để biết rõ hơn về nó và áp dụng linh hoạt, k thì để apply vào micro cũng đc nhé. tùy em nhé. => Done
+ Thiếu Caching: Những dữ liệu ít thay đổi như danh mục (category) hay cấu hình hệ thống đang không được cache, mỗi request đều phải query DB rất phí. Nghiên cứu áp dụng @Cacheable vào hoặc apply sau khi học micro cũng đc. => Pending
5. Performance & Performance tối ưu
N+1 Query Problem (Nguy cơ sập DB):
+ Mấy mối quan hệ @OneToMany như OrdersEntity.items, ProductsEntity.images, ProductsEntity.inventories đang để LAZY. Khi access các collection này ngoài transaction context sẽ có thể xảy ra lỗi N+1 query để map dữ liệu. => thử enable logs lên xem có bị N+1 query k nhé.
=> Phải dùng Fetch Join hoặc EntityGraph để xử lý N+1, đọc thêm về Entity Graph nữa. => Done
+ NotificationsRepository dùng Top50: Thằng này đang dùng Top50 hardcode nhìn hơi thô, đổi sang dùng Pageable cho linh hoạt e nhé. => Bỏ vì thấy k dùng
6. Bảo mật & Endpoint
+ Đang thiếu @PreAuthorize trên rất nhiều endpoint quan trọng (bất kỳ user nào cũng gọi được endpoint của Admin/Seller). Sửa cho anh các Controller sau:
+ OrderController: Endpoint preview order (POST /api/orders/preview) và order detail (POST /api/orders/order-detail/{id}). => Done
+ ShippingProviderController: Toàn bộ 5/6 endpoints (List all, Create, Update, Get by ID, Shipping order). => Done
+ ProductReviewController: Toàn bộ 4/4 endpoints liên quan đến tạo, đọc, sửa, xóa review. => Done
+ OrderStatusController: Endpoint get log (POST /api/order-status/). => Done
- Thiếu Serializable: entity OrdersEntity, OrderItemsEntity, VouchersEntity... chưa implements Serializable. => Done
- Password encoding: UserServiceImpl.createUser() dùng trực tiếp userMapper.toEntity(request). Phải check lại kỹ xem tầng mapper đã được encode password chưa, không được lưu password clear-text xuống DB. => Done