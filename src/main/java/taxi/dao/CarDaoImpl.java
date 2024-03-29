package taxi.dao;

import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class CarDaoImpl implements CarDao {
    private static final int ZERO_PLACEHOLDER = 0;
    private static final int SHIFT = 2;

    @Override
    public Car create(Car car) {
        String insertQuery = "INSERT INTO cars (model, manufacturer_id)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car " + car, e);
        }
        insertAllDrivers(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.id = ? AND c.is_deleted = false";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = parseCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id: " + id, e);
        }
        if (car != null) {
            car.setDrivers(getAllDriversByCarId(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.is_deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(parseCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        cars.forEach(car -> car.setDrivers(getAllDriversByCarId(car.getId())));
        return cars;
    }

    @Override
    public Car update(Car car) {
        String selectQuery = "UPDATE cars SET model = ?, manufacturer_id = ? WHERE id = ?"
                + " and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
        deleteAllDriversExceptList(car);
        insertAllDrivers(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String selectQuery = "UPDATE cars SET is_deleted = true WHERE id = ?"
                + " and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM cars c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " JOIN cars_drivers cd on c.id = cd.car_id"
                + " JOIN drivers d on cd.driver_id = d.id"
                + " where c.is_deleted = false and driver_id = ?"
                + " and d.is_deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(parseCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        cars.forEach(car -> car.setDrivers(getAllDriversByCarId(car.getId())));
        return cars;
    }

    private void insertAllDrivers(Car car) {
        Long carId = car.getId();
        List<Driver> drivers = car.getDrivers();
        if (drivers == null || drivers.size() == 0) {
            return;
        }
        String insertQuery = "INSERT INTO cars_drivers (car_id, driver_id) VALUES "
                + drivers.stream().map(driver -> "(?, ?)").collect(Collectors.joining(", "))
                + " ON DUPLICATE KEY UPDATE car_id = car_id";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(insertQuery)) {
            for (int i = 0; i < drivers.size(); i++) {
                Driver driver = drivers.get(i);
                statement.setLong((i * SHIFT) + 1, carId);
                statement.setLong((i * SHIFT) + 2, driver.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert drivers " + drivers, e);
        }
    }

    private void deleteAllDriversExceptList(Car car) {
        Long carId = car.getId();
        List<Driver> exceptions = car.getDrivers();
        int size = exceptions.size();
        String insertQuery = "DELETE FROM cars_drivers WHERE car_id = ? "
                + "AND NOT driver_id IN ("
                + ZERO_PLACEHOLDER + ", ?".repeat(size)
                + ");";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(insertQuery)) {
            statement.setLong(1, carId);
            for (int i = 0; i < size; i++) {
                Driver driver = exceptions.get(i);
                statement.setLong((i) + SHIFT, driver.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers " + exceptions, e);
        }
    }

    private List<Driver> getAllDriversByCarId(Long carId) {
        String selectQuery = "SELECT id, name, license_number, login, password "
                + "FROM cars_drivers cd "
                + "JOIN drivers d on cd.driver_id = d.id "
                + "where car_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(selectQuery)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(parseDriverFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers by car id" + carId, e);
        }
    }

    private Driver parseDriverFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getNString("name");
        String licenseNumber = resultSet.getNString("license_number");
        String login = resultSet.getNString("login");
        String password = resultSet.getNString("password");
        return new Driver(id, name, licenseNumber, login, password);
    }

    private Car parseCarFromResultSet(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getNString("manufacturer_name");
        String manufacturerCountry = resultSet.getNString("manufacturer_country");
        Manufacturer manufacturer
                = new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry);
        Long carId = resultSet.getObject("id", Long.class);
        String model = resultSet.getNString("model");
        return new Car(carId, model, manufacturer);
    }
}
