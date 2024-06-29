select id,
       name,
       scientific_name_disease,
       type,
       eppo_code,
       common_names,
       kingdom_taxonomy,
       entity_id,
       class_taxonomy,
       genus_taxonomy,
       order_taxonomy,
       family_taxonomy,
       phylum_taxonomy,
       language,
       wiki_urls,
       slug
from garden.diseases_candidate
where slug like :slug