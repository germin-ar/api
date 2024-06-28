package ar.germin.api.application.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PlantCatalog {
    private Integer id;
    private String scientificName;
    private String description;
    private String slug;
    private String genus;
    private String familyName;
    private Double maxSize;
    private String fertilizer;
    private String sunlight;
    private String wateringFrequency;
    private String pruning;
    private String soil;
    private String insecticide;
    private String tips;
    private Double temperatureMax;
    private Double temperatureMin;
    private String growthSeason;
    private String harvestTime;
    private String plantingTime;

    public PlantCatalog(Integer id, String scientificName, String description, String slug, String genus,
                             String familyName, Double maxSize, String fertilizer, String sunlight, String wateringFrequency,
                             String pruning, String soil, String insecticide, String tips, Double temperatureMax,
                             Double temperatureMin, String growthSeason, String harvestTime, String plantingTime) {
        this.id = id;
        this.scientificName = scientificName;
        this.description = description;
        this.slug = slug;
        this.genus = genus;
        this.familyName = familyName;
        this.maxSize = maxSize;
        this.fertilizer = fertilizer;
        this.sunlight = sunlight;
        this.wateringFrequency = wateringFrequency;
        this.pruning = pruning;
        this.soil = soil;
        this.insecticide = insecticide;
        this.tips = tips;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.growthSeason = growthSeason;
        this.harvestTime = harvestTime;
        this.plantingTime = plantingTime;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Double maxSize) {
        this.maxSize = maxSize;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getPruning() {
        return pruning;
    }

    public void setPruning(String pruning) {
        this.pruning = pruning;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getInsecticide() {
        return insecticide;
    }

    public void setInsecticide(String insecticide) {
        this.insecticide = insecticide;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getGrowthSeason() {
        return growthSeason;
    }

    public void setGrowthSeason(String growthSeason) {
        this.growthSeason = growthSeason;
    }

    public String getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(String harvestTime) {
        this.harvestTime = harvestTime;
    }

    public String getPlantingTime() {
        return plantingTime;
    }

    public void setPlantingTime(String plantingTime) {
        this.plantingTime = plantingTime;
    }

    @Override
    public String toString() {
        return "PlantCatalog{" +
                "id=" + id +
                ", scientificName='" + scientificName + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'' +
                ", genus='" + genus + '\'' +
                ", familyName='" + familyName + '\'' +
                ", maxSize=" + maxSize +
                ", fertilizer='" + fertilizer + '\'' +
                ", sunlight='" + sunlight + '\'' +
                ", wateringFrequency='" + wateringFrequency + '\'' +
                ", pruning='" + pruning + '\'' +
                ", soil='" + soil + '\'' +
                ", insecticide='" + insecticide + '\'' +
                ", tips='" + tips + '\'' +
                ", temperatureMax=" + temperatureMax +
                ", temperatureMin=" + temperatureMin +
                ", growthSeason='" + growthSeason + '\'' +
                ", harvestTime='" + harvestTime + '\'' +
                ", plantingTime='" + plantingTime + '\'' +
                '}';
    }

    public PlantCatalog toDomain() {
        PlantCatalog plantCatalog = new PlantCatalog();
        plantCatalog.setId(this.id);
        plantCatalog.setScientificName(this.scientificName);
        plantCatalog.setDescription(this.description);
        plantCatalog.setSlug(this.slug);
        plantCatalog.setGenus(this.genus);
        plantCatalog.setFamilyName(this.familyName);
        plantCatalog.setMaxSize(this.maxSize);
        plantCatalog.setFertilizer(this.fertilizer);
        plantCatalog.setSunlight(this.sunlight);
        plantCatalog.setWateringFrequency(this.wateringFrequency);
        plantCatalog.setPruning(this.pruning);
        plantCatalog.setSoil(this.soil);
        plantCatalog.setInsecticide(this.insecticide);
        plantCatalog.setTips(this.tips);
        plantCatalog.setTemperatureMax(this.temperatureMax);
        plantCatalog.setTemperatureMin(this.temperatureMin);
        plantCatalog.setGrowthSeason(this.growthSeason);
        plantCatalog.setHarvestTime(this.harvestTime);
        plantCatalog.setPlantingTime(this.plantingTime);
        return plantCatalog;
    }

    private String someField;

    public String getSomeField() {
        return someField;
    }

    public void setSomeField(String someField) {
        this.someField = someField;
    }
}
