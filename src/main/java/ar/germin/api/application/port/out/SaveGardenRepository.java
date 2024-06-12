package ar.germin.api.application.port.out;

public interface SaveGardenRepository {
    Boolean save(Integer userId, String name);
}
