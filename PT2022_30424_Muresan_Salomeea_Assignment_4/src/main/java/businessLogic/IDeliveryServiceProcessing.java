package businessLogic;
import java.io.IOException;
import java.util.Date;

public interface IDeliveryServiceProcessing {
    void generateReport1(int startHour, int endHour);
    void generateReport2(int minNumber);
    void generateReport3(int timesOrdered);
    void generateReport4(Date specifiedDay);
    void addProduct(MenuItem menuItem);
    User getUserByUsername(String username);
    User getUserById(int id);
    void createNewOrder(int clientId) throws IOException;
    void modifyProduct(MenuItem menuItem, String title, String rating, String calories, String protein, String fat, String sodium, String price);
    MenuItem getItemByName(String name);
}
