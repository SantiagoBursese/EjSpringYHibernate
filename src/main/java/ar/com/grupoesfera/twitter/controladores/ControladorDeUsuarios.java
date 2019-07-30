package ar.com.grupoesfera.twitter.controladores;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.grupoesfera.twitter.modelo.Usuario;
import ar.com.grupoesfera.twitter.servicios.TweetService;

@Controller
public class ControladorDeUsuarios {
	
	@Inject
	public TweetService tweetService;
	
	@RequestMapping("/administracion/usuarios")
	public ModelAndView mostrarUsuarios() {
		
		ModelAndView modelAndView = new ModelAndView("administracion/usuarios");
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = tweetService.buscarTodosLosUsuarios();
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	@RequestMapping("/administracion/usuario/{id}")
	public ModelAndView obtenerUsuarioPorId(@PathVariable Long id, @RequestParam ("accion") String accion) {
		ModelAndView modelAndView = new ModelAndView();
		if("EDITAR".equals(accion)) {
			Usuario usuario = tweetService.buscarPor(id);
			modelAndView.setViewName("administracion/detalleUsuario");
			modelAndView.addObject("usuario", usuario);
			
		}
		else if("BORRAR".equals(accion)) {
			Usuario usuario = tweetService.buscarPor(id);
			tweetService.borrarUsuario(usuario);
			modelAndView.setViewName("redirect:/administracion/usuarios");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "administracion/usuario/actualizar")
	public ModelAndView actualizar(@ModelAttribute ("usuario")Usuario usuario) {
		Usuario usuario2 = tweetService.buscarPor(usuario.getId());
		usuario2.setEmail(usuario.getEmail());
		usuario2.setRol(usuario.getRol());
		tweetService.modificarUsuario(usuario2);
		return new ModelAndView("redirect:/administracion/usuarios");
		
	}
	
	@RequestMapping("administracion/usuario/nuevo")
	public ModelAndView nuevo() {
		ModelAndView model = new ModelAndView("administracion/usuarioNuevo");
		model.addObject("usuario", new Usuario());
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "administracion/usuario/nuevo")
	public ModelAndView crear(@ModelAttribute ("usuario") Usuario usuario) {
		tweetService.crearUsuario(usuario);
		return new ModelAndView("redirect:/administracion/usuarios");
	}
	
}