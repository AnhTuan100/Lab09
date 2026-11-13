import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PaymentProcessor {
    protected String orderId;
    protected double amount;
    protected static final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public PaymentProcessor(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public final void processPayment() {
        System.out.println("===============================================================");
        System.out.println("BẮT ĐẦU XỬ LÝ GIAO DỊCH ĐƠN HÀNG: " + orderId);

        if (!validateOrder()) {
            System.out.println("[HỦY GIAO DỊCH] Dữ liệu đơn hàng không hợp lệ!");
            return;
        }

        if (!authenticate()) {
            System.out.println("[HỦY GIAO DỊCH] Xác thực thông tin thanh toán thất bại!");
            return;
        }

        double fee = calculateFee();
        double totalAmount = this.amount + fee;
        System.out.println("-> Số tiền gốc: " + df.format(this.amount) + " | Phí giao dịch: " + df.format(fee));

        if (executeTransaction(totalAmount)) {
            saveTransactionLog(totalAmount);

            sendNotification();
            System.out.println("[THÀNH CÔNG] Hoàn tất quy trình thanh toán đơn hàng " + orderId);
            System.out.println("===============================================================\n");
        } else {
            System.out.println("[THẤT BẠI] Lỗi trong quá trình trừ tiền tại cổng thanh toán.");
        }
    }

    private boolean validateOrder() {
        System.out.println("[1. Kiểm tra] Đang kiểm tra tính hợp lệ của đơn hàng " + orderId + "...");
        return this.orderId != null && !this.orderId.trim().isEmpty() && this.amount > 0;
    }

    private void saveTransactionLog(double totalAmount) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[4. Ghi Log] Đã lưu DB lúc " + time + " | Mã ĐH: " + orderId + " | Tổng thu: " + df.format(totalAmount));
    }

    protected double calculateFee() {
        return 0;
    }

    protected void sendNotification() {
        System.out.println("[5. Thông báo] Đã gửi Email biên lai thanh toán tiêu chuẩn cho đơn hàng " + orderId + ".");
    }

    protected abstract boolean authenticate();
    protected abstract boolean executeTransaction(double totalAmount);
}