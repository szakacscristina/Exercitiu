package sample.Tests;

import org.junit.jupiter.api.Test;
import sample.Domain.Invoice;
import sample.Domain.InvoiceDateFormatException;
import sample.Domain.InvoiceValidator;
import sample.Repository.FileRepository;
import sample.Service.InvoiceService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {
    @Test
    void addShouldAddTheGivenInvoice() {

        InvoiceValidator validator = new InvoiceValidator();
        FileRepository<Invoice> repository = new FileRepository<>(validator, "test1.json", Invoice[].class);
        InvoiceService service = new InvoiceService(repository);

        service.add("1", 200, "test", "20.04.2019");
        List<Invoice> all = service.getAll();
        assertEquals(1, all.size());
        assertEquals("1", all.get(0).getId());
        assertEquals(200, all.get(0).getSum());
        assertEquals("test", all.get(0).getDescription());
        assertEquals("20.04.2019", all.get(0).getDate());
    }

    @Test
    void getDaySumShouldComputeCorrectDailySums() {
        InvoiceValidator validator = new InvoiceValidator();
        FileRepository<Invoice> repository = new FileRepository<>(validator, "test2.json", Invoice[].class);
        InvoiceService service = new InvoiceService(repository);

        service.add("1", 261, "test", "20.04.2019");
        service.add("2", 252, "test1", "21.04.2019");
        service.add("3", 343, "test2", "20.04.2019");
        service.add("4", 434, "test3", "20.04.2019");
        service.add("5", 525, "test4", "21.04.2019");
        service.add("6", 616, "test5", "22.04.2019");

        double sum20 = service.getDaySum("20.04.2019");
        double sum21 = service.getDaySum("21.04.2019");
        double sum22 = service.getDaySum("22.04.2019");

        assertEquals(261+343+434, sum20);
        assertEquals(252+525, sum21);
        assertEquals(616, sum22);

        assertThrows(InvoiceDateFormatException.class, () -> service.getDaySum("rewrwfsd"));
        assertThrows(InvoiceDateFormatException.class, () -> service.getDaySum("63.13.2014"));
    }
}