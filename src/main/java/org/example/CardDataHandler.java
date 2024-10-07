package org.example;

import java.io.*;

public class CardDataHandler {
    private File file;

    public CardDataHandler(String filePath) {
        this.file = new File(filePath);
    }

    public void saveCardData(String cardNumber, String cardHolder, String expiryMonth, String expiryYear) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Номер карты: " + cardNumber + "\n");
            writer.write("Держатель карты: " + cardHolder + "\n");
            writer.write("Срок действия: " + expiryMonth + "/" + expiryYear + "\n");
        }
    }

    public String loadCardData() throws IOException {
        StringBuilder data = new StringBuilder();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line).append("\n");
                }
            }
        } else {
            data.append("Информация не найдена!");
        }
        return data.toString();
    }
}