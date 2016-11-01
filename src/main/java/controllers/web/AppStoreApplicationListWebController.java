package controllers.web;

import controllers.data.ApplicationInfoData;
import db.entities.AppStoreApplicationInfo;
import db.repositories.AppStoreApplicationInfoRepository;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppStoreApplicationListWebController {
    @Autowired
    AppStoreApplicationInfoRepository repository;

    @RequestMapping("/applicationList")
    public String getApplicatinList(Model model){
        List<AppStoreApplicationInfo> applicationInfoList = repository.findAll();
        List<ApplicationInfoData> applicationInfoDataList = new ArrayList<ApplicationInfoData>();
        for(AppStoreApplicationInfo applicationInfo : applicationInfoList) {
            ApplicationInfoData applicationInfoData = new ApplicationInfoData();
            applicationInfoData.setName(applicationInfo.getName());
            applicationInfoData.setUrl(applicationInfo.getUrl());

            String maxVersion = applicationInfo.getVersions().stream()
                .max((e1, e2) -> new DefaultArtifactVersion(e1)
                        .compareTo(new DefaultArtifactVersion(e2))).orElse(null);
            applicationInfoData.setMaxVersion(maxVersion);
            applicationInfoDataList.add(applicationInfoData);
        }
        model.addAttribute("applicationList", applicationInfoDataList);

        return "/applicationListPage.jsp";
    }
}
