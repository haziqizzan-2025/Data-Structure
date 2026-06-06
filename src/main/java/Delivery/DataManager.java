package Delivery;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// --- ENTITIES ---

class User {
    private String userId;
    private String name;
    private String phone;

    public User(String userId, String name, String phone) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }
    
    // Getters and Setters for Integration and Updates
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return "User[" + userId + "] " + name + " (" + phone + ")";
    }
}

class Restaurant {
    private String restaurantId;
    private String name;
    private String location;

    public Restaurant(String restaurantId, String name, String location) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
    }

    // Getters and Setters for Integration and Updates
    public String getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return "Restaurant[" + restaurantId + "] " + name + " - " + location;
    }
}

// --- MAIN DATA MANAGEMENT SYSTEM ---

public class DataManager {

    // Sequential Storage: O(N) traversal for display and sorting preparation
    private List<User> userList = new LinkedList<>();
    private List<Restaurant> restaurantList = new LinkedList<>();

    // Hash Tables: O(1) instant retrieval
    private Map<String, User> userMap = new HashMap<>();
    private Map<String, Restaurant> restaurantMap = new HashMap<>();

    // Regex pattern for basic phone number validation (digits and dashes)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9-]+$");

    public DataManager() {
        // Automatically load dummy data upon instantiation for group testing
        loadDummyData();
    }

    // --- USER CRUD OPERATIONS ---

    // CREATE: O(1)
    public boolean addUser(String id, String name, String phone) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            System.err.println("Error: User ID and Name cannot be empty.");
            return false;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            System.err.println("Error: Invalid phone number format for " + name);
            return false;
        }
        if (userMap.containsKey(id)) {
            System.err.println("Error: User ID " + id + " already exists.");
            return false;
        }
        
        User newUser = new User(id, name, phone);
        userList.add(newUser);
        userMap.put(id, newUser);
        return true;
    }

    // READ (Integration Hook): O(1)
    public User getUser(String id) {
        return userMap.get(id);
    }

    // READ ALL (Integration Hook for BST sorting): O(1) to return reference
    public List<User> getAllUsers() {
        return userList;
    }

    // UPDATE: O(1)
    public boolean updateUserPhone(String id, String newPhone) {
        User user = userMap.get(id);
        if (user != null) {
            if (PHONE_PATTERN.matcher(newPhone).matches()) {
                user.setPhone(newPhone);
                return true;
            } else {
                System.err.println("Error: Invalid phone number format.");
            }
        }
        return false;
    }

    // DELETE: O(N) due to LinkedList removal, O(1) for HashMap
    public boolean removeUser(String id) {
        User userToRemove = userMap.remove(id);
        if (userToRemove != null) {
            userList.remove(userToRemove);
            return true;
        }
        return false;
    }

    // DISPLAY: O(N)
    public void displayAllUsers() {
        System.out.println("\n--- Registered Users ---");
        for (User u : userList) {
            System.out.println(u);
        }
    }

    // --- RESTAURANT CRUD OPERATIONS ---

    // CREATE: O(1)
    public boolean addRestaurant(String id, String name, String location) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            return false;
        }
        if (restaurantMap.containsKey(id)) {
            return false;
        }
        Restaurant newRest = new Restaurant(id, name, location);
        restaurantList.add(newRest);
        restaurantMap.put(id, newRest);
        return true;
    }

    // READ (Integration Hook): O(1)
    public Restaurant getRestaurant(String id) {
        return restaurantMap.get(id);
    }
    
    // READ ALL (Integration Hook): O(1)
    public List<Restaurant> getAllRestaurants() {
        return restaurantList;
    }

    // UPDATE: O(1)
    public boolean updateRestaurantLocation(String id, String newLocation) {
        Restaurant rest = restaurantMap.get(id);
        if (rest != null && newLocation != null && !newLocation.trim().isEmpty()) {
            rest.setLocation(newLocation);
            return true;
        }
        return false;
    }

    // DELETE: O(N) LinkedList, O(1) HashMap
    public boolean removeRestaurant(String id) {
        Restaurant restToRemove = restaurantMap.remove(id);
        if (restToRemove != null) {
            restaurantList.remove(restToRemove);
            return true;
        }
        return false;
    }

    // DISPLAY: O(N)
    public void displayAllRestaurants() {
        System.out.println("\n--- Registered Restaurants ---");
        for (Restaurant r : restaurantList) {
            System.out.println(r);
        }
    }

    // --- DUMMY DATA PRE-LOADER ---
    private void loadDummyData() {
        addUser("U001", "Amirun", "012-3456789");
        addUser("U002", "Haziq", "019-8765432");
        addUser("U003", "Aasim", "011-1112223");
        addUser("U004", "Hazim", "017-9998887");
        addUser("U005", "Ammar", "013-4445556");

        addRestaurant("R001", "KFC", "Mid Valley");
        addRestaurant("R002", "McDonald's", "Bangsar");
        addRestaurant("R003", "Hadramawt", "Bukit Bintang");
        addRestaurant("R004", "Johnny's Restaurant", "1 Utama");
        addRestaurant("R005", "Damascus", "KLCC");
    }

    // --- TESTING MAIN METHOD ---
    public static void main(String[] args) {
        DataManager db = new DataManager();

        // 1. Display pre-loaded dummy data
        db.displayAllUsers();
        db.displayAllRestaurants();

        // 2. Test Error Handling & Validation
        System.out.println("\n--- Testing Validation ---");
        db.addUser("U006", "Invalid User", "letters-not-allowed");

        // 3. Test Update Functionality (CRUD)
        System.out.println("\n--- Testing Updates ---");
        db.updateUserPhone("U001", "010-0000000");
        db.updateRestaurantLocation("R001", "Sunway Pyramid");
        System.out.println(db.getUser("U001"));
        System.out.println(db.getRestaurant("R001"));

        // 4. Test O(1) Integration Hooks
        System.out.println("\n--- Testing Integration Retrieval O(1) ---");
        Restaurant target = db.getRestaurant("R003");
        System.out.println("Module retrieved: " + target.getName());
    }
}