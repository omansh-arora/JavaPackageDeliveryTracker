package cmpt213.assignment4.packagedeliveries.webappserver.controllers;

import cmpt213.assignment4.packagedeliveries.webappserver.model.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;


@RestController
public class APIController {

    ArrayList<PkgInfo> packages = new ArrayList<>();

    Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.toString());
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }).setPrettyPrinting().create();

    public APIController() {
        loadData();
    }

    @GetMapping("/ping")
    public String getHelloMessage() {
        return "System is up!";
    }

    @GetMapping("/exit")
    public void saveData() {

        String jsonArr = myGson.toJson(packages);
        try {
            FileWriter file = new FileWriter("src/list.json", false);
            file.write(jsonArr);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Loads data from json file/creates new json file if none exists
     */
    public void loadData() {
        packages = new ArrayList<>();

        File pkgFile = new File("src/list.json");

        //If no data file exists then it creates a new one
        if (!pkgFile.exists()) {
            pkgFile.getParentFile().mkdirs();
            try {
                pkgFile.createNewFile();
                FileWriter file = new FileWriter("src/list.json", false);
                file.write("[{}]");
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(pkgFile));
            JsonArray jsonArr = fileElement.getAsJsonArray();

            //Checks if json file is empty array
            if (!Objects.equals(jsonArr.toString(), "[{}]")) {

                //Goes through each object in Json file and adds them to the array
                for (JsonElement packageElement : jsonArr) {

                    JsonObject j1 = packageElement.getAsJsonObject();
                    String j = j1.toString();
                    if (j.contains("\"type\":\"Book\"")) {
                        PkgInfo pkg = myGson.fromJson(packageElement, BookPackage.class);
                        packages.add(pkg);
                    } else if (j.contains("Perishable")) {
                        PkgInfo pkg = myGson.fromJson(packageElement, PerishablePackage.class);
                        packages.add(pkg);
                    } else if (j.contains("\"type\":\"Electronic\"")) {
                        PkgInfo pkg = myGson.fromJson(packageElement, ElectronicPackage.class);
                        packages.add(pkg);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        packages.sort(new SortByDate());

    }

    @GetMapping("/listAll")
    public String getPackages() {
        loadData();
        return myGson.toJson(packages);
    }

    @PostMapping("/setPackages")
    public void setPackages(@RequestBody String responseBody) {
        packages.clear();
        JsonArray jsonArr = JsonParser.parseString(responseBody).getAsJsonArray();

        //Checks if json file is empty array
        if (!Objects.equals(jsonArr.toString(), "[{}]")) {

            //Goes through each object in Json file and adds them to the array
            for (JsonElement packageElement : jsonArr) {


                JsonObject j1 = packageElement.getAsJsonObject();
                String j = j1.toString();
                if (j.contains("\"type\":\"Book\"")) {
                    PkgInfo pkg = myGson.fromJson(packageElement, BookPackage.class);
                    pkg.setAuthor(j1.get("author").getAsString());
                    packages.add(pkg);
                } else if (j.contains("\"type\":\"Perishable\"")) {
                    PkgInfo pkg = myGson.fromJson(packageElement, PerishablePackage.class);
                    pkg.setExpiryDate(LocalDateTime.parse(j1.get("expiryDate").getAsString()));
                    packages.add(pkg);
                } else if (j.contains("\"type\":\"Electronic\"")) {
                    PkgInfo pkg = myGson.fromJson(packageElement, ElectronicPackage.class);
                    pkg.setFee(j1.get("fee").getAsDouble());
                    packages.add(pkg);
                }
            }
        }

        saveData();

    }

    @GetMapping("/listUpcomingPackage")
    public String getUPPackages() {
        loadData();

        ArrayList<PkgInfo> UPpacks = new ArrayList<>();

        for (int i = 0; i < packages.size(); i++) {
            if (!packages.get(i).isDelivered() && packages.get(i).getDeliveryDate().isAfter(LocalDateTime.now())) {
                UPpacks.add(packages.get(i));
            }
        }

        return myGson.toJson(UPpacks);
    }

    @GetMapping("/listOverduePackage")
    public String getODPackages() {
        loadData();

        ArrayList<PkgInfo> ODpacks = new ArrayList<>();

        for (int i = 0; i < packages.size(); i++) {
            if (!packages.get(i).isDelivered() && packages.get(i).getDeliveryDate().isBefore(LocalDateTime.now())) {
                ODpacks.add(packages.get(i));
            }
        }

        return myGson.toJson(ODpacks);
    }

    @PostMapping("/addPackage")
    public String addPackage(@RequestBody String requestBody) {
        loadData();
        JsonElement packageElement = JsonParser.parseString(requestBody).getAsJsonObject();

        JsonObject j1 = packageElement.getAsJsonObject();
        String j = j1.toString();
        PkgInfo pkg = null;
        if (j.contains("\"type\":\"Book\"")) {
            pkg = myGson.fromJson(packageElement, BookPackage.class);
            packages.add(pkg);
        } else if (j.contains("\"type\":\"Perishable\"")) {
            pkg = myGson.fromJson(packageElement, PerishablePackage.class);
            packages.add(pkg);
        } else if (j.contains("\"type\":\"Electronic\"")) {
            pkg = myGson.fromJson(packageElement, ElectronicPackage.class);
            packages.add(pkg);
        }


        saveData();


        return getPackages();

    }

    @PostMapping("/removePackage/{id}")
    public String remove(@PathVariable("id") int Id) {
        loadData();
        if (Id < 0 || Id > (packages.size() - 1)) throw new IllegalArgumentException();
        packages.remove(Id);
        saveData();
        return getPackages();
    }

    @PostMapping("/markPackageAsDelivered/{id}")
    public String markDel(@PathVariable("id") int Id) {
        loadData();
        if (Id < 0 || Id > (packages.size() - 1)) throw new IllegalArgumentException();
        packages.get(Id).changeDelivered();
        saveData();
        return getPackages();
    }


}
