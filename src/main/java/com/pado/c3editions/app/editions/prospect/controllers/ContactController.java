package com.pado.c3editions.app.editions.prospect.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.c3editions.app.editions.prospect.dtos.*;
import com.pado.c3editions.app.editions.prospect.dtos.logs.Logdto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.LogsDto;
import com.pado.c3editions.app.editions.prospect.middleware.UserClient;
import com.pado.c3editions.app.editions.prospect.middleware.UserServiceClient;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.*;
import com.pado.c3editions.app.editions.prospect.pojos.logs.*;
import com.pado.c3editions.app.editions.prospect.repositories.AuteursRepository;
import com.pado.c3editions.app.editions.prospect.repositories.ClientsRepository;
import com.pado.c3editions.app.editions.prospect.repositories.ContactRepository;
import com.pado.c3editions.app.editions.prospect.services.*;
import com.pado.c3editions.app.editions.prospect.utils.MapperUtils;
import com.pado.c3editions.app.editions.prospect.utils.Reponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("contact")
//@Api(value = "Contact",tags = "Contact controlleur")
public class ContactController {

    @Autowired
    private MapperUtils mapperUtils;
//
//    @Autowired
//    private RestTemplate template;

    @Autowired
    private UserClient userClient;
//    @Autowired
//    private FileUploadHelper fileUploadHelper;

    @Autowired
    private WebClient userWebClient;

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
    private ContactRepository contactRepository;
    @Autowired
    private AuteursRepository auteursRepository;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private ContactEntrepriseService contactEntrepriseService;

    @GetMapping("dashboard")
    public Reponse<Object> dashboard(HttpServletRequest request, Principal principal){
        Map<String,Object> data=new HashMap<>();
        var total=0l;
        var total_client=0l;
        var connected=userClient.getwe(principal.getName());
//        var user=connected.block();
        List<ContactDto> cdto=new ArrayList<>();
        if(connected.getPermission().contains("ADMIN")){
            total=contactRepository.count();
            total_client=clientsRepository.count();
        }else{
            total=contactRepository.countBySuccursale(connected.getEmploye().getSuccursale().getId());
            total_client=clientsRepository.countBySuccursale(connected.getEmploye().getSuccursale().getId());
//            cdto=mapperUtils.asDTOList(contactService.getBySuccursale(connected.getEmploye().getSuccursale().getId()),ContactDto.class);
        }
        data.put("contact",
                Map.of("total",total
                        ,"total_auteur", auteursRepository.count()
                        ,"total_client", total_client));


        System.out.println(data);
        return Reponse
                .builder()
                .path(request.getPathInfo())
                .statuscode(200)
                .message("dashboard")
                .data(data)
                .build();
    }

