public class EWalletPayment extends PaymentProcessor {
    private String phoneNumber;
    private String otpToken;

    public EWalletPayment(String orderId, double amount, String phoneNumber, String otpToken) {
        super(orderId, amount);
        this.phoneNumber = phoneNumber;
        this.otpToken = otpToken;
    }

    @Override
    protected boolean authenticate() {
        System.out.println("[2. Xác thực Ví] Đang kiểm tra liên kết ví điện tử SĐT " + phoneNumber + " và mã OTP...");
        return phoneNumber != null && phoneNumber.startsWith("0") && "123456".equals(otpToken);
    }

    @Override
    protected boolean executeTransaction(double totalAmount) {
        System.out.println("[3. Trừ tiền Ví] Gọi API Ví điện tử, trừ trực tiếp "
                + df.format(totalAmount) + " từ số dư ví " + phoneNumber + "...");
        return true;
    }

    @Override
    protected void sendNotification() {
        System.out.println("[5. Thông báo] Đã gửi Push Notification trực tiếp về ứng dụng Ví điện tử trên SĐT " + phoneNumber + ".");
    }
}