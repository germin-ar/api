package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.FileImageModel;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.exceptions.FileImageNotFoundException;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.SaveFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class FileImageJdbcAdapter implements SaveFileRepository, GetFileRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FileImageJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public FileImage save(FileImage fileImage) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", fileImage.getId())
                    .addValue("filePath", fileImage.getFilePath())
                    .addValue("isPublic", fileImage.getIsPublic());

            this.namedParameterJdbcTemplate
                    .update("insert into images.file_image (id, file_path, is_public) values (:id, :filePath, :isPublic)", params);

            return fileImage;
        } catch (DuplicateKeyException ex) {
            log.error("Error saving image for duplicate key", ex);
            throw new FileImageAlreadyExistsException("Image already exists");
        }
    }

    @Override
    public FileImage getById(String id) {
        String sql = "select id, file_path, is_public from images.file_image where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        log.info("Querying image with sql [{}] with params: [{}]", sql, params);

        return Optional.ofNullable(this.namedParameterJdbcTemplate.queryForObject(sql,
                        params,
                        new BeanPropertyRowMapper<>(FileImageModel.class))
                )
                .map(FileImageModel::toDomain)
                .orElseThrow(() -> {
                    log.error("Image not found with id: {}", id);
                    return new FileImageNotFoundException();
                });
    }
}
