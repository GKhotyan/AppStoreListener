package controllers.rest;

import db.entities.AppStoreApplicationInfo;
import db.repositories.AppStoreApplicationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationListControler {
    @Autowired
    AppStoreApplicationInfoRepository repository;

    @GetMapping
    public List<AppStoreApplicationInfo> getApplications() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity  createApplication(@RequestBody AppStoreApplicationInfo applicationInfo) {
        repository.save(applicationInfo);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity getApplication(@PathVariable("name") String name) {

        List<AppStoreApplicationInfo> applicationInfoList = repository.findByName(name);
        if (applicationInfoList == null || applicationInfoList.size()==0) {
            return new ResponseEntity("No Application found for name " + name, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(applicationInfoList, HttpStatus.OK);
    }

}
