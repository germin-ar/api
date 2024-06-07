package ar.germin.api.application.port.out;
import ar.germin.api.application.domain.Garden;
public interface DeleteGardenRepository {
    void deleteGardenById(Integer id);
}