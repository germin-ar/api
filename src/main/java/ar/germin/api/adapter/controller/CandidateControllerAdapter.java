package ar.germin.api.adapter.controller;

import ar.germin.api.adapter.controller.models.CandidateResponseModel;
import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateControllerAdapter {
    private final GetCandidatesPlantsPortIn getCandidatesPlantsPortIn;

    @Autowired
    public CandidateControllerAdapter(GetCandidatesPlantsPortIn getCandidatesPlantsPortIn) {
        this.getCandidatesPlantsPortIn = getCandidatesPlantsPortIn;
    }

    @GetMapping("/{id}")
    public CandidateResponseModel getCandidates(@PathVariable("id") String id) {
        AIDetection aiDetection = this.getCandidatesPlantsPortIn.get(id);

        return CandidateResponseModel.fromDomain(aiDetection);
    }
}
