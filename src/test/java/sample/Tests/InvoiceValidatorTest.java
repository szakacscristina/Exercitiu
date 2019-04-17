package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Domain.Invoice;
import sample.Domain.InvoiceDateFormatException;
import sample.Domain.InvoiceValidator;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceValidatorTest {

    @Test
    void validateShouldThrowOnlyWhenNecessary() {

        InvoiceValidator validator = new InvoiceValidator();
        Invoice correctInvoice = new Invoice("1", 200, "fdsf", "20.03.2019");
        assertDoesNotThrow(() -> validator.validate(correctInvoice));

        Invoice incorrectFormat = new Invoice("1", 200, "fdsf", "dasda222");
        assertThrows(InvoiceDateFormatException.class, () -> validator.validate(incorrectFormat));

        Invoice incorrectValues = new Invoice("1", 200, "fdsf", "56.32.2423");
        assertThrows(InvoiceDateFormatException.class, () -> validator.validate(incorrectValues));
    }
}