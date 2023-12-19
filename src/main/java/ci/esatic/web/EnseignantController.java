package ci.esatic.web;

import ci.esatic.entities.*;
import ci.esatic.repository.AffectationRepository;
import ci.esatic.repository.EnseignantRepository;
import ci.esatic.repository.InscriptionRepository;
import ci.esatic.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EnseignantController {

    private AccountService accountService;
    private EnseignantRepository enseignantRepository;
    private AffectationRepository affectationRepository;

    private InscriptionRepository inscriptionRepository;

    public EnseignantController(AccountService accountService, EnseignantRepository enseignantRepository, AffectationRepository affectationRepository, InscriptionRepository inscriptionRepository) {
        this.accountService = accountService;
        this.enseignantRepository = enseignantRepository;
        this.affectationRepository = affectationRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    @GetMapping("/enseignant")
    public String dashboard(Model model)
    {
        return "dashboard-enseignant";
    }

    @GetMapping("/enseignant/classe")
    public String course(@RequestParam("username") String username, Model model)
    {
        AppUser user = accountService.loaduserByUsername(username);
        Enseignant enseignant = enseignantRepository.findByAppUser(user);

        model.addAttribute("enseignant", enseignant);

        return "course";
    }

    @GetMapping("/enseignant/voirClasse")
    public String voirClasse(@RequestParam("en_id") Long id, Model model)
    {
        List<Long> c = new ArrayList<>();
        List<Affecte> a = affectationRepository.findByEnseignant_Id(id);
        a.forEach(affecte -> {
            c.add(affecte.getClasse().getId());
        });
        //model.addAttribute("classe", c);
        model.addAttribute("affecte", a);
        return "consulter-enseignant";
    }

    @GetMapping("/enseignant/list")
    public String list(@RequestParam("classe_id") Long id, Model model)
    {
        List<Inscription> i = inscriptionRepository.findByClasse_Id(id);
       /* i.forEach(inscription -> {
            System.out.println(inscription.getEtudiant().getNom());
        });*/
        model.addAttribute("inscription", i);
        return "classroom";
    }

}