    @GetMapping("")
//    @ApiOperation(value = "Liste des contacts",notes = "Liste des contacts")
    @PreAuthorize("hasAnyAuthority('READ_CONTACT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> index(Principal principal, HttpServletRequest request){
        var connected=userClient.getwe(principal.getName());
//        var user=connected.block();
        List<ContactVisiteDto> cdto=new ArrayList<>();
        if(connected.getPermission().contains("ADMIN")){
            cdto=mapperUtils.asDTOList(contactService.findAll(), ContactVisiteDto.class);
        }else{
            cdto=mapperUtils.asDTOList(contactService.getBySuccursale(connected.getEmploye().getSuccursale().getId()),ContactVisiteDto.class);
        }
        return Reponse
                .builder()
                .data(Map.of("contacts",cdto))
                .message("List contacts loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/visite")
//    @ApiOperation(value = "Liste des Visites",notes = "Liste des Visites")
    @PreAuthorize("hasAnyAuthority('READ_CONTACT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> indexvisite(Principal principal, HttpServletRequest request){
        var connected=userClient.getwe(principal.getName());
//        var user=connected.block();
        List<ContactDto> cdto=new ArrayList<>();
        if(connected.getPermission().contains("ADMIN")){
            cdto=mapperUtils.asDTOList(contactService.findAll(), ContactDto.class);
        }else{
            cdto=mapperUtils.asDTOList(contactService.getBySuccursale(connected.getEmploye().getSuccursale().getId()),ContactDto.class);
        }
        return Reponse
                .builder()
                .data(Map.of("visites",mapperUtils.asDTOList(visiteService.findAll(), VisiteDto.class)))
                .message("List visites loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/client")
//    @ApiOperation(value = "Liste des clients",notes = "Liste des clients")
//    @PreAuthorize("hasAnyAuthority('READ_CLIENT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> clientList(Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("clients",mapperUtils.asDTOList(contactService.findAll().stream().filter(contact -> contact.getType().equals("CLIENT")).collect(Collectors.toList()), ClientsDto.class)))
                .message("List clients loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/demarcheur")
//    @ApiOperation(value = "Liste des demarcheurs",notes = "Liste des demarcheurs")
//    @PreAuthorize("hasAnyAuthority('READ_CLIENT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> demarcheurList(Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("demarcheur",mapperUtils.asDTOList(contactService.getDemarcheur(), ContactDto.class)))
                .message("List clients loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/distributeur")
//    @ApiOperation(value = "Liste des distributeurs",notes = "Liste des distributeurs")
//    @PreAuthorize("hasAnyAuthority('READ_CLIENT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> distributeurList(Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("distributeurs",mapperUtils.asDTOList(contactService.getDistributeur(), ClientsDto.class)))
                .message("List distributeurs loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/auteur")
//    @ApiOperation(value = "Liste des auteurs",notes = "Liste des auteurs")
    @PreAuthorize("hasAnyAuthority('READ_AUTEUR','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> auteurList(Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("auteurs",mapperUtils.asDTOList(contactService.findAll().stream().filter(contact -> contact.getType().equals("AUTEUR")).collect(Collectors.toList()),AuteurDto.class)))
                .message("List auteurs loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/entreprise")
//    @ApiOperation(value = "Liste des entreprise",notes = "Liste des entreprise")
    @PreAuthorize("hasAnyAuthority('READ_CONTACT','READ_ENTREPRISE','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> entrepriseList(Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("entreprises",mapperUtils.asDTOList(entrepriseService.findAll(), EntrepriseDto.class)))
                .message("Listes entreprises loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/entreprise")
//    @ApiOperation(value = "Liste des entreprise",notes = "Liste des entreprise")
    @PreAuthorize("hasAnyAuthority('ADD_CONTACT','ADD_ENTREPRISE','ROLE_SUPERADMIN')")
    public Reponse<Object> entreprise(@RequestBody @Valid Entreprise ent, Principal principal, HttpServletRequest request){
        ent.setCreatedby(principal.getName());
        Entreprise entreprise=entrepriseService.save(ent);

        if(Objects.nonNull(entreprise)){
            userClient.savelog(Logdto.builder()
                    .action("Enregistrer une nouvelle entreprise")
                    .message(String.format("Enregistrement de l'entreprise %s avec succès",entreprise.getNom()))
                    .claz(Entreprise.class.getName())
                    .user(principal.getName()).build());

        }

        return Reponse
                .builder()
                .data(Map.of("entreprise",mapperUtils.asDTO(entreprise, EntrepriseDto.class)))
                .message("Nouvelle entreprise ajouté")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("{contact_id}")
//    @ApiOperation(value = "",notes = "Selectionner un contact")
    @PreAuthorize("hasAnyAuthority('READ_CONTACT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> get(@PathVariable("contact_id") long contact_id, Principal principal, HttpServletRequest request){
        return Reponse
                .builder()
                .data(Map.of("contact",mapperUtils.asDTO(contactService.findById(contact_id),ContactDto.class)))
                .message("contact loaded")
                .path(request.getContextPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("")
//    @ApiOperation(value = "Enregistrer un contact",notes = "Enregistrer un contact")
    @PreAuthorize("hasAnyAuthority('ADD_CONTACT','ROLE_SUPERADMIN')")
    public Reponse<Object> save(@RequestBody @Valid Contact emp, Principal principal, HttpServletRequest request){

        var connected=userClient.getwe(principal.getName());
        emp.setCreatedby(principal.getName());
        emp.setSuccursale(connected.getEmploye().getSuccursale().getId());
        Contact contact=contactService.save(emp);
//        UsersDto user=userService.fetchUser(principal.getName());
        if(Objects.nonNull(contact)){
            userClient.savelog(Logdto.builder()
                    .action("Enregistrer un nouveau contact")
                    .message(String.format("Enregistrement du contact %s avec succès",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(contact,ContactDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Nouveau contact créé")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/client")
//    @ApiOperation(value = "Enregistrer un client",notes = "Enregistrer un client")
    @PreAuthorize("hasAnyAuthority('ADD_CLIENT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> saveclient(@RequestBody @Valid Clients emp,HttpServletRequest request,Principal principal){
        Clients client=clientService.save(emp);
        UsersDto user=userService.fetchUser(principal.getName());
        if(Objects.nonNull(client)){
            userClient.savelog(Logdto.builder()
                    .action("Enregistrer un nouveau client")
                    .message(String.format("Enregistrement du client %s avec succès",client.getPrenom().concat(" "+client.getNom())))
                    .claz(Clients.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(client,ClientsDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Nouveau client créé")
                .status(HttpStatus.CREATED)
                .path(request.getPathInfo())
                .build();
    }

    private LogsDto saveLog(String action,String message,String user,String calssname){
        Logdto l=Logdto.builder()
                .action(action)
                .message(message)
                .claz(calssname)
                .user(user)
                .build();
        return userService.saveLog(l);
    }

    @PostMapping("/auteur")
//    @ApiOperation(value = "Enregistrer un auteur",notes = "Enregistrer un auteur")
    @PreAuthorize("hasAnyAuthority('ADD_AUTEUR','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public ResponseEntity<Object> saveauteur(@RequestBody @Valid Auteurs new_auteur,HttpServletRequest request,Principal principal)throws JsonProcessingException {
        if(Objects.nonNull(new_auteur.getReference())){
            new_auteur.getReference().setAuteur(new_auteur);
        }
        Auteurs auteur=auteurService.save(new_auteur);
        System.out.println("Object author"+auteur);
//        UsersDto user=userService.fetchUser();
        if(Objects.nonNull(auteur)){
            userClient.savelog(Logdto.builder()
                    .action("Enregistrer un nouveau auteur")
                    .message(String.format("Enregistrement de l'auteur %s avec succès",auteur.getPrenom().concat(" "+auteur.getNom())))
                    .claz(Auteurs.class.getName())
                    .user(principal.getName()).build());

        }
//        return Reponse.builder()
//                .data(Map.of("contact",auteur))
//                .statuscode(HttpStatus.CREATED.value())
//                .timestamp(LocalDateTime.now())
//                .message("Nouveau auteur créé")
//                .status(HttpStatus.CREATED)
//                .build();

        Reponse<Object> response = Reponse.builder()
                .data(Map.of("contact", auteur))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Nouveau auteur créé")
                .status(HttpStatus.CREATED)
                .build();


        String jsonResponse = new ObjectMapper().writeValueAsString(response);

        return ResponseEntity.ok(jsonResponse);

    }

    @PutMapping("/{contact_id}")
//    @ApiOperation(value = "Modifier un contact",notes = "Modifier un contact")
    @PreAuthorize("hasAnyAuthority('EDIT_CONTACT','ROLE_SUPERADMIN')")
    public Reponse<Object> update(@RequestBody @Valid Contact emp,@PathVariable("contact_id") long contact_id, HttpServletRequest request,Principal principal){
        System.out.println("id="+contact_id);
//        UsersDto user=userService.fetchUser();
        Contact c=contactService.findById(contact_id).get();

//        if(!emp.getVisites().isEmpty())
        c.getVisites().forEach(v->v.setContact(c));
        BeanUtils.copyProperties(emp,c);

        Contact contact=contactService.update(c,contact_id);
        if(Objects.nonNull(contact)){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un contact")
                    .message(String.format("Mis a jour du contact %s avec succès",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(contact,ContactDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("modification d'contact reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{contact_id}/to_client")
//    @ApiOperation(value = "Changer un contact en client",notes = "Changer un contact en client")
    @PreAuthorize("hasAnyAuthority('CONV_CONTACT','ROLE_SUPERADMIN')")
    public Reponse<Object> client(
            @RequestPart("c_type") String c_type, @PathVariable long contact_id, HttpServletRequest request,Principal principal){
        Contact contact=contactService.findById(contact_id).get();

//        UsersDto user=userService.fetchUser(principal.getName());
        int val=contactService.changeContactTo(contact,"CLIENT",c_type);
        if(val>0){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un contact")
                    .message(String.format("Convertis le contact %s en client",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(contactService.findById(contact_id), ClientsDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Changement contact en client reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{contact_id}/to_auteur")
    @PreAuthorize("hasAnyAuthority('CONV_CONTACT','ROLE_SUPERADMIN')")
//    @ApiOperation(value = "Changer un contact en auteur",notes = "Changer un contact en auteur")
    public Reponse<Object> auteur(
            @RequestBody @Valid Auteurs auteur,
            @PathVariable long contact_id, BindingResult bindingResult, HttpServletRequest request, Principal principal){
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return Reponse.builder()
                    .data(Map.of("errors",bindingResult.getAllErrors()))
                    .statuscode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(LocalDateTime.now())
                    .message("Les champs ne sont pas respecte")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        System.out.println(contact_id);
        if(Objects.nonNull(auteur.getReference())){
            auteur.getReference().setAuteur(auteur);
        }

        int res=contactService.initContactToAuteur(contact_id);
        if(res>0)
            auteur=auteurService.update(auteur,contact_id);

        if(Objects.nonNull(auteur)){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un contact")
                    .message(String.format("Convertis le contact %s en un auteur",auteur.getPrenom().concat(" "+auteur.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("auteur",mapperUtils.asDTO(auteur,AuteurDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Changement contact en auteur reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{contact_id}/to_demarcheur")
    @PreAuthorize("hasAnyAuthority('CONV_CONTACT','ROLE_SUPERADMIN')")
//    @ApiOperation(value = "Changer un contact en Demarcheur",notes = "Changer un contact en Demarcheur")
    public Reponse<Object> demarcheur(
            @PathVariable long contact_id, HttpServletRequest request, Principal principal){

        System.out.println(contact_id);

        Contact contact=contactService.findById(contact_id).get();
        System.out.println(contact);
        contact.setContact_type(CONTACT_TYPE.DEMARCHEUR);
        contact=contactService.update(contact,contact_id);
        if(Objects.nonNull(contact)){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un contact")
                    .message(String.format("Convertis le contact %s en un demarcheur",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("demarchuer","new demarcheur"))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Changement contact en demarcheur reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{contact_id}/to_distributeur")
    @PreAuthorize("hasAnyAuthority('CONV_CONTACT','ROLE_SUPERADMIN')")
//    @ApiOperation(value = "Changer un contact en distributeur",notes = "Changer un contact en distributeur")
    public Reponse<Object> auteur(
            @PathVariable long contact_id, HttpServletRequest request, Principal principal){

        System.out.println(contact_id);

        Contact contact=contactService.findById(contact_id).get();
        contact.setContact_type(CONTACT_TYPE.DISTRIBUTEUR);
        contact=contactService.update(contact,contact_id);
        if(Objects.nonNull(contact)){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un contact")
                    .message(String.format("Convertis le contact %s en un distibuteur",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("distibuteur","new distibuteur"))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Changement contact en distibuteur reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("{contact_id}/new_visite")
//    @ApiOperation(value = "Ajouter une nouvelle visite a un contact",notes = "Ajouter une nouvelle visite a un contact")
    @PreAuthorize("hasAnyAuthority('EDIT_CONTACT','ROLE_SUPERADMIN')")
    public Reponse<Object> newvisite(@RequestBody @Valid Visites visite, @PathVariable long contact_id, Principal principal, HttpServletRequest request){
        Contact contact=contactService.findById(contact_id).get();
        visite.setContact(contact);

        System.out.println(principal);
//        UsersDto user=userService.fetchUser(principal.getName());
        visite.setCreatedby(principal.getName());
        contact.getVisites().add(visite);

        contact=contactService.update(contact,contact_id);
        if(Objects.nonNull(contact)){
            userClient.savelog(Logdto.builder()
                    .action("Ajouter une nouvelle visite a un contact")
                    .message(String.format("Enregistrement d'une visite pour le contact %s avec succès",contact.getPrenom().concat(" "+contact.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(contact, ClientsDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Nouvelle visite d'un contact reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/client/{contact_id}")
//    @ApiOperation(value = "Modifier un client",notes = "Modifier un client")
    @PreAuthorize("hasAnyAuthority('EDIT_CLIENT','ROLE_SUPERADMIN')")
    public Reponse<Object> updateclient(@RequestBody @Valid Clients emp, @PathVariable("contact_id") long contact_id, HttpServletRequest request,Principal principal){
        System.out.println("id="+contact_id);
//        UsersDto user=userService.fetchUser(principal.getName());
        Clients client=clientService.update(emp,contact_id);
        if(Objects.nonNull(client)){
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'un client")
                    .message(String.format("Mis a jour du client %s avec succès",client.getPrenom().concat(" "+client.getNom())))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("client",mapperUtils.asDTO(client,ClientsDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("modification d'un client reussi")
                .status(HttpStatus.CREATED)
                .build();
    }


    @PutMapping("/auteur/{contact_id}")
//    @ApiOperation(value = "Modifier un auteur",notes = "Modifier un auteur")
    @PreAuthorize("hasAnyAuthority('EDIT_AUTEUR','ROLE_SUPERADMIN')")
    public Reponse<Object> updateAuteur(@RequestBody @Valid Auteurs emp, @PathVariable("contact_id") long contact_id, HttpServletRequest request,Principal principal){
        System.out.println("id="+contact_id);
        UsersDto user=userService.fetchUser(principal.getName());
        Auteurs auteur=auteurService.update(emp,contact_id);
        if(Objects.nonNull(auteur)){
            ContactLogs log=ContactLogs
                    .builder()
                    .action("Mis a jour d'un auteur")
                    .desciption(String.format("Mis a jour du auteur %s avec succès",auteur.getPrenom().concat(" "+auteur.getNom())))
                    .clazz(Auteurs.class)
                    .user(user.getId())
                    .build();
            contactLogServices.save(log);
        }
        return Reponse.builder()
                .data(Map.of("auteur",auteur))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("modification d'un auteurclient reussi")
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/entreprise/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN')")
    public Reponse<Object> updateAuteur( @PathVariable("id") long id, HttpServletRequest request,Principal principal){

//        UsersDto user=userService.fetchUser(principal.getName());
        Entreprise ent=entrepriseService.findById(id).get();
        boolean b=entrepriseService.deleteById(id);

        userClient.savelog(Logdto.builder()
                .action("Suppresion d'une entreprise")
                .message(String.format("Suppression de l'entreprise %s avec succès",ent.getNom()))
                .claz(Contact.class.getName())
                .user(principal.getName()).build());
        return Reponse.builder()
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Suppression de l'entreprise effectuer avec succes")
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/entreprise/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN', 'READ_ENTREPRISE', 'EDIT_ENTREPRISE')")
    public Reponse<Object> getEntreprise( @PathVariable("id") long id, HttpServletRequest request,Principal principal){

//        UsersDto user=userService.fetchUser(principal.getName());
        Entreprise ent=entrepriseService.findById(id).get();

        return Reponse.builder()
                .data(Map.of("entreprise",mapperUtils.asDTO(ent,EntrepriseCCDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Entreprise load avec succès")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/entreprise/{entreprise_id}")
    @PreAuthorize("hasAnyAuthority('EDIT_ENTREPRISE','ROLE_SUPERADMIN')")
    public Reponse<Object> update(@PathVariable Long entreprise_id, @RequestBody  Entreprise ent, HttpServletRequest request,Principal principal) {
        Entreprise entrep = entrepriseService.update(ent, entreprise_id);

        if (Objects.nonNull(entrep)) {
            userClient.savelog(Logdto.builder()
                    .action("Mis a jour d'une entreprise")
                    .message(String.format("Mis a jour de l'entreprise %s avec succès",entrep.getNom()))
                    .claz(Contact.class.getName())
                    .user(principal.getName()).build());

        }
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(entrep,ContactDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("modification de l'entreprise reussi")
                .status(HttpStatus.CREATED)
                .build();
    }


    @PostMapping("/entreprise/{id}/add-contact")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN', 'EDIT_ENTREPRISE')")
    public Reponse<Object> entrepriseAddContact( @PathVariable("id") long id , @RequestBody Contact contact, HttpServletRequest request,Principal principal){

//        UsersDto user=userService.fetchUser(principal.getName());
        Entreprise ent=entrepriseService.findById(id).get();
        EntrepriseByContact ec=EntrepriseByContact.builder()
                .contact(contact)
                .entreprise(ent)
                .build();
        ec.setCreatedby(principal.getName());

        contact=contactEntrepriseService.save(ec);
        return Reponse.builder()
                .data(Map.of("contact",mapperUtils.asDTO(contact,ContactDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Entreprise load avec succès")
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("clientlist")
//    @ApiOperation(value = "Liste des contacts",notes = "Liste des contacts")
    @PreAuthorize("hasAnyAuthority('READ_CONTACT','ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> contactvent(Principal principal, HttpServletRequest request){
        var connected=userClient.getwe(principal.getName());
//        var user=connected.block();
        List<ContactEDto> cdto=new ArrayList<>();
        if(connected.getPermission().contains("ADMIN")){
            cdto=mapperUtils.asDTOList(contactService.findAll(), ContactEDto.class);
        }else{
            cdto=mapperUtils.asDTOList(contactService.getBySuccursale(connected.getEmploye().getSuccursale().getId()),ContactEDto.class);
        }
        return Reponse
                .builder()
                .data(Map.of("contacts",cdto))
                .message("List contacts loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("dashboard/{succursale_id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPERADMIN')")
    public Reponse<Object> getBySuccursale(@PathVariable("succursale_id") long succursaleId, Principal principal, HttpServletRequest request) {

        var connected=userClient.getwe(principal.getName());

        List<ContactEDto> cdto=new ArrayList<>();
        if(connected.getPermission().contains("ADMIN")){
            cdto=mapperUtils.asDTOList(contactService.findAll(), ContactEDto.class);
        }else{
            cdto=mapperUtils.asDTOList(contactService.getBySuccursale(connected.getEmploye().getSuccursale().getId()),ContactEDto.class);
        }
        return Reponse
                .builder()
                .data(Map.of("contacts",cdto))
                .message("List contacts loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

}
