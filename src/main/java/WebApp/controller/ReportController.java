package WebApp.controller;

import WebApp.entity.Report;
import WebApp.service.ReportServiceImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reports")
public class ReportController extends AbstractController<Report,ReportServiceImpl> {
    public ReportController(ReportServiceImpl service) {
        super(service);
    }


}
