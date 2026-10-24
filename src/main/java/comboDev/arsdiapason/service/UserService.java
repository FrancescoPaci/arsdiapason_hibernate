package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.DatiUtente;
import comboDev.arsdiapason.model.Psicologo;
import comboDev.arsdiapason.model.Utente;
import comboDev.arsdiapason.repository.PsicologoRepository;
import comboDev.arsdiapason.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PsicologoRepository psicologoRepository;
    @Autowired
    private MailService mailService;

    @Transactional(readOnly = true)
    public Utente login(String email, String password) throws Exception {
        Optional<Utente> utenteOpt = utenteRepository.findById(email);

        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (BCrypt.checkpw(password, utente.getPassword())) {
                if (Boolean.FALSE.equals(utente.getActive())) {
                    throw new Exception("", new Throwable("Utente non attivo, controllare la mail per completare la registrazione"));
                }
                return utente;
            } else {
                throw new Exception("", new Throwable("Password non corretta"));
            }
        } else {
            throw new Exception("", new Throwable("Utente non trovato"));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createAccount(DatiUtente datiUtente, String regexPassword) throws Exception {
        Pattern pattern = Pattern.compile(regexPassword);
        Matcher matcher = pattern.matcher(datiUtente.getPassword());
        int temporaryCode = ThreadLocalRandom.current().nextInt(100000, 1000000);

        if (matcher.find()) {
            // 1. Crea e salva lo psicologo (JPA popolerà automaticamente l'ID autoincrementante)
            Psicologo psicologo = new Psicologo();
            psicologo.setNome(datiUtente.getNome());
            psicologo.setCognome(datiUtente.getCognome());
            psicologo = psicologoRepository.save(psicologo);

            // 2. Crea e salva l'utente collegato
            Utente utente = new Utente();
            utente.setTemporaryCode(temporaryCode);
            utente.setIdPsicologo(psicologo.getId());
            utente.setEmail(datiUtente.getEmail());
            utente.setPassword(BCrypt.hashpw(datiUtente.getPassword(), BCrypt.gensalt()));
            utente.setRole("PSICOLOGO");
            utente.setActive(true); // Questo per farlo funzionare anche senza mail

            utenteRepository.save(utente);
        } else {
            throw new Exception("", new Throwable("La password non rispetta i requisiti richiesti"));
        }
        //mailService.sendAuthenticationEmail(datiUtente, temporaryCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public Utente confirmAccount(DatiUtente confirmAccount) throws Exception {
        Optional<Utente> utenteOpt = utenteRepository.findById(confirmAccount.getEmail());

        if (utenteOpt.isEmpty()) {
            throw new Exception("", new Throwable("Utente non trovato"));
        }

        Utente utente = utenteOpt.get();
        if (!BCrypt.checkpw(confirmAccount.getPassword(), utente.getPassword())
                || confirmAccount.getTemporaryCode() == null
                || !confirmAccount.getTemporaryCode().equals(utente.getTemporaryCode())) {
            throw new Exception("", new Throwable("Dati non corretti"));
        }

        utente.setActive(true);
        utente.setTemporaryCode(null);
        return utenteRepository.save(utente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendResetPasswordEmail(DatiUtente datiUtente) throws Exception {
        Optional<Utente> utenteOpt = utenteRepository.findById(datiUtente.getEmail());

        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            if (Boolean.FALSE.equals(utente.getActive())) {
                throw new Exception("", new Throwable("Utente non attivo, controllare la mail per attivare l'utenza"));
            }
            int temporaryCode = ThreadLocalRandom.current().nextInt(100000, 1000000);
            utente.setTemporaryCode(temporaryCode);
            utenteRepository.save(utente);
            mailService.sendResetPasswordEmail(datiUtente, temporaryCode);
        } else {
            throw new Exception("", new Throwable("Mail non presente a sistema, è necessario registrarsi"));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(DatiUtente datiUtente) throws Exception {
        Optional<Utente> utenteOpt = utenteRepository.findById(datiUtente.getEmail());

        if (utenteOpt.isEmpty() || datiUtente.getTemporaryCode() == null
                || !datiUtente.getTemporaryCode().equals(utenteOpt.get().getTemporaryCode())) {
            throw new Exception("", new Throwable("Dati non corretti"));
        }

        Utente utente = utenteOpt.get();
        utente.setActive(true);
        utente.setPassword(BCrypt.hashpw(datiUtente.getPassword(), BCrypt.gensalt()));
        utente.setTemporaryCode(null);
        utenteRepository.save(utente);
    }
}