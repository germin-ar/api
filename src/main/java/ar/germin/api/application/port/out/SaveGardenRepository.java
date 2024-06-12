package ar.germin.api.application.port.out;

public interface SaveGardenRepository {
    Integer save(Integer userId, String name);
}
