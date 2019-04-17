package sample.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class InvoiceValidator implements IValidator<Invoice> {

    /**
     * Validates an invoice.
     * @param invoice the invoice to validate.
     * @throws InvoiceDateFormatException if the date is not in the correct format.
     */
    @Override
    public void validate(Invoice invoice) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);
        try {
            format.parse(invoice.getDate());
        } catch (ParseException pe) {
            throw new InvoiceDateFormatException("The date is not in a correct format!");
        }
    }
}
