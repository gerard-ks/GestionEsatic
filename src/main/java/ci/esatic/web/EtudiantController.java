package ci.esatic.web;

import ci.esatic.entities.*;
import ci.esatic.repository.*;
import ci.esatic.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EtudiantController {

    private EtudiantRepository  etudiantRepository;
    private InscriptionRepository inscriptionRepository;
    private AccountService accountService;
    private UeRepository ueRepository;
    private EcueRepository ecueRepository;
    private EnseignantEcueAnneeRepository enseignantEcueAnneeRepository;

    public EtudiantController(EtudiantRepository etudiantRepository, InscriptionRepository inscriptionRepository, AccountService accountService, NiveauRepository niveauRepository, UeRepository ueRepository, EcueRepository ecueRepository, EnseignantEcueAnneeRepository enseignantEcueAnneeRepository) {
        this.etudiantRepository = etudiantRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.accountService = accountService;
        this.ueRepository = ueRepository;
        this.ecueRepository = ecueRepository;
        this.enseignantEcueAnneeRepository = enseignantEcueAnneeRepository;
    }

    @GetMapping("/etudiant")
    public String dashboard()
    {
        return "dashboard-etudiant";
    }

    @GetMapping("/etudiant/profil")
    public String profil(@RequestParam("username") String username, Model model)
    {
        AppUser user = accountService.loaduserByUsername(username);
       Etudiant etudiant = etudiantRepository.findByAppUser(user);

        //Inscription i = inscriptionRepository.findFirstByEtudiant(etudiant);

        //model.addAttribute("inscription", i);
        //
        model.addAttribute("etudiant", etudiant);

        return "cours-student";
    }

    @GetMapping("/etudiant/voirCours")
    public String voirCours(@RequestParam("niveau") Long id, Model model)
    {
        List<Ue> list = ueRepository.findByNiveau_Id(id);

        model.addAttribute("listedesue", list);
        return "consulter-cours";
    }

    @GetMapping("/etudiant/voirMatiere")
    public String voirMatiere(@RequestParam("ue") Long id, Model model)
    {
        System.out.println("ue_id " + id);
        List<Ecue> listecue = ecueRepository.findByUe_Id(id);
        model.addAttribute("listeecue", listecue);
        return "consulter-matiere";
    }

    @GetMapping("/enseignant/voirEnseignant")
    public String voirEnseignant(@RequestParam("ecue") Long id)
    {
        System.out.println("ecue_id = " + id);
        //EnseignantEcueAnnee enseignantEcueAnnee = enseignantEcueAnneeRepository.findByEcue_Id(id);
        //System.out.println(enseignantEcueAnnee.getEnseignant().getNom());
        //model.addAttribute("enseignantEcueAnnee", enseignantEcueAnnee);
        return "consulter-enseignant";
    }
}
