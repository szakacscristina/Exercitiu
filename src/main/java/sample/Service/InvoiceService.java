package sample.Service;

import sample.Domain.Invoice;
import sample.Domain.InvoiceValidator;
import sample.Repository.IRepository;

import java.util.List;

public class InvoiceService {

    private IRepository<Invoice> repository;

    /**
     * Constructs a service.
     * @param repository the backing repository.
     */
    public InvoiceService(IRepository<Invoice> repository) {
        this.repository = repository;
    }

    /**
     * Adds an invoice with the given fields.
     * @param id the id - must be unique.
     * @param sum the sum.
     * @param description the description.
     * @param date the date - must be in dd.mm.yyyy format.
     */
    public void add(String id, double sum, String description, String date) {
        Invoice invoice = new Invoice(id, sum, description, date);
        repository.upsert(invoice);
    }

    /**
     * Gets the sum of all invoices in a given day.
     * @param date the given date.
     * @return the sum of all invoices in date.
     */
    public double getDaySum(String date) {
        InvoiceValidator validator = new InvoiceValidator();
        Invoice dummy = new Invoice(null, 0, null, date);
        validator.validate(dummy);

        double sum = 0;
        for (Invoice invoice : repository.getAll()) {
            if (invoice.getDate().equals(date)) {
                sum += invoice.getSum();
            }
        }
        return sum;
    }

    /**
     * Gets a list of all invoices.
     * @return a list of all invoices.
     */
    public List<Invoice> getAll() {
        return repository.getAll();
    }
}
