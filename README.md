+ Transaction: * AddressServiceImpl.java:13 đang import sai thư viện jakarta.transaction.Transactional. đổi sang org.springframework.transaction.annotation.Transactional thì các thuộc tính như readOnly hay rollbackFor mới có tác dụng (test thử nhé) - done

+ Nhiều method thực hiện ghi dữ liệu (create*, update*) ở UserServiceImpl, CategoryServiceImpl đang thiếu @Transactional. Ví dụ hàm deleteCategory - done

+ Thiếu readOnly = true cho toàn bộ các hàm query dữ liệu (như getUserById, getAll,...). cái này giúp tối ưu performance ở tầng DB nhiều. - done

+ Phần race-condition thì Toàn bộ Entity (User, Product, Category,...) đều đang thiếu @Version. Đặc biệt là InventoryEntity, nếu 2 request cùng trừ kho một lúc mà không có Optimistic Locking là sai số lượng ngay. => khong thì e dùng permistic lock như a có chỉ nhé. - done

+ Hàm validateNoReserved đang check-then-act mà không có khóa bi quan (PESSIMISTIC_WRITE). Trong thực tế, lúc e check xong mà chưa kịp lưu thì thằng khác đã nhảy vào sửa rồi. - done

+ Validation hơi hời hợt: CategoryRequest đang để trống trơn, user truyền tên null code vẫn chạy phăm phăm là hỏng. - done

+ Tại các Controller, e quên không thêm @Valid trước @RequestBody. Không có cái này thì mọi annotation ràng buộc trong DTO đều k có giá trị. - done

+ Các trường quan trọng như price trong ProductRequest bắt buộc phải có @NotNull. - done

+ Performance "tệ" do N+1 Query: getProductDetail đang bị dính N+1 vì gọi getImages() và getInventories() khi nó  đang ở chế độ Lazy (test xem phải k nhé, bật logs query lên)

+ Hàm getAll của Product cũng đang mapping thủ công trong vòng lặp, cực kỳ dễ trigger N+1 nếu có quan hệ lazy phức tạp.
+ Trong ProductRepository, e viết LEFT JOIN FETCH xong lại comment lại? Đây chính là cách giải quyết N+1 mà đang thấy e bổ đi

+ AddressController đang thiếu @PreAuthorize. - done

+ Vẫn còn tình trạng hardcode mật khẩu DB (root/123456) trong application.yaml. Tuyệt đối phải đưa vào biến môi trường.=> spike xem thực tế nên làm ntn nhé.
+ Entity phải để số ít (CategoryEntity thay vì CategoriesEntity). - done

+ Lỗi chính tả (typo) xuất hiện nhiều: AddresesEntity (thiếu s), biến addresesEntity. - done

+ Sai chuẩn REST: Các hàm update phải dùng @PutMapping, e đang dùng sai @PostMapping. - done

+ Code rác & Mapping thủ công: UserController.java:30 gọi lấy authorities xong để đấy không dùng. Code thừa thì xóa ngay cho sạch. - done
+ UserServiceImpl vẫn đang cì cụm mapping thủ công thay vì dùng UserMapper. dùng MapStruct đi nhé. - done
