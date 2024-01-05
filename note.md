# Phân công
- Thiết kế use case diagram và class diagram - 
- Code phần back. - Vinh, Quân
- Code phần giao diện bao gồm giao diện trong game (bảng số, tên người chơi,...) và giao diện phần hướng dẫn chơi. - Vinh, Quân
- Làm báo cáo - Vương
- Làm slide (có thể kiêm luôn thuyết trình) - Vương
- Cần thêm các tính năng của OOP (inheritance, polymorphism,...) vào ứng dụng.
- Cần thêm các tính năng của JavaFX (animation, transition,...) vào ứng dụng.

# Kiến trúc ứng dụng
- Sử dụng JavaFX để thiết kế giao diện.
- Phần back-end sử dụng kiến trúc MVC.
    - Kiến trúc MVC (Model-View-Controller) là một mô hình thiết kế phần mềm, nó tách biệt các thành phần của ứng dụng thành 3 phần: Model, View và Controller.
    - Model: là thành phần chứa các đối tượng dữ liệu và các phương thức xử lý dữ liệu.
    - View: là thành phần hiển thị dữ liệu.
    - Controller: là thành phần đóng vai trò trung gian giữa Model và View, nó thực hiện các thao tác điều khiển các thành phần của View và gọi các phương thức xử lý dữ liệu trong Model.
    - Controller sẽ nhận các yêu cầu từ người dùng thông qua View (ví dụ như click chuột, nhập dữ liệu,...), sau đó Controller sẽ gọi các phương thức xử lý dữ liệu trong Model, truyền vào các dữ liệu người dùng cung cấp để xử lý yêu cầu đó, sau khi xử lý xong, Controller sẽ gọi các phương thức hiển thị dữ liệu trong View để hiển thị kết quả cho người dùng.
- Phần Model trong ứng dụng này sẽ chứa các đối tượng dữ liệu như bàn cờ, quân cờ, người chơi,... và các phương thức xử lý dữ liệu như kiểm tra thắng thua, kiểm tra hợp lệ,... Các đối tượng dữ liệu này sẽ được lưu trữ trong các file `.java` riêng biệt.
- Phần View trong ứng dụng này sẽ chứa các thành phần giao diện như bàn cờ, bảng số, tên người chơi,... Các thành phần giao diện này sẽ được lưu trữ trong các file `.fxml` riêng biệt.
- Phần Controller trong ứng dụng này sẽ chứa các phương thức điều khiển các thành phần giao diện và các phương thức xử lý dữ liệu. Bao gồm các hàm xử lí sự kiện như click chuột, nhập dữ liệu, các hàm để binding dữ liệu, các hàm để hiển thị dữ liệu,... Các phương thức này sẽ được lưu trữ trong các file .java riêng biệt.
- **Chi tiết hơn về kiến trúc MVC ở phần Link tài liệu tham khảo**

# Link tài liệu tham khảo
- [Luật chơi cờ gánh](https://thuthuatchoi.com/huong-dan-cach-choi-co-ganh.html), trên mạng có một số luật chơi khác nhau, nhóm mình sẽ chọn luật chơi này do đây là luật chơi được yêu cầu.
- [JavaFX Tutorial](https://www.youtube.com/watch?v=FLkOX4Eez6o&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)
- [JavaFX Tutorial GUI](https://o7planning.org/11009/javafx)
- [MVC Design Pattern](https://www.geeksforgeeks.org/mvc-design-pattern/)