package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.FileImage;

public interface SaveImagePortIn {
    FileImage save(byte[] image);
}
