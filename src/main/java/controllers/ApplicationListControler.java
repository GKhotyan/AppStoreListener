package controllers;

import db.entities.ApplicationInfo;
import db.repositories.ApplicationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationListControler {
    @Autowired
    ApplicationInfoRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    List<ApplicationInfo> findAll() {
        return repository.findAll();
    }
}
