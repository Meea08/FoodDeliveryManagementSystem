package dataAccess;

import businessLogic.AccessRights;
import businessLogic.BaseProduct;
import businessLogic.User;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showMessageDialog;

public class FileReader {
    private final Function<String, BaseProduct> mapToItem = line -> {
        String[] p = line.split(",");
        return new BaseProduct(p[0],
                                        Double.parseDouble(p[1]),
                                        Integer.parseInt(p[2]),
                                        Integer.parseInt(p[3]),
                                        Integer.parseInt(p[4]),
                                        Integer.parseInt(p[5]),
                                        Double.parseDouble(p[6]));
    };

    private final Function<String, User> mapToUser = line -> {
        String[] p = line.split(",");
        User user = new User();
        user.setUserID(Integer.parseInt(p[0]));
        user.setUsername(p[1]);
        user.setPassword(p[2]);
        user.setAccessRights(AccessRights.valueOf(p[3]));

        return user;
    };

    public List<BaseProduct> readBaseProducts(String inputFilePath) {
        HashSet<BaseProduct> inputList = new HashSet<>();

        try {
            File inputFile = new File(inputFilePath);
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //skip header of CSV
            inputList = (HashSet<BaseProduct>) bufferedReader.lines().skip(1).map(mapToItem).collect(Collectors.toSet());
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog(null,"Something went wrong. Please check that the path is correct!");
        }
        return inputList.stream().toList();
    }
    public HashSet<User> readUsers(String inputFilePath){
        HashSet<User> inputList = new HashSet<>();

        try {
            File inputFile = new File(inputFilePath);
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //skip header of CSV
            inputList = (HashSet<User>) bufferedReader.lines().skip(1).map(mapToUser).collect(Collectors.toSet());
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputList;
    }
}
