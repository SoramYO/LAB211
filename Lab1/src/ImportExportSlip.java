class ImportExportSlip {
    private String productCode;
    private String Slipcode;
    private int quantity;
    private double price;
    private boolean isImport;

    public ImportExportSlip(String productCode,String Slipcode, int quantity, double price, boolean isImport) {
        this.productCode = productCode;
        this.Slipcode = Slipcode;
        this.quantity = quantity;
        this.price = price;
        this.isImport = isImport;
    }

    public void displaySlipInfo() {
        System.out.println("Product Code: " + productCode);
        System.out.println("Slip Code: " + Slipcode);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price per Unit: " + price);
        System.out.println("Type: " + (isImport ? "Import" : "Export"));
        System.out.println("_________________________");
    }
}