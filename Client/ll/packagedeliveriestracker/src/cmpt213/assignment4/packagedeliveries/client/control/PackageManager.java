package cmpt213.assignment4.packagedeliveries.client.control;

import cmpt213.assignment4.packagedeliveries.client.model.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletionException;


/**
 * Class for managing the list of packages
 *
 * @author Omansh
 */
public class PackageManager {

    private static ArrayList<PkgInfo> pkgInfos;
    private static ArrayList<PkgInfo> pkgInfosUP;
    private static ArrayList<PkgInfo> pkgInfosOD;

    /**
     * Default constructor which loads the package list
     */
    public PackageManager() {
        loadData();
    }

    /**
     * Parse data from http response
     */
    public static String parseDataHTTP(String responseBody) {
        pkgInfos = new ArrayList<>();

        if (Objects.equals(responseBody, "[]")) return "Success";
        JsonArray jsonArr = JsonParser.parseString(responseBody).getAsJsonArray();

        //Checks if json file is empty array
        if (!Objects.equals(jsonArr.toString(), "[{}]")) {

            //Goes through each object in Json file and adds them to the array
            for (JsonElement packageElement : jsonArr) {

                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();
                JsonObject j1 = packageElement.getAsJsonObject();
                String j = j1.toString();
                if (j.contains("Book")) {
                    PkgInfo pkg = gson.fromJson(packageElement, BookPackage.class);
                    pkg.setAuthor(j1.get("author").getAsString());
                    pkgInfos.add(pkg);
                } else if (j.contains("Perishable")) {
                    PkgInfo pkg = gson.fromJson(packageElement, PerishablePackage.class);
                    pkg.setExpiryDate(LocalDateTime.parse(j1.get("expiryDate").getAsString()));
                    pkgInfos.add(pkg);
                } else if (j.contains("\"type\":\"Electronic\"")) {
                    PkgInfo pkg = gson.fromJson(packageElement, ElectronicPackage.class);
                    pkg.setFee(j1.get("fee").getAsDouble());
                    pkgInfos.add(pkg);
                }
            }
        }

        pkgInfos.sort(new SortByDate());
        return "Success";
    }

    /**
     * Parse data from http response
     */
    public static String parseDataHTTPUP(String responseBody) {
        pkgInfosUP = new ArrayList<>();

        if (Objects.equals(responseBody, "[]")) return "Success";
        JsonArray jsonArr = JsonParser.parseString(responseBody).getAsJsonArray();

        //Checks if json file is empty array
        if (!Objects.equals(jsonArr.toString(), "[{}]")) {

            //Goes through each object in Json file and adds them to the array
            for (JsonElement packageElement : jsonArr) {

                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();
                JsonObject j1 = packageElement.getAsJsonObject();
                String j = j1.toString();
                if (j.contains("Book")) {
                    PkgInfo pkg = gson.fromJson(packageElement, BookPackage.class);
                    pkg.setAuthor(j1.get("author").getAsString());
                    pkgInfosUP.add(pkg);
                } else if (j.contains("Perishable")) {
                    PkgInfo pkg = gson.fromJson(packageElement, PerishablePackage.class);
                    pkg.setExpiryDate(LocalDateTime.parse(j1.get("expiryDate").getAsString()));
                    pkgInfosUP.add(pkg);
                } else if (j.contains("\"type\":\"Electronic\"")) {
                    PkgInfo pkg = gson.fromJson(packageElement, ElectronicPackage.class);
                    pkg.setFee(j1.get("fee").getAsDouble());
                    pkgInfosUP.add(pkg);
                }
            }
        }

        pkgInfosUP.sort(new SortByDate());
        return "Success";
    }

