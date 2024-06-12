package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.SaveGardenPortIn;
import ar.germin.api.application.port.out.SaveGardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveGardenUseCase implements SaveGardenPortIn {
    private final SaveGardenRepository saveGardenRepository;

    @Autowired
    public SaveGardenUseCase(SaveGardenRepository saveGardenRepository) {
        this.saveGardenRepository = saveGardenRepository;
    }

    @Override
    public Integer save(Integer userId, String name) {
        return this.saveGardenRepository.save(userId, name);
    }
}
