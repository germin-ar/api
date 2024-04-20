package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.CandidateResponseModel;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateControllerAdapter {
    private final GetCandidatesPlantsPortIn getCandidatesPlantsPortIn;

    @Autowired
    public CandidateControllerAdapter(GetCandidatesPlantsPortIn getCandidatesPlantsPortIn) {
        this.getCandidatesPlantsPortIn = getCandidatesPlantsPortIn;
    }

    @GetMapping("/{id}")
    public Mono<CandidateResponseModel> getCandidates(@PathVariable("id") String id) {
        return this.getCandidatesPlantsPortIn
                .get(id)
                .map(CandidateResponseModel::fromDomain);
    }
}
