package ar.germin.api.application.port.in;

public interface SaveGardenPortIn {
    Boolean save(Integer userId, String name);
}
