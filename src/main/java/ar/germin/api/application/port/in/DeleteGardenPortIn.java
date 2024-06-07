package ar.germin.api.application.port.in;

public interface DeleteGardenPortIn {
    void delete(Params params);

    class Params {
        private final Integer gardenId;

        public Params(Integer gardenId) {
            this.gardenId = gardenId;
        }

        public Integer getGardenId() {
            return gardenId;
        }
    }
}