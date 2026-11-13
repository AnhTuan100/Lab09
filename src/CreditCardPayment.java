public class CreditCardPayment extends PaymentProcessor {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String orderId, double amount, String cardNumber, String cvv) {
        super(orderId, amount);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    protected boolean authenticate() {
        System.out.println("[2. Xác thực Thẻ] Đang kiểm tra thẻ Visa/MasterCard (" + maskCardNumber() + ") và mã CVV...");
        return cardNumber != null && cardNumber.length() == 16 && cvv != null && cvv.length() == 3;
    }

    @Override
    protected double calculateFee() {
        return this.amount * 0.025;
    }

    @Override
    protected boolean executeTransaction(double totalAmount) {
        System.out.println("[3. Trừ tiền Thẻ] Kết nối cổng Stripe/OnePay, thực hiện khóa hạn mức và trừ "
                + df.format(totalAmount) + " từ thẻ " + maskCardNumber() + "...");
        return true;
    }

    private String maskCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) return "Invalid";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}