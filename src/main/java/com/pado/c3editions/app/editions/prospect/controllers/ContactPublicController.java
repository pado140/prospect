package com.pado.c3editions.app.editions.prospect.controllers;

import com.pado.c3editions.app.editions.prospect.dtos.*;
import com.pado.c3editions.app.editions.prospect.dtos.logs.Logdto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.LogsDto;
import com.pado.c3editions.app.editions.prospect.middleware.UserClient;
import com.pado.c3editions.app.editions.prospect.middleware.UserServiceClient;
import com.pado.c3editions.app.editions.prospect.pojos.*;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.logs.ContactLogs;
import com.pado.c3editions.app.editions.prospect.services.*;
import com.pado.c3editions.app.editions.prospect.utils.MapperUtils;
import com.pado.c3editions.app.editions.prospect.utils.Reponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("contact/public")
//@Api(value = "Contact",tags = "Contact controlleur")
public class ContactPublicController {

    @Autowired
    private MapperUtils mapperUtils;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ContactService contactService;

    @Autowired
    private VisiteService visiteService;

    @Autowired
    private UserServiceClient userService;

    @Autowired
    private ContactLogServices contactLogServices;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuteurService auteurService;

    @Autowired
    private EntrepriseService entrepriseService;
    @Autowired
    private DemarcheurService demarcheurService;
    @Autowired
    private DistributeurService distributeurService;

    @GetMapping("")
    public List<ContactEDto> index1(){
        return mapperUtils.asDTOList(contactService.findAll(), ContactEDto.class);
    }

    @GetMapping("/client")
    public List<ClientsDto> clientList(){
        return mapperUtils.asDTOList(clientService.findAll(), ClientsDto.class);
    }

    // Add those function 31-12-2023
    @GetMapping("/demarcheur")
    public List<DemarcheursDto> demarcheurList(){
        return mapperUtils.asDTOList(contactService.getDemarcheur(), DemarcheursDto.class);
    }

    @GetMapping("/distributeur")
    public List<DistributeursDto> distributeurList(){
        return mapperUtils.asDTOList(contactService.getDistributeur(), DistributeursDto.class);
    }

    //comment 31-12-2023
//    @GetMapping("/demarcheur")
//    public List<DemarcheursDto> demarcheurList(){
//        return mapperUtils.asDTOList(demarcheurService.findAll(), DemarcheursDto.class);
//    }
//
//    @GetMapping("/distributeur")
//    public List<DistributeursDto> distributeurList(){
//        return mapperUtils.asDTOList(distributeurService.findAll(), DistributeursDto.class);
//    }

    @GetMapping("/auteur")
    public List<AuteurDto> auteurList(){
        return mapperUtils.asDTOList(auteurService.findAll(),AuteurDto.class);
    }

    @GetMapping("/entreprise")
    public List<EntrepriseDto> entrepriseList(){
        return mapperUtils.asDTOList(entrepriseService.findAll(), EntrepriseDto.class);
    }

    @GetMapping("{contact_id}")
    public ContactPublicDto get(@PathVariable("contact_id") long contact_id){
        return mapperUtils.asDTO(contactService.findById(contact_id),ContactPublicDto.class);
    }
}
