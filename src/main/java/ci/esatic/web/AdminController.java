package ci.esatic.web;

import ci.esatic.entities.*;
import ci.esatic.repository.*;

import ci.esatic.services.AccountService;
import ci.esatic.services.FileHelper;
import jakarta.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    private EtudiantRepository etudiantRepository;

    private FiliereRepository filiereRepository;

    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    private ClasseRepository classeRepository;

    private InscriptionRepository inscriptionRepository;

    private NiveauRepository niveauRepository;

    private UeRepository ueRepository;

    private EcueRepository ecueRepository;

    private EnseignantRepository enseignantRepository;

    private EnseignantEcueAnneeRepository enseignantEcueAnneeRepository;

    private AffectationRepository affectationRepository;

    private AppRoleRepository appRoleRepository;

    private AccountService accountService;

    private AppUserRepository appUserRepository;



    public AdminController(EtudiantRepository etudiantRepository, FiliereRepository filiereRepository, AnneeAcademiqueRepository anneeAcademiqueRepository, ClasseRepository classeRepository, InscriptionRepository inscriptionRepository, NiveauRepository niveauRepository, UeRepository ueRepository, EcueRepository ecueRepository, EnseignantRepository enseignantRepository, EnseignantEcueAnneeRepository enseignantEcueAnneeRepository, AffectationRepository affectationRepository, AppRoleRepository appRoleRepository, AccountService accountService) {
        this.etudiantRepository = etudiantRepository;
        this.filiereRepository = filiereRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.classeRepository = classeRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.niveauRepository = niveauRepository;
        this.ueRepository = ueRepository;
        this.ecueRepository = ecueRepository;
        this.enseignantRepository = enseignantRepository;
        this.enseignantEcueAnneeRepository = enseignantEcueAnneeRepository;
        this.affectationRepository = affectationRepository;
        this.appRoleRepository = appRoleRepository;
        this.accountService = accountService;
    }

    @GetMapping(value = "/admin")
    public String dashboard(Model model)
    {
        List<Etudiant> list = etudiantRepository.findAll();
        int nombreTotalEtudiant = list.size();

        List<Filiere> filiereList = filiereRepository.findAll();
        int nombreTotalFiliere = filiereList.size();

        List<Classe> classes = classeRepository.findAll();
        int nombreTotalClasse = classes.size();

        List<Enseignant> enseignants = enseignantRepository.findAll();
        int nombreTotalEnseignant = enseignants.size();

        model.addAttribute("totalEtudiant", nombreTotalEtudiant);
        model.addAttribute("totalFiliere", nombreTotalFiliere);
        model.addAttribute("totalClasse", nombreTotalClasse);
        model.addAttribute("totalEnseignant", nombreTotalEnseignant);

        return "dashboard-admin";
    }

    @GetMapping("/admin/addStudent")
    public String ajouterEtudiant(Model model)
    {
        model.addAttribute("etudiant", new Etudiant());
        model.addAttribute("genre", Genre.values());
        return "add-student";
    }

    @GetMapping("/admin/listStudent")
    public String listEtudiant(Model model){

        /*List<Etudiant> list = etudiantRepository.findAll();*/
        List<Etudiant> list = etudiantRepository.findAllByEstActif(true);

        model.addAttribute("list", list);

        return "list-student";
    }

    @PostMapping("/admin/saveEtudiant")
    public String saveEtudiant(@Valid Etudiant etudiant, BindingResult  bindingResult, @RequestParam("file") MultipartFile file, Model model) throws IOException {

        if(bindingResult.hasErrors()){
            model.addAttribute("genre", Genre.values());
            return "add-student";
        }

        // Vérifie si le champ file est vide
      /*  if (file.isEmpty()) {
            bindingResult.addError(new FieldError("file", "file", "Le champ file est obligatoire."));
            return "add-student";
        }*/

        System.out.println("File info");
        System.out.println("name " + file.getOriginalFilename());
        System.out.println("Size (byte)" + file.getSize());
        System.out.println("Type " + file.getContentType());


        File imageFolder = new File(new ClassPathResource(".").getFile() + "/static/uploads");
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        String fileName = FileHelper.generateFileName(file.getOriginalFilename());
        Path path = Paths.get(imageFolder.getAbsolutePath() + File.separator + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(fileName);

        etudiant.setPhotoEtudiant(fileName);

        etudiantRepository.save(etudiant);


        // Redirect to list
        return "redirect:/admin/listStudent";
    }

    @GetMapping("/admin/addCourse")
    public String ajouterFiliere(Model model)
    {
        List<Niveau> list = niveauRepository.findAll();

        model.addAttribute("filiere", new Filiere());
        model.addAttribute("listniveaux", list);
        return "add-course";
    }

    @PostMapping("/admin/saveCourse")
    public String enregistrerFiliere(@Valid Filiere filiere, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "add-course";
        }

        // Si l'ID de la filière est null, cela indique une nouvelle filière
       /* if (filiere.getId() == null) {
            filiereRepository.save(filiere);
        } else {
            // Si l'ID n'est pas null, cela indique une mise à jour
            filiereRepository.saveAndFlush(filiere);
        }*/
        filiereRepository.save(filiere);


        return "redirect:/admin/listCourse";
    }

    @GetMapping("/admin/listCourse")
    public String listeFiliere(Model model)
    {
        List<Filiere> list = filiereRepository.findAll();
        model.addAttribute("filieres", list);
        return "list-course";
    }

    @GetMapping("/admin/listNiveaux")
    public String listeNiveau(Model model)
    {
        List<Filiere> filieres = filiereRepository.findAll();
        model.addAttribute("filieres", filieres);
        return "list-level";
    }

    @GetMapping("/admin/editCourse")
    public String editCourse(@RequestParam(name="id") Long id, Model model){

        List<Niveau> list = niveauRepository.findAll();
        Filiere filiere = filiereRepository.findById(id).get();
        model.addAttribute("filiere", filiere);
        model.addAttribute("listniveaux", list);
        return "edit-course";
    }

    @GetMapping("/admin/deleteCourse")
    public String deleteCourse(@RequestParam(name="id") Long id){

        filiereRepository.deleteById(id);

        return "redirect:/admin/listCourse";
    }

    @GetMapping("/admin/addSchoolYear")
    public String addSchoolYear(Model model)
    {
        model.addAttribute("schoolYear", new AnneeAcademique());
        return "add-school-year";
    }

    @PostMapping("/admin/saveSchoolYear")
    public String saveSchoolYear(@Valid AnneeAcademique anneeAcademique, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "add-school-year";
        }

        anneeAcademiqueRepository.save(anneeAcademique);

        return "redirect:/admin/listSchoolYear";
    }

    @GetMapping("/admin/listSchoolYear")
    public String listSchoolYear(Model model){
        List<AnneeAcademique> academiques = anneeAcademiqueRepository.findAll();
        model.addAttribute("academiques", academiques);
        return "list-school-year";
    }

    @GetMapping("/admin/editYear")
    public String editYear(@RequestParam(name="id") Long id, Model model){

        AnneeAcademique anneeAcademique = anneeAcademiqueRepository.findById(id).get();
        model.addAttribute("anneeAcademique", anneeAcademique);
        return "edit-school-year";
    }

    @GetMapping("/admin/deleteYear")
    public String deleteYear(@RequestParam(name="id") Long id){

        anneeAcademiqueRepository.deleteById(id);

        return "redirect:/admin/listSchoolYear";
    }

    @GetMapping("/admin/listClass")
    public String listClass(Model model){
        List<Classe> classes = classeRepository.findAll();
        model.addAttribute("classes", classes);
        return "list-class";
    }

    @GetMapping("/admin/addClass")
    public String addClass(Model model)
    {
        model.addAttribute("classe", new Classe());
        return "add-class";
    }

    @PostMapping("/admin/saveClass")
    public String saveSchoolYear(@Valid Classe classe, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "add-class";
        }

        classeRepository.save(classe);

        return "redirect:/admin/listClass";
    }

    @GetMapping("/admin/editClass")
    public String editClass(@RequestParam(name="id") Long id, Model model){

        Classe classe = classeRepository.findById(id).get();
        model.addAttribute("classe", classe);
        return "edit-class";
    }

    @GetMapping("/admin/deleteClass")
    public String deleteClass(@RequestParam(name="id") Long id){

        classeRepository.deleteById(id);

        return "redirect:/admin/listClass";
    }



    @GetMapping("/admin/registration")
    public String registration(Model  model, @RequestParam(name = "id") Long id)
    {
        Optional<Etudiant> etudiantSelect = etudiantRepository.findById(id);
        Etudiant etudiant = etudiantSelect.orElse(new Etudiant());


        List<Niveau> niveauList = niveauRepository.findAll();

        List<Classe> classeList = classeRepository.findAll();

        List<AnneeAcademique> anneeAcademiqueList = anneeAcademiqueRepository.findAll();

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);

        model.addAttribute("inscription", inscription);
        model.addAttribute("niveauList", niveauList);
        model.addAttribute("classeList", classeList);

       model.addAttribute("anneeAcademiqueList", anneeAcademiqueList);

        return "inscription";
    }

    @PostMapping("/admin/inscrire")
    public String inscription(Inscription inscription)
    {
        inscriptionRepository.save(inscription);

        return "redirect:/admin/listStudent";
    }

    @GetMapping("/admin/inscription")
    public String ListeInscription(Model model)
    {
        List<Etudiant> etudiantsinscrit = etudiantRepository.findAllByEstInscrit(true);
        model.addAttribute("etudiants", etudiantsinscrit);


        //model.addAttribute("inscriptions", inscriptions);
        return "list-inscription";
    }

    @GetMapping("/admin/profil")
    public String profil(@RequestParam(name="id") Long id, Model model)
    {
        Optional<Etudiant> etudiantSelect = etudiantRepository.findById(id);
        Etudiant netudiant = etudiantSelect.orElse(new Etudiant());
        model.addAttribute("etudiant", netudiant);
        return "profil";
    }

    @GetMapping("/admin/editStudent")
    public String editStudent(@RequestParam(name = "id") Long id, Model model)
    {
        Optional<Etudiant> etudiantSelect = etudiantRepository.findById(id);
        Etudiant etudiant = etudiantSelect.orElse(new Etudiant());
        model.addAttribute("genre", Genre.values());
        model.addAttribute("etudiant", etudiant);
        return "edit-student";
    }

    @GetMapping("/admin/deleteStudent")
    public String deleteStudent(@RequestParam(name = "id") Long id)
    {
        Optional<Etudiant> etudiantSelect = etudiantRepository.findById(id);
        Etudiant etudiant = etudiantSelect.orElse(new Etudiant());
        etudiant.setEstActif(false);
        etudiantRepository.save(etudiant);

        return "redirect:/admin/listStudent";
    }

    @GetMapping("/admin/addLevel")
    public String addLevel(Model model)
    {
        model.addAttribute("niveau", new Niveau());
        return "add-level";
    }

    @PostMapping("/admin/saveLevel")
    public String saveLevel(Niveau niveau){

        niveauRepository.save(niveau);

        return "redirect:/admin/listCourse";
    }

    @GetMapping("/admin/addUe")
    public String addUe(Model model)
    {
        List<Niveau> niveaux = niveauRepository.findAll();
        model.addAttribute("ue", new Ue());
        model.addAttribute("niveaux", niveaux);
        return "add-ue";
    }

    @GetMapping("/admin/addEcue")
    public String addEcue(Model model)
    {
        List<Ue> listue = ueRepository.findAll();
        model.addAttribute("ecue", new Ecue());
        model.addAttribute("listue", listue);
        return "add-ecue";
    }


    @PostMapping("/admin/saveUe")
    public String saveUe(@Valid Ue ue, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            return "add-ue";
        }

        ueRepository.save(ue);

        return "redirect:/admin/listUe";
    }

    @PostMapping("/admin/saveEcue")
    public String saveEcue(@Valid Ecue ecue, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "add-ecue";
        }

        ecueRepository.save(ecue);

        return "redirect:/admin/listEcue";
    }

    @GetMapping("/admin/listUe")
    public String listUe(Model model)
    {
        List<Niveau> listNiveau = niveauRepository.findAll();
        model.addAttribute("niveaux", listNiveau);
        return "list-ue";
    }

    @GetMapping("/admin/listEcue")
    public String listEcue(Model model)
    {
        List<Ue> listUe = ueRepository.findAll();
        model.addAttribute("ues", listUe);
        return "list-ecue";
    }

   @GetMapping("/admin/searchNiveau")
    public String searchNiveau(@RequestParam("niveau") Long id, Model model)
   {
       List<Niveau> listNiveau = niveauRepository.findAll();
       List<Ue> list = ueRepository.findByNiveau_Id(id);
       model.addAttribute("listeues", list);
       model.addAttribute("niveaux", listNiveau);
       return "list-ue";
   }

    @GetMapping("/admin/searchUe")
    public String searchUe(@RequestParam("ue") Long id, Model model)
    {
        List<Ue> listUe= ueRepository.findAll();
        List<Ecue> list = ecueRepository.findByUe_Id(id);
        model.addAttribute("listeecues", list);
        model.addAttribute("ues", listUe);
        return "list-ecue";
    }


    @GetMapping("/admin/searchFiliere")
    public String searchFiliere(@RequestParam("filiere") Long id, Model model)
    {
        List<Filiere> filieres = filiereRepository.findAll();
        model.addAttribute("filieres", filieres);

       List<Niveau> list = niveauRepository.findByFiliere_Id(id);
       model.addAttribute("list", list);

        return "list-level";
    }

    @GetMapping("/admin/addEnseignant")
    public String addEnseignant(Model model)
    {
        model.addAttribute("enseignant", new Enseignant());
        model.addAttribute("genre", Genre.values());
        model.addAttribute("newUser", new AppUser());
        List<AppRole> roles = appRoleRepository.findAll();
        model.addAttribute("roles", roles);
        return "add-teacher";
    }

    @PostMapping("/admin/saveEnseignant")
    public String saveEnseignant(@Valid Enseignant enseignant, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "add-teacher";
        }


        enseignantRepository.save(enseignant);

        return "redirect:/admin";
    }

    @GetMapping("/admin/listEnseignant")
    public String listEnseignant(Model model)
    {
        List<Enseignant> enseignants = enseignantRepository.findAll();
        model.addAttribute("enseignants", enseignants);
        return "list-teacher";
    }


    @GetMapping("/admin/attEnseignant")
    public String attEnseignant(@RequestParam(name = "id") Long id, Model model)
    {
        Optional<Enseignant> enseignantSelect = enseignantRepository.findById(id);
        Enseignant enseignant = enseignantSelect.orElse(new Enseignant());
        model.addAttribute("enseignant", enseignant);

        List<Ecue> listEcue = ecueRepository.findAll();
        List<AnneeAcademique> listAnneeAcademique = anneeAcademiqueRepository.findAll();

        EnseignantEcueAnnee  enseignantEcueAnnee = new EnseignantEcueAnnee();
        enseignantEcueAnnee.setEnseignant(enseignant);

        model.addAttribute("enseignantEcueAnnee", enseignantEcueAnnee);
        model.addAttribute("listAnneeAcademique", listAnneeAcademique);
        model.addAttribute("listEcue",listEcue);

        return "att-enseignant";
    }

    @PostMapping("/admin/saveAtt")
    public String saveAtt(EnseignantEcueAnnee enseignantEcueAnnee)
    {
        enseignantEcueAnneeRepository.save(enseignantEcueAnnee);
        return "redirect:/admin/listEnseignant";
    }

    @GetMapping("/admin/listAtt")
    public String listAtt(Model model)
    {
        List<EnseignantEcueAnnee> list = enseignantEcueAnneeRepository.findAll();
        model.addAttribute("listAttribution", list);

        return "list-att";
    }

    @GetMapping("/admin/affectEnseignant")
    public String affectEnseignant(@RequestParam(name = "id") Long id, Model model)
    {
        Optional<Enseignant> enseignantSelect = enseignantRepository.findById(id);
        Enseignant enseignant = enseignantSelect.orElse(new Enseignant());


        List<AnneeAcademique> listAnneeAcademique = anneeAcademiqueRepository.findAll();
        model.addAttribute("listAnneeAcademique", listAnneeAcademique);

        List<Classe> listClasse = classeRepository.findAll();
        model.addAttribute("listClasse",listClasse);

        Affecte affecte = new Affecte();
        affecte.setEnseignant(enseignant);

        model.addAttribute("affecte", affecte);
        return "affectation";
    }

    @PostMapping("/admin/affectation")
    public String affectation(Affecte affecte)
    {
        affectationRepository.save(affecte);
        return "redirect:/admin/listAtt";
    }

    @GetMapping("/admin/listAff")
    public String listAff(Model model)
    {
        List<Affecte> list = affectationRepository.findAll();
        model.addAttribute("listAffectation", list);

        return "list-aff";
    }

    @GetMapping("/admin/ediTeacher")
    public String ediTeacher(@RequestParam("id") Long id, Model model)
    {
        Optional<Enseignant> enseignantSelect = enseignantRepository.findById(id);
        Enseignant enseignant = enseignantSelect.orElse(new Enseignant());
        model.addAttribute("genre", Genre.values());
        model.addAttribute("enseignant", enseignant);
        return "edit-teacher";
    }

    @GetMapping("/admin/deleteTeacher")
    public String deleteTeacher(@RequestParam("id") Long id)
    {
      enseignantRepository.deleteById(id);
      return "redirect:/admin/listEnseignant";
    }

    @GetMapping("/admin/editAtt")
    public String editAtt(@RequestParam("id") Long id, Model model)
    {
           Optional<EnseignantEcueAnnee> select = enseignantEcueAnneeRepository.findById(id);
        EnseignantEcueAnnee enseignantEcueAnnee = select.orElse(new EnseignantEcueAnnee());
        model.addAttribute("enseignantEcueAnnee", enseignantEcueAnnee);

        List<Ecue> listEcue = ecueRepository.findAll();
        List<AnneeAcademique> listAnneeAcademique = anneeAcademiqueRepository.findAll();

        model.addAttribute("listAnneeAcademique", listAnneeAcademique);
        model.addAttribute("listEcue",listEcue);

        return "edit-enseignant";
    }

    @GetMapping("/admin/deleteAtt")
    public String deleteAtt(@RequestParam("id") Long id)
    {
        enseignantEcueAnneeRepository.deleteById(id);
        return "redirect:/admin/listAtt";
    }

    @GetMapping("/admin/editAff")
    public String editAff(@RequestParam("id") Long id, Model model)
    {
        Optional<Affecte> select = affectationRepository.findById(id);
        Affecte affecte = select.orElse(new Affecte());
        model.addAttribute("affecte", affecte);

        List<Classe> listClasse = classeRepository.findAll();
        List<AnneeAcademique> listAnneeAcademique = anneeAcademiqueRepository.findAll();

        model.addAttribute("listAnneeAcademique", listAnneeAcademique);
        model.addAttribute("listClasse",listClasse);

        return "edit-affectation";
    }

    @GetMapping("/admin/deleteAff")
    public String deleteAff(@RequestParam("id") Long id)
    {
        affectationRepository.deleteById(id);
        return "redirect:/admin/listAff";
    }

    @GetMapping("/admin/editLevel")
    public String editLevel(@RequestParam("id") Long id, Model model)
    {
        Niveau select = niveauRepository.findById(id).get();
        model.addAttribute("niveau", select);

        List<Filiere> filieres = filiereRepository.findAll();
        model.addAttribute("filieres", filieres);
        return "edit-level";
    }

    @GetMapping("/admin/deleteLevel")
    public String deleteLevel(@RequestParam("id") Long id)
    {
        niveauRepository.deleteById(id);
        return "redirect:/admin/listNiveaux";
    }

    @GetMapping("/admin/createAccount")
    public String createAccount(Model model)
    {
        List<AppRole> roles = appRoleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new AppUser());
        model.addAttribute("newRole", new AppRole());
        return "create-account-user";
    }

    @GetMapping("/admin/addRole")
    public String addRole(@RequestParam("id") String id, Model model)
    {
        AppUser loadUser = accountService.loaduserById(id);
        List<AppRole> roles = appRoleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("loadUser", loadUser);

        return "add-role-user";
    }

    @PostMapping("/admin/saveAccount")
    public String saveAccount(AppUser appUser)
    {

       accountService.addNewUser(appUser.getUsername(), appUser.getPassword(), appUser.getEmail(), appUser.getPassword());
        return "redirect:/admin/listaccount";
    }

    @PostMapping("/admin/saveRole")
    public String saveRole(@RequestParam("selectedName") String selectedName, @RequestParam("selectedRole") String selectedRole)
    {

        AppUser loadUser = accountService.loaduserByUsername(selectedName);
       accountService.addRoleToUser(loadUser.getUsername(), selectedRole);



//        AppUser loadUser = accountService.loaduserByUsername(appUser.getUsername());
//        Enseignant enseignant = enseignantRepository.findById(id).get();
//        enseignant.setAppUser(loadUser);
//        enseignantRepository.save(enseignant);
        return "redirect:/admin/listaccount";
    }

    @GetMapping("/admin/listaccount")
    public String listAccount(Model model)
    {
        model.addAttribute("users", accountService.listAppUsers());
        return "list-account-user";
    }

    @GetMapping("/admin/addAccountStudent")
    public String addAccountUser(@RequestParam("id") Long id, Model model)
    {
        Etudiant etudiant = etudiantRepository.findById(id).get();
        model.addAttribute("etudiant", etudiant);
        List<AppUser> l = accountService.listAppUsers();

        model.addAttribute("users", l);
        return "account-student";
    }

    @GetMapping("/admin/addAccountTeacher")
    public String addAccountTeacher(@RequestParam("id") Long id, Model model)
    {
        Enseignant enseignant = enseignantRepository.findById(id).get();
        model.addAttribute("enseignant", enseignant);
        List<AppUser> l = accountService.listAppUsers();

        model.addAttribute("users", l);
        return "account-teacher";
    }

    @PostMapping("/admin/saveAccountStudent")
    public String saveAccountStudent(@RequestParam("selectedId") Long idE, @RequestParam("selectedAppUser") String selectedAppUser)
    {
        Etudiant e = etudiantRepository.findById(idE).get();
        AppUser a = accountService.loaduserById(selectedAppUser);
        e.setAppUser(a);
        etudiantRepository.save(e);
        return "redirect:/admin/listStudent";
    }

    @PostMapping("/admin/saveAccountTeacher")
    public String saveAccountTeacher(@RequestParam("selectedId") Long idE, @RequestParam("selectedAppUser") String selectedAppUser)
    {
        Enseignant e = enseignantRepository.findById(idE).get();
        AppUser a = accountService.loaduserById(selectedAppUser);
        e.setAppUser(a);
        enseignantRepository.save(e);
        return "redirect:/admin/listStudent";
    }

}
