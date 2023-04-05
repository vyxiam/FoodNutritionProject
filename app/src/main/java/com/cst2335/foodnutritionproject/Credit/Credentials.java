package com.cst2335.foodnutritionproject.Credit;

import java.util.ArrayList;
import java.util.List;

public class Credentials {
    private List<String> authors;
    private List<String> descriptions;

    public Credentials(){
        generateCredentials();
    }
    private void generateCredentials(){
        authors = new ArrayList<>();
        descriptions = new ArrayList<>();

        authors.add("Mohamed Abdelsalam");
        authors.add("Nhuan Pham");
        authors.add("Van Vy Nguyen");

        descriptions.add("Analyse documents, prepare a detailed plans.\n" +
                        "Build credit screen.\n" +
                        "Contribute to the project.\n" +
                        "Assist other contributors");
        descriptions.add("Create and contribute to database.\n" +
                        "Create the favorite function.\n" +
                        "Design offline mode.");
        descriptions.add("Design project blueprint.\n" +
                        "Create the search function.\n" +
                        "Connect to the API server.\n");
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }
}
