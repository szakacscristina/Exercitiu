package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import sample.Domain.Invoice;
import sample.Domain.InvoiceDateFormatException;
import sample.Service.InvoiceService;
import sun.security.pkcs11.wrapper.CK_ATTRIBUTE;

public class Controller {
    public TableView tableViewInvoices;
    public TableColumn tableColumnId;
    public TableColumn tableColumnSum;
    public TableColumn tableColumnDescription;
    public TableColumn tableColumnDate;
    public TextField txtId;
    public TextField txtSum;
    public TextField txtDescription;
    public TextField txtDate;
    public Button btnAdd;
    public TextField txtSumDay;
    public TextField txtSumResult;
    public Button btnSumForDay;

    private InvoiceService invoiceService;
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            double sum = Double.parseDouble(txtSum.getText());
            String description = txtDescription.getText();
            String date = txtDate.getText();
            invoiceService.add(id, sum, description, date);

            txtId.clear();
            txtSum.clear();
            txtDescription.clear();
            txtDate.clear();

            invoices.clear();
            invoices.addAll(invoiceService.getAll());

        } catch (InvoiceDateFormatException idfe) {
            Common.showValidationError(idfe.getMessage());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void setServices(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            invoices.addAll(invoiceService.getAll());
            tableViewInvoices.setItems(invoices);
        });
    }

    public void btnSumForDayClick(ActionEvent actionEvent) {
        try {
            String date = txtSumDay.getText();
            double sum = invoiceService.getDaySum(date);
            txtSumResult.setText(String.valueOf(sum));
        } catch (InvoiceDateFormatException idfe) {
            Common.showValidationError(idfe.getMessage());
        }
    }
}
