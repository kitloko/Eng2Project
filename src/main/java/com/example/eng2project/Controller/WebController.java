package com.example.eng2project.Controller;

import com.example.eng2project.Entity.Horarios;
import com.example.eng2project.Entity.ItinerarioEntity;
import com.example.eng2project.Entity.UserItinerarioEntity;
import com.example.eng2project.Repository.HorariosRepository;
import com.example.eng2project.Repository.ItinerarioRepository;
import com.example.eng2project.Repository.UserItinerarioRepository;
import com.example.eng2project.User.UserEntity;
import com.example.eng2project.User.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    ItinerarioRepository itinerarioRepository;

    @Autowired
    HorariosRepository horariosRepository;

    @Autowired
    UserItinerarioRepository userItinerarioRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public String index(Model model, Authentication authentication) {
        UserEntity user = userRepository.findByEmail(authentication.getName());
        ItinerarioEntity itinerario = itinerarioRepository.findById(1L).get();
        List<UserItinerarioEntity> ListUserItinerarioNobuji = userItinerarioRepository.findByUserAndItinerarioOrderByHorarios(user, itinerario).orElse(new ArrayList<>());

        List<ItinerarioEntity> itinerarioListNobuji = new ArrayList<>();
        if (!ListUserItinerarioNobuji.isEmpty()) {
            List<Horarios> listHorarios = new ArrayList<>();
            ListUserItinerarioNobuji.forEach(c -> {
                listHorarios.add(horariosRepository.findById(c.getHorarios().getId()).get());
            });
            itinerario.setHorarios(listHorarios);
            itinerarioListNobuji.add(itinerario);
        }

        ItinerarioEntity itinerario2 = itinerarioRepository.findById(2L).get();
        List<UserItinerarioEntity> ListUserItinerarioRedentor = userItinerarioRepository.findByUserAndItinerarioOrderByHorarios(user, itinerario2).orElse(new ArrayList<>());

        List<ItinerarioEntity> itinerarioListRedentor = new ArrayList<>();
        if (!ListUserItinerarioRedentor.isEmpty()) {
            List<Horarios> listHorarios = new ArrayList<>();
            ListUserItinerarioRedentor.forEach(c -> {
                listHorarios.add(horariosRepository.findById(c.getHorarios().getId()).get());
            });
            itinerario2.setHorarios(listHorarios);
            itinerarioListRedentor.add(itinerario2);
        }
        model.addAttribute("itinerarioNobuji", itinerarioListNobuji);
        model.addAttribute("itinerarioRedentor", itinerarioListRedentor);
        model.addAttribute("itinerario", itinerarioRepository.findAll());
        return "index.html";
    }

    @PostMapping("/filtrarItinerario")
    public String filtrarItinerario(Authentication authentication, Model model, @RequestParam("idItinerario") String idItinerario) {
        UserEntity user = userRepository.findByEmail(authentication.getName());
        model.addAttribute("itinerario", itinerarioRepository.findAll());
        model.addAttribute("itinerarioEscolhido", itinerarioRepository.findById(Long.valueOf(idItinerario)).get());
        ItinerarioEntity itinerario = new ItinerarioEntity();
        itinerario.setId(Long.parseLong(idItinerario));

        List<UserItinerarioEntity> ListUserItinerarioRedentor = userItinerarioRepository.findByUserAndItinerarioOrderByHorarios(user, itinerario).orElse(new ArrayList<>());
        List<String> horarios1 = new ArrayList<>();
        ListUserItinerarioRedentor.forEach(c -> {
            horarios1.add(c.getHorarios().getHorario());
        });
        List<Horarios> horarios = horariosRepository.findByItinerarioOrderById(itinerario);
        List<Horarios> novaLista = horarios.stream()
                .filter(element -> !horarios1.contains(element.getHorario()))
                .collect(Collectors.toList());
        model.addAttribute("horarios", novaLista);
        return "index.html";
    }

    @PostMapping("/gravaItinerario")
    public ModelAndView gravaItinerario(Authentication authentication, Model model, @RequestParam("nomeItinerario") String nomeItinerario, @RequestParam("idHorario") String idHorario) {
        UserEntity user = userRepository.findByEmail(authentication.getName());
        Horarios horarios = horariosRepository.findById(Long.valueOf(idHorario)).get();
        ItinerarioEntity itinerario = itinerarioRepository.findByNome(nomeItinerario);
        horarios.setPessoas(horarios.getPessoas() + 1);
        horariosRepository.save(horarios);
        UserItinerarioEntity userItinerario = new UserItinerarioEntity();
        userItinerario.setUser(user);
        userItinerario.setItinerario(itinerario);
        userItinerario.setHorarios(horarios);
        userItinerarioRepository.save(userItinerario);
        return new ModelAndView("redirect:/");
    }

    @PostMapping(path = "/deleteItinerario", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView deleteItinerario(Authentication authentication, Model model, @RequestParam("horario") String horarioId, @RequestParam("itinerario") String itinerarioId) {
        UserEntity user = userRepository.findByEmail(authentication.getName());
        Horarios horarios = horariosRepository.findById(Long.valueOf(horarioId)).get();
        ItinerarioEntity itinerario = itinerarioRepository.findById(Long.valueOf(itinerarioId)).get();
        UserItinerarioEntity userItinerario = userItinerarioRepository.findByUserAndItinerarioAndHorarios(user,itinerario,horarios).get();
        userItinerarioRepository.delete(userItinerario);
        horarios.setPessoas(horarios.getPessoas() - 1);
        horariosRepository.save(horarios);
        return new ModelAndView("redirect:/");
    }
}
