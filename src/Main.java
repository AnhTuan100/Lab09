public class Main {
    public static void main(String[] args) {
        // Trường hợp 1: Thanh toán bằng Thẻ tín dụng (Có tính phí 2.5%, gửi thông báo Email mặc định)
        PaymentProcessor creditCardOrder = new CreditCardPayment(
                "ORD-2026-001",
                2000000,
                "4111222233334444",
                "888"
        );
        creditCardOrder.processPayment();

        // Trường hợp 2: Thanh toán bằng Ví điện tử (Miễn phí giao dịch, gửi Push Notification)
        PaymentProcessor eWalletOrder = new EWalletPayment(
                "ORD-2026-002",
                500000,
                "0987654321",
                "123456"
        );
        eWalletOrder.processPayment();

        // Trường hợp 3: Kiểm thử luồng thất bại khi xác thực sai mã OTP
        PaymentProcessor failedOrder = new EWalletPayment(
                "ORD-2026-003",
                350000,
                "0987654321",
                "000000" // Sai OTP
        );
        failedOrder.processPayment();
    }
}