    /**
     * Parse data from http response
     */
    public static String parseDataHTTPOD(String responseBody) {
        pkgInfosOD = new ArrayList<>();

        if (Objects.equals(responseBody, "[]")) return "Success";
        JsonArray jsonArr = JsonParser.parseString(responseBody).getAsJsonArray();

        //Checks if json file is empty array
        if (!Objects.equals(jsonArr.toString(), "[{}]")) {

            //Goes through each object in Json file and adds them to the array
            for (JsonElement packageElement : jsonArr) {

                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();
                JsonObject j1 = packageElement.getAsJsonObject();
                String j = j1.toString();
                if (j.contains("Book")) {
                    PkgInfo pkg = gson.fromJson(packageElement, BookPackage.class);
                    pkg.setAuthor(j1.get("author").getAsString());
                    pkgInfosOD.add(pkg);
                } else if (j.contains("Perishable")) {
                    PkgInfo pkg = gson.fromJson(packageElement, PerishablePackage.class);
                    pkg.setExpiryDate(LocalDateTime.parse(j1.get("expiryDate").getAsString()));
                    pkgInfosOD.add(pkg);
                } else if (j.contains("\"type\":\"Electronic\"")) {
                    PkgInfo pkg = gson.fromJson(packageElement, ElectronicPackage.class);
                    pkg.setFee(j1.get("fee").getAsDouble());
                    pkgInfosOD.add(pkg);
                }
            }
        }

        pkgInfosOD.sort(new SortByDate());
        return "Success";
    }

    /**
     * Loads data from http
     */
    public void loadData() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/listAll")).build();
        } catch (CompletionException e) {
            System.out.println("Web server not online");
        }

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(PackageManager::parseDataHTTP)
                    .join();
        } catch (CompletionException e) {
            System.out.println("Unable to reach web server");
            throw e;
        }
    }

    public void loadDataUP() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/listUpcomingPackage")).build();
        } catch (CompletionException e) {
            System.out.println("Web server not online");
        }

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(PackageManager::parseDataHTTPUP)
                    .join();
        } catch (CompletionException e) {
            System.out.println("Unable to reach web server");
            throw e;
        }
    }

    public void loadDataOD() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/listOverduePackage")).build();
        } catch (CompletionException e) {
            System.out.println("Web server not online");
        }

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(PackageManager::parseDataHTTP)
                    .join();
        } catch (CompletionException e) {
            System.out.println("Unable to reach web server");
            throw e;
        }
    }

    /**
     * Saves package list http
     */
    public void saveData() {

        URI uri = null;
        try {
            uri = new URI("http://localhost:8080/setPackages");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

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

        String jsonArr = myGson.toJson(pkgInfos);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(jsonArr))
                .header("Content-type", "application/json")
                .build();

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Returns packages
     *
     * @return ArrayList<Package>
     */
    public ArrayList<PkgInfo> getPackages() {
        loadData();
        pkgInfos.sort(new SortByDate());
        return pkgInfos;

    }

    /**
     * Sets packages
     */
    public void setPackages(ArrayList<PkgInfo> pkgInfos) {
        PackageManager.pkgInfos = pkgInfos;
        pkgInfos.sort(new SortByDate());
        saveData();
    }

    public ArrayList<PkgInfo> getPackagesUP() {
        loadDataUP();
        pkgInfosUP.sort(new SortByDate());
        return pkgInfosUP;

    }

    public ArrayList<PkgInfo> getPackagesOD() {
        loadDataOD();
        pkgInfosOD.sort(new SortByDate());
        return pkgInfosOD;

    }

    /**
     * Adds packages using HTTP
     */
    public void add(PkgInfo pkg) {
        URI uri = null;
        try {
            uri = new URI("http://localhost:8080/addPackage");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

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

        String jsonArr = myGson.toJson(pkg);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(jsonArr))
                .header("Content-type", "application/json")
                .build();

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        loadData();
    }

    /**
     * Removes package from server HTTP
     *
     * @param index
     */
    public void remove(int index) {
        URI uri = null;
        String ur = "http://localhost:8080/removePackage/" + index;
        try {
            uri = new URI(ur);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(uri)
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Content-type", "application/json")
                .build();

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        loadData();
    }

    public void markDel(int index) {
        URI uri = null;
        String ur = "http://localhost:8080/markPackageAsDelivered/" + index;
        try {
            uri = new URI(ur);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(uri)
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Content-type", "application/json")
                .build();

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        loadData();
    }
}
