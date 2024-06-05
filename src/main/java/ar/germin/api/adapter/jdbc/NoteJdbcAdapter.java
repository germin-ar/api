package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.NoteModel;
import ar.germin.api.application.domain.Note;
import ar.germin.api.application.exceptions.NoteNotFoundException;
import ar.germin.api.application.port.out.GetNoteRepository;
import ar.germin.api.application.port.out.SaveNoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class NoteJdbcAdapter implements GetNoteRepository, SaveNoteRepository {

    private static final String SAVE_NOTE_PATH = "sql/saveNote.sql";
    private static final String SELECT_NOTE_BY_ID_PATH = "sql/selectNoteById.sql";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String saveNoteSql;
    private final String selectNoteByIdSql;

    public NoteJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.saveNoteSql = sqlReader.readSql(SAVE_NOTE_PATH);
        this.selectNoteByIdSql = sqlReader.readSql(SELECT_NOTE_BY_ID_PATH);
    }

    @Override
    public Note save(Integer userId, Integer plantId, String observations) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("plantId", plantId)
                .addValue("observations", observations);
        log.info("Saving note with sql [{}] with params: [{}]", saveNoteSql, params);

        this.namedParameterJdbcTemplate.update(saveNoteSql, params);
        return null;
    }

    @Override
    public Note getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        log.info("Querying note with sql [{}] with params: [{}]", selectNoteByIdSql, params);

        return Optional
                .of(this.namedParameterJdbcTemplate.query(selectNoteByIdSql, params, BeanPropertyRowMapper.newInstance(NoteModel.class)))
                .map(NoteModel::toDomainFromModelList)
                .orElseThrow(() -> {
                    log.error("Note with id {} not found", id);
                    return new NoteNotFoundException();
                });
    }
}
